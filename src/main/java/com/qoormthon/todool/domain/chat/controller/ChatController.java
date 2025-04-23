package com.qoormthon.todool.domain.chat.controller;

import com.qoormthon.todool.domain.chat.service.ChatService;
import com.qoormthon.todool.domain.user.dto.UserDto;
import com.qoormthon.todool.global.common.request.MessageRequestDto;
import com.qoormthon.todool.global.common.response.MessageResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ChatController {
    @Autowired
    private ChatService chatService;


    @MessageMapping("/chat.{matchingId}") // "/pub/chat"
    @SendTo("/sub/chat")
    public MessageResponseDto sendMessage(MessageRequestDto requestDto, @DestinationVariable String matchingId) {
        System.out.println(requestDto.getMessage());
        MessageResponseDto responseDto = new MessageResponseDto();
        responseDto.setMessage(requestDto.getMessage());
        return responseDto;
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
