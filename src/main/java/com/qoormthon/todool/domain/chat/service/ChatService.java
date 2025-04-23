package com.qoormthon.todool.domain.chat.service;

import com.qoormthon.todool.domain.chat.dto.MatchingDto;
import com.qoormthon.todool.domain.user.dto.UserDto;
import com.qoormthon.todool.domain.user.repository.UserRepository;
import com.qoormthon.todool.global.common.response.ChatResponseDto;
import com.qoormthon.todool.global.common.response.ResponseDto;
import com.qoormthon.todool.global.common.util.BaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ChatService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BaseUtil baseUtil;

    private List<UserDto> matchWaitingList = new ArrayList<>(); //매칭을 누른 사용자의 대기 리스트
    private List<MatchingDto> matchingSuccessList = new ArrayList<>(); //매칭된 사용자들의 리스트



    public ResponseEntity<?> chatMatching(UserDto userDto) {
        try {
            //매칭 전 초기화
            this.matchWaitingList.remove(userDto);

            for(MatchingDto matchingDto : this.matchingSuccessList) {
                if(userDto.equals(matchingDto.getFirstUser()) || userDto.equals(matchingDto.getSecondUser())) {
                    this.matchingSuccessList.remove(matchingDto); //매칭 성공 리스트에서 제거
                }
            }

            this.registerToMatchingList(userDto);

            return ResponseEntity
                    .ok()
                    .body(ResponseDto
                            .response(HttpStatus.OK, "매칭 대기 리스트에 등록되었습니다.", userDto));
        } catch (Exception e) {
            return ResponseEntity
                    .ok()
                    .body(ResponseDto
                            .response(HttpStatus.BAD_REQUEST, e.getMessage(), userDto));
        }
    }

    public void registerToMatchingList(UserDto userDto) throws Exception {
            if(!userRepository.existsByStdNo(userDto.getStdNo())) { //등록되지 않은 학번
                throw new Exception("등록되지 않은 학번입니다.");
            } else {
                this.matchWaitingList.add(userDto); //매칭 대기 리스트에 추가
            }
    }

    @Scheduled(fixedRate = 300)
    public void processMatching(){

        List<UserDto> tempMatchWaitingList = new ArrayList<>(this.matchWaitingList); //매칭 대기 리스트 복사
        for(UserDto userDto1 : tempMatchWaitingList) {
            for(UserDto userDto2 : tempMatchWaitingList) {
                if(!userDto1.equals(userDto2)){
                    int baseUserStdNo = Integer.parseInt(userDto1.getStdNo().substring(2, 4)); //기준 유저의 학번
                    int targetUserStdNo = Integer.parseInt(userDto2.getStdNo().substring(2, 4)); //대상 유저의 학번

                    if(Math.abs(baseUserStdNo - targetUserStdNo) >= 2 && userDto1.getMajor().equals(userDto2.getMajor())){ //학번이 2 이상 차이나면서 같은 학과일 경우
                        MatchingDto matchingDto = new MatchingDto();
                        matchingDto.setMatchingId(baseUtil.getUUID()); //토픽 지정
                        matchingDto.setFirstUser(userDto1);
                        matchingDto.setSecondUser(userDto2);
                        this.matchingSuccessList.add(matchingDto); //매칭 성공 리스트에 추가
                        this.matchWaitingList.remove(userDto1);
                        this.matchWaitingList.remove(userDto2); //매칭 대기 리스트에서 제거

                        for(MatchingDto matchingDto1 : this.matchingSuccessList) {
                            System.out.println("매칭 성공 리스트");
                            System.out.println(matchingDto1.getFirstUser().getStdNo());
                            System.out.println(matchingDto1.getSecondUser().getStdNo());
                            System.out.println(matchingDto1.getMatchingId());
                        }
                        return;
                    }

                }
            }
        }
    }

    public ResponseEntity<?> chatMatchingStatus(String stdNo) {
        try {
            if(userRepository.existsByStdNo(stdNo)){
                for(MatchingDto matchingDto : this.matchingSuccessList) {
                    if(matchingDto.getFirstUser().getStdNo().equals(stdNo) || matchingDto.getSecondUser().getStdNo().equals(stdNo)){
                        return ResponseEntity
                                .ok()
                                .body(ChatResponseDto
                                        .response(HttpStatus.OK, "매칭 성공", true, matchingDto));
                    }
                }

                for(UserDto userDto : this.matchWaitingList) {
                    if(userDto.getStdNo().equals(stdNo)){
                        return ResponseEntity
                                .ok()
                                .body(ChatResponseDto
                                        .response(HttpStatus.OK, "매칭중", false, stdNo));
                    }
                }

                return ResponseEntity
                        .ok()
                        .body(ChatResponseDto
                                .response(HttpStatus.OK, "매칭 대기중", false, stdNo));

            } else {
                return ResponseEntity
                        .badRequest()
                        .body(ResponseDto
                                .response(HttpStatus.BAD_REQUEST, "등록되지 않은 학번입니다.", stdNo));
            }
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
            return ResponseEntity
                    .internalServerError()
                    .body(ResponseDto
                    .response(HttpStatus.INTERNAL_SERVER_ERROR, "조회중 오류가 발생하였습니다.", stdNo));
        }
    }


}
