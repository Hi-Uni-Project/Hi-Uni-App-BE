package com.project.hiuni.domain.comment.v1.controller;

import com.project.hiuni.domain.comment.dto.request.CommentCreateRequest;
import com.project.hiuni.domain.comment.dto.request.CommentUpdateRequest;
import com.project.hiuni.domain.comment.dto.response.CommentCreateResponse;
import com.project.hiuni.domain.comment.dto.response.CommentResponse;
import com.project.hiuni.domain.comment.dto.response.CommentUpdateResponse;
import com.project.hiuni.domain.comment.v1.service.CommentV1Service;
import com.project.hiuni.domain.post.dto.response.PostPreviewResponse;
import com.project.hiuni.domain.post.v1.service.PostV1Service;
import com.project.hiuni.global.common.dto.response.ResponseDTO;
import com.project.hiuni.global.security.core.CustomUserDetails;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentV1Controller {

    private final CommentV1Service commentV1Service;

    @PostMapping("/{postId}")
    public ResponseDTO<CommentCreateResponse> createComment(@PathVariable Long postId,
                                                            @RequestBody CommentCreateRequest request,
                                                            @AuthenticationPrincipal CustomUserDetails userDetails) {

        CommentCreateResponse commentCreateResponse = commentV1Service.createComment(request,postId,userDetails.getId());

        return ResponseDTO.of(commentCreateResponse, "댓글이 작성되었습니다.");
    }

    @GetMapping("/{postId}")
    public ResponseDTO<List<CommentResponse>> searchAllComment(@PathVariable Long postId,
                                                               @AuthenticationPrincipal CustomUserDetails userDetails) {

        List<CommentResponse> commentResponse = commentV1Service.getAllComments(postId);

        return ResponseDTO.of(commentResponse, "댓글 조회에 성공하였습니다.");
    }

    @PutMapping("/{id}")
    public ResponseDTO<CommentUpdateResponse> updateComment(@PathVariable Long id,
                                                            @RequestBody CommentUpdateRequest request,
                                                            @AuthenticationPrincipal CustomUserDetails userDetails) {

        CommentUpdateResponse commentUpdateResponse = commentV1Service.updateComment(id,request);

        return ResponseDTO.of(commentUpdateResponse, "댓글 수정에 성공하였습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Void> deleteComment(@PathVariable Long id,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {

        commentV1Service.deleteComment(id,userDetails.getId());

        return ResponseDTO.of("댓글 삭제에 성공하였습니다.");
    }

    @GetMapping("/my-comments")
    public ResponseDTO<List<PostPreviewResponse>> searchMyCommentsFromPost(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<PostPreviewResponse> postPreviewResponses = commentV1Service.getMyCommentsPosts(userDetails.getId());

        return ResponseDTO.of(postPreviewResponses, "내가 쓴 댓글의 게시글 목록 조회에 성공하였습니다.");
    }

}
