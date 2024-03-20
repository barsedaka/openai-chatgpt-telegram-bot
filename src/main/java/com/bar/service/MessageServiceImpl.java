package com.bar.service;

import com.bar.dto.ConversationDTO;
import com.bar.dto.TelegramChatMessageDTO;
import com.bar.dto.chatGPT.ChatGPTMessageDTO;
import com.bar.entity.MessageEntity;
import com.bar.mapper.ChatGPTChatMessageDTOMapper;
import com.bar.mapper.MessageMapper;
import com.bar.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{

    private final MessageRepository messageRepository;

    private final MessageMapper messageMapper;

    private final ChatGPTChatMessageDTOMapper chatGptChatMessageDTOMapper;

    @Override
    public void addMessage(TelegramChatMessageDTO telegramChatMessageDTO) {
        MessageEntity messageEntity = messageMapper.mapToEntity(telegramChatMessageDTO);
        messageRepository.save(messageEntity);

        log.info("---------------ChatMessage added successfully---------------");
        log.debug("---------------ChatMessage added: " + telegramChatMessageDTO + "---------------");
    }

    @Override
    public ConversationDTO getFullConversationMessagesByChatId(Long chatId) {
        ArrayList<ChatGPTMessageDTO> conversationMessages = getConversationMessagesFromRepository(chatId);

        log.info("---------------Conversation returned successfully---------------");
        log.debug("---------------Conversation returned: " + conversationMessages + "---------------");

        return ConversationDTO.builder()
                .conversation(conversationMessages)
                .build();
    }

    private ArrayList<ChatGPTMessageDTO> getConversationMessagesFromRepository(Long chatId) {
        return (ArrayList<ChatGPTMessageDTO>)messageRepository
                .findByChatId(chatId)
                .stream()
                .map(entity -> chatGptChatMessageDTOMapper
                        .mapToOpenAIChatMessageDTO(entity.getRole(), entity.getMessage()))
                .collect(Collectors.toList());
    }
}
