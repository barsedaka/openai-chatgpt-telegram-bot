package com.bar.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TelegramChatMessageDTO {

    private final long updateId;
    private final long chatId;
    private final String message;
    private final long userId;
    private final String role;
}
