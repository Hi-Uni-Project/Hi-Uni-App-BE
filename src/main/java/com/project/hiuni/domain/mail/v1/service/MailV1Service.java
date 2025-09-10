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
import java.util.regex.Pattern;
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

    // 해당 socialId를 가진 사용자가 존재하는지 확인
    if(userRepository.findBySocialId(socialId).isEmpty()) {
      throw new NotFoundInfoException(ErrorCode.USER_NOT_FOUND);
    }

    //메일 인증객체 입니다.
    MailAuthentication mailAuthentication = MailAuthentication.from(socialId);

    mailClient.deleteMailAuthenticationList(socialId);
    mailClient.addMailAuthenticationList(mailAuthentication);

    String mailAuthCode = mailAuthentication.getAuthCode();
    String email = mailRequest.getEmail();

    mailClient.addRecipient(email);

    String mailContent = HtmlTemplateUtil.createHtmlEmailTemplate(mailAuthCode, email);

    mailClient.sendEmail(mailClient.getRecipients(), "Hi-Uni 인증번호를 확인해주세요!", mailContent);
  }


  public boolean validateEmail(String email) {
      String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
      Pattern pattern = Pattern.compile(regex);

      if (email == null || !pattern.matcher(email).matches()) {
        return false;
      }

      return email.toLowerCase().endsWith("ac.kr");
  }

}
