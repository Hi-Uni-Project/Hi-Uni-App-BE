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
import com.project.hiuni.domain.post.entity.ExperiencePost;
import com.project.hiuni.domain.post.entity.InternshipPost;
import com.project.hiuni.domain.post.entity.InterviewPost;
import com.project.hiuni.domain.post.entity.JobPost;
import com.project.hiuni.domain.post.entity.LicensePost;
import com.project.hiuni.domain.post.entity.Post;
import com.project.hiuni.domain.post.entity.Type;
import com.project.hiuni.domain.post.exception.CustomForbiddenException;
import com.project.hiuni.domain.post.exception.CustomPostNotFoundException;
import com.project.hiuni.domain.post.repository.ExperiencePostRepository;
import com.project.hiuni.domain.post.repository.InternshipPostRepository;
import com.project.hiuni.domain.post.repository.InterviewPostRepository;
import com.project.hiuni.domain.post.repository.JobPostRepository;
import com.project.hiuni.domain.post.repository.LicensePostRepository;
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
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostV1Service {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final JobPostRepository jobPostRepository;
    private final InternshipPostRepository internshipPostRepository;
    private final InterviewPostRepository interviewPostRepository;
    private final ExperiencePostRepository experiencePostRepository;
    private final LicensePostRepository licensePostRepository;

    @Transactional
    public PostCreateNoReviewResponse createNoReviewPost(PostCreateNoReviewRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        final Category category = getCategory(request.type());

        Post saved = switch (request.type()) {
            case JOB -> jobPostRepository.save(
                    JobPost.builder()
                            .title(request.title())
                            .content(request.content())
                            .category(category)
                            .imageUrl(request.imageUrl())
                            .user(user)
                            // detail은 전부 null
                            .companyName(null)
                            .appliedPosition(null)
                            .applyMethod(null)
                            .interviewQuestions(null)
                            .preparation(null)
                            .result(null)
                            .feelings(null)
                            .additional(null)
                            .build()
            );
            case INTERNSHIP -> internshipPostRepository.save(
                    InternshipPost.builder()
                            .title(request.title())
                            .content(request.content())
                            .category(category)
                            .imageUrl(request.imageUrl())
                            .user(user)
                            .companyName(null)
                            .department(null)
                            .tasks(null)
                            .learned(null)
                            .feelings(null)
                            .additional(null)
                            .startDate(null)
                            .endDate(null)
                            .build()
            );
            case INTERVIEW -> interviewPostRepository.save(
                    InterviewPost.builder()
                            .title(request.title())
                            .content(request.content())
                            .category(category)
                            .imageUrl(request.imageUrl())
                            .user(user)
                            .companyName(null)
                            .appliedPosition(null)
                            .interviewFormat(null)
                            .interviewQuestions(null)
                            .preparation(null)
                            .atmosphere(null)
                            .feelings(null)
                            .additional(null)
                            .build()
            );
            case EXPERIENCE -> experiencePostRepository.save(
                    ExperiencePost.builder()
                            .title(request.title())
                            .content(request.content())
                            .category(category)
                            .imageUrl(request.imageUrl())
                            .user(user)
                            .organizationName(null)
                            .position(null)
                            .positionRank(null)
                            .whatWork(null)
                            .requiredSkills(null)
                            .characteristics(null)
                            .feelings(null)
                            .additional(null)
                            .startDate(null)
                            .endDate(null)
                            .build()
            );
            case LICENSE -> licensePostRepository.save(
                    LicensePost.builder()
                            .title(request.title())
                            .content(request.content())
                            .category(category)
                            .imageUrl(request.imageUrl())
                            .user(user)
                            .certificationName(null)
                            .prepDuration(null)
                            .materials(null)
                            .difficulty(null)
                            .studyMethod(null)
                            .tips(null)
                            .feelings(null)
                            .additional(null)
                            .build()
            );
        };

        return PostCreateNoReviewResponse.from(saved);
    }

    @Transactional
    public PostCreateReviewResponse createReviewPost(PostCreateReviewRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        Category category = getCategory(request.type());

        Post saved = switch (request.type()) {

            case JOB -> jobPostRepository.save(
                    JobPost.builder()
                            .title(request.title())
                            .content(request.content())
                            .category(category)
                            .imageUrl(request.imageUrl())
                            .user(user)
                            .companyName(request.firstQuestion())
                            .appliedPosition(request.secondQuestion())
                            .applyMethod(request.thirdQuestion())
                            .interviewQuestions(request.fourthQuestion())
                            .preparation(request.fifthQuestion())
                            .result(request.sixthQuestion())
                            .feelings(request.seventhQuestion())
                            .additional(request.eighthQuestion())
                            .build()
            );

            case INTERNSHIP -> internshipPostRepository.save(
                    InternshipPost.builder()
                            .title(request.title())
                            .content(request.content())
                            .category(category)
                            .imageUrl(request.imageUrl())
                            .user(user)
                            .companyName(request.firstQuestion())
                            .department(request.secondQuestion())
                            .tasks(request.thirdQuestion())
                            .learned(request.fourthQuestion())
                            .feelings(request.fifthQuestion())
                            .additional(request.sixthQuestion())
                            .startDate(request.startDate())
                            .endDate(request.endDate())
                            .build()
            );

            case INTERVIEW -> interviewPostRepository.save(
                    InterviewPost.builder()
                            .title(request.title())
                            .content(request.content())
                            .category(category)
                            .imageUrl(request.imageUrl())
                            .user(user)
                            .companyName(request.firstQuestion())
                            .appliedPosition(request.secondQuestion())
                            .interviewFormat(request.thirdQuestion())
                            .interviewQuestions(request.fourthQuestion())
                            .preparation(request.fifthQuestion())
                            .atmosphere(request.sixthQuestion())
                            .feelings(request.seventhQuestion())
                            .additional(request.eighthQuestion())
                            .build()
            );

            case EXPERIENCE -> experiencePostRepository.save(
                    ExperiencePost.builder()
                            .title(request.title())
                            .content(request.content())
                            .category(category)
                            .imageUrl(request.imageUrl())
                            .user(user)
                            .organizationName(request.firstQuestion())
                            .position(request.secondQuestion())
                            .positionRank(request.thirdQuestion())
                            .whatWork(request.fourthQuestion())
                            .requiredSkills(request.fifthQuestion())
                            .characteristics(request.sixthQuestion())
                            .feelings(request.seventhQuestion())
                            .additional(request.eighthQuestion())
                            .startDate(request.startDate())
                            .endDate(request.endDate())
                            .build()
            );

            case LICENSE -> licensePostRepository.save(
                    LicensePost.builder()
                            .title(request.title())
                            .content(request.content())
                            .category(category)
                            .imageUrl(request.imageUrl())
                            .user(user)
                            .certificationName(request.firstQuestion())
                            .prepDuration(request.secondQuestion())
                            .materials(request.thirdQuestion())
                            .difficulty(request.fourthQuestion())
                            .studyMethod(request.fifthQuestion())
                            .tips(request.sixthQuestion())
                            .feelings(request.seventhQuestion())
                            .additional(request.eighthQuestion())
                            .build()
            );
        };

        return PostCreateReviewResponse.from(saved);
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

        post.updateCommon(
                request.title(),
                request.content(),
                request.imageUrl(),
                post.getCategory()
        );
        return PostUpdateNoReviewResponse.from(post);
    }

    @Transactional
    public PostUpdateReviewResponse updateReviewPost(
            PostUpdateReviewRequest postUpdateReviewRequest, Long postId, Long userId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomPostNotFoundException(ErrorCode.POST_NOT_FOUND));

        if (!post.getUser().getId().equals(userId)) {
            throw new CustomForbiddenException(ErrorCode.FORBIDDEN);
        }

        post.updateCommon(
                postUpdateReviewRequest.title(),
                postUpdateReviewRequest.content(),
                postUpdateReviewRequest.imageUrl(),
                post.getCategory()
        );

        switch (post.getType()) {

            case JOB -> ((JobPost) post).updateDetail(
                    postUpdateReviewRequest.firstQuestion(),
                    postUpdateReviewRequest.secondQuestion(),
                    postUpdateReviewRequest.thirdQuestion(),
                    postUpdateReviewRequest.fourthQuestion(),
                    postUpdateReviewRequest.fifthQuestion(),
                    postUpdateReviewRequest.sixthQuestion(),
                    postUpdateReviewRequest.seventhQuestion(),
                    postUpdateReviewRequest.eighthQuestion()
            );

            case INTERNSHIP -> ((InternshipPost) post).updateDetail(
                    postUpdateReviewRequest.firstQuestion(),
                    postUpdateReviewRequest.secondQuestion(),
                    postUpdateReviewRequest.thirdQuestion(),
                    postUpdateReviewRequest.fourthQuestion(),
                    postUpdateReviewRequest.startDate(),
                    postUpdateReviewRequest.endDate(),
                    postUpdateReviewRequest.fifthQuestion(),
                    postUpdateReviewRequest.sixthQuestion()
            );

            case INTERVIEW -> ((InterviewPost) post).updateDetail(
                    postUpdateReviewRequest.firstQuestion(),
                    postUpdateReviewRequest.secondQuestion(),
                    postUpdateReviewRequest.thirdQuestion(),
                    postUpdateReviewRequest.fourthQuestion(),
                    postUpdateReviewRequest.fifthQuestion(),
                    postUpdateReviewRequest.sixthQuestion(),
                    postUpdateReviewRequest.seventhQuestion(),
                    postUpdateReviewRequest.eighthQuestion()
            );

            case EXPERIENCE -> ((ExperiencePost) post).updateDetail(
                    postUpdateReviewRequest.firstQuestion(),
                    postUpdateReviewRequest.startDate(),
                    postUpdateReviewRequest.endDate(),
                    postUpdateReviewRequest.secondQuestion(),
                    postUpdateReviewRequest.thirdQuestion(),
                    postUpdateReviewRequest.fourthQuestion(),
                    postUpdateReviewRequest.fifthQuestion(),
                    postUpdateReviewRequest.sixthQuestion(),
                    postUpdateReviewRequest.seventhQuestion(),
                    postUpdateReviewRequest.eighthQuestion()
            );

            case LICENSE -> ((LicensePost) post).updateDetail(
                    postUpdateReviewRequest.firstQuestion(),
                    postUpdateReviewRequest.secondQuestion(),
                    postUpdateReviewRequest.thirdQuestion(),
                    postUpdateReviewRequest.fourthQuestion(),
                    postUpdateReviewRequest.fifthQuestion(),
                    postUpdateReviewRequest.sixthQuestion(),
                    postUpdateReviewRequest.seventhQuestion(),
                    postUpdateReviewRequest.eighthQuestion()
            );
        }

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
                // .filter(post -> post.getLikeCount()>=3)
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

    public List<PostPreviewResponse> getAllPostsByCategory(Category category, String sort, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        String univName = user.getUnivName();

        Sort sortedPost = switch (sort) {
            case "like" -> Sort.by(Sort.Order.desc("likeCount"));
            case "comment" -> Sort.by(Sort.Order.desc("commentCount"));
            default -> Sort.by(Sort.Order.desc("createdAt"));
        };

        return postRepository.findAllPostsByCategory(univName,category, sortedPost)
                .stream()
                .map(PostPreviewResponse::from)
                .toList();
    }

    public List<PostPreviewResponse> getAllPostsByType(Type type, String sort, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        String univName = user.getUnivName();

        Sort sortedPost = switch (sort) {
            case "like" -> Sort.by(Sort.Order.desc("likeCount"));
            case "comment" -> Sort.by(Sort.Order.desc("commentCount"));
            default -> Sort.by(Sort.Order.desc("createdAt"));
        };

        return postRepository.findAllPostsByType(univName,type,sortedPost)
                .stream()
                .map(PostPreviewResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PostPreviewResponse> getKeywordPostsByCategory(Category category, String sort, String keyword, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));
        String univName = user.getUnivName();

        Sort sortedPost = switch (sort) {
            case "like" -> Sort.by(Sort.Order.desc("likeCount"));
            case "comment" -> Sort.by(Sort.Order.desc("commentCount"));
            default -> Sort.by(Sort.Order.desc("createdAt"));
        };

        return postRepository.searchByKeywordAndUnivAndCategory(keyword, univName, category, sortedPost)
                .stream()
                .map(PostPreviewResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PostPreviewResponse> getWeeklyHotPostByType(Type type, Long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        ZoneId zone = ZoneId.of("Asia/Seoul");
        LocalDate today = LocalDate.now(zone);

        LocalDateTime end   = today.with(DayOfWeek.SUNDAY).atStartOfDay();
        LocalDateTime start = end.minusWeeks(1);

        return postRepository.findWeeklyHotByType(start, end, user.getUnivName(),type)
                .stream()
                // .filter(post -> post.getLikeCount()>=3)
                .map(PostPreviewResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PostPreviewResponse> getWeeklyHotPostByCategory(Category category, Long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        ZoneId zone = ZoneId.of("Asia/Seoul");
        LocalDate today = LocalDate.now(zone);

        LocalDateTime end   = today.with(DayOfWeek.SUNDAY).atStartOfDay();
        LocalDateTime start = end.minusWeeks(1);

        return postRepository.findWeeklyHotByCategory(start, end, user.getUnivName(),category)
                .stream()
                // .filter(post -> post.getLikeCount()>=3)
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

    @Transactional(readOnly = true)
    public List<PostPreviewResponse> getMyPosts(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        List<Post> posts = postRepository.findAllByUserId(user.getId());

        return posts.stream()
                .map(PostPreviewResponse::from)
                .collect(Collectors.toList());
    }



    private static Category getCategory(Type type) {
        return switch (type) {
            case JOB, INTERNSHIP, INTERVIEW, EXPERIENCE, LICENSE -> Category.JOBINFORMATION;
        };
    }
}
