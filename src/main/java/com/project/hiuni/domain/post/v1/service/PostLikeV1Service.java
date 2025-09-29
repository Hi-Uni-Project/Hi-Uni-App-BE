package com.project.hiuni.domain.post.v1.service;

import com.project.hiuni.domain.post.repository.PostLikeRepository;
import com.project.hiuni.domain.post.repository.PostRepository;
import com.project.hiuni.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeV1Service {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void addLike(Long postId, Long userId) {
        if (postLikeRepository.existsByPostIdAndUserId(postId, userId)) return;

    }

}
