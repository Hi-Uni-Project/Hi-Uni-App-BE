package com.project.hiuni.global.security.core;

import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.exception.CustomUserNotFoundException;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  /**
   * 사용자 인증 객체를 생성하는 메서드입니다.
   *
   * @param SocialEmail 사용자 식별자인 소셜 이메일 값이 들어갑니다.
   * @return CustomUserDetails 객체
   * @throws UsernameNotFoundException 사용자를 찾을 수 없는 경우
   */
  @Override
  public UserDetails loadUserByUsername(String SocialEmail) throws CustomUserNotFoundException {
    User user = userRepository.findBySocialEmail(SocialEmail).orElseThrow(
        () -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));
    return new CustomUserDetails(user);
  }


  /**
   * 사용자 ID로 사용자 인증 객체를 생성하는 메서드입니다.
   *
   * @param id 사용자 ID
   * @return CustomUserDetails 객체
   * @throws CustomUserNotFoundException 사용자를 찾을 수 없는 경우
   */
  public UserDetails loadUserById(Long id) throws CustomUserNotFoundException {
    User user = userRepository.findById(id).orElseThrow(
        () -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));
    return new CustomUserDetails(user);
  }
}