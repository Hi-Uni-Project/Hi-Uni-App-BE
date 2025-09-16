package com.project.hiuni.domain.post.v1.controller;


import com.project.hiuni.domain.post.dto.request.SearchLogRequest;
import com.project.hiuni.domain.post.v1.service.SearchLogService;
import com.project.hiuni.global.common.dto.response.ResponseDTO;
import com.project.hiuni.global.security.core.CustomUserDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/search-logs")
@RequiredArgsConstructor
public class SearchLogV1Controller {

    private final SearchLogService searchLogService;

    @PostMapping
    public ResponseDTO<Void> saveRecentSearchLog(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                 @RequestBody SearchLogRequest searchLogRequest) {

        searchLogService.saveRecentSearchLog(searchLogRequest, userDetails.getId());
        return ResponseDTO.of("최근 검색어 저장에 성공하였습니다.");
    }

    @GetMapping
    public ResponseDTO<List<SearchLogRequest>> getRecentSearchLogs(@AuthenticationPrincipal CustomUserDetails userDetails) {

        List<SearchLogRequest> searchLogRequests =searchLogService.findRecentSearchLogs(userDetails.getId());

        return ResponseDTO.of(searchLogRequests, "최근 검색어 조회에 성공하였습니다.");
    }

    @DeleteMapping
    public ResponseDTO<Void> deleteRecentSearchLog(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                   @RequestBody SearchLogRequest searchLogRequest) {

        searchLogService.deleteRecentSearchLog(searchLogRequest,userDetails.getId());
        return ResponseDTO.of("최근 검색어 삭제에 성공하였습니다.");
    }
}
