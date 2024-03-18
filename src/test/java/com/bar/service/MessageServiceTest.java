package com.bar.service;

import com.bar.dto.TelegramChatMessageDTO;
import com.bar.entity.MessageEntity;
import com.bar.enums.Roles;
import com.bar.mapper.MessageMapper;
import com.bar.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @Mock private MessageRepository messageRepository;
    @Mock private MessageMapper messageMapper;
    @InjectMocks private MessageService underTest;
    @Captor ArgumentCaptor<MessageEntity> messageEntityCaptor;

    @Test
    void shouldAddMessage() {
        // given
        TelegramChatMessageDTO telegramChatMessageDTO = TelegramChatMessageDTO.builder()
                .updateId(1000L)
                .chatId(1L)
                .message("Hello")
                .userId(2L)
                .role(Roles.USER.toString())
                .build();

        MessageEntity messageEntity = messageMapper.mapToEntity(telegramChatMessageDTO);

        // when
        underTest.addMessage(telegramChatMessageDTO);

        // then
        verify(messageRepository)
                .save(messageEntityCaptor.capture());

        MessageEntity capturedMessage = messageEntityCaptor.getValue();

        assertThat(capturedMessage).isEqualTo(messageEntity);
    }

    @Test
    void shouldAddMessageAndGetFullConversation() {
        long chadId = 1;

        // given
        TelegramChatMessageDTO telegramChatMessageDTO = TelegramChatMessageDTO.builder()
                .updateId(1000L)
                .chatId(chadId)
                .message("Hello")
                .userId(2L)
                .role(Roles.USER.toString())
                .build();

        MessageEntity messageEntity = messageMapper.mapToEntity(telegramChatMessageDTO);

        // when
        underTest.addMessage(telegramChatMessageDTO);
        underTest.getConversationMessagesByChatId(1L);

        // then
        verify(messageRepository).save(messageEntityCaptor.capture());
        MessageEntity capturedMessage = messageEntityCaptor.getValue();
        assertThat(capturedMessage).isEqualTo(messageEntity);
        verify(messageRepository).findByChatId(chadId);
    }

    @Test
    void shouldGetConversationMessagesByChatId() {
        long chadId = 1;

        // when
        underTest.getConversationMessagesByChatId(chadId);

        // then
        verify(messageRepository).findByChatId(chadId);
    }
}