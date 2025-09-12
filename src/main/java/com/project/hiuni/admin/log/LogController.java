package com.project.hiuni.admin.log;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/admin/logs")
public class LogController {

  @GetMapping
  public ResponseEntity<?> getLogs(HttpServletRequest request) throws IOException {
    String authHeader = request.getHeader("AuthorizationAdmin");

    if(authHeader != null && authHeader.equals("hiuniAdmin94562734!!@")) {
      // 파일을 한 번에 문자열로 읽기
      String logs = Files.readString(Paths.get("./output.log"), StandardCharsets.UTF_8);

      Map<String, Object> map = new HashMap<>();
      map.put("logs", logs);

      return ResponseEntity.ok().body(map);
    } else {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
    }
  }

  @GetMapping("/page")
  public String getLogPage() {
    return "log";
  }

}
