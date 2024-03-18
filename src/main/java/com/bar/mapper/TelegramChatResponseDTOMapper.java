package com.bar.mapper;

import com.bar.dto.TelegramChatResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TelegramChatResponseDTOMapper {

    public TelegramChatResponseDTO mapToTelegramChatResponseDTO(Long chatId, String message) {
        return TelegramChatResponseDTO.builder()
                .chatId(chatId)
                .message(message)
                .build();
    }
}
