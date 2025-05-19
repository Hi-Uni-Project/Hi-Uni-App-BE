package com.qoormthon.todool.domain.chat.controller;

import com.qoormthon.todool.domain.chat.dto.ChatDto;
import com.qoormthon.todool.domain.chat.repository.ChatRepository;
import com.qoormthon.todool.domain.chat.service.ChatService;
import com.qoormthon.todool.global.common.request.MessageRequestDto;
import com.qoormthon.todool.global.common.util.BaseUtil;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Hidden
public class ChatController {
    @Autowired
    private ChatService chatService;

    @Autowired
    private BaseUtil baseUtil;

    @Autowired
    private ChatRepository chatRepository;


    @MessageMapping("/chat.{matchingId}") // "/pub/chat"
    @SendTo("/sub/chat.{matchingId}")
    public MessageRequestDto sendMessage(MessageRequestDto requestDto, @DestinationVariable String matchingId) {
        System.out.println("sendMessage 메서드 호출");
        ChatDto chatDto = new ChatDto();
        chatDto.setSenderUserId(requestDto.getSenderUserId());
        chatDto.setReceiverUserId(requestDto.getReceiverUserId());
        chatDto.setMessage(requestDto.getMessage());
        chatDto.setMatchingId(matchingId);
        chatDto.setDate(baseUtil.getTodayAndTime());
        chatRepository.save(chatDto.toEntity());

        return requestDto;
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
