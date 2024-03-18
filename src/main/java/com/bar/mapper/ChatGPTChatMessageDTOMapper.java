package com.bar.mapper;

import com.bar.dto.chatGPT.ChatGPTMessageDTO;
import org.springframework.stereotype.Component;

@Component
public class ChatGPTChatMessageDTOMapper {

    public ChatGPTMessageDTO mapToOpenAIChatMessageDTO(String role, String content) {
        return new ChatGPTMessageDTO(role, content);
    }
}
