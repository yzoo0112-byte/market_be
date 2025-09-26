package com.market_be.service;

import com.market_be.dtotest.ApiResponse;
import com.market_be.dtotest.PostDTO;
import com.market_be.entity.AppUser;
import com.market_be.entity.Posts;
import com.market_be.repository.PostRepository;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public ApiResponse getPosts(int page, int size, String sortKey, String sortOrder, String keyword) {
        // 키워드를 공백 기준으로 나누어 List<String> 형식으로 변환
        List<String> keywords = (keyword == null || keyword.isBlank())
                ? List.of()  // 키워드가 비어 있으면 빈 리스트 반환
                : Arrays.asList(keyword.trim().split("\\s+"));  // 공백으로 키워드 나누기

        // Pageable 설정: 페이지, 사이즈, 정렬
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), mapSortKey(sortKey)));

        // 키워드가 없으면 전체 게시글 조회, 있으면 Specification을 이용하여 검색
        Page<Posts> postPage = keywords.isEmpty()
                ? postRepository.findAll(pageable)
                : postRepository.findAll(searchByKeywords(keywords), pageable);

        // DTO로 변환
        List<PostDTO> postDTOs = postPage.getContent().stream().map(post -> {
            List<String> hashtags = post.getHashtag() != null
                    ? Arrays.stream(post.getHashtag().split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList())
                    : null;

            String nickname = post.getUserId() != null ? post.getUserId().getNickname() : null;

            return new PostDTO(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getCreateAt(),
                    post.getUpdateAt(),
                    post.getViews(),
                    hashtags,
                    nickname
            );
        }).collect(Collectors.toList());

        // 결과 반환
        return new ApiResponse(postDTOs, postPage.getTotalElements());
    }

    // Specification<Posts>를 만드는 메소드
     private Specification<Posts> searchByKeywords(List<String> keywords) {
        return new Specification<Posts>() {
            @Override
            public Predicate toPredicate(Root<Posts> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // `Posts` 엔티티와 `AppUser` 엔티티를 조인합니다.
                Join<Posts, AppUser> user = root.join("id", JoinType.LEFT);  // userId 필드를 기준으로 AppUser와 조인

                // 조건을 `OR`로 합칠 기본 조건을 생성합니다.
                Predicate predicate = cb.conjunction(); // 기본 조건을 "true"로 설정

                // 키워드마다 반복하며 조건을 `OR`로 합칩니다.
                for (String keyword : keywords) {
                    // 키워드가 `title`, `content`, `nickname`, `hashtag`에 포함되는지 체크
                    Predicate titlePredicate = cb.like(cb.lower(root.get("title")), "%" + keyword.toLowerCase() + "%");
                    Predicate contentPredicate = cb.like(cb.lower(root.get("content")), "%" + keyword.toLowerCase() + "%");
                    Predicate nicknamePredicate = cb.like(cb.lower(user.get("nickname")), "%" + keyword.toLowerCase() + "%");  // nickname을 AppUser에서 가져옵니다.
                    Predicate hashtagPredicate = cb.like(cb.lower(root.get("hashtag")), "%" + keyword.toLowerCase() + "%");

                    // 각 키워드에 대한 조건을 `OR`로 묶기
                    Predicate combinedPredicate = cb.or(titlePredicate, contentPredicate, nicknamePredicate, hashtagPredicate);

                    // 모든 키워드를 `OR`로 결합 (기존 `predicate`와 합침)
                    predicate = cb.or(predicate, combinedPredicate);

                    query.orderBy(cb.desc(root.get("id")));
                }

                // 결과적으로 만들어진 `predicate`를 반환
                return predicate;
            }
        };
    }

    // 정렬 키 매핑
    private String mapSortKey(String sortKey) {
        return switch (sortKey) {
            case "postId" -> "id";
            case "title" -> "title";
            case "nickname" -> "userId.nickname"; // 주의: join된 필드
            case "views" -> "views";
            case "create_at" -> "createAt";
            case "update_at" -> "updateAt";
            default -> "id";
        };
    }

}