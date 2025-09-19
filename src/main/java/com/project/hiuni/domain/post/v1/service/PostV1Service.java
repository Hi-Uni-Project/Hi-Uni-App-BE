package com.project.hiuni.domain.post.v1.service;

import com.project.hiuni.domain.post.dto.request.PostCreateNoReviewRequest;
import com.project.hiuni.domain.post.dto.request.PostCreateReviewRequest;
import com.project.hiuni.domain.post.dto.request.PostUpdateNoReviewRequest;
import com.project.hiuni.domain.post.dto.request.PostUpdateReviewRequest;
import com.project.hiuni.domain.post.dto.response.PostCreateNoReviewResponse;
import com.project.hiuni.domain.post.dto.response.PostCreateReviewResponse;
import com.project.hiuni.domain.post.dto.response.PostNoReviewResponse;
import com.project.hiuni.domain.post.dto.response.PostReviewResponse;
import com.project.hiuni.domain.post.dto.response.PostUpdateNoReviewResponse;
import com.project.hiuni.domain.post.dto.response.PostUpdateReviewResponse;
import com.project.hiuni.domain.post.dto.response.PostPreviewResponse;
import com.project.hiuni.domain.post.entity.Category;
import com.project.hiuni.domain.post.entity.Post;
import com.project.hiuni.domain.post.entity.Type;
import com.project.hiuni.domain.post.exception.CustomForbiddenException;
import com.project.hiuni.domain.post.exception.CustomPostNotFoundException;
import com.project.hiuni.domain.post.repository.PostRepository;
import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.exception.CustomUserNotFoundException;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.exception.ErrorCode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostV1Service {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostCreateNoReviewResponse createNoReviewPost(PostCreateNoReviewRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        Category category = getCategory(request.type());

        Post post = Post.builder()
                .title(request.title())
                .content(request.content())
                .type(request.type())
                .category(category)
                .imageUrl(request.imageUrl())
                .user(user)
                .build();

        return PostCreateNoReviewResponse.from(postRepository.save(post));
    }

    @Transactional
    public PostCreateReviewResponse createReviewPost(PostCreateReviewRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        Category category = getCategory(request.type());

        Post post = Post.builder()
                .title(request.title())
                .content(request.content())
                .companyName(request.companyName())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .type(request.type())
                .category(category)
                .userPosition(request.userPosition())
                .whatLearn(request.whatLearn())
                .feelings(request.feelings())
                .imageUrl(request.imageUrl())
                .user(user)
                .build();

        return PostCreateReviewResponse.from(postRepository.save(post));
    }

    @Transactional
    public PostNoReviewResponse searchNoReviewPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomPostNotFoundException(ErrorCode.POST_NOT_FOUND));

        post.incrementViewCount();
        return PostNoReviewResponse.from(post);
    }

    @Transactional
    public PostReviewResponse searchReviewPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomPostNotFoundException(ErrorCode.POST_NOT_FOUND));

        post.incrementViewCount();
        return PostReviewResponse.from(post);
    }

    @Transactional
    public PostUpdateNoReviewResponse updateNoReviewPost(PostUpdateNoReviewRequest request, Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomPostNotFoundException(ErrorCode.POST_NOT_FOUND));

        if (!post.getUser().getId().equals(userId)) {
            throw new CustomForbiddenException(ErrorCode.FORBIDDEN);
        }

        Category category = getCategory(request.type());

        post.updatePost(
                request.title(),
                request.content(),
                post.getCompanyName(),
                post.getStartDate(),
                post.getEndDate(),
                request.type(),
                category,
                post.getUserPosition(),
                post.getWhatLearn(),
                post.getFeelings(),
                request.imageUrl()
        );

        return PostUpdateNoReviewResponse.from(postRepository.save(post));
    }

    @Transactional
    public PostUpdateReviewResponse updateReviewPost(PostUpdateReviewRequest request, Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomPostNotFoundException(ErrorCode.POST_NOT_FOUND));

        if (!post.getUser().getId().equals(userId)) {
            throw new CustomForbiddenException(ErrorCode.FORBIDDEN);
        }

        Category category = getCategory(request.type());

        post.updatePost(
                request.title(),
                request.content(),
                request.companyName(),
                request.startDate(),
                request.endDate(),
                request.type(),
                category,
                request.userPosition(),
                request.whatLearn(),
                request.feelings(),
                request.imageUrl()
        );

        return PostUpdateReviewResponse.from(post);
    }

    @Transactional
    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomPostNotFoundException(ErrorCode.POST_NOT_FOUND));

        if (!post.getUser().getId().equals(userId)) {
            throw new CustomForbiddenException(ErrorCode.FORBIDDEN);
        }

        postRepository.delete(post);
    }

    @Transactional
    public List<PostPreviewResponse> getWeeklyHotPosts(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        ZoneId zone = ZoneId.of("Asia/Seoul");
        LocalDate today = LocalDate.now(zone);

        LocalDateTime end   = today.with(DayOfWeek.SUNDAY).atStartOfDay();
        LocalDateTime start = end.minusWeeks(1);

        return postRepository.findWeeklyHot(start, end, user.getUnivName())
                .stream()
                .map(PostPreviewResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PostPreviewResponse> getAllPosts(String sort, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        String univName = user.getUnivName();

        Sort sortedPost = switch (sort) {
            case "like" -> Sort.by(Sort.Order.desc("likeCount"));
            case "comment" -> Sort.by(Sort.Order.desc("commentCount"));
            default -> Sort.by(Sort.Order.desc("createdAt"));
        };

        return postRepository.findAllPosts(univName, sortedPost)
                .stream()
                .map(PostPreviewResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PostPreviewResponse> getKeywordPosts(String sort, String keyword, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));
        String univName = user.getUnivName();

        Sort sortedPost = switch (sort) {
            case "like" -> Sort.by(Sort.Order.desc("likeCount"));
            case "comment" -> Sort.by(Sort.Order.desc("commentCount"));
            default -> Sort.by(Sort.Order.desc("createdAt"));
        };

        return postRepository.searchByKeywordAndUniv(keyword, univName, sortedPost)
                .stream()
                .map(PostPreviewResponse::from)
                .toList();
    }

    private static Category getCategory(Type type) {
        return switch (type) {
            case JOB, INTERNSHIP, INTERVIEW, EXPERIENCE, LICENSE -> Category.JOBINFORMATION;
            case EDUCATION, CLUB -> Category.EXTERNALACTIVITIES;
        };
    }
}
