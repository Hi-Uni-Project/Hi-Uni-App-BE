package com.project.hiuni.domain.schedule.entity;

public enum Category {
    INTERVIEW("면접", "#B0B0B0"),
    RESULT("결과발표" ,"#B0B0B0"),
    CERTIFICATION("자격증" ,"#B0B0B0"),
    STUDY("스터디" ,"#B0B0B0"),
    CONTEST("대회/공모전", "#B0B0B0"),
    EMPLOYMENT("취업", "#B0B0B0"),
    PROJECT("프로젝트", "#B0B0B0"),
    ETC("기타", "#B0B0B0");

    private final String value;
    private final String color;

    Category(String value, String color) {
        this.value = value;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public String getValue() {
        return value;
    }
}
