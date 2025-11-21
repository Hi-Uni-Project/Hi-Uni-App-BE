package com.project.hiuni.infra.claude.prompt;

import com.project.hiuni.domain.post.entity.Post;
import com.project.hiuni.domain.user.entity.User;
import java.util.List;
import lombok.Getter;

@Getter
public class ClaudeAiPrompt {

  public static String ABOUT_ME_PROMPT(List<Post> posts, User user) {

    StringBuilder sb = new StringBuilder();
    for (Post post : posts) {
      sb.append("- 제목: ").append(post.getTitle()).append("\n");
      sb.append("  내용: ").append(post.getContent().replaceAll("\n", " ")).append("\n\n");
    }
    String formattedPosts = sb.toString();

    return """
        === 작성자 정보 ===
        전공: %s
        
        === 작성자 게시글 데이터 ===
        %s
        
        === 작성 예시 ===
        1. 저는 사용자 경험에 진심인 디자이너, 000입니다.
         단순히 예쁜 화면을 만드는 것을 넘어, 사용자의 감정과 맥락을 이해하 '왜 이 디자인이어야 하는가' 를 항상 고민합니다.
        
        === 작성 원칙 ===
        1. 작성자의 전공 및 게시글 데이터를 활용하여 작성
        2. 글자수는 200자 내외로 작성
        3. 작성자 이름은 000로 표시
        """.formatted(
            user.getFirstMajorName(),
            formattedPosts
            );
  }

}
