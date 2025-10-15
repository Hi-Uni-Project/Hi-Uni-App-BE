package com.project.hiuni.domain.comment.v1.service;

import com.project.hiuni.domain.comment.exception.CustomCommentNotFoundException;
import com.project.hiuni.domain.comment.dto.request.CommentCreateRequest;
import com.project.hiuni.domain.comment.dto.request.CommentUpdateRequest;
import com.project.hiuni.domain.comment.dto.response.CommentCreateResponse;
import com.project.hiuni.domain.comment.dto.response.CommentResponse;
import com.project.hiuni.domain.comment.dto.response.CommentUpdateResponse;
import com.project.hiuni.domain.comment.entity.Comment;
import com.project.hiuni.domain.comment.repository.CommentRepository;
import com.project.hiuni.domain.post.dto.response.PostPreviewResponse;
import com.project.hiuni.domain.post.entity.Post;
import com.project.hiuni.domain.post.exception.CustomForbiddenException;
import com.project.hiuni.domain.post.exception.CustomPostNotFoundException;
import com.project.hiuni.domain.post.repository.PostRepository;
import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.exception.CustomUserNotFoundException;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.exception.ErrorCode;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentV1Service {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentCreateResponse createComment(CommentCreateRequest request, Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new CustomPostNotFoundException(ErrorCode.POST_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        Comment comment = Comment.builder()
                .content(request.content())
                .post(post)
                .user(user)
                .build();

        return CommentCreateResponse.from(commentRepository.save(comment));
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getAllComments(Long postId) {

        return commentRepository.findAllByPostIdOrderByCreatedAtAsc(postId)
                .stream()
                .map(CommentResponse::from)
                .toList();
    }

    @Transactional
    public CommentUpdateResponse updateComment(Long id, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()-> new CustomCommentNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

        comment.updateComment(request.content());

        return CommentUpdateResponse.from(comment);
    }

    @Transactional
    public void deleteComment(Long id, Long userId) {

        Comment comment =commentRepository.findById(id)
                .orElseThrow(()-> new CustomCommentNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

        if(!comment.getUser().getId().equals(userId)) {
            throw new CustomForbiddenException(ErrorCode.FORBIDDEN);
        }

        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public List<PostPreviewResponse> getMyCommentsPosts(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        List<Post> posts = commentRepository.findPostsCommentedByUser(user.getId());

        return posts.stream()
                .map(PostPreviewResponse::from)
                .toList();
    }
}