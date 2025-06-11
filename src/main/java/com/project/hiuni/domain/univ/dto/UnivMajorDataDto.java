package com.project.hiuni.domain.univ.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

import java.util.List;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UnivMajorDataDto {

    private List<Major> records;

    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Major {
        @JsonAlias("학교명")
        private String univName;

        @JsonAlias("학과명")
        private String majorName;

        @JsonAlias("단과대학명")
        private String collegeName;
    }

}
