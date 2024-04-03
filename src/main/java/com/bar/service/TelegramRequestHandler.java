package com.bar.service;

import com.bar.dto.TelegramChatMessageDTO;
import com.bar.dto.TelegramChatResponseDTO;
import com.bar.dto.UserLoginResponseDTO;
import com.bar.mapper.TelegramChatResponseDTOMapper;
import com.bar.mapper.UserLoginRequestDTOMapper;
import com.bar.mapper.UserRegisterRequestDTOMapper;
import com.bar.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TelegramRequestHandler {
    private final TelegramChatResponseDTOMapper telegramChatResponseDTOMapper;
    private final AuthService authService;
    private final UserRegisterRequestDTOMapper userRegisterRequestDTOMapper;
    private final UserLoginRequestDTOMapper userLoginRequestDTOMapper;
    private final TelegramRequestService telegramRequestService;



    public TelegramChatResponseDTO handleTelegramRequest(TelegramChatMessageDTO requestTelegramChatMessageDTO) throws Exception {
        String message = requestTelegramChatMessageDTO.getMessage();
        Long chatId = requestTelegramChatMessageDTO.getChatId();

        if (message.equals("/start")) {
            return sendWelcomeMessage(chatId);
        } else if (message.startsWith("/register")) {
            return registerUser(chatId, message);
        } else if (message.startsWith("/login")) {
            return loginUser(chatId, message);
        } else {
            return handleChatState(requestTelegramChatMessageDTO);
        }
    }

    private TelegramChatResponseDTO telegramChatResponseDTO(Long chatId, String message) {
        return telegramChatResponseDTOMapper.mapToTelegramChatResponseDTO(
                chatId, message
        );
    }

    private TelegramChatResponseDTO sendWelcomeMessage(Long chatId) throws Exception {
        String welcomeMessage = "Welcome to our Telegram bot! Please use /register or /login and then your password.";
        return telegramChatResponseDTO(chatId, welcomeMessage);
    }

    private TelegramChatResponseDTO registerUser(Long chatId, String message) throws Exception {
        String password = message.substring("/register".length()).trim();
        authService.register(userRegisterRequestDTOMapper.mapToDTO(chatId.toString(), password));
        String registrationMessage = "You have successfully registered!";
        return telegramChatResponseDTO(chatId, registrationMessage);
    }

    private TelegramChatResponseDTO handleChatState(TelegramChatMessageDTO telegramChatMessageDTO) throws Exception {
        return telegramRequestService.handleTelegramRequest(telegramChatMessageDTO);
    }

    private TelegramChatResponseDTO loginUser(Long chatId, String message) throws Exception {
        String password = message.substring("/login".length()).trim();
        UserLoginResponseDTO userLoginResponseDTO = authService.authenticate(
                userLoginRequestDTOMapper.mapToDTO(chatId.toString(), password)
        );
        String loginMessage = "You are now logged in! token: " + userLoginResponseDTO.token();
        return telegramChatResponseDTO(chatId, loginMessage);
    }
}
