package com.project.hiuni.domain.record.v1.service;

import com.project.hiuni.domain.record.coverletter.entity.CoverLetter;
import com.project.hiuni.domain.record.coverletter.repository.CoverLetterRepository;
import com.project.hiuni.domain.record.dto.response.RecordOverviewResponse;
import com.project.hiuni.domain.record.resume.entity.Resume;
import com.project.hiuni.domain.record.resume.exception.CustomResumeNotFoundException;
import com.project.hiuni.domain.record.resume.repository.ResumeRepository;
import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.exception.CustomUserNotFoundException;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.exception.ErrorCode;
import com.project.hiuni.global.exception.InternalServerException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecordService {

  private final UserRepository userRepository;
  private final ResumeRepository resumeRepository;
  private final CoverLetterRepository coverLetterRepository;

  public RecordOverviewResponse getRecordOverview(Long userId) {

    try {

      String title = null;
      String imageUrl = null;

      User user = userRepository.findById(userId)
          .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

      Resume resume = resumeRepository.findByUser(user)
          .orElse(null);

      if(resume != null) {
        title = resume.getTitle();
        imageUrl = resume.getImageUrl();
      }

      List<CoverLetter> coverLetters = coverLetterRepository.findAllByUser(user);


      List<RecordOverviewResponse.CoverLetterResponse> coverLetterOverviewResponses = coverLetters.stream()
          .map(coverLetter -> RecordOverviewResponse.CoverLetterResponse.builder()
              .question(coverLetter.getQuestion())
              .answer(coverLetter.getAnswer())
              .build())
          .toList();

      RecordOverviewResponse response = RecordOverviewResponse
          .builder()
          .title(title)
          .imageUrl(imageUrl)
          .coverLetters(coverLetterOverviewResponses)
          .build();

      return response;

    } catch (CustomUserNotFoundException e) {
      log.error("유저가 존재하지 않습니다.: {}", e.getMessage());
      throw new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND);

    } catch (Exception e) {
      log.error("내기록 overview 조회중 에러 발생: {}", e.getMessage());
      throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
    }

  }

}
