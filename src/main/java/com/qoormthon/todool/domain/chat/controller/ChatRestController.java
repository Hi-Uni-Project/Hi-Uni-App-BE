package com.qoormthon.todool.domain.chat.controller;

import com.qoormthon.todool.domain.chat.dto.ChatUserDto;
import com.qoormthon.todool.domain.chat.service.ChatService;
import com.qoormthon.todool.domain.user.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
@Tag(name = "매칭 관련 api 입니다.", description = "매칭시작, 매칭 상태 조회, 매칭 취소 기능입니다.")
public class ChatRestController {

    @Autowired
    private ChatService chatService;


    @Operation(summary = "매칭 시작 api 입니다.", description = "사용자 정보를 함께 반환합니다.")
    @PostMapping("/matching")
    public ResponseEntity<?> chatMatching(@RequestBody ChatUserDto userDto) {
        return chatService.chatMatching(userDto);
    }

    @Operation(summary = "매칭 상태 조회 api 입니다.", description = "매칭 시작 이후 현재 유저가 매칭되었는지를 판단하며, 매칭되었을 경우 토픽 id를 반환합니다 / 폴링 방식으로 구현이 필요해 보입니다..")
    @ApiResponse(
            responseCode = "200",
            description = "매칭 상태 JSON 응답",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            example = """
            {
              "status": "OK",
              "message": "매칭 성공",
              "isMatched": true,
              "data": {
                "firstUser": {
                  "stdNo": "20191476",
                  "nickName": "techguru",
                  "major": "Computer Sience",
                  "gender": "male",
                  "mbti": "INTJ",
                  "imageUrl": null
                },
                "secondUser": {
                  "stdNo": "20211476",
                  "nickName": "techguru",
                  "major": "Computer Sience",
                  "gender": "male",
                  "mbti": "INTJ",
                  "imageUrl": null
                },
                "matchingId": "6ccc561d-5641-45fa-9925-84444332c0aa"
              }
            }
            """
                    )
            )
    )
    @GetMapping("/matching/status/{userId}")
    public ResponseEntity<?> chatMatchingStatus(@PathVariable String userId) {
        return chatService.chatMatchingStatus(userId);
    }

    @ApiResponse(
            responseCode = "200",
            description = "연결 종료 JSON 응답",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            example = """
            {
              "status": "OK",
              "message": "연결이 종료되었습니다.",
              "isMatched": false,
              "data": null
            }
            """
                    )
            )
    )
    @Operation(summary = "매칭 취소 및 채팅 나가기 api 입니다", description = "매칭중에 취소하거나 또는 채팅방에서 나갈때 호출되는 api 입니다. " +
            "채팅도중 해당 api를 호출하면 매칭 성공 리스트에서 제외되며 채팅방에서 나가게 됩니다. " +
            "이때 서버측에서는 웹소켓을 통해 나와 상대에게 연결 종료를 지시하게 되며 프론트엔드에서 연결 종료 시키면 될 것 같습니다")
    @GetMapping("/matching/cancel/{userId}")
    public ResponseEntity<?> cancelMatching(@PathVariable String userId) {
        return chatService.cancelMatching(userId);
    }
}
