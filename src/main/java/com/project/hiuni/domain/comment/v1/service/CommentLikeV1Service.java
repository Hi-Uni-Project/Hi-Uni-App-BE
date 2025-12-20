package com.project.hiuni.domain.comment.v1.service;

import com.project.hiuni.domain.comment.entity.Comment;
import com.project.hiuni.domain.comment.entity.CommentLike;
import com.project.hiuni.domain.comment.exception.CustomCommentNotFoundException;
import com.project.hiuni.domain.comment.repository.CommentLikeRepository;
import com.project.hiuni.domain.comment.repository.CommentRepository;
import com.project.hiuni.domain.post.exception.CustomDuplicatedLikeException;
import com.project.hiuni.domain.post.exception.CustomNotLikeException;
import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.exception.CustomUserNotFoundException;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeV1Service {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addLike(Long commentId, Long userId) {
        if (commentLikeRepository.existsByCommentIdAndUserId(commentId, userId)) {
            throw new CustomDuplicatedLikeException(ErrorCode.DUPLICATED_LIKE);
        }

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomCommentNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        CommentLike commentLike = CommentLike.builder()
                .comment(comment)
                .user(user)
                .build();

        try{
            commentLikeRepository.save(commentLike);
        } catch (DataIntegrityViolationException e){
            throw new CustomDuplicatedLikeException(ErrorCode.DUPLICATED_LIKE);
        }

        comment.increaseLikeCount();
    }

    @Transactional
    public void addReplyLike(Long commentId, Long replyId, Long userId) {

        Comment reply = commentRepository.findById(replyId)
                .orElseThrow(() -> new CustomCommentNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

        if (reply.getParent() == null) {
            throw new CustomCommentNotFoundException(ErrorCode.COMMENT_NOT_FOUND);
        }

        if (!reply.getParent().getId().equals(commentId)) {
            throw new CustomCommentNotFoundException(ErrorCode.COMMENT_NOT_FOUND);
        }

        if (commentLikeRepository.existsByCommentIdAndUserId(replyId, userId)) {
            throw new CustomDuplicatedLikeException(ErrorCode.DUPLICATED_LIKE);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        CommentLike like = CommentLike.builder()
                .comment(reply)
                .user(user)
                .build();

        try {
            commentLikeRepository.save(like);
        } catch (DataIntegrityViolationException e) {
            throw new CustomDuplicatedLikeException(ErrorCode.DUPLICATED_LIKE);
        }

        reply.increaseLikeCount();
    }

    @Transactional
    public void removeLike(Long commentId, Long userId) {
        CommentLike commentLike = commentLikeRepository.findByCommentIdAndUserId(commentId, userId)
                .orElseThrow(() -> new CustomNotLikeException(ErrorCode.NOT_LIKE));

        Comment comment = commentLike.getComment();
        commentLikeRepository.delete(commentLike);
        comment.decreaseLikeCount();
    }

    @Transactional
    public void removeReplyLike(Long commentId, Long replyId, Long userId) {

        Comment reply = commentRepository.findById(replyId)
                .orElseThrow(() -> new CustomCommentNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

        if (reply.getParent() == null) {
            throw new CustomCommentNotFoundException(ErrorCode.COMMENT_NOT_FOUND);
        }

        if (!reply.getParent().getId().equals(commentId)) {
            throw new CustomCommentNotFoundException(ErrorCode.COMMENT_NOT_FOUND);
        }

        CommentLike commentLike = commentLikeRepository
                .findByCommentIdAndUserId(replyId, userId)
                .orElseThrow(() -> new CustomNotLikeException(ErrorCode.NOT_LIKE));

        commentLikeRepository.delete(commentLike);
        reply.decreaseLikeCount();
    }

    @Transactional
    public void deleteAllByUser(User user) {
        commentLikeRepository.deleteAllByPostUser(user);
        commentLikeRepository.deleteAllByUser(user);
    }

}
