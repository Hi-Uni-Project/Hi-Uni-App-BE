package com.project.hiuni.domain.bookmark.v1.service;

import com.project.hiuni.domain.bookmark.entity.Bookmark;
import com.project.hiuni.domain.bookmark.exception.CustomNotBookmarkException;
import com.project.hiuni.domain.bookmark.repository.BookmarkRepository;
import com.project.hiuni.domain.bookmark.exception.CustomDuplicatedBookmarkException;
import com.project.hiuni.domain.post.entity.Post;
import com.project.hiuni.domain.post.exception.CustomPostNotFoundException;
import com.project.hiuni.domain.post.repository.PostRepository;
import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.exception.CustomUserNotFoundException;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public void addBookmark(Long postId, Long userId){
        if(bookmarkRepository.existsByPostIdAndUserId(postId, userId)){
            throw new CustomDuplicatedBookmarkException(ErrorCode.DUPLICATED_BOOKMARK);
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new CustomPostNotFoundException(ErrorCode.POST_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));

        Bookmark bookmark = Bookmark.builder()
                .post(post)
                .user(user)
                .build();

        try{
            bookmarkRepository.save(bookmark);
        }catch (DataIntegrityViolationException e){
            throw new CustomDuplicatedBookmarkException(ErrorCode.DUPLICATED_BOOKMARK);
        }

        post.incrementBookmarkCount();
    }

    @Transactional
    public void removeBookmark(Long postId, Long userId){

        Bookmark bookmark = bookmarkRepository.findByPostIdAndUserId(postId, userId)
                .orElseThrow(() -> new CustomNotBookmarkException(ErrorCode.NOT_BOOKMARK));

        Post post = bookmark.getPost();
        bookmarkRepository.delete(bookmark);
        post.decrementBookmarkCount();
    }

}
