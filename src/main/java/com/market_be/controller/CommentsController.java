package com.market_be.controller;

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
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class CommentsController {
    private final CommentsService commentsService;


    @PostMapping("/{Id}/comments")
    public ResponseEntity<?> creatComment(@PathVariable Long Id, @RequestBody CommentsDto dto){
        commentsService.createComment(
                Id,
                dto.getComment(),
                dto.getId(), 
                new Date(),
                new Date()
        );

        return ResponseEntity.ok("댓글이 등록되었습니다.");

    }

}
