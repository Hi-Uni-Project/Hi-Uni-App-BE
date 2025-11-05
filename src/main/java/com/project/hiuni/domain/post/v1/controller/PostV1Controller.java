package com.project.hiuni.domain.post.v1.controller;

import com.project.hiuni.domain.post.dto.request.PostCreateNoReviewRequest;
import com.project.hiuni.domain.post.dto.request.PostCreateReviewRequest;
import com.project.hiuni.domain.post.dto.request.PostUpdateNoReviewRequest;
import com.project.hiuni.domain.post.dto.request.PostUpdateReviewRequest;
import com.project.hiuni.domain.post.dto.response.PostCreateNoReviewResponse;
import com.project.hiuni.domain.post.dto.response.PostCreateReviewResponse;
import com.project.hiuni.domain.post.dto.response.PostNoReviewResponse;
import com.project.hiuni.domain.post.dto.response.PostReviewResponse;
import com.project.hiuni.domain.post.dto.response.PostUpdateNoReviewResponse;
import com.project.hiuni.domain.post.dto.response.PostUpdateReviewResponse;
import com.project.hiuni.domain.post.dto.response.PostPreviewResponse;
import com.project.hiuni.domain.post.entity.Category;
import com.project.hiuni.domain.post.entity.Type;
import com.project.hiuni.domain.post.v1.service.PostV1Service;
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

    private final PostV1Service postV1Service;

    @PostMapping("/no-review")
    public ResponseDTO<PostCreateNoReviewResponse> createNoReviewPost(@RequestBody @Valid PostCreateNoReviewRequest request,
                                                                      @AuthenticationPrincipal CustomUserDetails userDetails) {
        PostCreateNoReviewResponse postCreateNoReviewResponse = postV1Service.createNoReviewPost(request, userDetails.getId());

        return ResponseDTO.of(postCreateNoReviewResponse, "게시글 생성에 성공하였습니다.");
    }

    @PostMapping("/review")
    public ResponseDTO<PostCreateReviewResponse> createReviewPost(@RequestBody @Valid PostCreateReviewRequest request,
                                                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        PostCreateReviewResponse postCreateReviewResponse = postV1Service.createReviewPost(request, userDetails.getId());

        return ResponseDTO.of(postCreateReviewResponse,"게시글 생성에 성공하였습니다.");
    }

    @GetMapping("/no-review/{id}")
    public ResponseDTO<PostNoReviewResponse> searchNoReviewPost( @PathVariable Long id,
                                                                 @AuthenticationPrincipal CustomUserDetails userDetails) {
        PostNoReviewResponse postNoReviewResponse = postV1Service.searchNoReviewPost(id);
        return ResponseDTO.of(postNoReviewResponse, "게시글 조회에 성공하였습니다.");
    }

    @GetMapping("/review/{id}")
    public ResponseDTO<PostReviewResponse> searchReviewPost(@PathVariable Long id,
                                                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        PostReviewResponse postReviewResponse = postV1Service.searchReviewPost(id);
        return ResponseDTO.of(postReviewResponse, "게시글 조회에 성공하였습니다.");
    }

    @PutMapping("/review/{id}")
    public ResponseDTO<PostUpdateReviewResponse> updateReviewPost(@PathVariable Long id,
                                                                  @RequestBody @Valid PostUpdateReviewRequest request,
                                                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        PostUpdateReviewResponse postUpdateReviewResponse = postV1Service.updateReviewPost(request, id, userDetails.getId());

        return ResponseDTO.of(postUpdateReviewResponse, "게시글 수정에 성공하였습니다.");
    }

    @PutMapping("/no-review/{id}")
    public ResponseDTO<PostUpdateNoReviewResponse> updateNoReviewPost(@PathVariable Long id,
                                                                      @RequestBody @Valid PostUpdateNoReviewRequest request,
                                                                      @AuthenticationPrincipal CustomUserDetails userDetails) {
        PostUpdateNoReviewResponse postUpdateNoReviewResponse = postV1Service.updateNoReviewPost(request, id, userDetails.getId());

        return ResponseDTO.of(postUpdateNoReviewResponse, "게시글 수정에 성공하였습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Void> deletePost(@PathVariable Long id,
                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        postV1Service.deletePost(id, userDetails.getId());

        return ResponseDTO.of("게시글 삭제에 성공하였습니다.");
    }

    @GetMapping
    public ResponseDTO<List<PostPreviewResponse>> searchAllPosts(@RequestParam String sort,
                                                                 @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<PostPreviewResponse> postPreviewResponses = postV1Service.getAllPosts(sort,userDetails.getId());

        return ResponseDTO.of(postPreviewResponses,"게시글 목록 조회에 성공하였습니다.");
    }

    @GetMapping("/category")
    public ResponseDTO<List<PostPreviewResponse>> searchAllPostsByCategory(@RequestParam Category category,
                                                                           @RequestParam String sort,
                                                                           @AuthenticationPrincipal CustomUserDetails userDetails) {

        List<PostPreviewResponse> postPreviewResponses = postV1Service.getAllPostsByCategory(category,sort, userDetails.getId());

        return ResponseDTO.of(postPreviewResponses,"카테고리별 목록 조회에 성공하였습니다.");
    }

    @GetMapping("/type")
    public ResponseDTO<List<PostPreviewResponse>> searchAllPostsByType(@RequestParam Type type,
                                                                       @RequestParam String sort,
                                                                       @AuthenticationPrincipal CustomUserDetails userDetails){

        List<PostPreviewResponse> postPreviewResponses = postV1Service.getAllPostsByType(type,sort,userDetails.getId());

        return ResponseDTO.of(postPreviewResponses,"타입별 목록 조회에 성공하였습니다.");
    }

    @GetMapping("/weekly-hot")
    public ResponseDTO<List<PostPreviewResponse>> searchWeeklyHotPosts(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<PostPreviewResponse> postPreviewResponses = postV1Service.getWeeklyHotPosts(userDetails.getId());

        return ResponseDTO.of(postPreviewResponses,"게시글 목록 조회에 성공하였습니다.");
    }

    @GetMapping("/weekly-hot-type")
    public ResponseDTO<List<PostPreviewResponse>> searchWeeklyHotPostsByType(@RequestParam Type type,
                                                                            @AuthenticationPrincipal CustomUserDetails userDetails) {

        List<PostPreviewResponse> postPreviewResponses = postV1Service.getWeeklyHotPostByType(type,userDetails.getId());

        return ResponseDTO.of(postPreviewResponses, "타입별 인기 게시글 조회에 성공하였습니다.");
    }

    @GetMapping("/weekly-hot-category")
    public ResponseDTO<List<PostPreviewResponse>> searchWeeklyHotPostsByCategory(@RequestParam Category category,
                                                                                 @AuthenticationPrincipal CustomUserDetails userDetails) {

        List<PostPreviewResponse> postPreviewResponses = postV1Service.getWeeklyHotPostByCategory(category,userDetails.getId());

        return ResponseDTO.of(postPreviewResponses, "카테고리별 인기 게시글 조회에 성공하였습니다.");
    }

    @GetMapping("/search-category")
    public ResponseDTO<List<PostPreviewResponse>> searchPostsByCategory(@RequestParam Category category,
                                                              @RequestParam String keyword,
                                                              @RequestParam String sort,
                                                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        if(keyword.isBlank() || keyword.length() < 2) {
            throw new ValidationException(ErrorCode.INVALID_SEARCH_KEYWORD_LENGTH);
        }
        if(keyword.length()>15){
            throw new ValidationException(ErrorCode.INVALID_SEARCH_KEYWORD_MAXIMUM);
        }

        List<PostPreviewResponse> postPreviewResponses = postV1Service.getKeywordPostsByCategory(category, sort, keyword, userDetails.getId());

        return ResponseDTO.of(postPreviewResponses, "게시글 목록 조회에 성공하였습니다.");
    }

    @GetMapping("/search")
    public ResponseDTO<List<PostPreviewResponse>> searchAllPosts(@RequestParam String keyword,
                                                                 @RequestParam String sort,
                                                                 @AuthenticationPrincipal CustomUserDetails userDetails) {
        if(keyword.isBlank() || keyword.length() < 2) {
            throw new ValidationException(ErrorCode.INVALID_SEARCH_KEYWORD_LENGTH);
        }
        if(keyword.length()>15){
            throw new ValidationException(ErrorCode.INVALID_SEARCH_KEYWORD_MAXIMUM);
        }
        List<PostPreviewResponse> postPreviewResponses = postV1Service.getKeywordPosts(sort, keyword, userDetails.getId());

        return ResponseDTO.of(postPreviewResponses, "게시글 목록 조회에 성공하였습니다.");
    }

    @GetMapping("/my-posts")
    public ResponseDTO<List<PostPreviewResponse>> searchMyPosts(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<PostPreviewResponse> postPreviewResponses = postV1Service.getMyPosts(userDetails.getId());

        return ResponseDTO.of(postPreviewResponses, "내가 쓴 게시글 목록 조회에 성공하였습니다.");
    }
}
