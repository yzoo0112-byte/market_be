package com.market_be.service;

import com.market_be.content.Role;
import com.market_be.dto.CommentRequest;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentsService {
    private final CommentsRepository commentsRepository;
    private final AppUserRepository appUserRepository;
    private final PostsRepository postsRepository;

    // 댓글 생성
    public Comments createComment(Long postId, String comment, String userId){
        Posts posts = postsRepository.findById(postId).orElseThrow(EntityNotFoundException::new);
        AppUser user = appUserRepository.findByLoginId(userId).orElseThrow(EntityNotFoundException::new);
        Comments comments = Comments.builder()
                .comment(comment)
                .postId(posts)
                .userId(user)
                .build();
        commentsRepository.save(comments);
        return comments;
    }

    // 댓글 추적
    public List<CommentsDto> getCommentsByPostId(Long postId) {
        Posts post = postsRepository.findById(postId).orElseThrow(EntityNotFoundException::new);
        List<Comments> list = commentsRepository.findAllByPostId(post);
        List<CommentsDto> dtoList = new ArrayList<>();
        for (Comments comment : list) {
            CommentsDto commentDto = CommentsDto.builder()
                    .commentId(comment.getId())
                    .comment(comment.getComment())
                    .nickname(comment.getUserId().getNickname())
                    .postId(postId)
                    .userId(comment.getUserId().getLoginId())
                    .createdAt(comment.getCreatedAt().withNano(0))
                    .updatedAt(comment.getUpdatedAt() != null ? comment.getUpdatedAt().withNano(0) : null)
                    .build();
            dtoList.add(commentDto);
        }
        return dtoList;
    }

    //댓글 수정
    public void updateComment(CommentRequest dto, String loginId) {
        Comments comment = commentsRepository.findById(dto.getCommentId())
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다."));
        AppUser user = appUserRepository.findByLoginId(loginId)
                .orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다."));

        boolean isAdmin = user.getRole() == Role.ADMIN;
        boolean isAuthor = comment.getUserId().getLoginId().equals(loginId);

        if (!isAdmin && !isAuthor) {
            throw new AccessDeniedException("댓글 수정 권한이 없습니다.");
        }
        else{
//            Comments comments = Comments.builder()
//                    .comment(newContent)
//                    .build();

            comment.setComment(dto.getComment());
            comment.setUpdatedAt(LocalDateTime.now());
//            commentsRepository.save(comments);

        }
    }

    // 댓글 삭제
    public void deleteComment(Long commentId, String userId) {
        Comments comment = commentsRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다."));
        AppUser user = appUserRepository.findByLoginId(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다."));

        boolean isAdmin = user.getRole() == Role.ADMIN;
        boolean isAuthor = comment.getUserId().getLoginId().equals(userId);

        if (!isAdmin && !isAuthor) {
            throw new AccessDeniedException("댓글 삭제 권한이 없습니다.");
        }

        commentsRepository.delete(comment);
    }


}
