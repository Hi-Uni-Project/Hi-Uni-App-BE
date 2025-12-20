package com.project.hiuni.domain.comment.v1.controller;

import com.project.hiuni.domain.comment.v1.service.CommentLikeV1Service;
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
@RequestMapping("/api/v1/comments/{commentId}/likes")
@RequiredArgsConstructor
public class CommentLikeV1Controller {

    private final CommentLikeV1Service commentLikeV1Service;

    @PostMapping
    public ResponseDTO<Void> addLike(@PathVariable Long commentId,
                                     @AuthenticationPrincipal CustomUserDetails userDetails) {
        commentLikeV1Service.addLike(commentId, userDetails.getId());
        return ResponseDTO.of("좋아요가 추가되었습니다.");
    }

    @PostMapping("/reply/{replyId}")
    public ResponseDTO<Void> likeReply(
            @PathVariable Long commentId,
            @PathVariable Long replyId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        commentLikeV1Service.addReplyLike(commentId, replyId, userDetails.getId());
        return ResponseDTO.of("답글 좋아요가 추가되었습니다.");
    }

    @DeleteMapping
    public ResponseDTO<Void> removeLike(@PathVariable Long commentId,
                                        @AuthenticationPrincipal CustomUserDetails userDetails) {
        commentLikeV1Service.removeLike(commentId, userDetails.getId());
        return ResponseDTO.of("좋아요가 취소되었습니다.");
    }

    @DeleteMapping("/reply/{replyId}")
    public ResponseDTO<Void> unlikeReply(
            @PathVariable Long commentId,
            @PathVariable Long replyId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        commentLikeV1Service.removeReplyLike(commentId, replyId, userDetails.getId());
        return ResponseDTO.of("답글 좋아요 취소에 성공하였습니다.");
    }

}