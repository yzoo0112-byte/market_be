package com.market_be.service;

import com.market_be.dto.PostRequestDto;
import com.market_be.entity.AppUser;
import com.market_be.entity.Posts;
import com.market_be.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Posts save(Posts posts) {
        return postRepository.save(posts);
    }

    public List<Posts> findAll() {
        return postRepository.findAll();
    }
    // ✅ 게시글 수정
    public Posts updatePost(Long postId, PostRequestDto dto, AppUser user) {
        Posts post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        if (!post.getUserId().getId().equals(user.getId())) {
            throw new SecurityException("수정 권한이 없습니다.");
        }

        post.setTitle(dto.getTitle());
        post.setHashtag(dto.getHashtag());
        post.setContent(dto.getContent());
        post.setUpdateAt(LocalDateTime.now());

        return postRepository.save(post);
    }
}

