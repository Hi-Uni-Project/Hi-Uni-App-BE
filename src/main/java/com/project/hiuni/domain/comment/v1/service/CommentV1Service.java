package com.project.hiuni.domain.comment.v1.service;

import com.project.hiuni.domain.comment.dto.request.CommentReplyCreateRequest;
import com.project.hiuni.domain.comment.exception.CustomCommentNotFoundException;
import com.project.hiuni.domain.comment.dto.request.CommentCreateRequest;
import com.project.hiuni.domain.comment.dto.request.CommentUpdateRequest;
import com.project.hiuni.domain.comment.dto.response.CommentCreateResponse;
import com.project.hiuni.domain.comment.dto.response.CommentResponse;
import com.project.hiuni.domain.comment.dto.response.CommentUpdateResponse;
import com.project.hiuni.domain.comment.entity.Comment;
import com.project.hiuni.domain.comment.repository.CommentLikeRepository;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentV1Service {

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
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

    @Transactional
    public CommentCreateResponse createReply(CommentReplyCreateRequest request, Long postId, Long userId){

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomPostNotFoundException(ErrorCode.POST_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        Comment parent = commentRepository.findById(request.parentCommentId())
                .orElseThrow(() -> new CustomCommentNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

        if (!parent.getPost().getId().equals(postId)) {
            throw new CustomForbiddenException(ErrorCode.FORBIDDEN);
        }

        Comment reply = Comment.builder()
                .content(request.content())
                .post(post)
                .user(user)
                .parent(parent)
                .build();

        return CommentCreateResponse.from(commentRepository.save(reply));

    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getAllComments(Long postId, Long userId) {

        List<Long> likedCommentIds = commentLikeRepository.findLikedCommentIds(postId, userId);

        Set<Long> likedSet=new HashSet<>(likedCommentIds);

        return commentRepository
                .findParentCommentsByPostId(postId)
                .stream()
                .map(comment -> CommentResponse.from(comment,likedSet, userId))
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
    public CommentUpdateResponse updateReply(
            Long parentId,
            Long replyId,
            CommentUpdateRequest request,
            Long userId
    ) {
        Comment reply = commentRepository.findById(replyId)
                .orElseThrow(() -> new CustomCommentNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

        if (reply.getParent() == null) {
            throw new CustomForbiddenException(ErrorCode.FORBIDDEN);
        }

        if (!reply.getParent().getId().equals(parentId)) {
            throw new CustomForbiddenException(ErrorCode.FORBIDDEN);
        }

        if (!reply.getUser().getId().equals(userId)) {
            throw new CustomForbiddenException(ErrorCode.FORBIDDEN);
        }

        reply.updateComment(request.content());

        return CommentUpdateResponse.from(reply);
    }

    @Transactional
    public void deleteComment(Long id, Long userId) {

        Comment comment =commentRepository.findById(id)
                .orElseThrow(()-> new CustomCommentNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

        if(!comment.getUser().getId().equals(userId)) {
            throw new CustomForbiddenException(ErrorCode.FORBIDDEN);
        }

        int deleteCount = 1 + comment.getChildren().size();

        Post post = comment.getPost();

        commentRepository.delete(comment);
    }

    @Transactional
    public void deleteReply(Long parentId, Long replyId, Long userId) {
        Comment reply = commentRepository.findById(replyId)
                .orElseThrow(() -> new CustomCommentNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

        if (reply.getParent() == null) {
            throw new CustomForbiddenException(ErrorCode.FORBIDDEN);
        }

        if (!reply.getParent().getId().equals(parentId)) {
            throw new CustomForbiddenException(ErrorCode.FORBIDDEN);
        }

        if (!reply.getUser().getId().equals(userId)) {
            throw new CustomForbiddenException(ErrorCode.FORBIDDEN);
        }

        Post post = reply.getPost();

        commentRepository.delete(reply);
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

    @Transactional
    public void deleteAllByUser(User user) {
        commentRepository.deleteAllByPostUser(user);
        commentRepository.deleteAllByUser(user);
    }
}