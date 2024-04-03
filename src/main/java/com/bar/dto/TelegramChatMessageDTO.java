package com.bar.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TelegramChatMessageDTO {

    private final Long updateId;
    private final Long chatId;
    private final String message;
    private final Long userId;
    private final String role;
}
