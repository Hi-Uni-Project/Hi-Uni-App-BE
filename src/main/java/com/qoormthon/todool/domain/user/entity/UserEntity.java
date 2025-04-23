package com.qoormthon.todool.domain.user.entity;

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
    private String stdNo; //학번 pk

    @Column(length = 255)
    private String nickName; //닉네임

    @Column(length = 255)
    private String major; //학과

    @Column(length = 255)
    private String gender; //male, female

    @Column(length = 10)
    private String mbti; //mbti

    @Column(length = 255)
    private String imageUrl;

}
