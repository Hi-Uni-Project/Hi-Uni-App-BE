package com.project.hiuni.global.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.hiuni.domain.schedule.dto.CategoryDataDto;
import com.project.hiuni.domain.univ.dto.UnivDataDto;
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
public class ScheduleCategoryConfig {

  private final ResourceLoader resourceLoader;
  private final ObjectMapper objectMapper;

  @Bean
  public List<CategoryDataDto> loadCategoryData() {
    try {
      Resource resource = resourceLoader.getResource("classpath:static/data/scheduleCategoryData.json");
      InputStream inputStream = resource.getInputStream();
      return objectMapper.readValue(inputStream,
          new TypeReference<List<CategoryDataDto>>() {});
    } catch (Exception e) {
      log.error("파싱 중 에러 발생 :" + e.getMessage());
      return null;
    }
  }


}
