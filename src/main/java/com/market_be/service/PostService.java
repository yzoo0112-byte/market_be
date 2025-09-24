package com.market_be.service;

import com.market_be.entity.Posts;
import com.market_be.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
