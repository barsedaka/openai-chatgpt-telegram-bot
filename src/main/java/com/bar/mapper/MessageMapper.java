package com.bar.mapper;

import com.bar.dto.TelegramChatMessageDTO;
import com.bar.entity.MessageEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {
    public MessageEntity mapToEntity(@NotNull TelegramChatMessageDTO telegramChatMessageDTO) {
        return new MessageEntity(
                telegramChatMessageDTO.getUpdateId(),
                telegramChatMessageDTO.getChatId(),
                telegramChatMessageDTO.getMessage(),
                telegramChatMessageDTO.getUserId(),
                telegramChatMessageDTO.getRole());
    }
}
