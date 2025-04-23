package com.qoormthon.todool.domain.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchingDto {
    private ChatUserDto firstUser;
    private ChatUserDto secondUser;
    private String matchingId;
}
