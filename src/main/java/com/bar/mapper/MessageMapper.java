package com.bar.mapper;

import com.bar.dto.TelegramChatMessageDTO;
import com.bar.entity.AppUserEntity;
import com.bar.entity.MessageEntity;
import com.bar.entity.UserEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {
    public MessageEntity mapToEntity(@NotNull TelegramChatMessageDTO telegramChatMessageDTO, AppUserEntity user) {
        return new MessageEntity(
                telegramChatMessageDTO.getUpdateId(),
                telegramChatMessageDTO.getChatId(),
                telegramChatMessageDTO.getMessage(),
                user,
                telegramChatMessageDTO.getRole());
    }
}
