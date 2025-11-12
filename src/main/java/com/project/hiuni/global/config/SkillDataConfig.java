package com.project.hiuni.global.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.hiuni.domain.record.resume.dto.SkillDataDto;
import java.io.InputStream;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SkillDataConfig {

  private final ResourceLoader resourceLoader;
  private final ObjectMapper objectMapper;

  @Bean
  public List<SkillDataDto> loadSkillData() {
    try {
      Resource resource = resourceLoader.getResource("classpath:static/data/scheduleCategoryData.json");
      InputStream inputStream = resource.getInputStream();
      return objectMapper.readValue(inputStream,
          new TypeReference<List<SkillDataDto>>() {});
    } catch (Exception e) {
      log.error("파싱 중 에러 발생 :" + e.getMessage());
      return null;
    }
  }

}
