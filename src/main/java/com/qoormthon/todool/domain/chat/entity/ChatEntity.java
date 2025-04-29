package com.qoormthon.todool.domain.chat.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "채팅기록")
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    @Column(length = 255)
    private String senderUserId;

    @Column(length = 255)
    private String receiverUserId;

    @Column(length = 1000)
    private String message;

    @Column(length = 255)
    private String matchingId;

    @Column(length = 255)
    private String date;

}
