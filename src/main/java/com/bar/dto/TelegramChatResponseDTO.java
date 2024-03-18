package com.bar.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TelegramChatResponseDTO {

    private final Long chatId;

    private final String message;
}
