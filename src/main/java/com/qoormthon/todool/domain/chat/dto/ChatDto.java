package com.qoormthon.todool.domain.chat.dto;

import com.qoormthon.todool.domain.chat.entity.ChatEntity;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatDto {
    private String senderStdNo;
    private String receiverStdNo;
    private String message;

    @Schema(hidden = true)
    private String matchingId;

    @Schema(hidden = true)
    private String date;

    public ChatEntity toEntity() {
        ChatEntity chatEntity = new ChatEntity();
        return chatEntity.builder()
                .senderStdNo(this.senderStdNo)
                .receiverStdNo(this.receiverStdNo)
                .message(this.message)
                .matchingId(this.matchingId)
                .date(this.date)
                .build();
    }
}
