package com.project.hiuni.infra.mail;

import com.project.hiuni.domain.mail.dto.MailAuthentication;
import com.project.hiuni.domain.mail.exception.EmailSendException;
import com.project.hiuni.domain.mail.exception.InvalidEmailCodeException;
import com.project.hiuni.global.exception.ErrorCode;
import jakarta.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Getter
@Slf4j
@RequiredArgsConstructor
public class MailClient {

  private List<MailAuthentication> mailAuthenticationList = new ArrayList<>();
  private String[] recipients = {};


  private final JavaMailSender mailSender;

  @Async
  public void sendEmail(String[] to, String mailTitle, String mailContent) {
    try {
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
      helper.setFrom("Hi-Uni <hiuniv.official@gmail.com>");
      helper.setTo(to);
      helper.setSubject(mailTitle);
      helper.setText(mailContent, true);

      mailSender.send(message);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new EmailSendException(ErrorCode.EMAIL_SEND_FAILED);
    }
  }

  public void addRecipient(String email) {
    String[] newRecipients = new String[recipients.length + 1];
    System.arraycopy(recipients, 0, newRecipients, 0, recipients.length);
    newRecipients[recipients.length] = email;
    recipients = newRecipients;
  }

  public void clearRecipients() {
    recipients = new String[0];
  }

  public void addMailAuthenticationList(MailAuthentication mailAuthentication) {
    this.mailAuthenticationList.add(mailAuthentication);
  }

  public void deleteMailAuthenticationList(String authMailId) {
    for(MailAuthentication mailAuthentication : this.mailAuthenticationList) {
      if(mailAuthentication.getAuthMailId().equals(authMailId)) {
        this.mailAuthenticationList.remove(mailAuthentication);
        break;
      }
    }
  }

  public String getUserAuthenticationCode(String socialId) {
    try {
      for(MailAuthentication mailAuthentication : this.mailAuthenticationList) {
        if(mailAuthentication.getAuthMailId().equals(socialId)) {
          return mailAuthentication.getAuthCode();
        }
      }
    } catch (Exception e) {
      throw new InvalidEmailCodeException(ErrorCode.INVALID_EMAIL_CODE);
    }
    throw new InvalidEmailCodeException(ErrorCode.INVALID_EMAIL_CODE);
  }






}
