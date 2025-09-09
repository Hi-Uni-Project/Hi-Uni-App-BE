package com.project.hiuni.domain.post.v1.service;

import com.project.hiuni.domain.post.dto.request.PostCreateRequest;
import com.project.hiuni.domain.post.dto.request.PostUpdateRequest;
import com.project.hiuni.domain.post.dto.response.PostCreateResponse;
import com.project.hiuni.domain.post.dto.response.PostDetailResponse;
import com.project.hiuni.domain.post.dto.response.PostUpdateResponse;
import com.project.hiuni.domain.post.entity.Post;
import com.project.hiuni.domain.post.exception.CustomForbiddenException;
import com.project.hiuni.domain.post.exception.CustomPostNotFoundException;
import com.project.hiuni.domain.post.repository.PostRepository;
import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.exception.CustomUserNotFoundException;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostCreateResponse createPost(PostCreateRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        Post post = Post.builder()
                .title(request.title())
                .content(request.content())
                .companyName(request.companyName())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .type(request.type())
                .userPosition(request.userPosition())
                .whatLearn(request.whatLearn())
                .feelings(request.feelings())
                .imageUrl(request.imageUrl())
                .user(user)
                .build();

        return PostCreateResponse.from(postRepository.save(post));
    }

    @Transactional(readOnly = true)
    public PostDetailResponse searchPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new CustomPostNotFoundException(ErrorCode.NOT_FOUND));

        return PostDetailResponse.from(post);
    }

    @Transactional
    public PostUpdateResponse updatePost(PostUpdateRequest request, Long postId, Long userId ){
        Post post = postRepository.findById(postId).orElseThrow(()-> new CustomPostNotFoundException(ErrorCode.NOT_FOUND));

        if (!post.getUser().getId().equals(userId)) {
            throw new CustomForbiddenException(ErrorCode.FORBIDDEN);
        }

        post.updatePost(
                request.title(),
                request.content(),
                request.companyName(),
                request.startDate(),
                request.endDate(),
                request.type(),
                request.userPosition(),
                request.whatLearn(),
                request.feelings(),
                request.imageUrl()

        );

        return PostUpdateResponse.from(post);
    }

    @Transactional
    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new CustomPostNotFoundException(ErrorCode.NOT_FOUND));

        if(!post.getUser().getId().equals(userId)) {
            throw new CustomForbiddenException(ErrorCode.FORBIDDEN);
        }

        postRepository.delete(post);
    }
}
