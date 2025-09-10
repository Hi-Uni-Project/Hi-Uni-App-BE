package com.project.hiuni.global.common.util;

import jakarta.persistence.Access;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HtmlTemplateUtil {

  /**
   * HTML 이메일 템플릿 생성
   * @param authCode 인증 코드
   * @param socialEmail 소셜 이메일
   * @return HTML 문자열
   */
  public static String createHtmlEmailTemplate(String authCode, String socialEmail) {
    return "<!DOCTYPE html>\n" +
        "<html>\n" +
        "<head>\n" +
        "    <meta charset=\"UTF-8\">\n" +
        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
        "    <title>Hi-Uni 이메일 인증</title>\n" +
        "    <style>\n" +
        "        * {\n" +
        "            box-sizing: border-box;\n" +
        "            margin: 0;\n" +
        "            padding: 0;\n" +
        "        }\n" +
        "        \n" +
        "        body {\n" +
        "            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;\n" +
        "            line-height: 1.6;\n" +
        "            color: #1f2128;\n" +
        "            background-color: #ffffff;\n" +
        "            padding: 20px;\n" +
        "        }\n" +
        "        \n" +
        "        .email-wrapper {\n" +
        "            max-width: 480px;\n" +
        "            margin: 0 auto;\n" +
        "            background: #ffffff;\n" +
        "        }\n" +
        "        \n" +
        "        .email-header {\n" +
        "            text-align: center;\n" +
        "            margin-bottom: 32px;\n" +
        "            padding-bottom: 20px;\n" +
        "            border-bottom: 2px solid #6568EA;\n" +
        "        }\n" +
        "        \n" +
        "        .service-name {\n" +
        "            font-size: 24px;\n" +
        "            font-weight: 700;\n" +
        "            color: #6568EA;\n" +
        "            letter-spacing: -0.5px;\n" +
        "        }\n" +
        "        \n" +
        "        .container {\n" +
        "            padding: 0 20px;\n" +
        "        }\n" +
        "        \n" +
        "        .greeting {\n" +
        "            margin-bottom: 28px;\n" +
        "        }\n" +
        "        \n" +
        "        .greeting h2 {\n" +
        "            font-size: 18px;\n" +
        "            font-weight: 600;\n" +
        "            color: #1f2128;\n" +
        "            margin-bottom: 8px;\n" +
        "        }\n" +
        "        \n" +
        "        .greeting p {\n" +
        "            font-size: 15px;\n" +
        "            color: #6e6e6e;\n" +
        "        }\n" +
        "        \n" +
        "        .email-highlight {\n" +
        "            color: #6568EA;\n" +
        "            font-weight: 600;\n" +
        "        }\n" +
        "        \n" +
        "        .auth-section {\n" +
        "            background: #f8f9ff;\n" +
        "            border-radius: 8px;\n" +
        "            padding: 24px 20px;\n" +
        "            margin: 24px 0;\n" +
        "            text-align: center;\n" +
        "            border: 1px solid #eaeaea;\n" +
        "        }\n" +
        "        \n" +
        "        .auth-label {\n" +
        "            font-size: 14px;\n" +
        "            font-weight: 500;\n" +
        "            color: #5b5b5b;\n" +
        "            margin-bottom: 12px;\n" +
        "        }\n" +
        "        \n" +
        "        .auth-code {\n" +
        "            font-size: 28px;\n" +
        "            font-weight: 700;\n" +
        "            color: #6568EA;\n" +
        "            padding: 12px 16px;\n" +
        "            background: #ffffff;\n" +
        "            border-radius: 6px;\n" +
        "            letter-spacing: 3px;\n" +
        "            display: inline-block;\n" +
        "            border: 1px solid #eaeaea;\n" +
        "            font-family: 'Courier New', monospace;\n" +
        "        }\n" +
        "        \n" +
        "        .separator {\n" +
        "            height: 1px;\n" +
        "            background-color: #eaeaea;\n" +
        "            margin: 32px 0 20px;\n" +
        "        }\n" +
        "        \n" +
        "        .footer {\n" +
        "            text-align: center;\n" +
        "            color: #979797;\n" +
        "            font-size: 12px;\n" +
        "            line-height: 1.5;\n" +
        "        }\n" +
        "        \n" +
        "        @media only screen and (max-width: 500px) {\n" +
        "            .container { padding: 0 16px; }\n" +
        "            .auth-code { \n" +
        "                font-size: 24px; \n" +
        "                letter-spacing: 2px; \n" +
        "                padding: 10px 14px; \n" +
        "            }\n" +
        "            .service-name { font-size: 22px; }\n" +
        "            .greeting h2 { font-size: 16px; }\n" +
        "        }\n" +
        "    </style>\n" +
        "</head>\n" +
        "<body>\n" +
        "    <div class=\"email-wrapper\">\n" +
        "        <div class=\"email-header\">\n" +
        "            <div class=\"service-name\">Hi-Uni</div>\n" +
        "        </div>\n" +
        "        \n" +
        "        <div class=\"container\">\n" +
        "            <div class=\"greeting\">\n" +
        "                <h2>안녕하세요, <span class=\"email-highlight\">" + socialEmail + "</span>님!</h2>\n" +
        "                <p>회원가입을 위한 인증 코드입니다.</p>\n" +
        "            </div>\n" +
        "            \n" +
        "            <div class=\"auth-section\">\n" +
        "                <div class=\"auth-label\">인증 코드</div>\n" +
        "                <div class=\"auth-code\">" + authCode + "</div>\n" +
        "            </div>\n" +
        "            \n" +
        "            <div class=\"separator\"></div>\n" +
        "            \n" +
        "            <div class=\"footer\">\n" +
        "                <p>문의사항이 있으시면 본 메일로 회신 부탁드립니다.</p>\n" +
        "                <p>- Hi-Uni Team</p>\n" +
        "            </div>\n" +
        "        </div>\n" +
        "    </div>\n" +
        "</body>\n" +
        "</html>";
  }

}
