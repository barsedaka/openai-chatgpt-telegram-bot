//package com.bar.repository;
//
//import com.bar.entity.MessageEntity;
//import com.bar.enums.Roles;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@DataJpaTest
//class MessageRepositoryTests {
//
//    @Autowired
//    private MessageRepository underTest;
//
//    @AfterEach
//    void tearDown() {
//        underTest.deleteAll();
//    }
//
//    @Test
//    void itShouldCheckIfMessageExistsByChatId() {
//        // given
//        long chatId = 1;
//        MessageEntity message = new MessageEntity(
//                1000L,
//                chatId,
//                "Hello",
//                2L,
//                Roles.USER.toString()
//        );
//        underTest.save(message);
//
//        // when
//        boolean expected = underTest.existsMessageEntityByChatId(chatId);
//
//        // then
//        assertThat(expected).isTrue();
//    }
//
//    @Test
//    void itShouldCheckIfMessageNotExistsByChatId() {
//        // given
//        long chatId = 12345;
//
//        // when
//        boolean expected = underTest.existsMessageEntityByChatId(chatId);
//
//        // then
//        assertThat(expected).isFalse();
//    }
//
//    @Test
//    void itShouldReturnMessagesByChadId() {
//        // given
//        long chatId = 2;
//        List<MessageEntity> messageEntities = Arrays.asList(
//                new MessageEntity(2000L, chatId, "Hi", 3L, Roles.USER.toString()),
//                new MessageEntity(2001L, chatId, "Hello", 3L, Roles.USER.toString()),
//                new MessageEntity(2002L, 3L, "How are you?", 4L, Roles.USER.toString())
//        );
//        underTest.saveAll(messageEntities);
//
//        // when
//        ArrayList<MessageEntity> messages = underTest.findByChatId(chatId);
//
//        // then
//        assertThat(messages.size()).isEqualTo(2);
//    }
//
//    @Test
//    void itShouldNotReturnMessagesByChadId() {
//        // given
//        long chatId = 2;
//        long notExistsChatId = 10100;
//        List<MessageEntity> messageEntities = Arrays.asList(
//                new MessageEntity(2000L, chatId, "Hi", 3L, Roles.USER.toString()),
//                new MessageEntity(2001L, chatId, "Hello", 3L, Roles.USER.toString()),
//                new MessageEntity(2002L, 3L, "How are you?", 4L, Roles.USER.toString())
//        );
//        underTest.saveAll(messageEntities);
//
//        // when
//        ArrayList<MessageEntity> messages = underTest.findByChatId(notExistsChatId);
//
//        // then
//        assertThat(messages.size()).isEqualTo(0);
//    }
//}