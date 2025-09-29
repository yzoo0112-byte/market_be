package com.market_be.service;

import com.market_be.dto.PostRequestDto;
import com.market_be.entity.*;
import com.market_be.repository.CommentsRepository;
import com.market_be.repository.FilesRepository;
import com.market_be.repository.LikesRepository;
import com.market_be.repository.PostsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostsService {
    private final PostsRepository postsRepository;
    private final LikesRepository likesRepository;
    private final FilesRepository filesRepository;
    private final CommentsRepository commentsRepository;

    // ✅ 게시글 삭제
    public void deletePost(Long id) {
        Posts posts = postsRepository.findByIdIncludingDeleted(id)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        List<Likes> likes = likesRepository.findByPostId(posts);
        List<Files> files = filesRepository.findByPost(posts);
        List<Comments> comments = commentsRepository.findAllByPostId(posts);

        likesRepository.deleteAll(likes);
        filesRepository.deleteAll(files);
        commentsRepository.deleteAll(comments);
        postsRepository.delete(posts);

    }

    // ✅ 게시글 수정
    public Posts updatePost(Long postId, PostRequestDto dto, AppUser user) {
        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        if (!post.getUserId().getId().equals(user.getId())) {
            throw new SecurityException("수정 권한이 없습니다.");
        }

        post.setTitle(dto.getTitle());
        post.setHashtag(dto.getHashtag());
        post.setContent(dto.getContent());
        post.setUpdateAt(LocalDateTime.now());

        return postsRepository.save(post);
    }

    // 게시글 삭제(휴지통)
    public void softDelete(Long id) {
        Posts post = postsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));
        post.setDeleted(true);
        postsRepository.save(post);
    }

    // 게시글 복구
    public void restore(Long id) {
        Posts post = postsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));
        post.setDeleted(false);
        postsRepository.save(post);
    }


}