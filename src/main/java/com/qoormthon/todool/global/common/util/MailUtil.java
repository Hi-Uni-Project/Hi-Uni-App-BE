package com.qoormthon.todool.global.common.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Getter
@Component
public class MailUtil {

    @Autowired
    private JavaMailSender emailSender;

    private String[] recipients = {};


    @Async
    public void sendHtmlEmail(String[] to, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom("Daejin UNIV <daejindae2@gmail.com>");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
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
}
