package com.qoormthon.todool.domain.user.service;

import com.qoormthon.todool.domain.user.dto.UserDto;
import com.qoormthon.todool.domain.user.dto.UserLoginDto;
import com.qoormthon.todool.domain.user.entity.UserEntity;
import com.qoormthon.todool.domain.user.repository.UserRepository;
import com.qoormthon.todool.global.common.response.ResponseDto;
import com.qoormthon.todool.global.common.util.JWTutil;
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
public class UserService {
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

    public ResponseEntity<?> login(UserLoginDto userLoginDto) {
        try {
            if(userRepository.existsByUserId(userLoginDto.getUserId())) {
                UserEntity userEntity = userRepository.findByUserId(userLoginDto.getUserId());
                if(hash.matches(userLoginDto.getPassword(), userEntity.getPassword())) {
                    String accessToken = jwTutil.createAccessToken(userLoginDto.getUserId(), "USER");
                    String refreshToken = jwTutil.createRefreshToken(userLoginDto.getUserId(), "USER");

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
                                    .response(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.", userLoginDto.getUserId()));
                }

            } else {
                return ResponseEntity.badRequest()
                        .body(ResponseDto
                                .response(HttpStatus.BAD_REQUEST, "존재하지 않는 유저입니다", userLoginDto.getUserId()));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ResponseDto.response(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류입니다.", userLoginDto.getUserId()));
        }
    }


    public ResponseEntity<?> signUp(UserDto userDto, MultipartFile file) {
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

    public ResponseEntity<?> searchUserInfo(String userId, HttpServletRequest request) {
        try {
            if(userId.isEmpty() || userId == null) {
                return ResponseEntity.badRequest()
                        .body(ResponseDto
                                .response(HttpStatus.BAD_REQUEST, "유저 아이디를 입력해주세요", userId));
            }

            if(!jwTutil.getUserId(jwTutil.extractToken(request)).equals(userId)){
                return ResponseEntity.badRequest()
                        .body(ResponseDto
                                .response(HttpStatus.BAD_REQUEST, "본인만 조회할 수 있습니다.", userId));
            }

            if(!userRepository.existsByUserId(userId)) {
                return ResponseEntity.badRequest()
                        .body(ResponseDto
                                .response(HttpStatus.BAD_REQUEST, "존재하지 않는 유저입니다", userId));
            } else {
                UserEntity userEntity = userRepository.findByUserId(userId);
                userEntity.setPassword(null);
                return ResponseEntity.ok()
                        .body(ResponseDto
                                .response(HttpStatus.OK, "조회 성공", userEntity));
            }
        } catch (Exception e) {
            log.error("err : " + e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(ResponseDto
                            .response(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류입니다.", userId));
        }
    }

}
