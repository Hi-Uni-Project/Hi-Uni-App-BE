package com.qoormthon.todool.global.infra.univcert;

import com.qoormthon.todool.domain.user.application.port.out.UnivcertRqPort;
import com.qoormthon.todool.global.infra.univcert.dto.request.UnivcertRequestDto;
import com.qoormthon.todool.global.infra.univcert.dto.response.UnivcertResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UnivcertClient implements UnivcertRqPort {

    private final RestTemplate restTemplate;

    @Autowired
    public UnivcertClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean isValidUnivEmail(String email, String univName) {
        String url = "https://univcert.com:8080/api/try";
        UnivcertRequestDto univcertRequestDto = UnivcertRequestDto.builder()
                .email(email)
                .univName(univName)
                .build();

        UnivcertResponseDto univcertResponseDto = restTemplate.postForObject(url, univcertRequestDto, UnivcertResponseDto.class);
        return univcertResponseDto.isSuccess();
    }
}
