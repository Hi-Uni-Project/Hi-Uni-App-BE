package com.qoormthon.todool.global.common.util;

import org.springframework.stereotype.Component;

@Component
public class HtmlTemplateUtil {
    public String createHtmlEmailTemplate(String authCode, String username, String email) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>인증코드 안내</title>\n" +
                "    <style>\n" +
                "        @import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700&display=swap');\n" +
                "        \n" +
                "        * {\n" +
                "            box-sizing: border-box;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "        \n" +
                "        body {\n" +
                "            font-family: 'Noto Sans KR', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;\n" +
                "            line-height: 1.5;\n" +
                "            color: #333;\n" +
                "            background-color: #ffffff;\n" +
                "        }\n" +
                "        \n" +
                "        .email-wrapper {\n" +
                "            max-width: 500px;\n" +
                "            margin: 0 auto;\n" +
                "            background-color: #ffffff;\n" +
                "            padding: 25px 0;\n" +
                "        }\n" +
                "        \n" +
                "        .email-header {\n" +
                "            text-align: center;\n" +
                "            margin-bottom: 30px;\n" +
                "        }\n" +
                "        \n" +
                "        .university-name {\n" +
                "            font-size: 16px;\n" +
                "            font-weight: 500;\n" +
                "            color: #1a73e8;\n" +
                "            letter-spacing: 0.5px;\n" +
                "        }\n" +
                "        \n" +
                "        .container {\n" +
                "            padding: 0 20px;\n" +
                "        }\n" +
                "        \n" +
                "        .greeting {\n" +
                "            font-size: 15px;\n" +
                "            margin-bottom: 25px;\n" +
                "            color: #555;\n" +
                "            line-height: 1.6;\n" +
                "        }\n" +
                "        \n" +
                "        .greeting strong {\n" +
                "            color: #1a73e8;\n" +
                "            font-weight: 600;\n" +
                "        }\n" +
                "        \n" +
                "        .auth-code-container {\n" +
                "            background-color: #f8f9fa;\n" +
                "            border-radius: 8px;\n" +
                "            padding: 25px 20px;\n" +
                "            margin: 25px 0;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        \n" +
                "        .auth-code-label {\n" +
                "            font-size: 14px;\n" +
                "            text-align: center;\n" +
                "            font-weight: 500;\n" +
                "            color: #555;\n" +
                "            margin-bottom: 12px;\n" +
                "        }\n" +
                "        \n" +
                "        .auth-code {\n" +
                "            font-size: 30px;\n" +
                "            font-weight: 600;\n" +
                "            text-align: center;\n" +
                "            color: #1a73e8;\n" +
                "            padding: 12px 20px;\n" +
                "            background-color: #ffffff;\n" +
                "            border-radius: 6px;\n" +
                "            letter-spacing: 4px;\n" +
                "            display: inline-block;\n" +
                "            box-shadow: 0 1px 3px rgba(0,0,0,0.08);\n" +
                "        }\n" +
                "        \n" +
                "        .info-section {\n" +
                "            margin: 25px 0;\n" +
                "            font-size: 14px;\n" +
                "            color: #666;\n" +
                "        }\n" +
                "        \n" +
                "        .info-item {\n" +
                "            margin-bottom: 6px;\n" +
                "        }\n" +
                "        \n" +
                "        .info-label {\n" +
                "            font-weight: 500;\n" +
                "            color: #555;\n" +
                "            display: inline-block;\n" +
                "            width: 50px;\n" +
                "        }\n" +
                "        \n" +
                "        .separator {\n" +
                "            height: 1px;\n" +
                "            background-color: #f1f3f4;\n" +
                "            margin: 25px 0 20px;\n" +
                "        }\n" +
                "        \n" +
                "        .footer {\n" +
                "            text-align: center;\n" +
                "            color: #999;\n" +
                "            font-size: 12px;\n" +
                "        }\n" +
                "        \n" +
                "        @media only screen and (max-width: 500px) {\n" +
                "            .container { padding: 0 15px; }\n" +
                "            .auth-code { font-size: 26px; letter-spacing: 3px; }\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"email-wrapper\">\n" +
                "        <div class=\"email-header\">\n" +
                "            <div class=\"university-name\">DAEJIN UNIV</div>\n" +
                "        </div>\n" +
                "        \n" +
                "        <div class=\"container\">\n" +
                "            <div class=\"greeting\">\n" +
                "                <p>안녕하세요, <strong>" + username + "</strong>님!</p>\n" +
                "                <p>회원가입을 위한 인증 코드입니다.</p>\n" +
                "            </div>\n" +
                "            \n" +
                "            <div class=\"auth-code-container\">\n" +
                "                <div class=\"auth-code-label\">인증 코드</div>\n" +
                "                <div class=\"auth-code\">" + authCode + "</div>\n" +
                "            </div>\n" +
                "            \n" +
                "            <div class=\"info-section\">\n" +
                "                <div class=\"info-item\">\n" +
                "                    <span class=\"info-label\">이름</span>\n" +
                "                    <span class=\"info-value\">" + username + "</span>\n" +
                "                </div>\n" +
                "                <div class=\"info-item\">\n" +
                "                    <span class=\"info-label\">이메일</span>\n" +
                "                    <span class=\"info-value\">" + email + "</span>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            \n" +
                "            <div class=\"separator\"></div>\n" +
                "            \n" +
                "            <div class=\"footer\">\n" +
                "                <p>본 메일은 발신 전용입니다.</p>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }
}
