package com.project.hiuni.global.security.core;

import com.project.hiuni.domain.user.entity.UserEntity;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 커스텀 사용자 인증 정보를 담는 클래스입니다.
 */
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

  private final UserEntity userEntity;


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singleton(new Simple)
  }
}
