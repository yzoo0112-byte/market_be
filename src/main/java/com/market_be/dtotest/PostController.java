package com.market_be.dtotest;
import com.market_be.dtotest.ViewPost;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PostController {

    private final List<ViewPost> dummyPosts;

    // 생성자에서 더미 데이터 초기화
    public PostController() {
        dummyPosts = new ArrayList<>();
        dummyPosts.add(new ViewPost(1L, "React에서 GridColDef 사용하기", "JohnDoe", 100, "2025-09-01", "2025-09-02", "React의 DataGrid 사용법에 대해 알아봅시다.", "#react"));
        dummyPosts.add(new ViewPost(2L, "Material UI의 DataGrid 사용법", "JaneSmith", 200, "2025-09-03", "2025-09-04", "Material UI를 활용한 DataGrid 사용법", "#mui"));
        dummyPosts.add(new ViewPost(3L, "Next.js에서 SSR과 SSG 사용하기", "Alice", 150, "2025-09-05", "2025-09-06", "Next.js에서 SSR과 SSG의 차이점을 이해하고 구현해보자.", "#nextjs"));
        dummyPosts.add(new ViewPost(4L, "Typescript의 타입 시스템", "Bob", 120, "2025-09-07", "2025-09-08", "Typescript의 타입 시스템을 알아보자.", "#typescript"));
        dummyPosts.add(new ViewPost(5L, "Spring Boot에서 REST API 구현하기", "Charlie", 90, "2025-09-10", "2025-09-11", "Spring Boot로 REST API를 구현하는 방법", "#springboot"));
        dummyPosts.add(new ViewPost(6L, "GraphQL로 효율적인 API 설계하기", "Eve", 60, "2025-09-12", "2025-09-13", "GraphQL의 개념과 장점, 사용 방법을 배우자.", "#graphql"));
        dummyPosts.add(new ViewPost(7L, "Java 17의 새로운 기능들", "David", 110, "2025-09-15", "2025-09-16", "Java 17에서 새로 추가된 기능을 살펴보자.", "#java17"));
        dummyPosts.add(new ViewPost(8L, "Docker로 개발 환경 구축하기", "Grace", 250, "2025-09-17", "2025-09-18", "Docker를 이용해 개발 환경을 구축하는 방법을 배워봅시다.", "#docker"));
        dummyPosts.add(new ViewPost(9L, "Kotlin으로 Android 앱 개발하기", "Henry", 175, "2025-09-20", "2025-09-21", "Kotlin으로 Android 앱을 개발하는 법을 알아보자.", "#kotlin"));
        dummyPosts.add(new ViewPost(10L, "Typescri", "Bob", 120, "2025-09-07", "2025-09-08", "xxxxxxxxxxxxxxxxx", "#typescript"));
        dummyPosts.add(new ViewPost(11L, "Flutter로 모바일 앱 개발하기", "Ivy", 180, "2025-09-22", "2025-09-23", "xxxxxxxxxxxxxxxxx", "#flutter"));
    }

    // 더미 데이터를 반환하는 GET API
    @GetMapping("/posts")
    public ApiResponse getPosts(@RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value = "size", defaultValue = "5") int size,
                                @RequestParam(value = "sortKey", defaultValue = "postId") String sortKey,
                                @RequestParam(value = "sortOrder", defaultValue = "asc") String sortOrder,
                                @RequestParam(value = "keyword", required = false) String keyword) {

        List<ViewPost> filteredPosts = new ArrayList<>(dummyPosts);

        // keyword 필터링
        if (keyword != null && !keyword.isEmpty()) {
            String lowerKeyword = keyword.toLowerCase();

            filteredPosts.removeIf(post ->
                    (post.getTitle() == null || !post.getTitle().toLowerCase().contains(lowerKeyword)) &&
                            (post.getContents() == null || !post.getContents().toLowerCase().contains(lowerKeyword)) &&
                            (post.getNickname() == null || !post.getNickname().toLowerCase().contains(lowerKeyword)) &&
                            (post.getHashtag() == null || !post.getHashtag().toLowerCase().contains(lowerKeyword))
            );
        }

        // 정렬
        if ("desc".equalsIgnoreCase(sortOrder)) {
            filteredPosts.sort((a, b) -> {
                switch (sortKey) {
                    case "postId": return Long.compare(b.getPostId(), a.getPostId());
                    case "views": return Integer.compare(b.getViews(), a.getViews());
                    case "nickname": return b.getNickname().compareTo(a.getNickname());
                    case "create_at": return b.getCreate_at().compareTo(a.getCreate_at());
                    case "update_at": return b.getUpdate_at().compareTo(a.getUpdate_at());
                    case "title":
                    default: return b.getTitle().compareTo(a.getTitle());
                }
            });
        } else {
            filteredPosts.sort((a, b) -> {
                switch (sortKey) {
                    case "postId": return Long.compare(a.getPostId(), b.getPostId());
                    case "views": return Integer.compare(a.getViews(), b.getViews());
                    case "nickname": return a.getNickname().compareTo(b.getNickname());
                    case "create_at": return a.getCreate_at().compareTo(b.getCreate_at());
                    case "update_at": return a.getUpdate_at().compareTo(b.getUpdate_at());
                    case "title":
                    default: return a.getTitle().compareTo(b.getTitle());
                }
            });
        }

        // 전체 수 (페이징 전 기준)
        int total = filteredPosts.size();

        // 페이지 처리
        int fromIndex = Math.min(page * size, total);
        int toIndex = Math.min((page + 1) * size, total);
        List<ViewPost> pagedPosts = filteredPosts.subList(fromIndex, toIndex);

        return new ApiResponse(pagedPosts, total);
    }
}