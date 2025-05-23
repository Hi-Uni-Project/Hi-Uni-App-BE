package com.qoormthon.todool.domain.user.adapter.dto.request;


import com.qoormthon.todool.domain.user.domain.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpUserRequestDto {

    @Schema(example = "tempid")
    private String userId; //아이디 pk

    @Schema(example = "Temp1234!")
    private String password; //비밀번호

    @Schema(example = "2025")
    private String admissionYear; //입학연도

    @Schema(example = "익명이")
    private String nickName; //닉네임

    @Schema(example = "서울대학교")
    private String univName; //대학교명

    @Schema(example = "20250101@snu.ac.kr")
    private String univMail; //대학교 이메일

    @Schema(example = "컴퓨터공학과")
    private String firstMajor; //주전공

    @Schema(example = "Ai로봇학과")
    private String secondMajor; //부전공

    @Schema(example = "intj")
    private String mbti; //mbti
}
