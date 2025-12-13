package com.project.hiuni.domain.user.v1.service;

import com.project.hiuni.domain.record.coverletter.v1.service.CoverLetterV1Service;
import com.project.hiuni.domain.record.resume.v1.service.ResumeV1Service;
import com.project.hiuni.domain.tos.service.TosV1Service;
import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.exception.CustomUserNotFoundException;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.exception.ErrorCode;
import com.project.hiuni.global.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserV1Service {

  private final UserRepository userRepository;

  private final TosV1Service tosV1Service;
  private final CoverLetterV1Service coverLetterV1Service;
  private final ResumeV1Service resumeV1Service;

  public void deleteUser(Long userId) {
    try {

      User user = userRepository.findById(userId)
          .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

      //약관 동의 내역 제거
      tosV1Service.deleteAllByUser(user);

      //자기소개서 제거
      coverLetterV1Service.deleteAllByUser(user);

      //이력서 삭제
      resumeV1Service.deleteAllByUser(user);







    } catch (CustomUserNotFoundException e) {
      log.error("유저를 찾을 수 없음: {}", e.getMessage());
      throw e;

    } catch (Exception e) {
      log.error("회원탈퇴 실패: {}", e.getMessage(), e);
      throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
    }

  }
}
