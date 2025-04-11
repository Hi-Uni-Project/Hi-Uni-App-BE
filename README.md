# 9oormthon-To-Dool-backend

To-Dool api 서버입니다

## 기술 스택

- Spring Boot
- Spring JPA
- MySQL
- Swagger
- Postman
- Git

## 팀원
### Backend Developers
<div align="center">

| <img src="https://github.com/user-attachments/assets/6e521cc2-58d7-47d9-9740-3e1e654f17f2" width="150" height="150" alt="Junpyo"/> | <img src="https://avatars.githubusercontent.com/u/196579717?v=4" width="150" height="150" alt="Jeongyeon"/> |
|:---:|:---:|
| **Junpyo** | **Jeongyeon** |
| <br/> <br/> | <br/> <br/> |
</div>

## Project Structure

```bash
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── qoormthon/
│   │           └── todool/
│   │               ├── TodoolApplication.java
│   │               ├── domain/
│   │               │   ├── chatbot/
│   │               │   │   ├── controller/
│   │               │   │   │   └── ChatBotController.java
│   │               │   │   └── service/
│   │               │   │       └── ChatBotService.java
│   │               │   └── user/
│   │               │       ├── controller/
│   │               │       │   └── UserController.java
│   │               │       ├── entity/
│   │               │       │   └── UserEntity.java
│   │               │       ├── repository/
│   │               │       │   └── UserRepository.java
│   │               │       └── service/
│   │               │           └── UserService.java
│   │               └── global/
│   │                   ├── WebSocketHandler.java
│   │                   ├── common/
│   │                   │   ├── response/
│   │                   │   │   └── ResponseDto.java
│   │                   │   └── util/
│   │                   │       ├── BaseUtil.java
│   │                   │       ├── HtmlTemplateUtil.java
│   │                   │       └── MailUtil.java
│   │                   └── config/
│   │                       ├── MultipartJackson2HttpMessageConverter.java
│   │                       ├── SwaggerConfig.java
│   │                       ├── WebConfig.java
│   │                       └── WebSocketConfig.java
│   └── resources/
│       ├── application.properties
│       ├── static/
│       └── templates/



