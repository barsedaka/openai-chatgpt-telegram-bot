package com.bar.mapper;

import com.bar.dto.TelegramChatMessageDTO;
import com.bar.enums.Roles;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Update;

@Component
public class TelegramChatMessageDTOMapper {

    public TelegramChatMessageDTO updateToDTO(Update update) {
        return TelegramChatMessageDTO.builder()
                .updateId(update.getUpdateId())
                .chatId(update.getMessage().getChatId())
                .message(update.getMessage().getText())
                .userId(update.getMessage().getFrom().getId())
                .role(Roles.USER.toString())
                .build();
    }
}
