package com.project.hiuni.domain.post.v1.service;

import com.project.hiuni.domain.post.exception.CustomSearchLogNotFoundException;
import com.project.hiuni.domain.post.model.SearchLog;
import com.project.hiuni.domain.post.dto.request.SearchLogRequest;
import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.exception.CustomUserNotFoundException;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.exception.ErrorCode;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchLogService {

    private static final int MAXIMUM=10;

    private final UserRepository userRepository;
    private final RedisTemplate<String, SearchLog> redisTemplate;

    public void saveRecentSearchLog(SearchLogRequest request, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        String key = "SearchLog" + user.getId();

        List<SearchLog> allLogs= redisTemplate.opsForList().range(key, 0, -1);
        if (allLogs !=null) {
            allLogs.stream()
                    .filter(log -> Objects.equals(log.getName(), request.name()))
                    .findFirst()
                    .ifPresent(log -> redisTemplate.opsForList().remove(key, 1, log));
        }

        Long size = redisTemplate.opsForList().size(key);

        if(size!=null && size>= MAXIMUM) {
            redisTemplate.opsForList().rightPop(key);
        }

        SearchLog value = SearchLog.builder()
                .name(request.name())
                .build();

        redisTemplate.opsForList().leftPush(key, value);
    }

    public List<SearchLogRequest> findRecentSearchLogs(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        String key = "SearchLog" + user.getId();
        List<SearchLog> logs = redisTemplate.opsForList().range(key, 0, MAXIMUM-1);

        if (logs == null) return List.of();

        return logs.stream()
                .map(it -> new SearchLogRequest(it.getName()))
                .collect(Collectors.toList());
    }

    public void deleteRecentSearchLog(SearchLogRequest request, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        String key = "SearchLog" + user.getId();

        List<SearchLog> allLogs = redisTemplate.opsForList().range(key, 0, -1);
        if (allLogs == null) {
            throw new CustomSearchLogNotFoundException(ErrorCode.SEARCH_LOG_NOT_FOUND);
        }

        SearchLog value = allLogs.stream()
                .filter(log -> Objects.equals(log.getName(), request.name()))
                .findFirst()
                .orElseThrow(() -> new CustomSearchLogNotFoundException(ErrorCode.SEARCH_LOG_NOT_FOUND));

        Long removed = redisTemplate.opsForList().remove(key, 1, value);

        if (removed == null || removed == 0) {
            throw new CustomSearchLogNotFoundException(ErrorCode.SEARCH_LOG_NOT_FOUND);
        }
    }
}
