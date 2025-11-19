package com.project.hiuni.domain.record.resume.skill.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SkillDataDto {
    @JsonProperty("id")
    private Long skillId;
    private String name;
}
