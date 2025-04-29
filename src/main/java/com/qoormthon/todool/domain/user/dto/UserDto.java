package com.qoormthon.todool.domain.user.dto;


import com.qoormthon.todool.domain.user.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

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
    private String major; //학과

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
                .major(this.major)
                .gender(this.gender)
                .mbti(this.mbti)
                .imageUrl(this.imageUrl)
                .build();
    }
}
