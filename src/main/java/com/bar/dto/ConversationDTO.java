package com.bar.dto;

import com.bar.dto.chatGPT.ChatGPTMessageDTO;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Builder
@Data
public class ConversationDTO {

    private ArrayList<ChatGPTMessageDTO> conversation;
}
