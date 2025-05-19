package com.qoormthon.todool.domain.mail.service;

import com.qoormthon.todool.domain.mail.dto.MailAuthenticationDto;
import com.qoormthon.todool.domain.mail.dto.MailSendDto;
import com.qoormthon.todool.domain.user.adapter.out.persistence.UserRepository;
import com.qoormthon.todool.global.common.response.ResponseDto;
import com.qoormthon.todool.global.common.util.BaseUtil;
import com.qoormthon.todool.global.common.util.HtmlTemplateUtil;
import com.qoormthon.todool.global.common.util.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailService {

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private BaseUtil baseUtil;

    @Autowired
    private HtmlTemplateUtil htmlTemplateUtil;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> sendMailAuthenticationCode(MailSendDto mailSendDto) {
        try {

            if(!userRepository.existsByUserId(mailSendDto.getUserId())) {
                return ResponseEntity
                        .badRequest()
                        .body(ResponseDto
                                .response(HttpStatus.BAD_REQUEST, "등록되지 않은 학번입니다.", mailSendDto.getUserId()));
            }


            MailAuthenticationDto mailAuthenticationDto = new MailAuthenticationDto();
            mailAuthenticationDto.setUserId(mailSendDto.getUserId());
            mailAuthenticationDto.setStdNo(mailSendDto.getStdNo());
            mailAuthenticationDto.setAuthenticationCode(baseUtil.getCode());

            mailUtil.deleteMailAuthenticationList(mailSendDto.getUserId());
            mailUtil.addMailAuthenticationList(mailAuthenticationDto);

            if(mailSendDto.getUnivName().equals("대진대학교")){
                for(MailAuthenticationDto mailAuthenticationDto1 : mailUtil.getMailAuthenticationDtoList()){
                    mailUtil.addRecipient(mailSendDto.getStdNo()+"@daejin.ac.kr");
                    if(mailAuthenticationDto1.getStdNo().equals(mailSendDto.getStdNo())) {
                        String htmlContent = htmlTemplateUtil.createHtmlEmailTemplate(mailAuthenticationDto1.getAuthenticationCode(), mailAuthenticationDto.getStdNo(), mailSendDto.getStdNo()+"@daejin.ac.kr");
                        mailUtil.sendHtmlEmail(mailUtil.getRecipients(), "이메일 인증 코드입니다.", htmlContent);
                        mailUtil.clearRecipients(); //메일 리스트 초기화
                        return ResponseEntity.ok()
                                .body(ResponseDto.response(HttpStatus.OK, "메일 전송에 성공하였습니다.", mailAuthenticationDto1.getStdNo()));
                    }
                }
            } else {
                return ResponseEntity.badRequest()
                        .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "등록되지 않은 학교입니다.", mailSendDto.getStdNo()));
            }

        } catch (Exception e) {
//            log.error("상세 오류 발생: ", e);
//            // 또는
//            log.error("오류 발생: {} - 스택트레이스: {}", e.getMessage(), e.getStackTrace());
//            // 또는
//            StringWriter sw = new StringWriter();
//            e.printStackTrace(new PrintWriter(sw));
//            log.error("예외 상세 정보: {}", sw.toString());
            return ResponseEntity.internalServerError()
                    .body(ResponseDto
                            .response(HttpStatus.INTERNAL_SERVER_ERROR, "메세지 전송에 실패하였습니다.", mailSendDto.getStdNo()));
        }
        return ResponseEntity.internalServerError()
                .body(ResponseDto.response(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류입니다.", mailSendDto.getStdNo()));
    }

    public ResponseEntity<?> mailAuthenticationCodeIsValid(MailAuthenticationDto mailAuthenticationDto) {

        try {
            for(MailAuthenticationDto mailAuthenticationDto1 : mailUtil.getMailAuthenticationDtoList()) {
                if(mailAuthenticationDto1.getStdNo().equals(mailAuthenticationDto.getStdNo())){
                    if(mailAuthenticationDto1.getAuthenticationCode().equals(mailAuthenticationDto.getAuthenticationCode())){
                        return ResponseEntity.ok()
                                .body(ResponseDto.response(HttpStatus.OK, "인증번호가 일치합니다.", mailAuthenticationDto.getStdNo()));
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(ResponseDto.response(HttpStatus.UNAUTHORIZED, "인증번호가 일치하지 않습니다.", mailAuthenticationDto.getStdNo()));
                    }
                }
            }
            return ResponseEntity.badRequest()
                    .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "발급된 인증번호가 존재하지 않습니다.", mailAuthenticationDto.getStdNo()));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(ResponseDto.response(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류입니다.", mailAuthenticationDto.getStdNo()));
        }
    }
}
