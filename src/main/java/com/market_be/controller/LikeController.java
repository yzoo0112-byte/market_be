package com.market_be.controller;

import com.market_be.dto.LikeResponse;
import com.market_be.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    // 좋아요 토글
    @PostMapping("/{id}/like")
    public ResponseEntity<String> toggleLike(@PathVariable Long id, Authentication authentication) {
        String loginId = authentication.getName(); // 로그인 ID는 문자열
        likeService.toggleLike(id, loginId);       // postId 먼저, loginId 두 번째
        boolean liked = likeService.isLikedByUser(id, loginId);
        return ResponseEntity.ok(liked ? "Liked" : "Unliked");
    }

    // 좋아요 여부 확인
    @GetMapping("/{id}/like/status")
    public ResponseEntity<Boolean> isLiked(@PathVariable Long id, Authentication authentication) {
        String loginId = authentication.getName();
        boolean liked = likeService.isLikedByUser(id, loginId);
        return ResponseEntity.ok(liked);
    }

    // 좋아요 수 조회
    @GetMapping("/{id}/like/count")
    public ResponseEntity<Long> getLikeCount(@PathVariable Long id) {
        Long count = likeService.getLikeCount(id);
        return ResponseEntity.ok(count);
    }

    // 좋아요 상태와 개수를 함께 조회
    @GetMapping("/{id}/like/summary")
    public ResponseEntity<LikeResponse> getLikeSummary(@PathVariable Long id, Authentication authentication) {
        String loginId = authentication.getName();
        boolean liked = likeService.isLikedByUser(id, loginId);
        Long count = likeService.getLikeCount(id);

        LikeResponse response = LikeResponse.builder()
                .postId(id)
                .liked(liked)
                .likeCount(count)
                .build();

        return ResponseEntity.ok(response);
    }

}
