package com.qoormthon.todool.domain.user.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "유저")
public class UserEntity {

    @Id
    @Column(length = 255)
    private String userId; //아이디 pk

    @Column(length = 255)
    private String stdNo; //학번

    @Column(length = 255)
    private String password; //비밀번호

    @Column(length = 255)
    private String nickName; //닉네임

    @Column(length = 255)
    private String univ;

    @Column(length = 255)
    private String firstMajor; //학과

    @Column(length = 255)
    private String secondMajor; //부전공

    @Column(length = 10)
    private String mbti; //mbti

    @Column(length = 255)
    private String imageUrl;

}
