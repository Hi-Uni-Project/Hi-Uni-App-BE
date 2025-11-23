package com.project.hiuni.domain.record.resume.v1.service;

import com.project.hiuni.domain.post.entity.Post;
import com.project.hiuni.domain.post.exception.CustomPostNotFoundException;
import com.project.hiuni.domain.post.repository.PostRepository;
import com.project.hiuni.domain.record.exception.InsufficientGenerationCountException;
import com.project.hiuni.domain.record.resume.dto.response.AiAboutMeResponse;
import com.project.hiuni.domain.record.resume.exception.CustomInvalidException;
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
public class ResumeService {

  private final UserRepository userRepository;
  private final PostRepository postRepository;

  private final ClaudeAiClient claudeAiClient;

  @Transactional
  public AiAboutMeResponse getAiAboutMe(Long userId) {

    try {
      User user = userRepository.findById(userId)
          .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

      List<Post> userPosts = postRepository.findAllByUserId(userId);

      //후기글이 존재하지 않을 경우
      //TODO: 현재 후기글만 따로 불러오는 로직이 없어, 일단 전체 게시글을 불러오도록 구현하였습니다. 추후에 수정이 필요합니다.
      if(userPosts.isEmpty()) {
        throw new CustomPostNotFoundException(ErrorCode.POST_NOT_FOUND);
      }

      //생성 가능 횟수가 0 이하일 경우
      if(user.getAboutMeCnt() <= 0) {
        throw new InsufficientGenerationCountException(ErrorCode.INSUFFICIENT_GENERATION_COUNT);
      }

      log.info("생성된 프롬프트: {}", ClaudeAiPrompt.ABOUT_ME_PROMPT(userPosts, user));

      String response = claudeAiClient.sendMessage(ClaudeAiPrompt.ABOUT_ME_PROMPT(userPosts, user));

      user.decreaseAiAboutMe();

      return AiAboutMeResponse.builder()
          .aboutMeCnt(user.getAboutMeCnt())
          .aboutMe(response)
          .build();
    } catch (Exception e) {
      log.info("AI 자기소개서 생성 실패: {}", e.getMessage());
      throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
    }

  }

}
