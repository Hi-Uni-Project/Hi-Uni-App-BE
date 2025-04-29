package com.qoormthon.todool.global.common.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MessageRequestDto {
    private String senderUserId;
    private String receiverUserId;
    private String message;
    private String date;
}
