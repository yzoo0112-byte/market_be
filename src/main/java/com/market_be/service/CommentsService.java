package com.market_be.service;

import com.market_be.content.Role;
import com.market_be.dto.CommentsDto;
import com.market_be.entity.AppUser;
import com.market_be.entity.Comments;
import com.market_be.entity.Posts;
import com.market_be.repository.AppUserRepository;
import com.market_be.repository.CommentsRepository;
import com.market_be.repository.PostsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentsService {
    private final CommentsRepository commentsRepository;
    private final AppUserRepository appUserRepository;
    private final PostsRepository postsRepository;

    // 댓글 생성
    public void createComment(Long id, String comment, Long userId){
        Posts posts = postsRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        AppUser user = appUserRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        Comments comments = Comments.builder()
                .comment(comment)
                .postId(posts)
                .userId(user)

                .build();
        commentsRepository.save(comments);
    }

    // 댓글 추적
    public List<CommentsDto> getCommentsByPostId(Long postId) {
        Posts post = postsRepository.findById(postId).orElseThrow(EntityNotFoundException::new);
        List<Comments> list = commentsRepository.findAllByPostId(post);
        List<CommentsDto> dtoList = new ArrayList<>();
        for (Comments comment : list) {
            CommentsDto commentDto = CommentsDto.builder()
                    .comment(comment.getComment())
                    .nickname(comment.getUserId().getNickname())
                    .postId(postId)
                    .createdAt(comment.getCreatedAt().withNano(0))
                    .updatedAt(comment.getUpdatedAt() != null ? comment.getUpdatedAt().withNano(0) : null)
                    .build();
            dtoList.add(commentDto);
        }
        return dtoList;
    }

    //댓글 수정
    public void updateComment(Long commentId, String newContent, Long userId) {
        Comments comment = commentsRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다."));
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다."));

        boolean isAdmin = user.getRole() == Role.ADMIN;
        boolean isAuthor = comment.getUserId().getId().equals(userId);

        if (!isAdmin && !isAuthor) {
            throw new AccessDeniedException("댓글 수정 권한이 없습니다.");
        }
        else{
            Comments comments = Comments.builder()
                    .comment(newContent)
                    .build();

            comment.setComment(newContent);
            comment.setUpdatedAt(LocalDateTime.now());
            commentsRepository.save(comments);

        }
    }

}
