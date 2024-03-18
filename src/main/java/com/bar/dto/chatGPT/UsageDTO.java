package com.bar.dto.chatGPT;

public record UsageDTO (Integer prompt_tokens, Integer completion_tokens, Integer total_tokens){
}
