package com.market_be.service;

import com.market_be.entity.Posts;
import com.market_be.repository.PostsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostsService {
    private final PostsRepository postsRepository;

    //게시글 삭제
    public void deletePost(Long id){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));
        postsRepository.delete(posts);
    }
}
