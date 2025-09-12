package com.project.hiuni.domain.univ.v1.docs;

import com.project.hiuni.domain.univ.dto.UnivDataDto.School;
import com.project.hiuni.global.common.dto.response.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "전국 대학교 및 학과 조회 api 입니다.", description = "전국에 있는 학교와 학과를 조회할 수 있습니다. / 데이터 출처: https://www.data.go.kr/index.do")
public interface UnivV1Docs {


  @ApiResponse(
      responseCode = "200",
      description = "조회 성공",
      content = @Content(
          mediaType = "application/json",
          examples = @ExampleObject(
              value =
                  """
                      [
                          {
                              "univName": "경기과학기술대학교",
                              "univTypeName": "전문대학",
                              "websiteUrl": "www.gtec.ac.kr"
                          },
                          {
                              "univName": "경북과학대학교",
                              "univTypeName": "전문대학",
                              "websiteUrl": "www.kbsc.ac.kr"
                          }
                      ]
                  """
          )
      )
  )
  @ApiResponse(
      responseCode = "500",
      description = "조회 실패",
      content = @Content(
          mediaType = "application/json",
          examples = @ExampleObject(
              value =
                  """
                      {
                          "code": "INTERNAL_SERVER_ERROR",
                          "message": "서버 내부 오류가 발생했습니다.",
                          "errors": []
                      }
                  """
          )
      )
  )
  @Operation(summary = "대학교를 모두 조회합니다.", description = "리스트 형태로 반환됩니다.")
  ResponseDTO<List<School>> findAllSchools();


  @ApiResponse(
      responseCode = "200",
      description = "조회 성공",
      content = @Content(
          mediaType = "application/json",
          examples = @ExampleObject(
              value =
                  """
                      [
                          {
                              "univName": "경기과학기술대학교",
                              "univTypeName": "전문대학",
                              "websiteUrl": "www.gtec.ac.kr"
                          },
                          {
                              "univName": "경북과학대학교",
                              "univTypeName": "전문대학",
                              "websiteUrl": "www.kbsc.ac.kr"
                          }
                      ]
                  """
          )
      )
  )
  @ApiResponse(
      responseCode = "400",
      description = "조회 실패",
      content = @Content(
          mediaType = "application/json",
          examples = @ExampleObject(
              value =
                  """
                      {
                          "code": "INVALID_SEARCH_KEYWORD_LENGTH",
                          "message": "검색어는 2자 이상 입력해주세요.",
                          "errors": []
                      }
                  """
          )
      )
  )
  @ApiResponse(
      responseCode = "500",
      description = "조회 실패",
      content = @Content(
          mediaType = "application/json",
          examples = @ExampleObject(
              value =
                  """
                      {
                          "code": "INTERNAL_SERVER_ERROR",
                          "message": "서버 내부 오류가 발생했습니다.",
                          "errors": []
                      }
                  """
          )
      )
  )
  @Operation(summary = "대학교 이름으로 조회합니다.", description = "부분 일치 검색 방식이며 리스트 형태로 반환됩니다.")
  ResponseDTO<List<School>> findSchoolsByUnivName(String keyword);

}
