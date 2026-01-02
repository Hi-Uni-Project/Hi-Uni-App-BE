package com.project.hiuni.domain.record.resume.v1.service;

import com.project.hiuni.domain.post.exception.CustomPostNotFoundException;
import com.project.hiuni.domain.post.repository.PostRepository;
import com.project.hiuni.domain.post.v1.service.PostV1Service;
import com.project.hiuni.domain.record.exception.InsufficientGenerationCountException;
import com.project.hiuni.domain.record.resume.achievement.entity.Achievement;
import com.project.hiuni.domain.record.resume.achievement.repository.AchievementRepository;
import com.project.hiuni.domain.record.resume.career.entity.Career;
import com.project.hiuni.domain.record.resume.career.repository.CareerRepository;
import com.project.hiuni.domain.record.resume.dto.request.ResumeRequest;
import com.project.hiuni.domain.record.resume.dto.response.AiAboutMeResponse;
import com.project.hiuni.domain.record.resume.dto.response.ResumeResponse;
import com.project.hiuni.domain.record.resume.education.dto.EducationDto;
import com.project.hiuni.domain.record.resume.education.entity.Education;
import com.project.hiuni.domain.record.resume.education.repository.EducationRepository;
import com.project.hiuni.domain.record.resume.entity.Resume;
import com.project.hiuni.domain.record.resume.exception.CustomResumeNotFoundException;
import com.project.hiuni.domain.record.resume.skill.repository.SkillDataRepository;
import java.util.ArrayList;
import com.project.hiuni.domain.record.resume.language.entity.Language;
import com.project.hiuni.domain.record.resume.language.repository.LanguageRepository;
import com.project.hiuni.domain.record.resume.link.entity.Link;
import com.project.hiuni.domain.record.resume.link.repository.LinkRepository;
import com.project.hiuni.domain.record.resume.project.entity.Project;
import com.project.hiuni.domain.record.resume.project.repository.ProjectRepository;
import com.project.hiuni.domain.record.resume.repository.ResumeRepository;
import com.project.hiuni.domain.record.resume.skill.repository.SkillRepository;
import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.exception.CustomUserNotFoundException;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.common.util.ImageUtil;
import com.project.hiuni.global.exception.ErrorCode;
import com.project.hiuni.global.exception.InternalServerException;
import com.project.hiuni.infra.claude.ClaudeAiClient;
import com.project.hiuni.infra.claude.prompt.ClaudeAiPrompt;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResumeV1Service {

  private final UserRepository userRepository;
  private final PostRepository postRepository;
  private final ResumeRepository resumeRepository;

  private final CareerRepository careerRepository;
  private final ProjectRepository projectRepository;
  private final EducationRepository educationRepository;
  private final LanguageRepository languageRepository;
  private final AchievementRepository achievementRepository;
  private final LinkRepository linkRepository;
  private final SkillRepository skillRepository;
  private final SkillDataRepository skillDataRepository;

  private final PostV1Service postV1Service;

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

      resumeRepository.save(resume);

      String IMAGE_PATH = "/home/ubuntu/image";
      String imageUrl = null;

      if(file != null) {
        imageUrl = imageUtil.saveImage(file, IMAGE_PATH);
      }

      // 이력서 기본정보 업데이트
      if(request.getUpdateImage()) { //이미지 변경 필드가 true 일 경우에만 반영
        resume.updateImageUrl(imageUrl);
      }

      resume.updateName(request.getName());
      resume.updateGender(request.getGender());
      resume.updateBirthYear(request.getBirthYear());
      resume.updateTitle(request.getTitle());
      resume.updateAboutMe(request.getAboutMe());

      // 이력서 리스트 업데이트
      // 1. 경력 사항
      List<Long> existingCareerIds = careerRepository.findAllByResume(resume).stream()
          .map(Career::getId)
          .collect(Collectors.toCollection(ArrayList::new));

      if(request.getCareers() != null && !request.getCareers().isEmpty()) {
        request.getCareers().forEach(career -> {
          if(career.getCareerId() == null) {
            careerRepository.save(career.toEntity(resume));
          } else {
            Career originCareer = careerRepository.findById(career.getCareerId())
                .orElseThrow(() -> new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR));

            originCareer.update(career.toEntity(resume));

            existingCareerIds.remove(originCareer.getId());
          }
        });

        if (!existingCareerIds.isEmpty()) {
          careerRepository.deleteAllById(existingCareerIds);
        }
      } else {
        careerRepository.deleteAll();
      }





      // 2. 프로젝트 사항
      List<Long> existingProjectIds = projectRepository.findAllByResume(resume).stream()
          .map(Project::getId)
          .collect(Collectors.toCollection(ArrayList::new));

      if(request.getProjects() != null && !request.getProjects().isEmpty()) {
        request.getProjects().forEach(project -> {
          if(project.getProjectId() == null) {
            projectRepository.save(project.toEntity(resume));
          } else {
            Project originProject = projectRepository.findById(project.getProjectId())
                .orElseThrow(() -> new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR));

            originProject.update(project.toEntity(resume));

            existingProjectIds.remove(originProject.getId());
          }
        });

        if (!existingProjectIds.isEmpty()) {
          projectRepository.deleteAllById(existingProjectIds);
        }
      } else {
        projectRepository.deleteAll();
      }





      // 3. 학력 사항
      List<Long> existingEducationIds = educationRepository.findAllByResume(resume).stream()
          .map(Education::getId)
          .collect(Collectors.toCollection(ArrayList::new));

      if(request.getEducations() != null && !request.getEducations().isEmpty()) {
        request.getEducations().forEach(education -> {
          if(education.getEducationId() == null) {
            educationRepository.save(education.toEntity(resume));
          } else {
            Education originEducation = educationRepository.findById(education.getEducationId())
                .orElseThrow(() -> new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR));

            originEducation.update(education.toEntity(resume));

            existingEducationIds.remove(originEducation.getId());
          }
        });

        if (!existingEducationIds.isEmpty()) {
          educationRepository.deleteAllById(existingEducationIds);
        }
      } else {
        educationRepository.deleteAll();
      }







      // 4. 어학
      List<Long> existingLanguageIds = languageRepository.findAllByResume(resume).stream()
          .map(Language::getId)
          .collect(Collectors.toCollection(ArrayList::new));

      if(request.getLanguages() != null && !request.getLanguages().isEmpty()) {
        request.getLanguages().forEach(language -> {
          if(language.getLanguageId() == null) {
            languageRepository.save(language.toEntity(resume));
          } else {
            Language originLanguage = languageRepository.findById(language.getLanguageId())
                .orElseThrow(() -> new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR));

            originLanguage.update(language.toEntity(resume));

            existingLanguageIds.remove(originLanguage.getId());
          }
        });

        if (!existingLanguageIds.isEmpty()) {
          languageRepository.deleteAllById(existingLanguageIds);
        }
      } else {
        languageRepository.deleteAll();
      }





      // 5. 수상/자격증/교육
      List<Long> existingAchievementIds = achievementRepository.findAllByResume(resume).stream()
          .map(Achievement::getId)
          .collect(Collectors.toCollection(ArrayList::new));

      if(request.getAchievements() != null && !request.getAchievements().isEmpty()) {
        request.getAchievements().forEach(achievement -> {
          if(achievement.getAchievementId() == null) {
            achievementRepository.save(achievement.toEntity(resume));
          } else {
            Achievement originAchievement = achievementRepository.findById(achievement.getAchievementId())
                .orElseThrow(() -> new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR));

            originAchievement.update(achievement.toEntity(resume));

            existingAchievementIds.remove(originAchievement.getId());
          }
        });

        if (!existingAchievementIds.isEmpty()) {
          achievementRepository.deleteAllById(existingAchievementIds);
        }
      } else {
        achievementRepository.deleteAll();
      }



      // 6. 링크
      List<Long> existingLinkIds = linkRepository.findAllByResume(resume).stream()
          .map(Link::getId)
          .collect(Collectors.toCollection(ArrayList::new));

      if(request.getLinks() != null && !request.getLinks().isEmpty()) {
        request.getLinks().forEach(link -> {
          if(link.getLinkId() == null) {
            linkRepository.save(link.toEntity(resume));
          } else {
            Link originLink = linkRepository.findById(link.getLinkId())
                .orElseThrow(() -> new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR));

            originLink.update(link.toEntity(resume));

            existingLinkIds.remove(originLink.getId());
          }
        });

        if (!existingLinkIds.isEmpty()) {
          linkRepository.deleteAllById(existingLinkIds);
        }
      } else {
        linkRepository.deleteAll();
      }



      // 7. 스킬
      skillRepository.deleteAll();
      if(request.getSkills() != null && !request.getSkills().isEmpty()) {
        request.getSkills().forEach(skill -> {
          skillRepository.save(skill.toEntity(resume));
        });
      } else { }


    } catch (CustomUserNotFoundException e) {
      log.error("유저를 찾을 수 없음: {}", e.getMessage());
      throw e;

    } catch (Exception e) {
      log.error("이력서 저장 실패: {}", e.getMessage(), e);  // 스택 트레이스 포함
      throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
    }

  }



  @Transactional
  public AiAboutMeResponse getAiAboutMe(Long userId) {

    try {
      User user = userRepository.findById(userId)
          .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

      List<List<String>> postsReviewData = postV1Service.getMyReviewDetailList(userId);

      //후기글이 존재하지 않을 경우
      if (postsReviewData.isEmpty()) {
        throw new CustomPostNotFoundException(ErrorCode.POST_NOT_FOUND);
      }

      //생성 가능 횟수가 0 이하일 경우
      if (user.getAboutMeCnt() <= 0) {
        throw new InsufficientGenerationCountException(ErrorCode.INSUFFICIENT_GENERATION_COUNT);
      }

      log.info("생성된 프롬프트: {}", ClaudeAiPrompt.ABOUT_ME_PROMPT(postsReviewData, user));

      String response = claudeAiClient.sendMessage(ClaudeAiPrompt.ABOUT_ME_PROMPT(postsReviewData, user));

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
      log.info("내 소개 생성 실패: {}", e.getMessage());
      throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
    }
  }


  @Transactional(readOnly = true)
  public ResumeResponse getResume(Long userId) {

    try {

      User user = userRepository.findById(userId)
          .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

      Resume resume = resumeRepository.findByUser(user)
          .orElseThrow(() -> new CustomResumeNotFoundException(ErrorCode.RESUME_NOT_FOUND));

      if(resumeIsEmpty(user)) {
        throw new CustomResumeNotFoundException(ErrorCode.RESUME_NOT_FOUND);
      }

      return ResumeResponse
          .builder()
          .resumeId(resume.getId())
          .name(resume.getName())
          .gender(resume.getGender())
          .birthYear(resume.getBirthYear())
          .imageUrl(resume.getImageUrl())
          .title(resume.getTitle())
          .aboutMe(resume.getAboutMe())
          .aboutMeCnt(user.getAboutMeCnt())
          .careers(
              careerRepository.findAllByResume(resume).stream()
                  .map(Career::toDto)
                  .collect(Collectors.toList())
          )
          .projects(
              projectRepository.findAllByResume(resume).stream()
                  .map(Project::toDto)
                  .collect(Collectors.toList())
          )
          .educations(
              educationRepository.findAllByResume(resume).stream()
                  .map(Education::toDto)
                  .sorted(
                      Comparator.comparing(EducationDto::getStartDate)
                          .thenComparing(EducationDto::)
                  )
                  .collect(Collectors.toList())
          )
          .languages(
              languageRepository.findAllByResume(resume).stream()
                  .map(Language::toDto)
                  .collect(Collectors.toList())
          )
          .achievements(
              achievementRepository.findAllByResume(resume).stream()
                  .map(Achievement::toDto)
                  .collect(Collectors.toList())
          )
          .links(
              linkRepository.findAllByResume(resume).stream()
                  .map(Link::toDto)
                  .collect(Collectors.toList())
          )
          .skills(
              skillRepository.findAllByResume(resume).stream()
                  .map(skill -> skill.toDto(skillDataRepository))
                  .collect(Collectors.toList())
          )
          .build();

    } catch (CustomResumeNotFoundException e) {
    log.error("이력서를 찾을 수 없음: {}", e.getMessage());
    throw e;

    } catch (CustomUserNotFoundException e) {
      log.error("유저를 찾을 수 없음: {}", e.getMessage());
      throw e;

    } catch (Exception e) {
      log.error("이력서 조회 실패: {}", e.getMessage(), e);  // 스택 트레이스 포함
      throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
    }


  }

  public boolean resumeIsEmpty(User user) {

    Resume resume = resumeRepository.findByUser(user).orElse(null);

    if(resume == null) {
      return true;
    }

    if(
        (resume.getCareers() == null || resume.getCareers().isEmpty()) &&
        (resume.getProjects() == null || resume.getProjects().isEmpty()) &&
        (resume.getEducations() == null || resume.getEducations().isEmpty()) &&
        (resume.getLanguages() == null || resume.getLanguages().isEmpty()) &&
        (resume.getAchievements() == null || resume.getAchievements().isEmpty()) &&
        (resume.getLinks() == null || resume.getLinks().isEmpty()) &&
        (resume.getSkills() == null || resume.getSkills().isEmpty()) &&
            (resume.getName() == null || resume.getName().isEmpty()) &&
            (resume.getGender() == null) &&
            (resume.getBirthYear() == null) &&
            (resume.getImageUrl() == null || resume.getImageUrl().isEmpty()) &&
            (resume.getTitle() == null || resume.getTitle().isEmpty()) &&
            (resume.getAboutMe() == null || resume.getAboutMe().isEmpty())
    )
    {
      return true;
    }


    return false;

  }

  @Transactional
  public void deleteAllByUser(User user) {

      Resume resume = resumeRepository.findByUser(user)
          .orElse(null);

      if(resume == null) {
        return;
      }

      //이력서 관련 모든 항목 삭제
      careerRepository.deleteAllByResume(resume);
      projectRepository.deleteAllByResume(resume);
      educationRepository.deleteAllByResume(resume);
      languageRepository.deleteAllByResume(resume);
      achievementRepository.deleteAllByResume(resume);
      linkRepository.deleteAllByResume(resume);
      skillRepository.deleteAllByResume(resume);

      //이력서 삭제
      resumeRepository.delete(resume);

  }

}

