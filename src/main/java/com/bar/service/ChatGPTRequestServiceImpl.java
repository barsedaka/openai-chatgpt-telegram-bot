package com.bar.service;

import com.bar.constants.Constants;
import com.bar.dto.chatGPT.ChatGPTMessageDTO;
import com.bar.dto.chatGPT.ChatGPTRequestDTO;
import com.bar.dto.ConversationDTO;
import com.bar.dto.chatGPT.ChatGPTResponseDTO;
import com.bar.mapper.ChatGPTRequestDTOMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Log4j2
@RequiredArgsConstructor
public class ChatGPTRequestServiceImpl implements ChatGPTRequestService{

    private final ChatGPTRequestDTOMapper chatGptRequestDTOMapper;

    private final ObjectMapper objectMapper;

    @Value("${OPENAI_API_KEY}") String openAiKey;

    @Override
    public ChatGPTMessageDTO createAnswer(ConversationDTO conversationDTO) throws IOException, InterruptedException {
        ChatGPTRequestDTO chatGptRequestDTO = chatGptRequestDTOMapper.mapToDTO(
                Constants.CHAT_COMPLETION,
                conversationDTO,
                Constants.OPEN_AI_MAX_TOKENS,
                Constants.OPEN_AI_TEMPERATURE);

        log.info("----------AI_Request : " + chatGptRequestDTO + "----------");

        String body = objectMapper.writeValueAsString(chatGptRequestDTO);

        String response = sendChatGPTRequest(body);

        ChatGPTResponseDTO chatGPTResponseDTO = objectMapper.readValue(response, ChatGPTResponseDTO.class);
        ChatGPTMessageDTO chatGPTMessageDTO = chatGPTResponseDTO.getChoices().get(0).getMessage();

        log.info("----------AI_Response has sent successfully----------");
        log.debug("----------AI_Response : " + chatGPTResponseDTO + "----------");

        return chatGPTMessageDTO;
    }

    @Override
    public String sendChatGPTRequest(String body) throws InterruptedException, IOException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(Constants.CHAT_COMPLETION_URL))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, openAiKey)
                .POST(HttpRequest.BodyPublishers.ofString(body)).build();

        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
}
