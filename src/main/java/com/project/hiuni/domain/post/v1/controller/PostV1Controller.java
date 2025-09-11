package com.project.hiuni.domain.post.v1.controller;

import com.project.hiuni.domain.post.dto.request.PostCreateRequest;
import com.project.hiuni.domain.post.dto.request.PostUpdateRequest;
import com.project.hiuni.domain.post.dto.response.PostCreateResponse;
import com.project.hiuni.domain.post.dto.response.PostDetailResponse;
import com.project.hiuni.domain.post.dto.response.PostUpdateResponse;
import com.project.hiuni.domain.post.dto.response.WeeklyHotPost;
import com.project.hiuni.domain.post.entity.Post;
import com.project.hiuni.domain.post.v1.service.PostService;
import com.project.hiuni.global.security.core.CustomUserDetails;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostV1Controller {

    private final PostService postService;

    @PostMapping
    public PostCreateResponse createPost(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @RequestBody @Valid PostCreateRequest postCreateRequest) {
        return postService.createPost(postCreateRequest, userDetails.getId());
    }

    @GetMapping("/{id}")
    public PostDetailResponse searchPost(@AuthenticationPrincipal CustomUserDetails userDetails,
                                         @PathVariable Long id) {

        return postService.searchPost(id);
    }

    @PutMapping("/{id}")
    public PostUpdateResponse updatePost(@PathVariable Long id,
                                         @AuthenticationPrincipal CustomUserDetails userDetails,
                                         @RequestBody @Valid PostUpdateRequest postUpdateRequest){
        return postService.updatePost(postUpdateRequest, id, userDetails.getId());
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id,
                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        postService.deletePost(id, userDetails.getId());
    }

    @GetMapping()
    public List<WeeklyHotPost> searchWeeklyHotPosts(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return postService.getWeeklyHotPosts();
    }



}
