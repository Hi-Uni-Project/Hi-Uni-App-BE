package com.qoormthon.todool.domain.user.dto;


import com.qoormthon.todool.domain.user.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String stdNo; //학번 pk
    private String nickName; //닉네임
    private String major; //학과
    private String gender; //male, female
    private String mbti; //mbti

    @Schema(hidden = true)
    private String imageUrl;

    public UserEntity toEntity() {
        UserEntity userEntity = new UserEntity();
        return UserEntity.builder()
                .stdNo(this.stdNo)
                .nickName(this.nickName)
                .major(this.major)
                .gender(this.gender)
                .mbti(this.mbti)
                .imageUrl(this.imageUrl)
                .build();
    }
}
