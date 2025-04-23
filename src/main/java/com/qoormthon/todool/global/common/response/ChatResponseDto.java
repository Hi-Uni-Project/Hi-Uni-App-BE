package com.qoormthon.todool.global.common.response;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ChatResponseDto<T>  {
    private HttpStatus status;
    private String message;
    private Boolean isMatched;
    private T data;

    public static <T> ChatResponseDto<T> response(HttpStatus status, String message, Boolean isMatched, T data) {
        ChatResponseDto<T> chatResponseDto = new ChatResponseDto<>();
        chatResponseDto.setStatus(status);
        chatResponseDto.setMessage(message);
        chatResponseDto.setIsMatched(isMatched);
        chatResponseDto.setData(data);
        return chatResponseDto;
    }
}