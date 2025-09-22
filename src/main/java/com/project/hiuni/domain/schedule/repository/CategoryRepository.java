package com.project.hiuni.domain.schedule.repository;

import com.project.hiuni.domain.schedule.dto.CategoryDataDto;
import com.project.hiuni.domain.schedule.dto.response.CategoryResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryRepository {

  private final List<CategoryDataDto> categories;

  public CategoryDataDto findById(Long id) {
    return categories.stream()
        .filter(category -> category.getCategoryid().equals(id))
        .findFirst()
        .orElse(null);
  }

  public List<CategoryResponse> findAll() {
    List<CategoryResponse> response = categories.stream()
        .map(category -> {
          return CategoryResponse
              .builder()
              .categoryId(category.getCategoryid())
              .categoryName(category.getCategoryname())
              .build();
        })
        .toList();

    return response;
  }

}
