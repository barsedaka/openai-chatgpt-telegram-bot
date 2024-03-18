package com.bar.dto.chatGPT;

import lombok.Data;

@Data
public class ChoiceDTO {
    private ChatGPTMessageDTO message;
    private Integer index;
    private Integer logprobs;
    private String finish_reason;
}
