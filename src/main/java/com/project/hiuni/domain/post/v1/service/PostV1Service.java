package com.project.hiuni.domain.post.v1.service;

import com.project.hiuni.domain.bookmark.repository.BookmarkRepository;
import com.project.hiuni.domain.post.common.PostPreviewMapper;
import com.project.hiuni.domain.comment.projection.PostCommentCount;
import com.project.hiuni.domain.comment.repository.CommentRepository;
import com.project.hiuni.domain.post.common.WeekRange;
import com.project.hiuni.domain.post.dto.request.PostCreateNoReviewRequest;
import com.project.hiuni.domain.post.dto.request.PostCreateReviewRequest;
import com.project.hiuni.domain.post.dto.request.PostUpdateNoReviewRequest;
import com.project.hiuni.domain.post.dto.request.PostUpdateReviewRequest;
import com.project.hiuni.domain.post.dto.response.ExperienceMyReviewResponse;
import com.project.hiuni.domain.post.dto.response.InternshipMyReviewResponse;
import com.project.hiuni.domain.post.dto.response.PostCreateNoReviewResponse;
import com.project.hiuni.domain.post.dto.response.PostCreateReviewResponse;
import com.project.hiuni.domain.post.dto.response.PostMyReviewResponse;
import com.project.hiuni.domain.post.dto.response.PostNoReviewResponse;
import com.project.hiuni.domain.post.dto.response.PostReviewResponse;
import com.project.hiuni.domain.post.dto.response.PostUpdateNoReviewResponse;
import com.project.hiuni.domain.post.dto.response.PostUpdateReviewResponse;
import com.project.hiuni.domain.post.dto.response.PostPreviewResponse;
import com.project.hiuni.domain.post.dto.response.PostWeeklyHotPreviewResponse;
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
import com.project.hiuni.domain.post.repository.PostLikeRepository;
import com.project.hiuni.domain.post.repository.PostRepository;
import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.exception.CustomUserNotFoundException;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.exception.ErrorCode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostV1Service {

    private final PostPreviewMapper postPreviewMapper;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final BookmarkRepository bookmarkRepository;
    private final CommentRepository commentRepository;
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
                            .user(user)
                            .isReview(false)
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
                            .user(user)
                            .isReview(false)
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
                            .user(user)
                            .isReview(false)
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
                            .user(user)
                            .isReview(false)
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
                            .user(user)
                            .isReview(false)
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
                            .user(user)
                            .isReview(true)
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
                            .user(user)
                            .isReview(true)
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
                            .user(user)
                            .isReview(true)
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
                            .user(user)
                            .isReview(true)
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
                            .user(user)
                            .isReview(true)
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
    public PostNoReviewResponse searchNoReviewPost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomPostNotFoundException(ErrorCode.POST_NOT_FOUND));

        boolean isLiked=postLikeRepository.existsByPostIdAndUserId(postId, userId);
        boolean isScrap=bookmarkRepository.existsByPostIdAndUserId(postId, userId);
        boolean isUser = post.getUser().getId().equals(userId);
        int commentCount = commentRepository.countAllByPostId(postId);

        post.incrementViewCount();
        return PostNoReviewResponse.from(post, isLiked, isScrap, isUser,commentCount);
    }

    @Transactional
    public PostReviewResponse searchReviewPost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomPostNotFoundException(ErrorCode.POST_NOT_FOUND));

        boolean isLiked=postLikeRepository.existsByPostIdAndUserId(postId, userId);
        boolean isScrap=bookmarkRepository.existsByPostIdAndUserId(postId, userId);
        boolean isUser = post.getUser().getId().equals(userId);
        int commentCount = commentRepository.countAllByPostId(postId);

        post.incrementViewCount();
        return PostReviewResponse.from(post,isLiked, isScrap, isUser,commentCount);
    }

    @Transactional
    public PostUpdateNoReviewResponse updateNoReviewPost(PostUpdateNoReviewRequest request, Long postId, Long userId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomPostNotFoundException(ErrorCode.POST_NOT_FOUND));

        int commentCount = commentRepository.countAllByPostId(postId);

        if (!post.getUser().getId().equals(userId)) {
            throw new CustomForbiddenException(ErrorCode.FORBIDDEN);
        }

        post.updateCommon(
                request.title(),
                request.content(),
                post.getCategory()
        );

        return PostUpdateNoReviewResponse.from(post,commentCount);
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
    public List<PostPreviewResponse> getWeeklyHotPosts(String sort, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        LocalDateTime start = WeekRange.start();
        LocalDateTime end   = WeekRange.end();

        List<Post> posts = postRepository.findWeeklyHot(start, end, user.getUnivName())
                .stream()
                .filter(post -> post.getLikeCount() >= 3)
                .toList();

        List<PostPreviewResponse> postPreviewResponses = postPreviewMapper.toPreviewResponses(posts);
        postPreviewResponses.sort(getPreviewComparator(sort));

        return postPreviewResponses;
    }

    public List<PostWeeklyHotPreviewResponse> getWeeklyHotPreviewTop4Posts(String sort, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        LocalDateTime start = WeekRange.start();
        LocalDateTime end   = WeekRange.end();

        List<Post> posts = postRepository.findWeeklyHot(start, end, user.getUnivName())
                .stream()
                .filter(post -> post.getLikeCount() >= 3)
                .toList();

        List<Long> postIds = posts.stream().map(Post::getId).toList();

        Map<Long, Integer> commentCountMap = commentRepository
                .countByPostIds(postIds)
                .stream()
                .collect(Collectors.toMap(
                        PostCommentCount::getPostId,
                        PostCommentCount::getCount
                ));

        List<PostWeeklyHotPreviewResponse> previews = new ArrayList<>(posts.stream()
                .map(post -> PostWeeklyHotPreviewResponse.from(
                        post, commentCountMap.getOrDefault(post.getId(), 0)
                ))
                .toList());

        previews.sort(getWeeklyHotPreviewComparator(sort));

        return previews.stream()
                .limit(4)
                .toList();

    }

    @Transactional(readOnly = true)
    public List<PostPreviewResponse> getAllPosts(String sort, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        List<Post> posts = postRepository.findAllPosts(user.getUnivName());

        List<PostPreviewResponse> postPreviewResponses = postPreviewMapper.toPreviewResponses(posts);
        postPreviewResponses.sort(getPreviewComparator(sort));

        return postPreviewResponses;
    }

    public List<PostPreviewResponse> getAllPostsByCategory(Category category, String sort, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        String univName = user.getUnivName();

        List<Post> posts = postRepository.findAllPostsByCategory(univName, category);
        List<PostPreviewResponse> postPreviewResponses = postPreviewMapper.toPreviewResponses(posts);
        postPreviewResponses.sort(getPreviewComparator(sort));
        return postPreviewResponses;
    }

    public List<PostPreviewResponse> getAllPostsByType(Type type, String sort, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        String univName = user.getUnivName();

        List<Post> posts = postRepository.findAllPostsByType(univName, type);
        List<PostPreviewResponse> postPreviewResponses = postPreviewMapper.toPreviewResponses(posts);
        postPreviewResponses.sort(getPreviewComparator(sort));
        return postPreviewResponses;
    }

    @Transactional(readOnly = true)
    public List<PostPreviewResponse> getKeywordPostsByCategory(Category category, String sort, String keyword, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));
        String univName = user.getUnivName();

        List<Post> posts = postRepository.searchByKeywordAndUnivAndCategory(keyword, univName, category);
        List<PostPreviewResponse> postPreviewResponses = postPreviewMapper.toPreviewResponses(posts);
        postPreviewResponses.sort(getPreviewComparator(sort));
        return postPreviewResponses;
    }

    @Transactional(readOnly = true)
    public List<PostPreviewResponse> getWeeklyHotPostsByType(Type type, String sort, Long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        LocalDateTime start = WeekRange.start();
        LocalDateTime end   = WeekRange.end();

        List<Post> posts=postRepository
                .findWeeklyHotByType(start,end,user.getUnivName(),type)
                .stream()
                .filter(post -> post.getLikeCount()>=3)
                .toList();

        List<PostPreviewResponse> postPreviewResponses = postPreviewMapper.toPreviewResponses(posts);
        postPreviewResponses.sort(getPreviewComparator(sort));

        return postPreviewResponses;
    }

    @Transactional(readOnly = true)
    public List<PostPreviewResponse> getWeeklyHotPostsByCategory(Category category, String sort, Long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        LocalDateTime start = WeekRange.start();
        LocalDateTime end   = WeekRange.end();

        List<Post> posts = postRepository
                .findWeeklyHotByCategory(start, end, user.getUnivName(), category)
                .stream()
                .filter(post -> post.getLikeCount() >= 3)
                .toList();

        List<PostPreviewResponse> postPreviewResponses = postPreviewMapper.toPreviewResponses(posts);
        postPreviewResponses.sort(getPreviewComparator(sort));
        return postPreviewResponses;
    }

    @Transactional(readOnly = true)
    public List<PostPreviewResponse> getKeywordPosts(String sort, String keyword, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));
        String univName = user.getUnivName();

        List<Post> posts=postRepository.searchByKeywordAndUniv(keyword, univName);
        List<PostPreviewResponse> postPreviewResponses = postPreviewMapper.toPreviewResponses(posts);
        postPreviewResponses.sort(getPreviewComparator(sort));
        return postPreviewResponses;
    }

    @Transactional(readOnly = true)
    public List<PostPreviewResponse> getMyPosts(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        List<Post> posts = postRepository.findAllByUserId(user.getId());

        return postPreviewMapper.toPreviewResponses(posts);
    }

    public List<PostMyReviewResponse> getMyReviewPosts(Long userId) {

        List<PostMyReviewResponse> result = new ArrayList<>();

        experiencePostRepository.findReviewPostsByUser(userId)
                .forEach(p -> result.add(
                        new ExperienceMyReviewResponse(
                                p.getCategory(),
                                p.getType(),
                                p.getTitle(),
                                p.getContent(),
                                p.getOrganizationName(),
                                p.getPosition(),
                                p.getPositionRank(),
                                p.getWhatWork(),
                                p.getStartDate(),
                                p.getEndDate()
                        )
                ));

        internshipPostRepository.findReviewPostsByUser(userId)
                .forEach(p -> result.add(
                        new InternshipMyReviewResponse(
                                p.getCategory(),
                                p.getType(),
                                p.getTitle(),
                                p.getContent(),
                                p.getCompanyName(),
                                p.getDepartment(),
                                p.getTasks(),
                                p.getStartDate(),
                                p.getEndDate()
                        )
                ));

        result.sort(Comparator.comparing(PostMyReviewResponse::startDate).reversed());

        return result;
    }

    private static Category getCategory(Type type) {
        return switch (type) {
            case JOB, INTERNSHIP, INTERVIEW, EXPERIENCE, LICENSE -> Category.JOBINFORMATION;
        };
    }

    /*
[
  [
    "삼성전자 DX본부",      // organizationName
    "백엔드 개발 인턴",      // position
    "사원급",               // positionRank
    "API 개발 및 테스트",    // whatWork
    "Java / Spring",       // requiredSkills
    "협업 중심",            // characteristics
    "많이 성장함",          // feelings
    "추가 팁"               // additional
  ],
  [
    "카카오",               // companyName
    "서버팀",               // department
    "로그 시스템 구축",       // tasks
    "운영 자동화 경험",       // learned
    "배움이 많았음",         // feelings
    "추가 내용"              // additional
  ] 이런식으로 자기 후기에 대해서 반환되는 로직입니다. 자기소개서 AI 이용 시 사용해주세요!
     */
    public List<List<String>> getMyReviewDetailList(Long userId) {

        List<Post> result = new ArrayList<>();

        result.addAll(experiencePostRepository.findReviewPostsByUser(userId));
        result.addAll(internshipPostRepository.findReviewPostsByUser(userId));
        result.addAll(interviewPostRepository.findReviewPostsByUser(userId));
        result.addAll(jobPostRepository.findReviewPostsByUser(userId));
        result.addAll(licensePostRepository.findReviewPostsByUser(userId));

        return result.stream()
                .map(this::extractDetailFields)
                .toList();
    }

    private List<String> extractDetailFields(Post p) {
        List<String> details = new ArrayList<>();

        if (p instanceof ExperiencePost e) {
            add(details, e.getOrganizationName());
            add(details, e.getPosition());
            add(details, e.getPositionRank());
            add(details, e.getWhatWork());
            add(details, e.getRequiredSkills());
            add(details, e.getCharacteristics());
            add(details, e.getFeelings());
            add(details, e.getAdditional());
        }

        else if (p instanceof InternshipPost i) {
            add(details, i.getCompanyName());
            add(details, i.getDepartment());
            add(details, i.getTasks());
            add(details, i.getLearned());
            add(details, i.getFeelings());
            add(details, i.getAdditional());
        }

        else if (p instanceof InterviewPost i) {
            add(details, i.getCompanyName());
            add(details, i.getAppliedPosition());
            add(details, i.getInterviewFormat());
            add(details, i.getInterviewQuestions());
            add(details, i.getPreparation());
            add(details, i.getAtmosphere());
            add(details, i.getFeelings());
            add(details, i.getAdditional());
        }

        else if (p instanceof JobPost j) {
            add(details, j.getCompanyName());
            add(details, j.getAppliedPosition());
            add(details, j.getApplyMethod());
            add(details, j.getInterviewQuestions());
            add(details, j.getPreparation());
            add(details, j.getResult());
            add(details, j.getFeelings());
            add(details, j.getAdditional());
        }

        else if (p instanceof LicensePost l) {
            add(details, l.getCertificationName());
            add(details, l.getPrepDuration());
            add(details, l.getMaterials());
            add(details, l.getDifficulty());
            add(details, l.getStudyMethod());
            add(details, l.getTips());
            add(details, l.getFeelings());
            add(details, l.getAdditional());
        }

        return details;
    }

    private void add(List<String> list, String value) {
        if (value != null && !value.isBlank()) {
            list.add(value);
        }
    }

    private Comparator<PostPreviewResponse> getPreviewComparator(String sort) {

        if ("like".equals(sort)) {
            return Comparator.comparing(PostPreviewResponse::likeCount).reversed()
                    .thenComparing(PostPreviewResponse::createdAt).reversed();
        }

        if ("comment".equals(sort)) {
            return Comparator.comparing(PostPreviewResponse::commentCount).reversed()
                    .thenComparing(PostPreviewResponse::createdAt).reversed();
        }

        return Comparator.comparing(PostPreviewResponse::createdAt).reversed();
    }

    private Comparator<PostWeeklyHotPreviewResponse> getWeeklyHotPreviewComparator(String sort) {
        if ("like".equals(sort)) {
            return Comparator.comparing(PostWeeklyHotPreviewResponse::likeCount).reversed()
                    .thenComparing(PostWeeklyHotPreviewResponse::createdAt).reversed();
        }

        if ("comment".equals(sort)) {
            return Comparator.comparing(PostWeeklyHotPreviewResponse::commentCount).reversed()
                    .thenComparing(PostWeeklyHotPreviewResponse::createdAt).reversed();
        }

        return Comparator.comparing(PostWeeklyHotPreviewResponse::createdAt).reversed();
    }
}
