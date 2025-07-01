package com.project.hiuni.global.security.core;

import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String socialEmail) throws UsernameNotFoundException {
    User user = userRepository.findBySocialEmail(socialEmail).orElseThrow(
        () -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    return new CustomUserDetails(user);
  }
}
