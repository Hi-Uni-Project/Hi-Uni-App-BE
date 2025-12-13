package com.project.hiuni.domain.user.v1.service;

import com.project.hiuni.domain.auth.repository.AuthRepository;
import com.project.hiuni.domain.bookmark.repository.BookmarkRepository;
import com.project.hiuni.domain.comment.v1.service.CommentLikeV1Service;
import com.project.hiuni.domain.comment.v1.service.CommentV1Service;
import com.project.hiuni.domain.post.repository.PostLikeRepository;
import com.project.hiuni.domain.post.repository.PostRepository;
import com.project.hiuni.domain.post.v1.service.PostLikeV1Service;
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
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserV1Service {

  private final UserRepository userRepository;
  private final BookmarkRepository bookmarkRepository;
  private final PostLikeRepository postLikeRepository;
  private final PostRepository postRepository;
  private final AuthRepository authRepository;

  private final TosV1Service tosV1Service;
  private final CoverLetterV1Service coverLetterV1Service;
  private final ResumeV1Service resumeV1Service;
  private final CommentLikeV1Service commentLikeV1Service;
  private final CommentV1Service commentV1Service;
  private final PostLikeV1Service postLikeV1Service;

  @Transactional
  public void deleteUser(Long userId) {
    try {

      User user = userRepository.findById(userId)
          .orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

      Long authId = user.getAuth().getId();

      //약관 동의 내역 제거
      tosV1Service.deleteAllByUser(user);

      //자기소개서 제거
      coverLetterV1Service.deleteAllByUser(user);

      //이력서 삭제
      resumeV1Service.deleteAllByUser(user);

      //댓글 좋아요 삭제
      commentLikeV1Service.deleteAllByUser(user);

      //댓글 삭제
      commentV1Service.deleteAllByUser(user);

      //북마크 삭제
      bookmarkRepository.deleteAllByUser(user);

      //게시글 좋아요 삭제
      postLikeV1Service.deleteAllByUser(user);

      //게시글 삭제
      postRepository.deleteAllByUserId(user);

      //유저 삭제
      userRepository.delete(user);

      //리프레시 토큰 삭제
      authRepository.deleteById(authId);

    } catch (CustomUserNotFoundException e) {
      log.error("유저를 찾을 수 없음: {}", e.getMessage());
      throw e;

    } catch (Exception e) {
      log.error("회원탈퇴 실패: {}", e.getMessage(), e);
      throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
    }

  }
}
