package com.project.hiuni.domain.univ.controller;

import com.project.hiuni.domain.univ.service.UnivService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/univ")
@Tag(name = "전국 대학교 및 학과 조회 api 입니다.", description = "전국에 있는 학교와 학과를 조회할 수 있습니다. / 데이터 출처: https://www.data.go.kr/index.do")
public class UnivController {


    @Autowired
    private UnivService univService;

    @Operation(summary = "대학교를 모두 조회합니다.", description = "리스트 형태로 반환됩니다.")
    @GetMapping("/find/all")
    public ResponseEntity<?> findAll() {
       return univService.findAll();
    }

    @Operation(summary = "대학교 이름으로 조회합니다.", description = "부분 일치 검색 방식이며 리스트 형태로 반환됩니다.")
    @GetMapping("/find/{univName}")
    public ResponseEntity<?> findByUnivName(@PathVariable String univName) {
        return univService.findByUnivName(univName);
    }

    @Operation(summary = "특정 대학교의 학과를(학교명으로) 모두 조회합니다.", description = "부분 일치 검색 방식이며 리스트 형태로 반환됩니다.")
    @GetMapping("/find/major/{univName}")
    public ResponseEntity<?> findByUnivMajorName(@PathVariable String univName) {
        return univService.findByUnivMajorName(univName);
    }

}
