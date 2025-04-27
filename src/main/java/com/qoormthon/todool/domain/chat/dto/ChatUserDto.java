package com.qoormthon.todool.domain.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatUserDto {

    @Schema(example = "20251444")
    private String stdNo; //학번 pk

    @Schema(example = "홍길동")
    private String nickName; //닉네임

    @Schema(example = "서울대학교")
    private String univ;

    @Schema(example = "컴퓨터공학과")
    private String major; //학과

    @Schema(example = "male")
    private String gender; //male, female

    @Schema(example = "intj")
    private String mbti; //mbti
}
