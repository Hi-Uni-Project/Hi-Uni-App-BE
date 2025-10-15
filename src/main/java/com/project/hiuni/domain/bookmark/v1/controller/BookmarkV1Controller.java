package com.project.hiuni.domain.bookmark.v1.controller;

import com.project.hiuni.domain.bookmark.v1.service.BookmarkService;
import com.project.hiuni.global.common.dto.response.ResponseDTO;
import com.project.hiuni.global.security.core.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bookmarks")
@RequiredArgsConstructor
public class BookmarkV1Controller {

    private final BookmarkService bookmarkService;

    @PostMapping("/{postId}")
    public ResponseDTO<Void> createBookmark(@PathVariable("postId") Long postId,
                                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        bookmarkService.addBookmark(postId, userDetails.getId());

        return ResponseDTO.of("북마크 등록에 성공하였습니다.");
    }

    @DeleteMapping("/{postId}")
    public ResponseDTO<Void> deleteBookmark(@PathVariable("postId") Long id,
                                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        bookmarkService.removeBookmark(id, userDetails.getId());

        return ResponseDTO.of("북마크 삭제에 성공하였습니다.");
    }
}
