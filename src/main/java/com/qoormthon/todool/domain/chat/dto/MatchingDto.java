package com.qoormthon.todool.domain.chat.dto;

import com.qoormthon.todool.domain.user.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchingDto {
    private UserDto firstUser;
    private UserDto secondUser;
    private String matchingId;
}
