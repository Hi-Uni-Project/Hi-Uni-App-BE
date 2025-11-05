package com.project.hiuni.domain.post.v1.controller;

import com.project.hiuni.domain.post.dto.response.PostPreviewResponse;
import com.project.hiuni.domain.post.v1.service.PostLikeV1Service;
import com.project.hiuni.global.common.dto.response.ResponseDTO;
import com.project.hiuni.global.security.core.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts/likes")
@RequiredArgsConstructor
public class PostLikeV1Controller {

    private final PostLikeV1Service postLikeV1Service;

    @PostMapping("/{postId}")
    public ResponseDTO<Void> addLike(@PathVariable("postId") Long postId,
                                     @AuthenticationPrincipal CustomUserDetails userDetails) {
        postLikeV1Service.addLike(postId, userDetails.getId());
        return ResponseDTO.of("좋아요가 추가되었습니다.");
    }

    @DeleteMapping("/{postId}")
    public ResponseDTO<Void> removeLike(@PathVariable("postId") Long postId,
                                        @AuthenticationPrincipal CustomUserDetails userDetails) {
        postLikeV1Service.removeLike(postId, userDetails.getId());
        return ResponseDTO.of("좋아요가 삭제되었습니다.");
    }

    @GetMapping("/my")
    public ResponseDTO<List<PostPreviewResponse>> getMyLikedPosts(@AuthenticationPrincipal CustomUserDetails userDetails){
        List<PostPreviewResponse> likedPosts= postLikeV1Service.getLikedPostsByUserId(userDetails.getId());

        return ResponseDTO.of(likedPosts, "내가 좋아요한 게시글들이 조회되었습니다.");
    }

}