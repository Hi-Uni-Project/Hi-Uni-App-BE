package com.project.hiuni.domain.schedule.repository;

import com.project.hiuni.domain.schedule.dto.CategoryDataDto;
import com.project.hiuni.domain.schedule.dto.response.CategoryResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryRepository {

  private final List<CategoryDataDto> categories;

  public Optional<CategoryDataDto> findById(Long id) {
    return categories.stream()
        .filter(category -> category.getCategoryid().equals(id))
        .findFirst();
  }

  public List<CategoryResponse> findAll() {
    List<CategoryResponse> response = categories.stream()
        .map(category -> {
          return CategoryResponse
              .builder()
              .categoryId(category.getCategoryid())
              .categoryName(category.getCategoryname())
              .backgroundColor(category.getBackgroundcolor())
              .textColor(category.getTextcolor())
              .build();
        })
        .toList();

    return response;
  }

}
