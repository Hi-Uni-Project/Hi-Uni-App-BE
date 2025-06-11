package com.project.hiuni.infra.univcert.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UnivcertRequestDto {
    private String email;
    private String univName;
}
