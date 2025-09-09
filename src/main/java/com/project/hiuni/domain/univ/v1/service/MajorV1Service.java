package com.project.hiuni.domain.univ.v1.service;


import com.project.hiuni.domain.univ.dto.MajorDataDto;
import com.project.hiuni.domain.univ.dto.MajorDataDto.Major;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MajorV1Service {

  private final MajorDataDto majorDataDto;

  public List<Major> findMajorsByUnivName(String univName, String keyword) {

    List<Major> filteredMajors = majorDataDto.getRecords().stream()
        .filter(major -> major.getUnivName().equals(univName))
        .filter(major -> major.getMajorName().contains(keyword))
        .toList();

    return filteredMajors;

  }





}
