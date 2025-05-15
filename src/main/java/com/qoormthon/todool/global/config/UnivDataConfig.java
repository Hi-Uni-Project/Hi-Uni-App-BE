package com.qoormthon.todool.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qoormthon.todool.domain.univ.dto.UnivDataDto;
import com.qoormthon.todool.domain.univ.dto.UnivMajorDataDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class UnivDataConfig {
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    @Autowired
    public UnivDataConfig(ResourceLoader resourceLoader, ObjectMapper objectMapper) {
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
    }

    @Bean
    public UnivDataDto loadUnivData(){

        Map<String, UnivDataDto.School> uniqueSchools = new HashMap<>();

        try {
            Resource resource = resourceLoader.getResource("classpath:static/data/univdata.json");
            InputStream inputStream = resource.getInputStream();
            UnivDataDto univDataDto = objectMapper.readValue(inputStream, UnivDataDto.class);
            univDataDto.getRecords().stream()
                    .filter(school -> !"대학원".equals(school.getUnivTypeName()))
                    .forEach(school -> uniqueSchools.putIfAbsent(school.getUnivName(), school));

            List<UnivDataDto.School> filter = new ArrayList<>(uniqueSchools.values());
            UnivDataDto filteredDto = new UnivDataDto();
            filteredDto.setRecords(filter);
            return filteredDto;
        } catch (Exception e) {
            log.error("파싱 중 에러 발생 :" + e.getMessage());
            return new UnivDataDto();
        }
    }

    @Bean
    public UnivMajorDataDto univMajorDataDto() {
        try {
            Resource resource = resourceLoader.getResource("classpath:static/data/univMajorData.json");
            InputStream inputStream = resource.getInputStream();
            UnivMajorDataDto univMajorDataDto = objectMapper.readValue(inputStream, UnivMajorDataDto.class);

            List<UnivMajorDataDto.Major> filter = univMajorDataDto.getRecords().stream()
                    .filter(major -> !major.getUnivName().contains("대학원"))
                    .toList();
            UnivMajorDataDto filteredDto = new UnivMajorDataDto();
            filteredDto.setRecords(filter);

            filter = filteredDto.getRecords().stream()
                    .distinct()
                    .collect(Collectors.toList());

            filteredDto.setRecords(filter);

            return filteredDto;
        } catch (Exception e) {
            log.error("파싱 중 에러 발생 :" + e.getMessage());
            return new UnivMajorDataDto();
        }
    }

}
