package com.qoormthon.todool.global.common.util;

import com.qoormthon.todool.domain.mail.dto.MailAuthenticationDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class MailUtil {

    @Autowired
    private JavaMailSender emailSender;

    private List<MailAuthenticationDto> mailAuthenticationDtoList = new ArrayList<>();

    private String[] recipients = {};


    @Async
    public void sendHtmlEmail(String[] to, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom("Daejin UNIV <daejindae2@gmail.com>");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        emailSender.send(message);
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

    public void addMailAuthenticationList(MailAuthenticationDto mailAuthenticationDto) {
        this.mailAuthenticationDtoList.add(mailAuthenticationDto);
    }

    public void deleteMailAuthenticationList(String stdNo) {
        for(MailAuthenticationDto mailAuthenticationDto : this.mailAuthenticationDtoList) {
            if(mailAuthenticationDto.getStdNo().equals(stdNo)){
                this.mailAuthenticationDtoList.remove(mailAuthenticationDto);
                break;
            }
        }
    }

    public String getUserAuthenticationCode(String stdNo) {
        for(MailAuthenticationDto mailAuthenticationDto : this.mailAuthenticationDtoList) {
            if(mailAuthenticationDto.getStdNo().equals(stdNo)){
                return mailAuthenticationDto.getAuthenticationCode();
            }
        }
        return null;
    }
}
