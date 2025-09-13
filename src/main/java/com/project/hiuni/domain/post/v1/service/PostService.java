package com.project.hiuni.domain.post.v1.service;

import com.project.hiuni.domain.post.dto.request.PostCreateRequest;
import com.project.hiuni.domain.post.dto.request.PostUpdateRequest;
import com.project.hiuni.domain.post.dto.response.PostCreateResponse;
import com.project.hiuni.domain.post.dto.response.PostDetailResponse;
import com.project.hiuni.domain.post.dto.response.PostUpdateResponse;
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
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
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

        return PostCreateResponse.from(postRepository.save(post));
    }

    @Transactional
    public PostDetailResponse searchPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new CustomPostNotFoundException(ErrorCode.NOT_FOUND));

        post.incrementViewCount();
        return PostDetailResponse.from(post);
    }

    @Transactional
    public PostUpdateResponse updatePost(PostUpdateRequest request, Long postId, Long userId){
        Post post = postRepository.findById(postId).orElseThrow(()-> new CustomPostNotFoundException(ErrorCode.NOT_FOUND));

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

    @Transactional
    public List<PostPreviewResponse> getWeeklyHotPosts(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        ZoneId zone = ZoneId.of("Asia/Seoul");
        LocalDate today = LocalDate.now(zone);

        LocalDateTime end   = today.with(DayOfWeek.SUNDAY).atStartOfDay();
        LocalDateTime start = end.minusWeeks(1);

        return postRepository.
                findWeeklyHot(start, end,user.getUnivName()).stream()
                .map(PostPreviewResponse::from)
                .toList();
    }

    @Transactional
    public List<PostPreviewResponse> getWeeklyPosts(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        ZoneId zone = ZoneId.of("Asia/Seoul");
        LocalDate today = LocalDate.now(zone);

        LocalDateTime end   = today.with(DayOfWeek.SUNDAY).atStartOfDay();
        LocalDateTime start = end.minusWeeks(1);

        return postRepository.
                findWeekly(start, end,user.getUnivName()).stream()
                .map(PostPreviewResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PostPreviewResponse> getKeywordPosts(String sort, String keyword, Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));
        String univName = user.getUnivName();

        Sort sortedPost = switch (sort){
            case "like" -> Sort.by(Sort.Order.desc("likeCount"));
            case "comment" -> Sort.by(Sort.Order.desc("commentCount"));
            default        -> Sort.by(Sort.Order.desc("createdAt"));
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
