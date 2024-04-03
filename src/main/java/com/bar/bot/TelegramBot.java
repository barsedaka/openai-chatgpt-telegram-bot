package com.bar.bot;

import com.bar.dto.TelegramChatMessageDTO;
import com.bar.dto.TelegramChatResponseDTO;
import com.bar.mapper.TelegramChatMessageDTOMapper;
import com.bar.service.TelegramRequestHandler;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.bar.constants.Constants.*;

@Component
@RequiredArgsConstructor
@Log4j2
public class TelegramBot extends TelegramLongPollingBot {
    private final TelegramBotsApi telegramBotsApi;
    private final TelegramChatMessageDTOMapper telegramChatMessageDTOMapper;
    private final TelegramRequestHandler telegramRequestHandler;

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.getMessage() != null && update.getMessage().hasText()) {
                TelegramChatMessageDTO requestTelegramChatMessageDTO = telegramChatMessageDTOMapper.updateToDTO(update);
                TelegramChatResponseDTO response = telegramRequestHandler
                        .handleTelegramRequest(requestTelegramChatMessageDTO);
                sendMessage(response.getChatId(), response.getMessage());
            }
            else{
                sendMessage(update.getMessage().getChatId(), "no message!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessage(Long chatId, String message) throws Exception{
        execute(new SendMessage(chatId.toString(), message));
    }

    @PostConstruct
    public void registerBot() {
        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException ex) {
            throw new RuntimeException("Error registering Telegram bot", ex);
        }
    }

    @Override
    public String getBotUsername() {
        return TELEGRAM_BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return TELEGRAM_BOT_TOKEN;
    }
}
