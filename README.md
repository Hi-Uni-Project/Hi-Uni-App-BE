# Hi-Uni api 서버 입니다.

---
# [1] Git 커밋 규칙

ex)
feat: 한글

* **fix**: 버그 수정
* **docs**: 문서 수정
* **style**: 코드 포맷팅, 세미콜론 등 (로직 변경 없음)
* **chore**: 빌드, 패키지 관리, 파일 삭제 등 기타 작업 (간단하고 하찮은? 일들)
* **refactor**: 코드 리팩토링
* **test**: 테스트 코드, 리팩토링 테스트 코드 추가
* **feat**: 새로운 기능 추가


# [2] 프로젝트 구조
```
src/main/java/com/project/hiuni/
├── HiuniApplication.java
├── domain/
│   ├── mail/
│   │   ├── dto/
│   │   │   ├── request/
│   │   │   │   └── MailRequest.java
│   │   │   └── response/
│   │   │       └── MailResponse.java
│   │   ├── entity/
│   │   │   └── MailEntity.java
│   │   ├── repository/
│   │   │   └── MailRepository.java
│   │   └── v1/
│   │       ├── controller/
│   │       │   └── MailV1Controller.java
│   │       └── service/
│   │           └── MailV1Service.java
│   ├── univ/
│   │   ├── dto/
│   │   │   ├── UnivDataDto.java
│   │   │   ├── UnivMajorDataDto.java
│   │   │   ├── request/
│   │   │   │   └── UnivRequest.java
│   │   │   └── response/
│   │   │       └── UnivResponse.java
│   │   ├── entity/
│   │   │   └── UnivEntity.java
│   │   ├── repository/
│   │   │   └── UnivRepository.java
│   │   └── v1/
│   │       ├── controller/
│   │       │   └── UnivV1Controller.java
│   │       └── service/
│   │           └── UnivV1Service.java
│   └── user/
│       ├── dto/
│       │   ├── request/
│       │   │   └── UserRequest.java
│       │   └── response/
│       │       └── UserResponse.java
│       ├── entity/
│       │   └── UserEntity.java
│       ├── repository/
│       │   └── UserRepository.java
│       └── v1/
│           ├── controller/
│           │   └── UserV1Controller.java
│           └── service/
│               └── UserV1Service.java
├── global/
│   ├── common/
│   │   └── dto/
│   │       └── response/
│   │           └── ResponseDto.java
│   ├── config/
│   │   ├── RestTemplateConfig.java
│   │   ├── SwaggerConfig.java
│   │   ├── UnivDataConfig.java
│   │   └── WebConfig.java
│   ├── exception/
│   │   ├── CustomException.java
│   │   ├── ErrorCode.java
│   │   └── GlobalExceptionHandler.java
│   ├── security/
│   │   ├── config/
│   │   │   └── SecurityConfig.java
│   │   ├── filter/
│   │   │   └── JwtAuthenticationFilter.java
│   │   └── util/
│   │       └── JWTUtil.java
│   └── util/
│       └── GlobalUtil.java
├── infra/
│   ├── claude/
│   │   ├── ClaudeAiClient.java
│   │   ├── dto/
│   │   │   ├── request/
│   │   │   └── response/
│   │   └── prompt/
│   ├── mail/
│   │   ├── MailClient.java
│   │   └── dto/
│   │       ├── request/
│   │       └── response/
│   └── univcert/
│       ├── UnivcertClient.java
│       └── dto/
│           ├── request/
│           │   └── UnivcertRequest.java
│           └── response/
│               └── UnivcertResponse.java
└── src/main/resources/
    ├── application.properties
    ├── application.properties.sample
    ├── application.propetries.prod
    └── static/
        └── data/
            ├── univMajorData.json
            └── univdata.json
```
