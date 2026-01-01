package com.project.hiuni.domain.post.common;

import com.project.hiuni.domain.comment.projection.PostCommentCount;
import com.project.hiuni.domain.comment.repository.CommentRepository;
import com.project.hiuni.domain.post.dto.response.PostPreviewResponse;
import com.project.hiuni.domain.post.entity.Post;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostPreviewMapper {
    private final CommentRepository commentRepository;

    public List<PostPreviewResponse> toPreviewResponses(List<Post> posts) {
        List<Long> postIds = posts.stream().map(Post::getId).toList();

        Map<Long, Integer> commentCountMap = commentRepository
                .countByPostIds(postIds)
                .stream()
                .collect(Collectors.toMap(
                        PostCommentCount::getPostId,
                        PostCommentCount::getCount
                ));

        return posts.stream()
                .map(post -> PostPreviewResponse.from(
                        post, commentCountMap.getOrDefault(post.getId(), 0)
                ))
                .toList();
    }
}
