package com.qoormthon.todool.domain.univ.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qoormthon.todool.domain.univ.dto.UnivDataDto;
import com.qoormthon.todool.domain.univ.dto.UnivDto;
import com.qoormthon.todool.domain.univ.dto.UnivMajorDataDto;
import com.qoormthon.todool.global.common.response.ResponseDto;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@Service
@Slf4j
public class UnivService {

    private UnivDataDto univDataDto;
    private UnivMajorDataDto univMajorDataDto;

    @Autowired
    public UnivService(UnivDataDto univDataDto, UnivMajorDataDto univMajorDataDto) {
        this.univDataDto = univDataDto;
        this.univMajorDataDto = univMajorDataDto;
    }


    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.ok()
                    .body(ResponseDto.response(HttpStatus.OK, "대학 목록 조회 성공", univDataDto.getRecords()));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(ResponseDto.response(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러 입니다.", null));
        }
    }

    public ResponseEntity<?> findByUnivName(String univName) {
        try {
            if(univName.isEmpty() || univName == null){
                throw new Exception("대학명이 null 입니다");
            }
            List<UnivDataDto.School> filter = univDataDto.getRecords().stream()
                    .filter(school -> school.get학교영문명().contains(univName) || school.get학교명().contains(univName))
                    .toList();
            UnivDataDto filteredDto = new UnivDataDto();
            filteredDto.setRecords(filter);
            return ResponseEntity.ok()
                    .body(ResponseDto.response(HttpStatus.OK, "대학 목록 조회 성공", filteredDto.getRecords()));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(ResponseDto.response(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러 입니다.", null));
        }
    }

    public ResponseEntity<?> findByUnivMajorName(String univName) {
        try {
            if(univName.isEmpty() || univName == null){
                throw new Exception("대학명이 null 입니다");
            }
            List<UnivMajorDataDto.Major> filter = univMajorDataDto.getRecords().stream()
                    .filter(major -> major.get학교명().contains(univName))
                    .toList();
            UnivMajorDataDto filteredDto = new UnivMajorDataDto();
            filteredDto.setRecords(filter);
            return ResponseEntity.ok()
                    .body(ResponseDto.response(HttpStatus.OK, "학과 목록 조회 성공", filteredDto.getRecords()));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(ResponseDto.response(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러 입니다.", null));
        }
    }


}
