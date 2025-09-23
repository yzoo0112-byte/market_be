package com.market_be.controller;

import com.market_be.dto.PostsDto;
import com.market_be.entity.Posts;
import com.market_be.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostsController {
    private final PostsRepository postsRepository;

    @GetMapping("/{id}")
    public ResponseEntity<PostsDto> findById(@PathVariable Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다."));

        PostsDto dto = PostsDto.fromEntity(posts);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
}
