package com.project.hiuni.domain.tos.service;

import com.project.hiuni.domain.tos.dto.request.TosRequest;
import com.project.hiuni.domain.tos.dto.response.TosResponse;
import com.project.hiuni.domain.tos.entity.InPersonTosHistory;
import com.project.hiuni.domain.tos.entity.MarketingTosHistory;
import com.project.hiuni.domain.tos.entity.PersonalInfoTos;
import com.project.hiuni.domain.tos.entity.PersonalInfoTosHistory;
import com.project.hiuni.domain.tos.entity.ServiceImprovementTos;
import com.project.hiuni.domain.tos.entity.ServiceImprovementTosHistory;
import com.project.hiuni.domain.tos.entity.ServiceTosHistory;
import com.project.hiuni.domain.tos.exception.RequiredTermsNotAgreedException;
import com.project.hiuni.domain.tos.repository.InPersonTosHistoryRepository;
import com.project.hiuni.domain.tos.repository.MarketingTosHistoryRepository;
import com.project.hiuni.domain.tos.repository.PersonalInfoTosHistoryRepository;
import com.project.hiuni.domain.tos.repository.ServiceImprovementTosHistoryRepository;
import com.project.hiuni.domain.tos.repository.ServiceImprovementTosRepository;
import com.project.hiuni.domain.tos.repository.ServiceTosHistoryRepository;
import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.exception.ErrorCode;
import com.project.hiuni.global.exception.NotFoundInfoException;
import com.project.hiuni.global.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TosService {

  private final JwtTokenProvider jwtTokenProvider;
  private final UserRepository userRepository;

  private final ServiceTosHistoryRepository serviceTosHistoryRepository;
  private final PersonalInfoTosHistoryRepository personalInfoTosHistoryRepository;
  private final MarketingTosHistoryRepository marketingTosHistoryRepository;
  private final ServiceImprovementTosHistoryRepository serviceImprovementTosHistoryRepository;
  private final InPersonTosHistoryRepository inPersonTosHistoryRepository;

  @Transactional
  public void agreeTos(TosRequest tosRequest, HttpServletRequest httpServletRequest) {

    String token = jwtTokenProvider.extractToken(httpServletRequest);
    String socialId = jwtTokenProvider.getSocialIdFromToken(token);

    User user = userRepository.findBySocialId(socialId)
        .orElseThrow(() -> new NotFoundInfoException(ErrorCode.USER_NOT_FOUND));

    // 필수 약관 동의 여부 확인
    if(!tosRequest.isServiceTosIsAgreed() || !tosRequest.isPersonalInfoTosIsAgreed() ||
    !tosRequest.isInPersonTosIsAgreed()) {
      throw new RequiredTermsNotAgreedException(ErrorCode.REQUIRED_TERMS_NOT_AGREED);
    }

    //====== 약관 동의내역 저장 로직 ======//

    if(tosRequest.isServiceTosIsAgreed()) {
      serviceTosHistoryRepository.save(ServiceTosHistory.createTemp(user));
    }

    if(tosRequest.isPersonalInfoTosIsAgreed()) {
      personalInfoTosHistoryRepository.save(PersonalInfoTosHistory.createTemp(user));
    }

    if(tosRequest.isMarketingTosIsAgreed()) {
      marketingTosHistoryRepository.save(MarketingTosHistory.createTemp(user));
    }

    if(tosRequest.isServiceImprovementTosIsAgreed()) {
      serviceImprovementTosHistoryRepository.save(ServiceImprovementTosHistory.createTemp(user));
    }

    if(tosRequest.isInPersonTosIsAgreed()) {
      inPersonTosHistoryRepository.save(InPersonTosHistory.createTemp(user));
    }

  }

}
