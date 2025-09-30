package com.project.hiuni.domain.post.v1.controller;

import com.project.hiuni.domain.post.v1.service.PostLikeV1Service;
import com.project.hiuni.global.common.dto.response.ResponseDTO;
import com.project.hiuni.global.security.core.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts/{postId}/likes")
@RequiredArgsConstructor
public class PostLikeV1Controller {

    private final PostLikeV1Service postLikeV1Service;

    @PostMapping
    public ResponseDTO<Void> addLike(@PathVariable("postId") Long postId,
                                     @AuthenticationPrincipal CustomUserDetails userDetails) {
        postLikeV1Service.addLike(postId, userDetails.getId());
        return ResponseDTO.of("좋아요가 추가되었습니다.");
    }

    @DeleteMapping
    public ResponseDTO<Void> removeLike(@PathVariable("postId") Long postId,
                                        @AuthenticationPrincipal CustomUserDetails userDetails) {
        postLikeV1Service.removeLike(postId, userDetails.getId());
        return ResponseDTO.of("좋아요가 삭제되었습니다.");
    }

}