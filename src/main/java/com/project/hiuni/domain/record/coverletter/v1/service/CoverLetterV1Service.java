package com.project.hiuni.domain.record.coverletter.v1.service;

import com.project.hiuni.domain.post.entity.Post;
import com.project.hiuni.domain.post.exception.CustomPostNotFoundException;
import com.project.hiuni.domain.post.repository.PostRepository;
import com.project.hiuni.domain.record.coverletter.dto.request.AiCoverLetterRequest;
import com.project.hiuni.domain.record.coverletter.dto.request.CoverLetterRequest;
import com.project.hiuni.domain.record.coverletter.dto.response.AiCoverLetterResponse;
import com.project.hiuni.domain.record.coverletter.dto.response.CoverLetterResponse;
import com.project.hiuni.domain.record.coverletter.entity.CoverLetter;
import com.project.hiuni.domain.record.coverletter.exception.CustomCoverLetterNotFountException;
import com.project.hiuni.domain.record.coverletter.repository.CoverLetterRepository;
import com.project.hiuni.domain.record.exception.InsufficientGenerationCountException;
import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.exception.CustomUserNotFoundException;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.exception.ErrorCode;
import com.project.hiuni.global.exception.InternalServerException;
import com.project.hiuni.infra.claude.ClaudeAiClient;
import com.project.hiuni.infra.claude.prompt.ClaudeAiPrompt;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CoverLetterV1Service {

  private final ClaudeAiClient claudeAiClient;

  private final UserRepository userRepository;
  private final CoverLetterRepository coverLetterRepository;
  private final PostRepository postRepository;

  public List<CoverLetterResponse> getCoverLetter(Long userId) {
    try {

      User user = userRepository.findById(userId).orElseThrow(
          () -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND)
      );

      List<CoverLetter> coverLetters = coverLetterRepository.findAllByUser(user);

      if(coverLetters.isEmpty()) {
        throw new CustomCoverLetterNotFountException(ErrorCode.COVER_LETTER_NOT_FOUND);
      }

      return coverLetters.stream()
          .map(CoverLetter::toCoverLetterResponse)
          .toList();

    } catch (CustomUserNotFoundException e) {
      log.error("유저를 찾을 수 없습니다.: {}", e.getMessage());
      throw e;

    } catch (CustomCoverLetterNotFountException e) {
      log.error("자기소개서를 찾을 수 없습니다.: {}", e.getMessage());
      throw e;

    } catch (Exception e) {
      log.error("자기소개서 조회 중 오류가 발생했습니다.: {}", e.getMessage());
      throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
    }

  }

  public void createCoverLetter(List<CoverLetterRequest> request, Long userId) {

    try {

      User user = userRepository.findById(userId).orElseThrow(
          () -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND)
      );

      List<CoverLetter> coverLetters = request.stream()
          .map(coverLetterRequest -> coverLetterRequest.toEntity(user))
          .toList();

      coverLetterRepository.saveAll(coverLetters);

    } catch (CustomUserNotFoundException e) {
      log.error("유저를 찾을 수 없습니다.: {}", e.getMessage());
      throw e;

    } catch (Exception e) {
      log.error("자기소개서 저장 중 오류가 발생했습니다.: {}", e.getMessage());
      throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
    }

  }

  @Transactional
  public AiCoverLetterResponse getAiCoverLetter(AiCoverLetterRequest request, Long userId) {

    try {

      User user = userRepository.findById(userId).orElseThrow(
          () -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND)
      );

      List<Post> userPosts = postRepository.findAllByUserId(userId);

      //후기글이 존재하지 않을 경우
      //TODO: 현재 후기글만 따로 불러오는 로직이 없어, 일단 전체 게시글을 불러오도록 구현하였습니다. 추후에 수정이 필요합니다.
      if (userPosts.isEmpty()) {
        throw new CustomPostNotFoundException(ErrorCode.POST_NOT_FOUND);
      }

      user.decreaseAiCoverLetter();


      //TODO: UT 시 주석 해제 필요
      //String response = claudeAiClient.sendMessage(ClaudeAiPrompt.COVER_LETTER_PROMPT(userPosts, user, request.getRole(), request.getQuestion()));

      Thread.sleep(12000); //임시로 12초 대기
      String response = """
          저 000의 가장 큰 장점은 문제의 본질을 파악하고 체계적으로 개선해나가는 분석력입니다. 네이버 인턴 시절 데이터 파이프라인의 병목 구간을 발견했을 때, 단순히 증상만 해결하는 것이 아니라 전체 ETL 프로세스를 분석하여 캐시 전략을 재설계했습니다. 쿠팡에서 보안 로그 탐지 시스템을 개선할 때도 오탐을 줄이기 위해 로그 스키마를 표준화하고 파싱 로직을 체계화했습니다. 이러한 접근 덕분에 삼성SDS에서 테스트 리포트 자동화 시스템을 구축할 때도 운영 효율성을 크게 높일 수 있었습니다.\\n\\n또한 지속적인 학습을 통해 기술 깊이를 더해가는 것도 제 강점입니다. 정보보안기사와 SQLD를 취득하며 암호화, 인덱스 최적화 등 백엔드 개발에 필요한 이론적 기반을 다졌고, 실무에서 SLA 모니터링과 데이터 정합성 검증을 경험하며 이를 실전에 적용했습니다.\\n\\n반면 단점은 완벽을 추구하다 일정 관리에 어려움을 겪는 경우가 있다는 점입니다. 라인 인턴 시절 데이터 정합성 검증 로직을 구현할 때 모든 엣지 케이스를 고려하려다 초기 일정보다 지연된 적이 있습니다. 이후 우선순위를 명확히 설정하고 MVP를 먼저 완성한 뒤 점진적으로 개선하는 방식으로 업무 방식을 바꿨습니다. 현재는 기능의 중요도와 긴급도를 구분해 효율적으로 작업하고 있으며, 이를 통해 품질과 속도의 균형을 맞춰가고 있습니다.
          """;

      return AiCoverLetterResponse.builder()
          .answer(response)
          .build();

    } catch (CustomUserNotFoundException e) {
      log.error("유저를 찾을 수 없습니다.: {}", e.getMessage());
      throw e;

    } catch (InsufficientGenerationCountException e) {
      log.error("생성 가능 횟수가 0 이하임: {}", e.getMessage());
      throw e;

    } catch (Exception e) {
      log.error("자기소개서 생성 중 오류가 발생했습니다.: {}", e.getMessage());
      throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
    }

  }
}
