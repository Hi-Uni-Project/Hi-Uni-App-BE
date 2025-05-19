package com.qoormthon.todool.domain.user.application.service;

import com.qoormthon.todool.domain.user.adapter.dto.request.UserSignUpRequestDto;
import com.qoormthon.todool.domain.user.adapter.dto.request.UserLoginRequestDto;
import com.qoormthon.todool.domain.user.adapter.dto.response.UserFindResponseDto;
import com.qoormthon.todool.domain.user.application.port.in.FindUserUseCase;
import com.qoormthon.todool.domain.user.application.port.out.FindUserPort;
import com.qoormthon.todool.domain.user.domain.entity.UserEntity;
import com.qoormthon.todool.domain.user.adapter.out.persistence.UserRepository;
import com.qoormthon.todool.domain.user.exception.UserFindException;
import com.qoormthon.todool.domain.user.mapper.UserMapper;
import com.qoormthon.todool.global.common.response.ResponseDto;
import com.qoormthon.todool.global.security.util.JWTutil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.Duration;


@Slf4j
@Service
public class UserService implements FindUserUseCase {

    private final UserMapper userMapper;
    private final FindUserPort findUserPort;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private JWTutil jwTutil;

    @Autowired
    private BCryptPasswordEncoder hash;

    @Autowired
    private CheckUserService checkUserService;

    @Autowired
    public UserService(UserMapper userMapper, FindUserPort findUserPort) {
        this.findUserPort = findUserPort;
        this.userMapper = userMapper;
    }

    public ResponseEntity<?> login(UserLoginRequestDto userLoginRequestDto) {
        try {
            if(userRepository.existsByUserId(userLoginRequestDto.getUserId())) {
                UserEntity userEntity = userRepository.findByUserId(userLoginRequestDto.getUserId());
                if(hash.matches(userLoginRequestDto.getPassword(), userEntity.getPassword())) {
                    String accessToken = jwTutil.createAccessToken(userLoginRequestDto.getUserId(), "USER");
                    String refreshToken = jwTutil.createRefreshToken(userLoginRequestDto.getUserId(), "USER");

                    ResponseCookie responseCookie = ResponseCookie.from("refreshToken", refreshToken)
                            .httpOnly(true)
                            .secure(true)
                            .sameSite("Strict")
                            .path("/auth")
                            .maxAge(Duration.ofDays(7))
                            .build();



                    return ResponseEntity.ok()
//                            .header("Authorization", "Bearer " + accessToken)
                            .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                            .body(ResponseDto
                                    .response(HttpStatus.OK, "로그인에 성공하였습니다.", accessToken));
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(ResponseDto
                                    .response(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.", userLoginRequestDto.getUserId()));
                }

            } else {
                return ResponseEntity.badRequest()
                        .body(ResponseDto
                                .response(HttpStatus.BAD_REQUEST, "존재하지 않는 유저입니다", userLoginRequestDto.getUserId()));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ResponseDto.response(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류입니다.", userLoginRequestDto.getUserId()));
        }
    }


    public ResponseEntity<?> signUp(UserSignUpRequestDto userDto, MultipartFile file) {
        String filePath;

        try {
            if (file != null && !file.isEmpty()) {

                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.badRequest()
                            .body(ResponseDto
                                    .response(HttpStatus.BAD_REQUEST, "이미지 파일만 업로드 가능합니다.", null));
                }

                String fileName = file.getOriginalFilename(); //파일 이름
                Resource resource = resourceLoader.getResource("classpath:static/images");
                String uploadDir = resource.getFile().getAbsolutePath();
                filePath = "/images/" + fileName;
                file.transferTo(new File(uploadDir + File.separator + fileName));
                userDto.setImageUrl(filePath);
            } else {
                userDto.setImageUrl(null);
            }

            if(!checkUserService.booleanCheckUserId(userDto.getUserId())){ //이미 존재하는 유저..
                return ResponseEntity.badRequest()
                        .body(ResponseDto
                                .response(HttpStatus.BAD_REQUEST, "유효하지 않은 id 입니다.", userDto.getStdNo()));
            } else {

                if(!checkUserService.booleanCheckUserPwd(userDto.getPassword())) {
                    return ResponseEntity.badRequest()
                            .body(ResponseDto
                                    .response(HttpStatus.BAD_REQUEST, "유효하지 않은 패스워드 입니다.", userDto.getStdNo()));
                }

                userDto.setPassword(hash.encode(userDto.getPassword())); //비밀번호 해싱처리
                userRepository.save(userDto.toEntity());
                //jwt 토큰 반환 로직
                String accessToken = jwTutil.createAccessToken(userDto.getUserId(), "USER");
                String refreshToken = jwTutil.createRefreshToken(userDto.getUserId(), "USER");

                ResponseCookie responseCookie = ResponseCookie.from("refreshToken", refreshToken)
                        .httpOnly(true)
                        .secure(true)
                        .sameSite("Strict")
                        .path("/auth")
                        .maxAge(Duration.ofDays(7))
                        .build();



                return ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                        .body(ResponseDto
                                .response(HttpStatus.OK, "회원가입에 성공하였습니다.", accessToken));
            }
        } catch (Exception e) {
            log.error("err : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto
                            .response(HttpStatus.INTERNAL_SERVER_ERROR, "회원가입에 실패하였습니다.", userDto.getStdNo()));
        }


    }

    public UserFindResponseDto userFind(String userId, HttpServletRequest request) {
            if(userId == null || userId.isEmpty()) {
                throw new UserFindException("유저 아이디를 입력해주세요.");
            }

            if(!jwTutil.getUserId(jwTutil.extractToken(request)).equals(userId)){
                throw new UserFindException("본인만 조회할 수 있습니다.");
            }

            if(!userRepository.existsByUserId(userId)) {
                throw new UserFindException("존재하지 않는 유저입니다.");
            }

            return userMapper.userToUserFindResponseDto(findUserPort.findByUserId(userId));

    }

}
