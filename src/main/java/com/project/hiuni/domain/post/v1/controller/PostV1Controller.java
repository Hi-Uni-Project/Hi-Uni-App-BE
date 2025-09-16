package com.project.hiuni.domain.post.v1.controller;

import com.project.hiuni.domain.post.dto.request.PostCreateNoReviewRequest;
import com.project.hiuni.domain.post.dto.request.PostCreateReviewRequest;
import com.project.hiuni.domain.post.dto.request.PostUpdateRequest;
import com.project.hiuni.domain.post.dto.response.PostCreateNoReviewResponse;
import com.project.hiuni.domain.post.dto.response.PostCreateReviewResponse;
import com.project.hiuni.domain.post.dto.response.PostDetailResponse;
import com.project.hiuni.domain.post.dto.response.PostUpdateResponse;
import com.project.hiuni.domain.post.dto.response.PostPreviewResponse;
import com.project.hiuni.domain.post.v1.service.PostService;
import com.project.hiuni.global.common.dto.response.ResponseDTO;
import com.project.hiuni.global.exception.ErrorCode;
import com.project.hiuni.global.exception.ValidationException;
import com.project.hiuni.global.security.core.CustomUserDetails;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostV1Controller {

    private final PostService postService;

    @PostMapping("/no-review")
    public ResponseDTO<PostCreateNoReviewResponse> createNoReviewPost(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                      @RequestBody @Valid PostCreateNoReviewRequest request) {
        PostCreateNoReviewResponse postCreateNoReviewRequest = postService.createNoReviewRequest(request, userDetails.getId());

        return ResponseDTO.of(postCreateNoReviewRequest, "게시글 생성에 성공하였습니다.");
    }

    @PostMapping("/review")
    public ResponseDTO<PostCreateReviewResponse> createReviewPost(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                            @RequestBody @Valid PostCreateReviewRequest request) {
        PostCreateReviewResponse postCreateReviewResponse = postService.createReviewPost(request, userDetails.getId());

        return ResponseDTO.of(postCreateReviewResponse,"게시글 생성에 성공하였습니다.");
    }

    @GetMapping("/{id}")
    public ResponseDTO<PostDetailResponse> searchPost(@AuthenticationPrincipal CustomUserDetails userDetails,
                                         @PathVariable Long id) {
        PostDetailResponse postDetailResponse = postService.searchPost(id);
        return ResponseDTO.of(postDetailResponse, "게시글 조회에 성공하였습니다.");
    }

    @PutMapping("/{id}")
    public ResponseDTO<PostUpdateResponse> updatePost(@PathVariable Long id,
                                         @AuthenticationPrincipal CustomUserDetails userDetails,
                                         @RequestBody @Valid PostUpdateRequest postUpdateRequest){
        PostUpdateResponse postUpdateResponse = postService.updatePost(postUpdateRequest, id, userDetails.getId());

        return ResponseDTO.of(postUpdateResponse, "게시글 수정에 성공하였습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Void> deletePost(@PathVariable Long id,
                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        postService.deletePost(id, userDetails.getId());

        return ResponseDTO.of("게시글 삭제에 성공하였습니다.");
    }

    @GetMapping
    public ResponseDTO<List<PostPreviewResponse>> searchAllPosts(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                    @RequestParam String sort) {
        List<PostPreviewResponse> postPreviewResponses = postService.getAllPosts(sort,userDetails.getId());

        return ResponseDTO.of(postPreviewResponses,"게시글 목록 조회에 성공하였습니다.");
    }

    @GetMapping("/weekly-hot")
    public ResponseDTO<List<PostPreviewResponse>> searchWeeklyHotPosts(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<PostPreviewResponse> postPreviewResponses = postService.getWeeklyHotPosts(userDetails.getId());

        return ResponseDTO.of(postPreviewResponses,"게시글 목록 조회에 성공하였습니다.");
    }

    @GetMapping("/search")
    public ResponseDTO<List<PostPreviewResponse>> searchPosts(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                              @RequestParam String keyword,
                                                              @RequestParam String sort) {
        if(keyword.isBlank() || keyword.length() < 2) {
            throw new ValidationException(ErrorCode.INVALID_SEARCH_KEYWORD_LENGTH);
        }
        if(keyword.length()>15){
            throw new ValidationException(ErrorCode.INVALID_SEARCH_KEYWORD_MAXIMUM);
        }

        List<PostPreviewResponse> postPreviewResponses = postService.getKeywordPosts(sort, keyword, userDetails.getId());

        return ResponseDTO.of(postPreviewResponses, "게시글 목록 조회에 성공하였습니다.");
    }

}
