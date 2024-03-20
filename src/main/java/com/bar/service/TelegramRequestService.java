package com.bar.service;

import com.bar.dto.TelegramChatMessageDTO;
import com.bar.dto.TelegramChatResponseDTO;
import com.bar.dto.chatGPT.ChatGPTMessageDTO;

import java.io.IOException;

public interface TelegramRequestService {
    TelegramChatResponseDTO handleTelegramRequest(TelegramChatMessageDTO requestTelegramChatMessageDTO) throws IOException, InterruptedException;
}