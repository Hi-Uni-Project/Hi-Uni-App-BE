package com.qoormthon.todool.domain.user.service;

import com.qoormthon.todool.domain.user.dto.UserDto;
import com.qoormthon.todool.domain.user.repository.UserRepository;
import com.qoormthon.todool.global.common.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
