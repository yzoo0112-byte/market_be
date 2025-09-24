package com.market_be.service;

import com.market_be.dto.CommentsDto;
import com.market_be.entity.AppUser;
import com.market_be.entity.Comments;
import com.market_be.entity.Posts;
import com.market_be.repository.AppUserRepository;
import com.market_be.repository.CommentsRepository;
import com.market_be.repository.PostsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentsService {
    private final CommentsRepository commentsRepository;
    private final AppUserRepository appUserRepository;
    private final PostsRepository postsRepository;

    public void createComment(Long id, String comment, Long nickname){
        Posts posts = postsRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        AppUser user = appUserRepository.findById(nickname).orElseThrow(EntityNotFoundException::new);
        Comments comments = Comments.builder()
                .comment(comment)
                .postId(posts)
                .userId(user)

                .build();
        commentsRepository.save(comments);
    }

    public List<CommentsDto> getCommentsByPostId(Long postId) {
        Posts post = postsRepository.findById(postId).orElseThrow(EntityNotFoundException::new);
        List<Comments> list = commentsRepository.findAllByPostId(post);
        List<CommentsDto> dtoList = new ArrayList<>();
        for (Comments comment : list) {
            CommentsDto commentDto = CommentsDto.builder()
                    .comment(comment.getComment())
                    .userId(comment.getUserId().getId())
                    .postId(postId)
                    .createdAt(comment.getCreatedAt().withNano(0))
                    .updatedAt(comment.getUpdatedAt().withNano(0))
                    .build();
            dtoList.add(commentDto);
        }
        return dtoList;
    }
}
