package com.project.hiuni.domain.auth.v1.service;


import com.project.hiuni.domain.auth.dto.OAuthUserInfo;
import com.project.hiuni.domain.auth.dto.request.AuthSignUpRequest;
import com.project.hiuni.domain.auth.dto.request.AuthSignUpRequest.Tos;
import com.project.hiuni.domain.auth.dto.request.AuthSignUpRequest.Univ;
import com.project.hiuni.domain.auth.dto.request.AuthSocialRequest;
import com.project.hiuni.domain.auth.dto.response.AuthSignUpResponse;
import com.project.hiuni.domain.auth.dto.response.AuthSocialResponse;
import com.project.hiuni.domain.auth.entity.Auth;
import com.project.hiuni.domain.auth.entity.SocialProvider;
import com.project.hiuni.domain.auth.exception.ProviderNotFoundException;
import com.project.hiuni.domain.auth.repository.AuthRepository;
import com.project.hiuni.domain.tos.repository.InPersonTosHistoryRepository;
import com.project.hiuni.domain.tos.repository.MarketingTosHistoryRepository;
import com.project.hiuni.domain.tos.repository.PersonalInfoTosHistoryRepository;
import com.project.hiuni.domain.tos.repository.ServiceImprovementTosHistoryRepository;
import com.project.hiuni.domain.tos.repository.ServiceTosHistoryRepository;
import com.project.hiuni.domain.tos.service.TosV1Service;
import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.exception.CustomUserNotFoundException;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.exception.ErrorCode;
import com.project.hiuni.global.exception.InternalServerException;
import com.project.hiuni.global.exception.NotFoundInfoException;
import com.project.hiuni.global.exception.TokenInvalidType;
import com.project.hiuni.global.security.jwt.JwtTokenProvider;
import com.project.hiuni.infra.google.GoogleApiClient;
import com.project.hiuni.infra.kakao.KakaoApiClient;
import com.project.hiuni.infra.naver.NaverApiClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

  private final JwtTokenProvider jwtTokenProvider;

  private final GoogleApiClient googleApiClient;
  private final KakaoApiClient kakaoApiClient;
  private final NaverApiClient naverApiClient;


  private final UserRepository userRepository;
  private final AuthRepository authRepository;

  private final TosV1Service tosV1Service;

  private final ServiceTosHistoryRepository serviceTosHistoryRepository;
  private final PersonalInfoTosHistoryRepository personalInfoTosHistoryRepository;
  private final MarketingTosHistoryRepository marketingTosHistoryRepository;
  private final ServiceImprovementTosHistoryRepository serviceImprovementTosHistoryRepository;
  private final InPersonTosHistoryRepository inPersonTosHistoryRepository;



  @Transactional
  public AuthSocialResponse socialLogin(AuthSocialRequest authSocialRequest) {
    SocialProvider userProvider = authSocialRequest.getProvider();

    // 소셜 플랫폼이 구글일 경우
    if (userProvider == SocialProvider.GOOGLE) {

      OAuthUserInfo userInfo = googleApiClient.getUserInfo(authSocialRequest.getAuthToken());

      String socialId = userInfo.getSocialId();
      String name = userInfo.getName();
      String socialEmail = userInfo.getEmail();

      if(socialEmail == null) {
        log.error("구글 OAuth2 로그인 시 이메일이 제공되지 않았습니다.");
        socialEmail = socialId + "@google.com";
      }

      // 이미 가입된 유저인 경우 로그인 처리
      if (userRepository.findBySocialId(socialId).isPresent()) {
        User user = userRepository.findBySocialId(socialId)
            .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        Auth auth = user.getAuth();

        // 기존 회원인데 auth 정보가 없는 경우
        if (auth == null) {
          log.error("로그인된 유저의 Auth 정보가 없습니다.");
          throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        String olderRefreshToken = auth.getRefreshToken();

        // 기존 회원인데 발급해놓은 리프레시 토큰이 유효하지 않은 경우 (보통은 만료된 경우) 토큰 재발급
        if (!jwtTokenProvider.isValidateToken(olderRefreshToken)) {
          String newRefreshToken = jwtTokenProvider.createRefreshToken(socialId, socialEmail);
          auth.updateRefreshToken(newRefreshToken);
        }

        String newRefreshToken = auth.getRefreshToken();
        String accessToken = jwtTokenProvider.createAccessToken(newRefreshToken);

        AuthSocialResponse.User userDataInfo;

        if(!isFinalSignUp(user)) {
          userDataInfo = null;
        } else {
          userDataInfo = AuthSocialResponse.User
              .builder()
              .tos(getTosAgreeInfo(user))
              .univ(
                  AuthSocialResponse.Univ
                      .builder()
                      .univName(user.getUnivName())
                      .firstMajorName(user.getFirstMajorName())
                      .secondMajorName(user.getSecondMajorName())
                      .univEmail(user.getUnivEmail())
                      .build()
              )
              .social(
                  AuthSocialResponse.Social
                      .builder()
                      .socialEmail(user.getSocialEmail())
                      .provider(user.getSocialProvider())
                      .build()
              )
              .build();
        }

        return AuthSocialResponse
            .builder()
            .accessToken(accessToken)
            .refreshToken(newRefreshToken)
            .isSignUp(isFinalSignUp(user))
            .user(userDataInfo)
            .build();
      }

      // 가입된 유저가 아닌 경우 회원가입 처리

      String accessToken = jwtTokenProvider.createAccessToken(socialId, socialEmail);
      String refreshToken = jwtTokenProvider.createRefreshToken(socialId, socialEmail);

      Auth auth = authRepository.save(Auth.from(refreshToken));

      User user = User.createStandardUserForSocial(socialId, auth, socialEmail,
          SocialProvider.GOOGLE);
      userRepository.save(user);

      return AuthSocialResponse
          .builder()
          .accessToken(accessToken)
          .refreshToken(refreshToken)
          .isSignUp(false)
          .user(null)
          .build();
    }




    // 소셜 플랫폼이 카카오일 경우
    if (userProvider == SocialProvider.KAKAO) {
      OAuthUserInfo userInfo = kakaoApiClient.getUserInfo(authSocialRequest.getAuthToken());

      String socialId = userInfo.getSocialId();
      String name = userInfo.getName();
      String socialEmail = userInfo.getEmail();

      if(socialEmail == null) {
        log.error("카카오 OAuth2 로그인 시 이메일이 제공되지 않았습니다.");
        socialEmail = socialId + "@kakao.com";
      }

      // 이미 가입된 유저인 경우 로그인 처리
      if (userRepository.findBySocialId(socialId).isPresent()) {
        User user = userRepository.findBySocialId(socialId).orElse(null);

        Auth auth = user.getAuth();

        // 기존 회원인데 auth 정보가 없는 경우
        if (auth == null) {
          log.error("로그인된 유저의 Auth 정보가 없습니다.");
          throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        String olderRefreshToken = auth.getRefreshToken();

        // 기존 회원인데 발급해놓은 리프레시 토큰이 유효하지 않은 경우 (보통은 만료된 경우) 토큰 재발급
        if (!jwtTokenProvider.isValidateToken(olderRefreshToken)) {
          String newRefreshToken = jwtTokenProvider.createRefreshToken(socialId, socialEmail);
          auth.updateRefreshToken(newRefreshToken);
        }

        String newRefreshToken = auth.getRefreshToken();
        String accessToken = jwtTokenProvider.createAccessToken(newRefreshToken);

        AuthSocialResponse.User userDataInfo;

        if(!isFinalSignUp(user)) {
          userDataInfo = null;
        } else {
          userDataInfo = AuthSocialResponse.User
              .builder()
              .tos(getTosAgreeInfo(user))
              .univ(
                  AuthSocialResponse.Univ
                      .builder()
                      .univName(user.getUnivName())
                      .firstMajorName(user.getFirstMajorName())
                      .secondMajorName(user.getSecondMajorName())
                      .univEmail(user.getUnivEmail())
                      .build()
              )
              .social(
                  AuthSocialResponse.Social
                      .builder()
                      .socialEmail(user.getSocialEmail())
                      .provider(user.getSocialProvider())
                      .build()
              )
              .build();
        }

        return AuthSocialResponse
            .builder()
            .accessToken(accessToken)
            .refreshToken(newRefreshToken)
            .isSignUp(isFinalSignUp(user))
            .user(userDataInfo)
            .build();
      }

      // 가입된 유저가 아닌 경우 회원가입 처리

      String accessToken = jwtTokenProvider.createAccessToken(socialId, socialEmail);
      String refreshToken = jwtTokenProvider.createRefreshToken(socialId, socialEmail);

      Auth auth = authRepository.save(Auth.from(refreshToken));

      User user = User.createStandardUserForSocial(socialId, auth, socialEmail,
          SocialProvider.KAKAO);
      userRepository.save(user);

      return AuthSocialResponse
          .builder()
          .accessToken(accessToken)
          .refreshToken(refreshToken)
          .isSignUp(false)
          .user(null)
          .build();
    }


      // 소셜 플랫폼이 네이버일 경우
      if (userProvider == SocialProvider.NAVER) {
        OAuthUserInfo userInfo = naverApiClient.getUserInfo(authSocialRequest.getAuthToken());

        String socialId = userInfo.getSocialId();
        String name = userInfo.getName();
        String socialEmail = userInfo.getEmail();

        if(socialEmail == null) {
          log.error("네이버 OAuth2 로그인 시 이메일이 제공되지 않았습니다.");
          socialEmail = socialId + "@naver.com";
        }

        // 이미 가입된 유저인 경우 로그인 처리
        if (userRepository.findBySocialId(socialId).isPresent()) {
          User user = userRepository.findBySocialId(socialId).orElse(null);

          Auth auth = user.getAuth();

          // 기존 회원인데 auth 정보가 없는 경우
          if (auth == null) {
            log.error("로그인된 유저의 Auth 정보가 없습니다.");
            throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
          }

          String olderRefreshToken = auth.getRefreshToken();

          // 기존 회원인데 발급해놓은 리프레시 토큰이 유효하지 않은 경우 (보통은 만료된 경우) 토큰 재발급
          if (!jwtTokenProvider.isValidateToken(olderRefreshToken)) {
            String newRefreshToken = jwtTokenProvider.createRefreshToken(socialId, socialEmail);
            auth.updateRefreshToken(newRefreshToken);
          }

          String newRefreshToken = auth.getRefreshToken();
          String accessToken = jwtTokenProvider.createAccessToken(newRefreshToken);

          AuthSocialResponse.User userDataInfo;

          if(!isFinalSignUp(user)) {
            userDataInfo = null;
          } else {
            userDataInfo = AuthSocialResponse.User
                .builder()
                .tos(getTosAgreeInfo(user))
                .univ(
                    AuthSocialResponse.Univ
                        .builder()
                        .univName(user.getUnivName())
                        .firstMajorName(user.getFirstMajorName())
                        .secondMajorName(user.getSecondMajorName())
                        .univEmail(user.getUnivEmail())
                        .build()
                )
                .social(
                    AuthSocialResponse.Social
                        .builder()
                        .socialEmail(user.getSocialEmail())
                        .provider(user.getSocialProvider())
                        .build()
                )
                .build();
          }

          return AuthSocialResponse
              .builder()
              .accessToken(accessToken)
              .refreshToken(newRefreshToken)
              .isSignUp(isFinalSignUp(user))
              .user(userDataInfo)
              .build();
        }

        // 가입된 유저가 아닌 경우 회원가입 처리

        String accessToken = jwtTokenProvider.createAccessToken(socialId, socialEmail);
        String refreshToken = jwtTokenProvider.createRefreshToken(socialId, socialEmail);

        Auth auth = authRepository.save(Auth.from(refreshToken));

        User user = User.createStandardUserForSocial(socialId, auth, socialEmail,
            SocialProvider.NAVER);
        userRepository.save(user);

        return AuthSocialResponse
            .builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .isSignUp(false)
            .user(null)
            .build();
      }



      // 소셜 플랫폼이 애플일 경우
      if (userProvider == SocialProvider.APPLE) {

      }

    throw new ProviderNotFoundException(ErrorCode.PROVIDER_NOT_FOUND);
  }

  @Transactional
  public AuthSignUpResponse authSignUp(AuthSignUpRequest authSignUpRequest) {

    // 1. 소셜로그인 회원가입 처리
    log.info("1. 소셜로그인 회원가입 처리");
    AuthSocialResponse authSocialResponse = socialLogin(authSignUpRequest.getSocial());

    // 2. 토큰 추출
    log.info("2. 토큰 추출");
    String token = authSocialResponse.getAccessToken();

    // 3. 토큰에서 socialId 추출
    log.info("3. 토큰에서 socialId 추출");
    String socialId = jwtTokenProvider.getSocialIdFromToken(token);

    // 4. socialId로 User 조회
    log.info("4. socialId로 User 조회");
    User user = userRepository.findBySocialId(socialId).orElseThrow(
        () -> new NotFoundInfoException(ErrorCode.USER_NOT_FOUND));

    // 5. 약관동의 처리
    log.info("5. 약관동의 처리");
    Tos tos = authSignUpRequest.getTos();
    tosV1Service.agreeTos(tos, user);

    // 6. 대학교, 학과, 학교 이메일 정보 업데이트
    log.info("6. 대학교, 학과, 학교 이메일 정보 업데이트");
    Univ univ = authSignUpRequest.getUniv();
    user.updateUnivInfo(univ);

    return AuthSignUpResponse.builder()
        .accessToken(authSocialResponse.getAccessToken())
        .refreshToken(authSocialResponse.getRefreshToken())
        .build();
  }


  @Transactional
  public String refreshToken (HttpServletRequest httpServletRequest){
    String refreshToken = jwtTokenProvider.extractToken(httpServletRequest);
    String socialId = jwtTokenProvider.getSocialIdFromToken(refreshToken);


    if(!jwtTokenProvider.getTypeFromToken(refreshToken).equals("refresh")) {
      throw new TokenInvalidType(ErrorCode.TOKEN_INVALID_TYPE);
    }

    User user = userRepository.findBySocialId(socialId)
        .orElseThrow(() -> new NotFoundInfoException(ErrorCode.USER_NOT_FOUND));

    String newAccessToken = jwtTokenProvider.createAccessToken(refreshToken);
    return newAccessToken;
  }

  @Transactional(readOnly = true)
  public boolean isFinalSignUp(User user) {
    if(user.getUnivEmail() == null || user.getUnivName() == null) {
      return false;
    }
    return true;
  }

  @Transactional(readOnly = true)
  public AuthSocialResponse.Tos getTosAgreeInfo(User user) {
    return AuthSocialResponse.Tos
        .builder()
        .serviceTosIsAgreed(serviceTosHistoryRepository.existsByUser(user))
        .personalInfoTosIsAgreed(personalInfoTosHistoryRepository.existsByUser(user))
        .marketingTosIsAgreed(marketingTosHistoryRepository.existsByUser(user))
        .serviceImprovementTosIsAgreed(serviceImprovementTosHistoryRepository.existsByUser(user))
        .inPersonTosIsAgreed(inPersonTosHistoryRepository.existsByUser(user))
        .build();
  }

}
