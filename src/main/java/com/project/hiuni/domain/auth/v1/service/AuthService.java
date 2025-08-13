package com.project.hiuni.domain.auth.v1.service;

import com.project.hiuni.domain.auth.dto.request.AuthSocialRequest;
import com.project.hiuni.domain.auth.dto.response.AuthSocialResponse;
import com.project.hiuni.domain.auth.entity.SocialProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

  public AuthSocialResponse socialLogin(AuthSocialRequest authSocialRequest) {
    SocialProvider userProvider = authSocialRequest.getProvider();
    if(userProvider == SocialProvider.GOOGLE) {
      log.info("Google social login");


    }

  }

}
