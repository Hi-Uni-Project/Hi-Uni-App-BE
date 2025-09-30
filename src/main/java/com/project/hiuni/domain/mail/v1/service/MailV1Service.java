package com.project.hiuni.domain.mail.v1.service;

import com.project.hiuni.domain.mail.dto.MailAuthentication;
import com.project.hiuni.domain.mail.dto.request.CodeRequest;
import com.project.hiuni.domain.mail.dto.request.MailRequest;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.common.util.HtmlTemplateUtil;
import com.project.hiuni.global.exception.ErrorCode;
import com.project.hiuni.global.exception.NotFoundInfoException;
import com.project.hiuni.global.security.jwt.JwtTokenProvider;
import com.project.hiuni.infra.mail.MailClient;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Random;
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

  public String sendMail(MailRequest mailRequest) {
    log.info("메일 전송 서비스 실행");

    // 메일 인증 코드 생성
    String authMailId = generateRandomString();

    //메일 인증객체 입니다.
    MailAuthentication mailAuthentication = MailAuthentication.from(authMailId);

    mailClient.deleteMailAuthenticationList(authMailId);
    mailClient.addMailAuthenticationList(mailAuthentication);

    String mailAuthCode = mailAuthentication.getAuthCode();
    String email = mailRequest.getEmail();

    mailClient.addRecipient(email);

    String mailContent = HtmlTemplateUtil.createHtmlEmailTemplate(mailAuthCode, email);

    mailClient.sendEmail(mailClient.getRecipients(), "Hi-Uni 인증번호를 확인해주세요!", mailContent);

    return authMailId;
  }


  public boolean validateEmail(String email) {
      String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
      Pattern pattern = Pattern.compile(regex);

      if (email == null || !pattern.matcher(email).matches()) {
        return false;
      }

      return email.toLowerCase().endsWith("ac.kr");
  }

  public boolean validateCode(CodeRequest codeRequest) {

    String targetAuthCode = codeRequest.getAuthCode();
    String userAuthCode = mailClient.getUserAuthenticationCode(codeRequest.getAuthMailId());

    log.info("targetAuthCode: {}, userAuthCode: {}", targetAuthCode, userAuthCode);

    if(targetAuthCode.equals(userAuthCode)) {
      return true;
    } else {
      return false;
    }

  }

  private String generateRandomString() {
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    Random random = new Random();
    StringBuilder result = new StringBuilder(10);

    for (int i = 0; i < 10; i++) {
      int index = random.nextInt(characters.length());
      result.append(characters.charAt(index));
    }

    return result.toString();
  }

}
