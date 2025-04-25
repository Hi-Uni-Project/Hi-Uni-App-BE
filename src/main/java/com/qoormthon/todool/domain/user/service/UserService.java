package com.qoormthon.todool.domain.user.service;

import com.qoormthon.todool.domain.user.dto.UserDto;
import com.qoormthon.todool.domain.user.dto.UserLoginDto;
import com.qoormthon.todool.domain.user.entity.UserEntity;
import com.qoormthon.todool.domain.user.repository.UserRepository;
import com.qoormthon.todool.global.common.response.ResponseDto;
import com.qoormthon.todool.global.common.util.JWTutil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


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

    public ResponseEntity<?> login(UserLoginDto userLoginDto) {
        try {
            if(userRepository.existsByStdNo(userLoginDto.getStdNo())) {
                UserEntity userEntity = userRepository.findByStdNo(userLoginDto.getStdNo());
                if(hash.matches(userLoginDto.getPassword(), userEntity.getPassword())) {
                    String token = jwTutil.createToken(userLoginDto.getStdNo(), "USER");
                    return ResponseEntity.ok()
                            .body(ResponseDto
                                    .response(HttpStatus.OK, "로그인에 성공하였습니다.", token));
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(ResponseDto
                                    .response(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.", userLoginDto.getStdNo()));
                }

            } else {
                return ResponseEntity.badRequest()
                        .body(ResponseDto
                                .response(HttpStatus.BAD_REQUEST, "존재하지 않는 유저입니다", userLoginDto.getStdNo()));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ResponseDto.response(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류입니다.", userLoginDto.getStdNo()));
        }
    }


    public ResponseEntity<?> signUp(UserDto userDto, MultipartFile file) {
        String filePath;

        try {
            if (file != null && !file.isEmpty()) {
                String fileName = file.getOriginalFilename(); //파일 이름
                Resource resource = resourceLoader.getResource("classpath:static/images");
                String uploadDir = resource.getFile().getAbsolutePath();
                filePath = "/images/" + fileName;
                file.transferTo(new File(uploadDir + File.separator + fileName));
                userDto.setImageUrl(filePath);
            } else {
                userDto.setImageUrl(null);
            }

            if(userRepository.existsByStdNo(userDto.getStdNo())){ //이미 존재하는 유저..
                return ResponseEntity.badRequest()
                        .body(ResponseDto
                                .response(HttpStatus.BAD_REQUEST, "이미 존재하는 유저입니다", userDto.getStdNo()));
            } else {
                userDto.setPassword(hash.encode(userDto.getPassword())); //비밀번호 해싱처리
                userRepository.save(userDto.toEntity());
                return ResponseEntity.ok()
                        .body(ResponseDto
                                .response(HttpStatus.OK, "회원가입에 성공하였습니다.", userDto.getStdNo()));
            }
        } catch (Exception e) {
            log.error("err : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto
                            .response(HttpStatus.INTERNAL_SERVER_ERROR, "회원가입에 실패하였습니다.", userDto.getStdNo()));
        }


    }

}
