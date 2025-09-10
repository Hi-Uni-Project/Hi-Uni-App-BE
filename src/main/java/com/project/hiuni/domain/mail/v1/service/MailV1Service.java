package com.project.hiuni.domain.mail.v1.service;

import com.project.hiuni.domain.mail.dto.MailAuthentication;
import com.project.hiuni.domain.mail.dto.request.MailRequest;
import com.project.hiuni.domain.mail.exception.EmailSendException;
import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.common.util.HtmlTemplateUtil;
import com.project.hiuni.global.exception.ErrorCode;
import com.project.hiuni.global.exception.NotFoundInfoException;
import com.project.hiuni.global.security.jwt.JwtTokenProvider;
import com.project.hiuni.infra.mail.MailClient;
import jakarta.servlet.http.HttpServletRequest;
import java.util.StringTokenizer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailV1Service {

  private final MailClient mailClient;
  private final JwtTokenProvider jwtTokenProvider;
  private final UserRepository userRepository;

  public void sendMail(MailRequest mailRequest, HttpServletRequest httpServletRequest) {
    log.info("메일 전송 서비스 실행");

    String token = jwtTokenProvider.extractToken(httpServletRequest);
    String socialId = jwtTokenProvider.getSocialIdFromToken(token);

    if(userRepository.findBySocialId(socialId).isEmpty()) {
      throw new NotFoundInfoException(ErrorCode.USER_NOT_FOUND);
    }

    MailAuthentication mailAuthentication = MailAuthentication.from(socialId);

    mailClient.deleteMailAuthenticationList(socialId);
    mailClient.addMailAuthenticationList(mailAuthentication);

    String mailAuthCode = mailAuthentication.getAuthCode();
    String email = mailRequest.getEmail();

    mailClient.addRecipient(email);

    String mailContent = HtmlTemplateUtil.createHtmlEmailTemplate(mailAuthCode, email);

    mailClient.sendEmail(mailClient.getRecipients(), "Hi-Uni 인증번호를 확인해주세요!", mailContent);
  }

}
