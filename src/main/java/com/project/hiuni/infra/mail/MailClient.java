package com.project.hiuni.infra.mail;

import com.project.hiuni.domain.mail.dto.MailAuthentication;
import jakarta.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Getter
@RequiredArgsConstructor
public class MailClient {

  private List<MailAuthentication> mailAuthenticationList = new ArrayList<>();
  private String[] recipients = {};


  private final JavaMailSender mailSender;

  @Async
  public void sendMail(String[] to, String mailTitle, String mailContent) throws MessagingException {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

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

  public void deleteMailAuthenticationList(String socialId) {
    for(MailAuthentication mailAuthentication : this.mailAuthenticationList) {
      if(mailAuthentication.getSocialId().equals(socialId)) {
        this.mailAuthenticationList.remove(mailAuthentication);
        break;
      }
    }
  }

  public String getUserAuthenticationCode(String socialId) {
    for(MailAuthentication mailAuthentication : this.mailAuthenticationList) {
      if(mailAuthentication.getSocialId().equals(socialId)) {
        return mailAuthentication.getAuthCode();
      }
    }
    return null;
  }






}
