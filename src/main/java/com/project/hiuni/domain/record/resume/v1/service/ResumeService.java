package com.project.hiuni.domain.record.resume.v1.service;

import com.project.hiuni.domain.post.entity.Post;
import com.project.hiuni.domain.post.exception.CustomPostNotFoundException;
import com.project.hiuni.domain.post.repository.PostRepository;
import com.project.hiuni.domain.record.exception.InsufficientGenerationCountException;
import com.project.hiuni.domain.record.resume.career.entity.Career;
import com.project.hiuni.domain.record.resume.career.repository.CareerRepository;
import com.project.hiuni.domain.record.resume.dto.request.ResumeRequest;
import com.project.hiuni.domain.record.resume.dto.response.AiAboutMeResponse;
import com.project.hiuni.domain.record.resume.education.entity.Education;
import com.project.hiuni.domain.record.resume.education.repository.EducationRepository;
import com.project.hiuni.domain.record.resume.entity.Resume;
import com.project.hiuni.domain.record.resume.exception.CustomInvalidException;
import com.project.hiuni.domain.record.resume.exception.CustomResumeNotFoundException;
import com.project.hiuni.domain.record.resume.project.entity.Project;
import com.project.hiuni.domain.record.resume.project.repository.ProjectRepository;
import com.project.hiuni.domain.record.resume.repository.ResumeRepository;
import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.exception.CustomUserNotFoundException;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.common.util.ImageUtil;
import com.project.hiuni.global.exception.ErrorCode;
import com.project.hiuni.global.exception.InternalServerException;
import com.project.hiuni.infra.claude.ClaudeAiClient;
import com.project.hiuni.infra.claude.prompt.ClaudeAiPrompt;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResumeService {

  private final UserRepository userRepository;
  private final PostRepository postRepository;
  private final ResumeRepository resumeRepository;

  private final CareerRepository careerRepository;
  private final ProjectRepository projectRepository;
  private final EducationRepository educationRepository;

  private final ClaudeAiClient claudeAiClient;

  private final ImageUtil imageUtil;

  @Transactional
  public void createOrUpdateResume(
      MultipartFile file,
      ResumeRequest request,
      Long userId) {

    try {

      User user = userRepository.findById(userId)
          .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

      Resume resume = resumeRepository.findByUser(user)
          .orElse(
              Resume.of(user, request.getName(), request.getGender(), request.getBirthYear(), request.getTitle(), request.getAboutMe(), null)
          );

      String IMAGE_PATH = "/home/ubuntu/image";
      String imageUrl = imageUtil.saveImage(file, IMAGE_PATH);

      // 이력서 기본정보 업데이트
      resume.updateImageUrl(imageUrl);
      resume.updateName(request.getName());
      resume.updateGender(request.getGender());
      resume.updateBirthYear(request.getBirthYear());
      resume.updateTitle(request.getTitle());
      resume.updateAboutMe(request.getAboutMe());

      // 이력서 리스트 업데이트
      // 1. 경력 사항
      request.getCareers().forEach(career -> {
        if(career.getCareerId() == null) {
          careerRepository.save(career.toEntity(resume));
        } else {
          Career originCareer = careerRepository.findById(career.getCareerId())
              .orElseThrow(() -> new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR));

          originCareer.update(career.toEntity(resume));
        }
      });

      // 2. 프로젝트 사항
      request.getProjects().forEach(project -> {
        if(project.getProjectId() == null) {
          projectRepository.save(project.toEntity(resume));
        } else {
          Project originProject = projectRepository.findById(project.getProjectId())
              .orElseThrow(() -> new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR));

          originProject.update(project.toEntity(resume));
        }
      });

      // 3. 학력 사항
      request.getEducations().forEach(education -> {
        if(education.getEducationId() == null) {
          educationRepository.save(education.toEntity(resume));
        } else {
          Education originEducation = educationRepository.findById(education.getEducationId())
              .orElseThrow(() -> new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR));

          originEducation.update(education.toEntity(resume));
        }
      });













    } catch (CustomUserNotFoundException e) {
      log.error("유저를 찾을 수 없음: {}", e.getMessage());
      throw e;

    } catch (Exception e) {
      log.info("AI 자기소개서 생성 실패: {}", e.getMessage());
      throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
    }

  }



  @Transactional
  public AiAboutMeResponse getAiAboutMe(Long userId) {

    try {
      User user = userRepository.findById(userId)
          .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

      List<Post> userPosts = postRepository.findAllByUserId(userId);

      //후기글이 존재하지 않을 경우
      //TODO: 현재 후기글만 따로 불러오는 로직이 없어, 일단 전체 게시글을 불러오도록 구현하였습니다. 추후에 수정이 필요합니다.
      if (userPosts.isEmpty()) {
        throw new CustomPostNotFoundException(ErrorCode.POST_NOT_FOUND);
      }

      //생성 가능 횟수가 0 이하일 경우
      if (user.getAboutMeCnt() <= 0) {
        throw new InsufficientGenerationCountException(ErrorCode.INSUFFICIENT_GENERATION_COUNT);
      }

      log.info("생성된 프롬프트: {}", ClaudeAiPrompt.ABOUT_ME_PROMPT(userPosts, user));

      String response = claudeAiClient.sendMessage(ClaudeAiPrompt.ABOUT_ME_PROMPT(userPosts, user));

      user.decreaseAiAboutMe();

      return AiAboutMeResponse.builder()
          .aboutMeCnt(user.getAboutMeCnt())
          .aboutMe(response)
          .build();
    } catch (CustomPostNotFoundException e) {
      log.error("게시글을 찾을 수 없음: {}", e.getMessage());
      throw e;

    } catch (CustomUserNotFoundException e) {
      log.error("유저를 찾을 수 없음: {}", e.getMessage());
      throw e;

    } catch (InsufficientGenerationCountException e) {
      log.error("생성 가능 횟수가 0 이하임: {}", e.getMessage());
      throw e;

    } catch (Exception e) {
      log.info("AI 자기소개서 생성 실패: {}", e.getMessage());
      throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
    }
  }
}

