package com.market_be.service;

import com.market_be.dto.PostRequestDto;
import com.market_be.dtotest.ApiResponse;
import com.market_be.dtotest.PostDTO;
import com.market_be.entity.AppUser;
import com.market_be.entity.Posts;
import com.market_be.repository.PostRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final EntityManager entityManager;

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


    @Transactional(readOnly = true)
    public ApiResponse getPosts(int page, int size, String sortKey, String sortOrder, String keyword) {
        // 키워드를 공백 기준으로 나누어 List<String> 형식으로 변환
        List<String> keywords = (keyword == null || keyword.isBlank())
                ? List.of()  // 키워드가 비어 있으면 빈 리스트 반환
                : Arrays.asList(keyword.trim().split("\\s+"));  // 공백으로 키워드 나누기

        // Pageable 설정: 페이지, 사이즈, 정렬
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), mapSortKey(sortKey)));

        // 키워드가 없으면 전체 게시글 조회, 있으면 `findBy` 메소드 호출하여 검색
        Page<Posts> postPage;

        if (keywords.isEmpty()) {
            postPage = postRepository.findAll(pageable);
        } else if (keywords.size() == 1) {
            postPage = postRepository.searchPostsByKeyword(keywords.get(0), pageable);
        } else if (keywords.size() == 2) {
            postPage = postRepository.searchPostsByMultipleKeywords2(
                    keywords.get(0), keywords.get(1), pageable);
        } else if (keywords.size() == 3) {
            postPage = postRepository.searchPostsByMultipleKeywords3(
                    keywords.get(0), keywords.get(1), keywords.get(2), pageable);
        } else if (keywords.size() == 4) {
            postPage = postRepository.searchPostsByMultipleKeywords4(
                    keywords.get(0), keywords.get(1), keywords.get(2), keywords.get(3), pageable);
        } else if (keywords.size() == 5) {
            postPage = postRepository.searchPostsByMultipleKeywords5(
                    keywords.get(0), keywords.get(1), keywords.get(2), keywords.get(3), keywords.get(4), pageable);
        } else if (keywords.size() == 6) {
            postPage = postRepository.searchPostsByMultipleKeywords6(
                    keywords.get(0), keywords.get(1), keywords.get(2), keywords.get(3), keywords.get(4), keywords.get(5), pageable);
        } else if (keywords.size() == 7) {
            postPage = postRepository.searchPostsByMultipleKeywords7(
                    keywords.get(0), keywords.get(1), keywords.get(2), keywords.get(3), keywords.get(4),
                    keywords.get(5), keywords.get(6), pageable);
        } else {
            throw new IllegalArgumentException("검색 키워드는 최대 10개까지 입력 가능합니다.");
        }

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
                    nickname,
                    post.isDeleted()
            );
        }).collect(Collectors.toList());

        return new ApiResponse(postDTOs, postPage.getTotalElements());
    }

    // 정렬 키 매핑
    private String mapSortKey(String sortKey) {
        return switch (sortKey) {
            case "postId" -> "id";
            case "title" -> "title";
            case "nickname" -> "userId.nickname";
            case "views" -> "views";
            case "create_at" -> "createAt";
            case "update_at" -> "updateAt";
            default -> "id";
        };
    }

}

