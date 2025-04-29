package com.qoormthon.todool.global.common.response;
import com.qoormthon.todool.domain.chat.dto.MatchingDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
public class ChatResponseDto<T>  {
    private HttpStatus status;
    private String message;
    private boolean isMatched;
    private T data;

    public static <T> ChatResponseDto<T> response(HttpStatus status, String message, boolean isMatched, T data) {
        ChatResponseDto<T> chatResponseDto = new ChatResponseDto<>();
        chatResponseDto.setStatus(status);
        chatResponseDto.setMessage(message);
        chatResponseDto.setMatched(isMatched);
        chatResponseDto.setData(data);
        return chatResponseDto;
    }
}