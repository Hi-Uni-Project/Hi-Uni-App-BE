package com.project.hiuni.domain.mail.v1.service;

import com.project.hiuni.domain.mail.dto.MailAuthentication;
import com.project.hiuni.domain.mail.dto.request.CodeRequest;
import com.project.hiuni.domain.mail.dto.request.MailRequest;
import com.project.hiuni.domain.univ.v1.service.UnivV1Service;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.common.util.HtmlTemplateUtil;
import com.project.hiuni.global.exception.ErrorCode;
import com.project.hiuni.global.exception.InternalServerException;
import com.project.hiuni.global.exception.NotFoundInfoException;
import com.project.hiuni.global.security.jwt.JwtTokenProvider;
import com.project.hiuni.infra.mail.MailClient;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Random;
import java.util.regex.Matcher;
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
  private final UnivV1Service univV1Service;

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


  public boolean validateEmail(String email, String univName) {
      try {

        // 테스트용 이메일 허용 입니다. 추후 제거돼야합니다.
        // TODO: 추후 제거
        if(email.equals("seolbotdae@gmail.com") || email.equals("hjphjp321212@gmail.com") || email.equals("tlsgksk159@naver.com")) {
          return true;
        }


        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(regex);

        if(email == null || !pattern.matcher(email).matches()) {
          log.info("이메일 형식이 올바르지 않습니다. : " + email);
          return false;
        }

        String targetEmail = univV1Service.findSchoolsByUnivName(univName).get(0).getWebsiteUrl();

        if(targetEmail.isEmpty()) {
          log.info("존재하지 않는 학교입니다. : " + univName);
          return false;
        }

        String targetDomainName = extractDomainName(targetEmail);
        String domainName = extractDomainName(email);

        if (domainName == null || targetDomainName == null) {
          log.info("이메일 형식이 올바르지 않습니다. : " + email);
          return false;
        }

        if(domainName.equalsIgnoreCase(targetDomainName)) {
          return true;
        } else {
          log.info("이메일 도메인이 일치하지 않습니다. targetDomainName : " + targetDomainName + " / " + "domainName : " + domainName);
          return false;
        }
      } catch (Exception e) {
        log.error("메일 전송중 에러 발생 : " + e.getMessage());
        throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
      }
  }

  public boolean validateCode(CodeRequest codeRequest) {

    String targetAuthCode = codeRequest.getAuthCode();
    String userAuthCode = mailClient.getUserAuthenticationCode(codeRequest.getAuthMailId());

    log.info("targetAuthCode: {}, userAuthCode: {}", targetAuthCode, userAuthCode);

    if(targetAuthCode.equals(userAuthCode)) {
      mailClient.deleteMailAuthenticationList(codeRequest.getAuthMailId());
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

  private static String extractDomainName(String email) {
    Pattern pattern = Pattern.compile("\\.?([a-zA-Z]+)\\.ac\\.kr");
    Matcher matcher = pattern.matcher(email);

    if (matcher.find()) {
      return matcher.group(1);
    }
    return null;
  }

}
