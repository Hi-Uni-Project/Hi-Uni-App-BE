package com.qoormthon.todool.domain.chatbot.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChatBotService {
    public static final String PROMPT = """
            
          
            """;
    private final AnthropicChatModel chatModel;

    @Autowired
    public ChatBotService(AnthropicChatModel chatModel) { //챗봇 생성자 주입
        this.chatModel = chatModel;
    }

     public void sendChatBot(String chatBotMessage) throws Exception {
        String response = chatModel.call(PROMPT + "/채팅 : " + chatBotMessage);
     }

}
