package com.qoormthon.todool.domain.univ.dto;

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
        private String 학교명;
        private String 학과명;
    }

}
