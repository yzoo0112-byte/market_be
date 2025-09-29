package com.market_be.controller;

import com.market_be.dto.CommentRequest;
import com.market_be.dto.CommentsDto;
import com.market_be.entity.AppUser;
import com.market_be.entity.Comments;
import com.market_be.entity.Posts;
import com.market_be.repository.AppUserRepository;
import com.market_be.repository.CommentsRepository;
import com.market_be.repository.PostsRepository;
import com.market_be.service.CommentsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class CommentsController {
    private final CommentsService commentsService;

    // 댓글 작성
    @PostMapping("/{id}/comment")
    public ResponseEntity<?> createComment(
            @PathVariable Long id,
            @RequestBody CommentsDto dto,
            Authentication authentication

    ) {
        Long parentId = dto.getParentId();
       commentsService.createComment(id, dto.getComment(), authentication.getName(), parentId);

        return ResponseEntity.status(201).build();
    }

    //댓글 조회
    @GetMapping("/{id}/comment")
    public ResponseEntity<?> getCommentsByBoardId(@PathVariable Long id) {
        return ResponseEntity.ok(commentsService.getCommentsByPostId(id));
    }

    //댓글 수정
    @PutMapping("/{id}/comment/{commentId}")
    public ResponseEntity<?> updateComment(
                                           @RequestBody CommentRequest dto,

                                           Authentication authentication) {
        commentsService.updateComment(dto, authentication.getName());
        return ResponseEntity.ok("댓글이 수정되었습니다.");
    }

    // 댓글 삭제
    @DeleteMapping("/{id}/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id, @PathVariable Long commentId, Authentication authentication) {
        String userId = authentication.getName();
        commentsService.deleteComment(commentId, userId);
        return ResponseEntity.noContent().build();
    }

}
