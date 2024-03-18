package com.bar.service;

import com.bar.dto.TelegramChatResponseDTO;
import com.bar.dto.chatGPT.ChatGPTMessageDTO;
import com.bar.dto.ConversationDTO;
import com.bar.dto.TelegramChatMessageDTO;
import com.bar.enums.Roles;
import com.bar.mapper.TelegramChatResponseDTOMapper;
import lombok.extern.log4j.Log4j2;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Log4j2
public class TelegramRequestService {

    private final MessageService messageService;
    private final ChatGPTRequestService chatGPTRequestService;
    private final TelegramChatResponseDTOMapper telegramChatResponseDTOMapper;

    public TelegramRequestService(MessageService messageService,
                                  ChatGPTRequestService chatGPTRequestService,
                                  TelegramChatResponseDTOMapper telegramChatResponseDTOMapper) {
        this.messageService = messageService;
        this.chatGPTRequestService = chatGPTRequestService;
        this.telegramChatResponseDTOMapper = telegramChatResponseDTOMapper;
    }

    public TelegramChatResponseDTO handleTelegramRequest(TelegramChatMessageDTO requestTelegramChatMessageDTO) throws IOException, JSONException, InterruptedException {
        ConversationDTO conversationDTO = messageService.addMessageAndGetFullConversation(requestTelegramChatMessageDTO);
        ChatGPTMessageDTO chatGPTMessageDTO = chatGPTRequestService.createAnswer(conversationDTO);

        log.info("----------AI_Answer : " + chatGPTMessageDTO + "----------");

        addChatGPTAnswerToConversation(requestTelegramChatMessageDTO, chatGPTMessageDTO);

        return telegramChatResponseDTOMapper.mapToTelegramChatResponseDTO(
                requestTelegramChatMessageDTO.getChatId(),
                chatGPTMessageDTO.getContent());
    }

    private void addChatGPTAnswerToConversation(TelegramChatMessageDTO requestTelegramChatMessageDTO,
                                                ChatGPTMessageDTO chatGPTMessageDTO) {
        messageService.addMessage(
                TelegramChatMessageDTO.builder()
                        .updateId(requestTelegramChatMessageDTO.getUpdateId())
                        .chatId(requestTelegramChatMessageDTO.getChatId())
                        .message(chatGPTMessageDTO.getContent())
                        .userId(requestTelegramChatMessageDTO.getUserId())
                        .role(Roles.ASSISTANCE.toString())
                        .build());
    }
}
