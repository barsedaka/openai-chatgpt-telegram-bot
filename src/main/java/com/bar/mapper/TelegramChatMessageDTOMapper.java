package com.bar.mapper;

import com.bar.dto.TelegramChatMessageDTO;
import com.bar.enums.Roles;
import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramChatMessageDTOMapper {

    public TelegramChatMessageDTO updateToDTO(Update update) {
        return TelegramChatMessageDTO.builder()
                .updateId(update.getUpdateId().longValue())
                .chatId(update.getMessage().getChatId())
                .message(update.getMessage().getText())
                .userId(update.getMessage().getFrom().getId().longValue())
                .role(Roles.USER.toString())
                .build();
    }
}
