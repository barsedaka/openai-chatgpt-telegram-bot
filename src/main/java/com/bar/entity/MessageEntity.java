package com.bar.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "MessageEntity")
@Table(name = "messages")
public class MessageEntity {

    @Id
    @SequenceGenerator(
            name = "message_sequence",
            sequenceName = "message_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "message_sequence"
    )
    @Column(
            name = "message_id",
            updatable = false,
            nullable = false
    )
    private Long messageId;

    @Column(
            name = "update_id",
            updatable = false,
            nullable = false
    )
    private Long updateId;

    @Column(
            name = "chat_id",
            nullable = false
    )
    private Long chatId;

    @Column(
            name = "message",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String message;

    @Column(
            name = "user_id",
            nullable = false
    )
    private Long userId;

    @Column(
            name = "role",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",
            referencedColumnName = "user_id",
            insertable = false,
            updatable = false)
    private UserEntity user;

    public MessageEntity(Long updateId,Long chatId, String message, Long userId, String role) {
        this.updateId = updateId;
        this.chatId = chatId;
        this.message = message;
        this.userId = userId;
        this.role = role;
    }

    @Override
    public String toString() {
        return "ChatMessageEntity{" +
                "updateId=" + updateId +
                ", chatId=" + chatId +
                ", message='" + message + '\'' +
                ", userId=" + userId +
                ", appRole='" + role + '\'' +
                '}';
    }
}
