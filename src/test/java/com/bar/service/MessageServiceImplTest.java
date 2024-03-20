package com.bar.service;

import com.bar.dto.ConversationDTO;
import com.bar.dto.TelegramChatMessageDTO;
import com.bar.dto.chatGPT.ChatGPTMessageDTO;
import com.bar.entity.MessageEntity;
import com.bar.entity.UserEntity;
import com.bar.enums.Roles;
import com.bar.mapper.ChatGPTChatMessageDTOMapper;
import com.bar.mapper.MessageMapper;
import com.bar.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {

    @Mock private MessageRepository messageRepository;
    @Mock private MessageMapper messageMapper;
    @Mock private ChatGPTChatMessageDTOMapper chatGptChatMessageDTOMapper;
    @InjectMocks private MessageServiceImpl underTest;
    @Captor ArgumentCaptor<MessageEntity> messageEntityCaptor;
    private TelegramChatMessageDTO telegramChatMessageDTO;
    private UserEntity userEntity;
    private MessageEntity messageEntity;
    private ArrayList<MessageEntity> conversationMessagesEntities;
    private ArrayList<ChatGPTMessageDTO> conversationMessages;
    private ConversationDTO expectedConversationDTO;

    @BeforeEach
    void setUp() {
        Long chatId = 1L;
        telegramChatMessageDTO = TelegramChatMessageDTO.builder()
                .updateId(1000L)
                .chatId(chatId)
                .message("Hello")
                .userId(1L)
                .role(Roles.USER.toString())
                .build();

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("Test");
        userEntity.setFirstName("Test");
        userEntity.setLastName("Test");
        userEntity.setEmail("Test@gmail.com");
        userEntity.setChatId(7L);

        messageEntity = new MessageEntity();
        messageEntity.setUpdateId(telegramChatMessageDTO.getUpdateId());
        messageEntity.setChatId(telegramChatMessageDTO.getChatId());
        messageEntity.setMessage(telegramChatMessageDTO.getMessage());
        messageEntity.setUserId(telegramChatMessageDTO.getUserId());
        messageEntity.setRole(telegramChatMessageDTO.getRole());
        messageEntity.setUser(userEntity);

        conversationMessagesEntities = new ArrayList<>();
        conversationMessagesEntities.add(messageEntity);

        conversationMessages = new ArrayList<>();
        conversationMessages.add(
                 chatGptChatMessageDTOMapper.mapToOpenAIChatMessageDTO(
                        telegramChatMessageDTO.getRole(),
                         telegramChatMessageDTO.getMessage()
        ));

        expectedConversationDTO = ConversationDTO.builder()
                .conversation(conversationMessages)
                .build();
    }

    @Test
    void shouldAddMessage() {
        when(messageMapper.mapToEntity(telegramChatMessageDTO)).thenReturn(messageEntity);

        underTest.addMessage(telegramChatMessageDTO);

        verify(messageRepository).save(messageEntityCaptor.capture());
        assertThat(messageEntityCaptor.getValue()).isEqualTo(messageEntity);
    }

    @Test
    void shouldGetFullConversationMessagesByChatId() {
        when(messageRepository.findByChatId(telegramChatMessageDTO.getChatId()))
                .thenReturn(conversationMessagesEntities);

        ConversationDTO conversationDTO = underTest.getFullConversationMessagesByChatId(telegramChatMessageDTO.getChatId());

        assertThat(conversationDTO).isEqualTo(expectedConversationDTO);
    }
}