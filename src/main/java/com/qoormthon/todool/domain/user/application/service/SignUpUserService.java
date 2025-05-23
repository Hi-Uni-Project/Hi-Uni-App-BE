package com.qoormthon.todool.domain.user.application.service;

import com.qoormthon.todool.domain.user.adapter.dto.response.SignUpUserResponseDto;
import com.qoormthon.todool.domain.user.application.command.SignUpUserCommand;
import com.qoormthon.todool.domain.user.application.port.in.SignUpUserUseCase;
import com.qoormthon.todool.domain.user.application.port.out.ExistsByUserPort;
import com.qoormthon.todool.domain.user.application.port.out.SaveUserPort;
import com.qoormthon.todool.domain.user.domain.model.User;
import com.qoormthon.todool.domain.user.exception.InternalServerException;
import com.qoormthon.todool.domain.user.exception.InvalidFileTypeException;
import com.qoormthon.todool.domain.user.exception.UserDuplicatedException;
import com.qoormthon.todool.domain.user.exception.UserInvalidValueException;
import com.qoormthon.todool.domain.user.mapper.UserMapper;
import com.qoormthon.todool.global.common.response.ResponseDto;
import com.qoormthon.todool.global.error.exception.ErrorCode;
import com.qoormthon.todool.global.security.util.JWTutil;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.Duration;

@Service
@Slf4j
public class SignUpUserService implements SignUpUserUseCase {

    private final SaveUserPort saveUserPort;
    private final UserMapper userMapper;
    private final JWTutil jwTutil;
    private final CheckUserService checkUserService;
    private final ResourceLoader resourceLoader;
    private final ExistsByUserPort existsByUserPort;
    private final BCryptPasswordEncoder hash;

    @Autowired
    public SignUpUserService(SaveUserPort saveUserPort, UserMapper userMapper
    , JWTutil jwTutil, CheckUserService checkUserService, ResourceLoader resourceLoader
    , ExistsByUserPort existsByUserPort, BCryptPasswordEncoder hash)
    {
        this.userMapper = userMapper;
        this.saveUserPort = saveUserPort;
        this.jwTutil = jwTutil;
        this.checkUserService = checkUserService;
        this.resourceLoader = resourceLoader;
        this.existsByUserPort = existsByUserPort;
        this.hash = hash;
    }

    @Override
    public SignUpUserResponseDto signUp(SignUpUserCommand signUpUserCommand, MultipartFile file) {
        String filePath;
        if (file != null && !file.isEmpty()) {
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new InvalidFileTypeException(ErrorCode.IMAGE_FILE_ONLY);
            }
            try {
                String fileName = file.getOriginalFilename(); //파일 이름
                Resource resource = resourceLoader.getResource("classpath:static/images");
                String uploadDir = resource.getFile().getAbsolutePath();
                filePath = "/images/" + fileName;
                file.transferTo(new File(uploadDir + File.separator + fileName));
                signUpUserCommand.setImageUrl(filePath);
            } catch (Exception e) {
                log.error("File upload failed", e);
                throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
            }

        } else {
            signUpUserCommand.setImageUrl(null);
        }

        if(existsByUserPort.existsByUserId(signUpUserCommand.getUserId())) { //아이디 중복 검사
            throw new UserDuplicatedException(ErrorCode.DUPLICATED_USER);
        }

        checkUserService.checkUserId(signUpUserCommand.getUserId()); //id 유효성 검사
        log.info("[SignUp] 아이디 유효성 검증 통과 : " + signUpUserCommand.getUserId());
        checkUserService.checkUserPwd(signUpUserCommand.getPassword()); //비밀번호 유효성 검사
        log.info("[SignUp] 비밀번호 유효성 검증 통과" + signUpUserCommand.getPassword());

        //jwt 토큰 반환 로직
        String accessToken = jwTutil.createAccessToken(signUpUserCommand.getUserId(), "USER");
        String refreshToken = jwTutil.createRefreshToken(signUpUserCommand.getUserId(), "USER");
        signUpUserCommand.setPassword(hash.encode(signUpUserCommand.getPassword())); //비밀번호 해싱
         saveUserPort.saveUser(userMapper.signUpUserCommandToUser(signUpUserCommand));
         return SignUpUserResponseDto.builder()
                 .accessToken(accessToken)
                 .refreshToken(refreshToken)
                 .build();
    }
}
