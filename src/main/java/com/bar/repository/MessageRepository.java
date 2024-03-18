package com.bar.repository;

import com.bar.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    ArrayList<MessageEntity> findByChatId(Long chatId);
    boolean existsMessageEntityByChatId(Long chatId);
}
