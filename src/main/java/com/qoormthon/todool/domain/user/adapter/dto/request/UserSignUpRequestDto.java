package com.qoormthon.todool.domain.user.adapter.dto.request;


import com.qoormthon.todool.domain.user.domain.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpRequestDto {

    @Schema(example = "tempId")
    private String userId; //아이디 pk

    @Schema(example = "20251444")
    private String stdNo; //학번 pk

    @Schema(example = "your_password")
    private String password; //비밀번호

    @Schema(example = "홍길동")
    private String nickName; //닉네임

    @Schema(example = "서울대학교")
    private String univ;

    @Schema(example = "컴퓨터공학과")
    private String firstMajor;

    @Schema(example = "소프트웨어학과")
    private String secondMajor;

    @Schema(example = "male")
    private String gender; //male, female

    @Schema(example = "intj")
    private String mbti; //mbti

    @Schema(hidden = true)
    private String imageUrl;

    public UserEntity toEntity() {
        UserEntity userEntity = new UserEntity();
        return UserEntity.builder()
                .userId(this.userId)
                .stdNo(this.stdNo)
                .password(this.password)
                .nickName(this.nickName)
                .univ(this.univ)
                .firstMajor(this.firstMajor)
                .secondMajor(this.secondMajor)
                .mbti(this.mbti)
                .imageUrl(this.imageUrl)
                .build();
    }
}
