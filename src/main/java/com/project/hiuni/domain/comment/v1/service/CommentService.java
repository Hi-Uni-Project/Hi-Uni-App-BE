package com.project.hiuni.domain.comment.v1.service;

import com.project.hiuni.domain.comment.entity.Comment;
import com.project.hiuni.domain.comment.repository.CommentRepository;
import com.project.hiuni.domain.post.entity.Post;
import com.project.hiuni.domain.post.exception.CustomPostNotFoundException;
import com.project.hiuni.domain.post.repository.PostRepository;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.exception.ErrorCode;
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
    }

}