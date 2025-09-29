package com.project.hiuni.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.hiuni.domain.user.exception.CustomUserNotFoundException;
import com.project.hiuni.global.common.dto.response.ErrorResponse;
import com.project.hiuni.global.common.dto.response.ResponseDTO;
import com.project.hiuni.global.common.threadlocal.TraceIdHolder;
import com.project.hiuni.global.exception.ErrorCode;
import com.project.hiuni.global.exception.InvalidAccessJwtException;
import com.project.hiuni.global.exception.InvalidRefrashJwtException;
import com.project.hiuni.global.security.core.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


/**
 * JWT 토큰을 검사하고 사용자 인증 정보를 설정하는 필터 클래스입니다. 이 필터는 모든 HTTP 요청을 가로채어 JWT 토큰의 유효성을 검사하고 유효한 경우 사용자 인증
 * 정보를 SecurityContext에 저장합니다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

  /**
   * Jwt 토큰 생성 및 검증을 담당하는 객체입니다.
   */
  private final JwtTokenProvider jwtTokenProvider;

  /**
   * 사용자 인증 정보 생성을 담당하는 서비스 객체입니다.
   */
  private final CustomUserDetailsService customUserDetailsService;

  /**
   * 예외처리 응답을 위해 사용되는 객체입니다.
   */
  private final ObjectMapper objectMapper;


  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response, FilterChain filterChain
  ) throws ServletException, IOException {

    try {
      TraceIdHolder.set(UUID.randomUUID().toString().substring(0, 8));

      String accessToken = this.getTokenFromRequest(request);
      String url = request.getRequestURI().toString();
      String method = request.getMethod();

      // 토큰이 존재하면 유효성 검사를 수행합니다.
      if (accessToken != null) {
        try {
          jwtTokenProvider.validateToken(accessToken);
          log.info("[" + TraceIdHolder.get() + "][" + request.getRemoteAddr() + "]:"
              + "[" + method + ":" + url + "](allowed)");

          UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(accessToken);
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } catch (InvalidAccessJwtException e) {
          handleAccessTokenInvalid(response);
          return; // 필터 체인 중단
        } catch (InvalidRefrashJwtException e) {
          handleRefreshTokenInvalid(response);
          return; // 필터 체인 중단
        }
      } else {
        log.info("[" + TraceIdHolder.get() + "][" + request.getRemoteAddr() + "]:"
            + "[" + method + ":" + url + "](no token)");
      }

      filterChain.doFilter(request, response);

    } catch (CustomUserNotFoundException e) {
      handleUserNotFound(response);
    } finally {
      TraceIdHolder.clear();
    }
  }


  /**
   * 요청 헤더에서 JWT 토큰을 추출하는 메서드입니다.
   *
   * @param request HTTP 요청 객체
   * @return 요청 헤더에서 추출한 순수 JWT 토큰 문자열을 반환합니다
   */
  private String getTokenFromRequest(HttpServletRequest request) {

    // Authorization 헤더에서 JWT 토큰을 추출합니다.
    String token = request.getHeader("Authorization");

    // 토큰이 "Bearer "로 시작하는지 확인합니다.
    if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {

      //Bearer 제거하고 토큰만을 추출합니다
      token = token.substring(7);
    }

    return token;
  }

  private UsernamePasswordAuthenticationToken getAuthentication(String token) {

    //토큰에서 사용자 ID를 추출합니다.
    String socialId = jwtTokenProvider.getSocialIdFromToken(token);

    //사용자 ID로 사용자 인증 정보를 생성합니다.
    UserDetails userDetails = customUserDetailsService.loadUserById(socialId);

    return new UsernamePasswordAuthenticationToken(
        userDetails,
        null,
        userDetails.getAuthorities()
    );
  }


  private void handleAccessTokenInvalid(HttpServletResponse response) throws IOException {
    String errorResponse = objectMapper.writeValueAsString(ResponseDTO.of(ErrorCode.ACCESS_TOKEN_INVALID));
    response.setStatus(ErrorCode.ACCESS_TOKEN_INVALID.getActualStatusCode());
    response.setContentType("application/json");
    response.getWriter().write(errorResponse);
  }

  private void handleRefreshTokenInvalid(HttpServletResponse response) throws IOException {
    String errorResponse = objectMapper.writeValueAsString(ResponseDTO.of(ErrorCode.REFRESH_TOKEN_INVALID));
    response.setStatus(ErrorCode.REFRESH_TOKEN_INVALID.getActualStatusCode());
    response.setContentType("application/json");
    response.getWriter().write(errorResponse);
  }

  private void handleUserNotFound(HttpServletResponse response) throws IOException {
    String errorResponse = objectMapper.writeValueAsString(ErrorResponse.of(ErrorCode.USER_NOT_FOUND));
    response.setStatus(ErrorCode.USER_NOT_FOUND.getActualStatusCode());
    response.setContentType("application/json");
    response.getWriter().write(errorResponse);
  }

}
