package com.qoormthon.todool.domain.chat.service;

import com.qoormthon.todool.domain.chat.dto.ChatUserDto;
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
import org.springframework.messaging.simp.SimpMessagingTemplate;
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

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private List<ChatUserDto> matchWaitingList = new ArrayList<>(); //매칭을 누른 사용자의 대기 리스트
    private List<MatchingDto> matchingSuccessList = new ArrayList<>(); //매칭된 사용자들의 리스트



    public ResponseEntity<?> chatMatching(ChatUserDto userDto) {
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
                    .badRequest()
                    .body(ResponseDto
                            .response(HttpStatus.BAD_REQUEST, e.getMessage(), userDto));
        }
    }

    public void registerToMatchingList(ChatUserDto userDto) throws Exception {
            if(!userRepository.existsByUserId(userDto.getUserId())) { //등록되지 않은 학번
                throw new Exception("등록되지 않은 학번입니다.");
            } else {
                for(ChatUserDto chatUserDto : this.matchWaitingList) {
                    if(chatUserDto.getStdNo().equals(userDto.getStdNo())) { //이미 매칭 대기 리스트에 등록된 경우
                        throw new Exception("이미 매칭 대기 리스트에 등록되어 있습니다.");
                    }
                }
                this.matchWaitingList.add(userDto); //매칭 대기 리스트에 추가
            }
    }

    @Scheduled(fixedRate = 300)
    public void processMatching(){

        List<ChatUserDto> tempMatchWaitingList = new ArrayList<>(this.matchWaitingList); //매칭 대기 리스트 복사
        for(ChatUserDto userDto1 : tempMatchWaitingList) {
            for(ChatUserDto userDto2 : tempMatchWaitingList) {
                if(!userDto1.equals(userDto2)){
                    int baseUserStdNo = Integer.parseInt(userDto1.getStdNo().substring(2, 4)); //기준 유저의 학번
                    int targetUserStdNo = Integer.parseInt(userDto2.getStdNo().substring(2, 4)); //대상 유저의 학번

                    if(Math.abs(baseUserStdNo - targetUserStdNo) >= 2 && userDto1.getMajor().equals(userDto2.getMajor()) && userDto1.getUniv().equals(userDto2.getUniv())) { //학번이 2 이상 차이나면서 같은 학교 학과일 경우
                        MatchingDto matchingDto = new MatchingDto();
                        matchingDto.setMatchingId(baseUtil.getUUID()); //토픽 지정
                        matchingDto.setFirstUser(userDto1);
                        matchingDto.setSecondUser(userDto2);
                        this.matchingSuccessList.add(matchingDto); //매칭 성공 리스트에 추가
                        this.matchWaitingList.remove(userDto1);
                        this.matchWaitingList.remove(userDto2); //매칭 대기 리스트에서 제거
                        return;
                    }

                }
            }
        }
    }

    public ResponseEntity<?> chatMatchingStatus(String userId) {

        try {
            if(userRepository.existsByUserId(userId)){
                for(MatchingDto matchingDto : this.matchingSuccessList) {
                    if(matchingDto.getFirstUser().getUserId().equals(userId) || matchingDto.getSecondUser().getUserId().equals(userId)) {
                        return ResponseEntity
                                .ok()
                                .body(ChatResponseDto
                                        .response(HttpStatus.OK, "매칭 성공", true, matchingDto));
                    }
                }

                for(ChatUserDto userDto : this.matchWaitingList) {
                    if(userDto.getUserId().equals(userId)) {
                        return ResponseEntity
                                .ok()
                                .body(ChatResponseDto
                                        .response(HttpStatus.OK, "매칭중", false, userId));
                    }
                }

                return ResponseEntity
                        .ok()
                        .body(ChatResponseDto
                                .response(HttpStatus.OK, "매칭 대기중", false, userId));

            } else {
                return ResponseEntity
                        .badRequest()
                        .body(ResponseDto
                                .response(HttpStatus.BAD_REQUEST, "등록되지 않은 학번입니다.", userId));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity
                    .internalServerError()
                    .body(ResponseDto
                    .response(HttpStatus.INTERNAL_SERVER_ERROR, "조회중 오류가 발생하였습니다.", userId));
        }
    }

    public ResponseEntity<?> cancelMatching(String userId){
        try{
            if(userRepository.existsByUserId(userId)) {
                this.disconnectClientsFromTopic(this.findMatchingId(userId));
                for(ChatUserDto chatUserDto : this.matchWaitingList) { //매칭 대기 리스트에서 제거
                    if(chatUserDto.getUserId().equals(userId)) {
                        this.matchWaitingList.remove(chatUserDto);
                        break;
                    }
                }
                return ResponseEntity
                        .ok()
                        .body(ResponseDto
                                .response(HttpStatus.OK, "매칭 취소되었습니다.", userId));
            } else {
                return ResponseEntity
                        .badRequest()
                        .body(ResponseDto
                                .response(HttpStatus.BAD_REQUEST, "등록되지 않은 학번입니다.", userId));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity
                    .internalServerError()
                    .body(ResponseDto
                            .response(HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생하였습니다.", userId));
        }
    }

    public String findMatchingId(String userId) {
        for(MatchingDto matchingDto : this.matchingSuccessList) {
            if(matchingDto.getFirstUser().getUserId().equals(userId) || matchingDto.getSecondUser().getUserId().equals(userId)) {
                return matchingDto.getMatchingId();
            }
        }
        return null;
    }

    public void disconnectClientsFromTopic(String matchingId) {
        // 연결 종료 메시지 전송
        messagingTemplate.convertAndSend("/sub/chat." + matchingId,
                ChatResponseDto.response(HttpStatus.OK, "연결이 종료되었습니다.", false, null));

        for(MatchingDto matchingDto : this.matchingSuccessList) { //매칭 성공 리스트에서 제거
            if(matchingDto.getMatchingId().equals(matchingId)){
                for(ChatUserDto chatUserDto : this.matchWaitingList) {
                    if(chatUserDto.equals(matchingDto.getFirstUser()) || chatUserDto.equals(matchingDto.getSecondUser())){
                        this.matchWaitingList.remove(matchingDto.getFirstUser());
                        this.matchWaitingList.remove(matchingDto.getSecondUser());
                        break;
                    }
                }
                this.matchingSuccessList.remove(matchingDto);
                break;
            }
        }
    }


}
