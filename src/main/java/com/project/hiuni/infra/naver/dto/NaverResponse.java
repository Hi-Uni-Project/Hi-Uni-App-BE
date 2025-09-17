package com.project.hiuni.infra.naver.dto;

import lombok.Getter;

@Getter
public class NaverResponse {

  private String resultcode;
  private String message;
  private Response response;

  @Getter
  public static class Response {
    private String id;
    private String name;
    private String email;
  }

}
