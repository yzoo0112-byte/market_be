package com.market_be.service;

import com.market_be.entity.AppUser;
import com.market_be.entity.Likes;
import com.market_be.entity.Posts;
import com.market_be.repository.AppUserRepository;
import com.market_be.repository.LikesRepository;
import com.market_be.repository.PostsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final PostsRepository postsRepository;
    private final AppUserRepository appUserRepository;
    private final LikesRepository likesRepository;

    // 좋아요 토글
    public void toggleLike(Long postId, String loginId) {
        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));
        AppUser user = appUserRepository.findByLoginId(loginId)
                .orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다."));

        boolean alreadyLiked = likesRepository.existsByUserIdAndPostId(user, post);

        if (alreadyLiked) {
            likesRepository.deleteByUserIdAndPostId(user, post);
        } else {
            Likes like = Likes.builder()
                    .userId(user)
                    .postId(post)
                    .build();
            likesRepository.save(like);
        }
    }

    // 좋아요 여부 확인
    public boolean isLikedByUser(Long postId, String loginId) {
        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));
        AppUser user = appUserRepository.findByLoginId(loginId)
                .orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다."));

        return likesRepository.existsByUserIdAndPostId(user, post);
    }

    // 좋아요 수 조회
    public Long getLikeCount(Long postId) {
        return likesRepository.countByPostId(postId); // @Query로 구현된 메서드 사용
    }



}
