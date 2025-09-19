package com.project.hiuni.domain.comment.v1.service;

import com.project.hiuni.domain.comment.CustomCommentNotFoundException;
import com.project.hiuni.domain.comment.dto.request.CommentUpdateRequest;
import com.project.hiuni.domain.comment.dto.response.CommentUpdateResponse;
import com.project.hiuni.domain.comment.entity.Comment;
import com.project.hiuni.domain.comment.repository.CommentRepository;
import com.project.hiuni.domain.post.entity.Post;
import com.project.hiuni.domain.post.exception.CustomForbiddenException;
import com.project.hiuni.domain.post.exception.CustomPostNotFoundException;
import com.project.hiuni.domain.post.repository.PostRepository;
import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.exception.CustomUserNotFoundException;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Comment addComment(String content, Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new CustomPostNotFoundException(ErrorCode.POST_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        Comment comment = Comment.builder()
                .content(content)
                .post(post)
                .user(user)
                .build();

        return commentRepository.save(comment);
    }

    @Transactional
    public List<Comment> getAllComments(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new CustomPostNotFoundException(ErrorCode.POST_NOT_FOUND));

        return commentRepository.findByPost(post);
    }

    @Transactional
    public CommentUpdateResponse updateComment(Long commentId, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new CustomCommentNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

        comment.updateComment(request.content());

        return CommentUpdateResponse.from(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, Long userId) {

        Comment comment =commentRepository.findById(commentId)
                .orElseThrow(()-> new CustomCommentNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

        if(!comment.getUser().getId().equals(userId)) {
            throw new CustomForbiddenException(ErrorCode.FORBIDDEN);
        }

        commentRepository.delete(comment);
    }
}