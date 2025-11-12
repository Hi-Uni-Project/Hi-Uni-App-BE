package com.project.hiuni.domain.record.v1.controller;

import com.project.hiuni.domain.record.dto.response.RecordOverviewResponse;
import com.project.hiuni.domain.record.dto.response.RecordOverviewResponse.CoverLetterResponse;
import com.project.hiuni.global.common.dto.response.ResponseDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 내 기록 메인화면에 보여질 데이터를 제공하는 컨트롤러 입니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/record/")
public class RecordController {

  //TODO: 목 데이터를 반환하는 API 입니다. 추후에 서비스 로직이 구현되면 수정이 필요합니다.
  @GetMapping("/overview")
  public ResponseDTO<RecordOverviewResponse> getRecordOverview() {

    String imageUrl = "https://picsum.photos/200/200"; //200 x 200 랜덤 이미지

    RecordOverviewResponse response = RecordOverviewResponse
        .builder()
        .title("하이유니 개발자 이력서 2025")
        .coverLetters(List.of(
            CoverLetterResponse.builder()
                .question("지원동기")
                .answer("대학교 2학년 때 처음 웹 개발을 접하면서 사용자의 불편함을 해결하는 서비스를 만드는 일에 매력을 느꼈습니다. 특히 백엔드 개발자로서 안정적인 시스템 설계와 데이터 처리 로직을 구현할 때 가장 큰 보람을 느낍니다.\n\n이번 프로젝트에서 결제 시스템을 구축하며 사용자 경험과 비즈니스 로직을 동시에 고민해야 했던 경험이 있습니다. 결제 실패 시 재시도 로직을 설계하고, 웹훅을 통해 실시간으로 구독 상태를 동기화하는 과정에서 서비스의 신뢰성이 얼마나 중요한지 체감했습니다. 이러한 경험을 바탕으로 더 큰 규모의 서비스에서 기술적 문제를 해결하고 싶다는 목표가 생겼습니다.\n\n귀사의 기술 블로그를 통해 대용량 트래픽 처리와 MSA 전환 사례를 접하며 깊은 인상을 받았습니다. 특히 실무에서 마주할 수 있는 기술적 챌린지를 함께 고민하고 성장할 수 있는 환경이라는 점에서 큰 매력을 느꼈습니다. 주니어 개발자로서 배움에 대한 열정과 책임감을 바탕으로 팀에 기여하고 싶습니다.")
                .build(),
            CoverLetterResponse.builder()
                .question("성격의 장단점")
                .answer("제 성격의 가장 큰 장점은 문제 해결에 대한 집요함입니다. 코드 리뷰에서 지적받은 부분이나 버그가 발생했을 때, 원인을 찾을 때까지 파고드는 편입니다. 최근 JWT 인증 오류를 디버깅할 때도 공식 문서부터 스택오버플로우, 실제 라이브러리 코드까지 직접 확인하며 근본 원인을 찾아냈습니다. 이런 과정에서 단순히 문제를 해결하는 것을 넘어 관련 기술에 대한 깊이 있는 이해를 얻게 됩니다.\n\n또한 팀원들과의 협업에서 명확한 커뮤니케이션을 중요하게 생각합니다. API 설계 시 엔드포인트 네이밍이나 응답 구조를 프론트엔드 개발자 입장에서 한 번 더 고민하고, 변경사항이 생기면 즉시 공유하는 편입니다. 코드에서도 var 대신 명시적인 타입을 선호하는 것처럼, 의도가 분명하게 드러나는 것을 선호합니다.\n\n반면 단점은 완벽주의 성향이 강해 때로는 과도하게 시간을 투자한다는 점입니다. 작은 기능 하나를 구현할 때도 '더 나은 방법이 있지 않을까' 고민하다 보면 일정이 밀릴 때가 있습니다. 최근에는 이를 개선하기 위해 '일단 동작하는 코드를 먼저 만들고, 리팩토링은 그 다음'이라는 원칙을 세우고 있습니다. MVP를 빠르게 만드는 것도 중요한 능력이라는 것을 실무에서 배웠기 때문입니다.\n\n또한 새로운 기술에 대한 호기심이 강해 이것저것 시도해보다가 집중력이 분산될 때가 있습니다. 예를 들어 백엔드 개발에 집중해야 할 때 React Native도 공부하고 싶어지는 경우가 있습니다. 이를 보완하기 위해 현재는 우선순위를 명확히 정하고, 학습 계획을 세워 한 가지씩 깊이 있게 파는 방식으로 개선하고 있습니다.")
                .build(),
            CoverLetterResponse.builder()
                .question("직무를 위한 노력 및 경험")
                .answer("백엔드 개발자로 성장하기 위해 실무 중심의 경험을 쌓는 것에 집중해왔습니다. 단순히 강의를 듣는 것을 넘어 실제 서비스를 배포하고 운영하며 발생하는 문제들을 직접 해결하는 과정에서 가장 많이 배웠습니다.\n\n가장 의미 있었던 경험은 결제 시스템 구축 프로젝트였습니다. RevenueCat과 Google Play를 연동하며 웹훅 처리, 구독 상태 동기화, 결제 실패 시 재시도 로직 등을 구현했습니다. 특히 프로덕션 환경에서 실제 결제가 이루어지는 만큼, 데이터 정합성과 예외 처리에 신경을 많이 썼습니다. 이 과정에서 트랜잭션 관리와 멱등성의 중요성을 체감했고, 로그 관리의 필요성도 깊이 이해하게 되었습니다.\n\nSpring Boot를 주력으로 사용하면서도, 단순히 기능 구현에 그치지 않고 왜 이렇게 동작하는지 이해하려고 노력했습니다. JWT 인증 오류가 발생했을 때 라이브러리 내부 코드까지 확인하며 원리를 파악했고, JPA N+1 문제를 해결하며 쿼리 최적화에 대해 공부했습니다.\n\n알고리즘 실력을 키우기 위해 백준에서 꾸준히 문제를 풀어 실버 티어를 달성했습니다. 코딩 테스트 준비뿐만 아니라, 효율적인 자료구조 선택과 시간복잡도를 고려하는 습관이 실무 코드 작성에도 도움이 되고 있습니다.\n\n또한 팀 프로젝트 경험을 통해 협업 능력을 키웠습니다. Git 브랜치 전략, 코드 리뷰 문화, API 문서화의 중요성을 배웠고, 프론트엔드 개발자가 사용하기 편한 API를 설계하는 것이 좋은 백엔드 개발자의 조건이라는 것을 깨달았습니다.")
                .build(),
            CoverLetterResponse.builder()
                .question("입사 후 포부")
                .answer("입사 후 가장 먼저 하고 싶은 것은 팀의 코드베이스와 개발 문화를 빠르게 이해하는 것입니다. 기존 시스템의 아키텍처와 설계 의도를 파악하고, 팀원들의 코드 리뷰를 통해 회사의 코딩 컨벤션과 베스트 프랙티스를 익히고 싶습니다. 주니어 개발자로서 겸손한 자세로 배우되, 능동적으로 질문하고 학습하는 모습을 보여드리겠습니다.\n\n단기적으로는 맡은 업무를 책임감 있게 완수하며 신뢰를 쌓고 싶습니다. 작은 기능 하나를 구현하더라도 예외 처리, 테스트 코드 작성, 문서화까지 꼼꼼하게 챙기는 개발자가 되고자 합니다. 특히 코드 리뷰에 적극적으로 참여하며, 다른 개발자들의 코드에서 배우고 제 코드도 개선해 나가고 싶습니다.\n\n중장기적으로는 서비스의 핵심 기능을 담당할 수 있는 개발자로 성장하고 싶습니다. 대용량 트래픽을 처리하는 시스템 설계, 성능 최적화, 장애 대응 등 실무에서 마주하는 다양한 기술적 챌린지를 경험하며 역량을 키우고 싶습니다. 또한 백엔드뿐만 아니라 인프라, 데이터베이스, 보안 등 전반적인 시스템에 대한 이해도를 높여 문제를 다각도로 분석할 수 있는 개발자가 되겠습니다.\n\n궁극적으로는 후배 개발자들에게 좋은 멘토가 될 수 있는 시니어 개발자로 성장하고 싶습니다. 제가 받은 도움을 다시 나눠줄 수 있는 개발자, 기술적 깊이와 함께 팀워크를 중요시하는 개발자로 기억되고 싶습니다.")
                .build()
        ))
        .image_url(imageUrl)
        .build();

    return ResponseDTO.of(response, "조회에 성공하였습니다.");

  }




}
