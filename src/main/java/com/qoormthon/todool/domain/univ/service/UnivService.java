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

            if(univName.length() <= 1) {
                return ResponseEntity.badRequest()
                        .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "대학명은 2글자 이상이어야 합니다.", null));
            }

            if(univName.equals("대학") || univName.equals("대학교") || univName.equals("학교")){
                return ResponseEntity.badRequest()
                        .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "대학명이 유효하지 않습니다.", null));
            }

            List<UnivDataDto.School> filter = univDataDto.getRecords().stream()
                    .filter(school -> school.getUnivName().contains(univName))
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
            if(univName.isEmpty() || univName == null || univName.equals(" ")){
                throw new Exception("대학명이 null 입니다");
            }

            if(univName.length() <= 1) {
                return ResponseEntity.badRequest()
                        .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "대학명은 2글자 이상이어야 합니다.", null));
            }

            if(univName.equals("대학") || univName.equals("대학교") || univName.equals("학교")){
                return ResponseEntity.badRequest()
                        .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "대학명이 유효하지 않습니다.", null));
            }

            List<UnivMajorDataDto.Major> filter = univMajorDataDto.getRecords().stream()
                    .filter(major -> major.getUnivName().contains(univName))
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
