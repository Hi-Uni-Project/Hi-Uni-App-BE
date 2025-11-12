package com.project.hiuni.domain.record.resume.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillDataDto {
    @JsonProperty("id")
    private Long skillId;
    private String name;
}
