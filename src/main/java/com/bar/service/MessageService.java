package com.bar.service;

import com.bar.dto.ConversationDTO;
import com.bar.dto.TelegramChatMessageDTO;

public interface MessageService {
    void addMessage(TelegramChatMessageDTO telegramChatMessageDTO);
    ConversationDTO getFullConversationMessagesByChatId(Long chatId);
}
