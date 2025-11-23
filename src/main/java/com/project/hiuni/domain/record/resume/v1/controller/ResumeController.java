package com.project.hiuni.domain.record.resume.v1.controller;


import com.project.hiuni.domain.record.resume.achievement.dto.response.AchievementResponse;
import com.project.hiuni.domain.record.resume.achievement.entity.Type;
import com.project.hiuni.domain.record.resume.career.dto.response.CareerResponse;
import com.project.hiuni.domain.record.resume.dto.response.ResumeResponse;
import com.project.hiuni.domain.record.resume.education.dto.response.EducationResponse;
import com.project.hiuni.domain.record.resume.education.entity.GraduationStatus;
import com.project.hiuni.domain.record.resume.entity.Gender;
import com.project.hiuni.domain.record.resume.language.dto.response.LanguageResponse;
import com.project.hiuni.domain.record.resume.language.entity.Level;
import com.project.hiuni.domain.record.resume.link.dto.response.LinkResponse;
import com.project.hiuni.domain.record.resume.project.dto.response.ProjectResponse;
import com.project.hiuni.domain.record.resume.skill.repository.SkillDataRepository;
import com.project.hiuni.global.common.dto.response.ResponseDTO;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 내 이력서 화면에 보여질 데이터를 제공하는 컨트롤러 입니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/resume")
public class ResumeController {

  private final SkillDataRepository skillDataRepository;

  @GetMapping
  public ResponseDTO()


  //TODO: 목 데이터를 반환하는 API 입니다. 추후에 서비스 로직이 구현되면 수정이 필요합니다.
  @GetMapping
  public ResponseDTO<ResumeResponse> getResume() {

    String imageUrl = "https://picsum.photos/200/200"; //200 x 200 랜덤 이미지

    ResumeResponse response = ResumeResponse
        .builder()
        .name("김유니")
        .gender(Gender.FEMALE)
        .birthYear(1998)
        .imageUrl(imageUrl)
        .title("하이유니 개발자 이력서 2025")
        .aboutMe("안녕하세요! 사용자 중심의 안정적인 백엔드 개발을 지향하는 김유니입니다. 다양한 프로젝트 경험과 문제 해결 능력을 바탕으로 귀사에 기여하고 싶습니다.")
        .aboutMeCnt(5)
        .careers(
            List.of(
                CareerResponse
                    .builder()
                    .careerId(1L)
                    .companyName("카카오")
                    .startDate(LocalDateTime.now().minusMonths(6))
                    .endDate(LocalDateTime.now())
                    .role("CX 디자인팀/UI디자이너")
                    .position("인턴")
                    .jobDescription("카카오톡 및 관련 서비스의 UI 개선 작업")
                    .build(),
                CareerResponse
                    .builder()
                    .careerId(2L)
                    .companyName("네이버")
                    .startDate(LocalDateTime.now().minusYears(2))
                    .endDate(LocalDateTime.now().minusYears(1))
                    .role("검색개발팀/백엔드 개발자")
                    .position("주니어")
                    .jobDescription("네이버 통합검색 API 개발 및 성능 최적화")
                    .build(),
                CareerResponse
                    .builder()
                    .careerId(3L)
                    .companyName("토스")
                    .startDate(LocalDateTime.now().minusYears(1).minusMonths(6))
                    .endDate(LocalDateTime.now().minusMonths(8))
                    .role("프로덕트팀/프론트엔드 개발자")
                    .position("시니어")
                    .jobDescription("토스뱅크 웹 서비스 개발 및 UI/UX 개선")
                    .build(),
                CareerResponse
                    .builder()
                    .careerId(4L)
                    .companyName("우아한형제들")
                    .startDate(LocalDateTime.now().minusYears(3))
                    .endDate(LocalDateTime.now().minusYears(2).minusMonths(3))
                    .role("배민서비스개발팀/풀스택 개발자")
                    .position("주니어")
                    .jobDescription("배달의민족 주문 시스템 개발 및 유지보수")
                    .build()
            )
        )
        .projects(
            List.of(
                ProjectResponse
                    .builder()
                    .projectId(1L)
                    .projectName("하이유니 프로젝트")
                    .startDate(LocalDateTime.now().minusMonths(3))
                    .endDate(LocalDateTime.now())
                    .role("UI디자이너")
                    .experienceDescription("하이유니 UI 디자인 및 컴포넌트, 디자인시스템 제작")
                    .build(),
                ProjectResponse
                    .builder()
                    .projectId(2L)
                    .projectName("AI 챗봇 서비스 개발")
                    .startDate(LocalDateTime.now().minusMonths(8))
                    .endDate(LocalDateTime.now().minusMonths(3))
                    .role("백엔드 개발자")
                    .experienceDescription("Spring Boot 기반 챗봇 API 서버 구축 및 LLM 연동")
                    .build(),
                ProjectResponse
                    .builder()
                    .projectId(3L)
                    .projectName("전자상거래 플랫폼 리뉴얼")
                    .startDate(LocalDateTime.now().minusYears(1))
                    .endDate(LocalDateTime.now().minusMonths(6))
                    .role("프론트엔드 개발자")
                    .experienceDescription("React 기반 사용자 대시보드 개발 및 반응형 UI 구현")
                    .build(),
                ProjectResponse
                    .builder()
                    .projectId(4L)
                    .projectName("사내 업무 자동화 시스템")
                    .startDate(LocalDateTime.now().minusMonths(5))
                    .endDate(LocalDateTime.now().minusMonths(1))
                    .role("풀스택 개발자")
                    .experienceDescription("Python 기반 데이터 크롤링 및 자동 리포트 생성 시스템 구축")
                    .build()
            )
        )
        .educations(
            List.of(
                EducationResponse
                    .builder()
                    .educationId(1L)
                    .universityName("유니 대학교")
                    .startDate(LocalDateTime.now().minusYears(4))
                    .endDate(LocalDateTime.now())
                    .graduationStatus(GraduationStatus.COMPLETED)
                    .major("산업디자인학과 멀티미디어디자이학부")
                    .build()
            )
        )
        .languages(
            List.of(
                LanguageResponse
                    .builder()
                    .languageId(1L)
                    .language("영어")
                    .level(Level.BUSINESS)
                    .build(),
                LanguageResponse
                    .builder()
                    .languageId(2L)
                    .language("일어")
                    .level(Level.PROFESSIONAL)
                    .build(),
                LanguageResponse
                    .builder()
                    .languageId(3L)
                    .language("중국어")
                    .level(Level.FLUENT)
                    .build()
            )
        )
        .achievements(
            List.of(
                AchievementResponse
                    .builder()
                    .achievementId(1L)
                    .activityName("대한민국 패키징 디자인 대회 대상")
                    .periodDate(LocalDateTime.now().minusMonths(1))
                    .type(Type.AWARD)
                    .achievementDescription("숟가락 없이도 편리하게 떠먹을 수 있는 요거트 뚜껑스푼")
                    .build(),
                AchievementResponse
                    .builder()
                    .achievementId(2L)
                    .activityName("정보처리기사")
                    .periodDate(LocalDateTime.now().minusMonths(6))
                    .type(Type.CERTIFICATE)
                    .achievementDescription("정보처리기사 자격증 취득")
                    .build(),
                AchievementResponse
                    .builder()
                    .achievementId(3L)
                    .activityName("2024 해커톤 대회")
                    .periodDate(LocalDateTime.now().minusYears(1))
                    .type(Type.AWARD)
                    .achievementDescription("2024 해커톤 대회 최우수상 수상")
                    .build(),
                AchievementResponse
                    .builder()
                    .achievementId(4L)
                    .activityName("AWS Certified Solutions Architect Associate")
                    .periodDate(LocalDateTime.now().minusMonths(3))
                    .type(Type.CERTIFICATE)
                    .achievementDescription("AWS Certified Solutions Architect Associate 자격증 취득")
                    .build()
            )
        )
        .links(
            List.of(
                LinkResponse
                    .builder()
                    .linkId(1L)
                    .linkName("포트폴리오")
                    .linkUrl("https://hiuni826.myportfolio.com/work")
                    .build(),
                LinkResponse
                    .builder()
                    .linkId(2L)
                    .linkName("GitHub")
                    .linkUrl("https://github.com/hiuni826")
                    .build(),
                LinkResponse
                    .builder()
                    .linkId(3L)
                    .linkName("LinkedIn")
                    .linkUrl("https://www.linkedin.com/in/hiuni826")
                    .build(),
                LinkResponse
                    .builder()
                    .linkId(4L)
                    .linkName("개인 블로그")
                    .linkUrl("https://blog.hiuni.dev")
                    .build()
            )
        )
        .skills(
            List.of(
                skillDataRepository.findById(1L).orElse(null),
                skillDataRepository.findById(2L).orElse(null),
                skillDataRepository.findById(3L).orElse(null),
                skillDataRepository.findById(4L).orElse(null)
            )
        )
        .build();

    return ResponseDTO.of(response, "조회에 성공하였습니다.");
  }

}
