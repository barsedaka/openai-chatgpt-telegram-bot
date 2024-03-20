package com.bar.service;

import com.bar.dto.ConversationDTO;
import com.bar.dto.chatGPT.ChatGPTMessageDTO;

import java.io.IOException;

public interface ChatGPTRequestService {
    ChatGPTMessageDTO createAnswer(ConversationDTO conversationDTO) throws IOException, InterruptedException;
    String sendChatGPTRequest(String body) throws InterruptedException, IOException;
}
