package com.bar.mapper;

import com.bar.dto.ConversationDTO;
import com.bar.dto.chatGPT.ChatGPTRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class ChatGPTRequestDTOMapper {

    public ChatGPTRequestDTO mapToDTO(String model,
                                      ConversationDTO conversationDTO,
                                      int maxTokens,
                                      double temperature) {
        return ChatGPTRequestDTO.builder()
                .model(model)
                .maxTokens(maxTokens)
                .temperature(temperature)
                .conversation(conversationDTO.getConversation())
                .build();
    }
}
