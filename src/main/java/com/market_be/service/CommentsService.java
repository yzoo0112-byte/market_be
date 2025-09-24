package com.market_be.service;

import com.market_be.entity.AppUser;
import com.market_be.entity.Comments;
import com.market_be.entity.Posts;
import com.market_be.repository.AppUserRepository;
import com.market_be.repository.CommentsRepository;
import com.market_be.repository.PostsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class CommentsService {
    private final CommentsRepository commentsRepository;
    private final AppUserRepository appUserRepository;
    private final PostsRepository postsRepository;

    public void createComment(Long id, String comment, Long nickname, Date createdAt, Date updatedAt){
        Posts posts = postsRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        AppUser user = appUserRepository.findById(nickname).orElseThrow(EntityNotFoundException::new);
        Comments comments = Comments.builder()
                .comment(comment)
                .postId(posts)
                .userId(user)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
        commentsRepository.save(comments);
    }
}
