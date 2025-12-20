package com.project.hiuni.infra.claude.prompt;

import com.project.hiuni.domain.user.entity.User;
import java.util.List;
import lombok.Getter;

@Getter
public class ClaudeAiPrompt {

  public static String ABOUT_ME_PROMPT(List<List<String>> posts, User user) {

    StringBuilder sb = new StringBuilder();

    int cnt = 1;
    for (List<String> post : posts) {
      sb.append("[게시글 ").append(cnt++).append("]\n");
      for (String item : post) {
        if (item != null && !item.trim().isEmpty()) {
          sb.append("  - ").append(item).append("\n");
        }
      }
      sb.append("\n");
    }

    String formattedPosts = sb.toString();

    return """
        === Author Profile ===
        Major: %s
        
        === Author's Posts ===
        %s
        
        === Example Output ===
        저는 사용자 경험에 진심인 디자이너, 000입니다.
        단순히 예쁜 화면을 만드는 것을 넘어, 사용자의 감정과 맥락을 이해하고 '왜 이 디자인이어야 하는가'를 항상 고민합니다.
        
        === Instructions ===
        1. Analyze author's writing style: vocabulary choice, sentence patterns, tone, and expressions
        2. Write in a similar but 20%% more polished style than the original posts
        3. Keep the author's unique voice and language level, but refine awkward phrases
        4. Naturally incorporate major and experience data
        5. ~200 characters in Korean
        6. Replace author name with "000"
        7. **CRITICAL**: Only use information from the posts above. Do not add fictional content or experiences.
        """.formatted(
            user.getFirstMajorName(),
            formattedPosts
            );
  }

  public static String COVER_LETTER_PROMPT(List<List<String>> posts, User user, String role, String question) {

    StringBuilder sb = new StringBuilder();

    int cnt = 1;
    for (List<String> post : posts) {
      sb.append("[게시글 ").append(cnt++).append("]\n");
      for (String item : post) {
        if (item != null && !item.trim().isEmpty()) {
          sb.append("  - ").append(item).append("\n");
        }
      }
      sb.append("\n");
    }

    String formattedPosts = sb.toString();

    return """
    === Author Profile ===
    Major: %s
    Target Role: %s
    Question: %s
    
    === Author's Posts ===
    %s
    
    === Instructions ===
    1. Analyze author's writing style: vocabulary choice, sentence patterns, tone, and expressions
    2. Write in a similar but 20-30%% more polished style than the original posts
    3. Keep the author's unique voice and language level, but refine awkward phrases
    4. Naturally incorporate major, target role, and experience data to answer the question
    5. ~700 characters in Korean
    6. Answer only the question asked - no extra content
    7. No markdown or unnecessary punctuation (line breaks allowed)
    8. Replace author name with "000"
    9. **CRITICAL**: Only use information from the posts above. Do not add fictional content or experiences.
    
    Note: Mirror the style, don't copy verbatim. Maintain authenticity while improving clarity.
    """.formatted(
        user.getFirstMajorName(),
        role,
        question,
        formattedPosts
    );
  }







}
