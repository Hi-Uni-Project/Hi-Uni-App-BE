package com.project.hiuni.domain.post.v1.service;

import com.project.hiuni.domain.post.dto.response.PostPreviewResponse;
import com.project.hiuni.domain.post.entity.Post;
import com.project.hiuni.domain.post.entity.PostLike;
import com.project.hiuni.domain.post.exception.CustomDuplicatedLikeException;
import com.project.hiuni.domain.post.exception.CustomNotLikeException;
import com.project.hiuni.domain.post.exception.CustomPostNotFoundException;
import com.project.hiuni.domain.post.repository.PostLikeRepository;
import com.project.hiuni.domain.post.repository.PostRepository;
import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.exception.CustomUserNotFoundException;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostLikeV1Service {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addLike(Long postId, Long userId) {
        if (postLikeRepository.existsByPostIdAndUserId(postId, userId)){
            throw new CustomDuplicatedLikeException(ErrorCode.DUPLICATED_LIKE);
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new CustomPostNotFoundException(ErrorCode.POST_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        PostLike postLike = PostLike.builder()
                .post(post)
                .user(user)
                .build();

        try{
            postLikeRepository.save(postLike);
        }catch (DataIntegrityViolationException e){
            throw new CustomDuplicatedLikeException(ErrorCode.DUPLICATED_LIKE);
        }

        post.incrementLikeCount();
    }

    @Transactional
    public void removeLike(Long postId, Long userId) {
        PostLike postLike = postLikeRepository.findByPostIdAndUserId(postId, userId)
                .orElseThrow(() -> new CustomNotLikeException(ErrorCode.NOT_LIKE));

        Post post = postLike.getPost();
        postLikeRepository.delete(postLike);
        post.decrementLikeCount();
    }

    @Transactional
    public List<PostPreviewResponse> getLikedPostsByUserId(Long userId) {
        List<Post> posts = postLikeRepository.findLikedPostsByUserId(userId);

        return posts.stream()
                .map(PostPreviewResponse::from)
                .toList();
    }

}
