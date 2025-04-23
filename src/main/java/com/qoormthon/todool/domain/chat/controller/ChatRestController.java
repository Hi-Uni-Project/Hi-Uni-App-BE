package com.qoormthon.todool.domain.chat.controller;

import com.qoormthon.todool.domain.chat.service.ChatService;
import com.qoormthon.todool.domain.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatRestController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/matching")
    public ResponseEntity<?> chatMatching(@RequestBody UserDto userDto) {
        return chatService.chatMatching(userDto);
    }

    @GetMapping("/matching/status/{stdNo}")
    public ResponseEntity<?> chatMatchingStatus(@PathVariable String stdNo) {
        return chatService.chatMatchingStatus(stdNo);
    }

    @GetMapping("/matching/cancel/{stdNo}")
    public ResponseEntity<?> cancelMatching(@PathVariable String stdNo) {
        return chatService.cancelMatching(stdNo);
    }
}
