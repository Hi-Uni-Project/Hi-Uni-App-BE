package com.project.hiuni.infra.claude;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClaudeAiClient {
  private final AnthropicChatModel chatModel;

  public String sendMessage(String prompt) {
    try {

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new
    }
  }


}
