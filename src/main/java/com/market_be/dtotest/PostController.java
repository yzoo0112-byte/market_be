package com.market_be.dtotest;

import com.market_be.dtotest.ApiResponse;
import com.market_be.dtotest.Post;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class PostController {

    // 예시용 데이터 (실제 서비스는 DB 연결)
    private List<Post> allPosts = createDummyPosts();

    @GetMapping("/posts")
    public ApiResponse getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "postId") String sortKey,
            @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(required = false) String keyword) {

        List<String> keywords = (keyword == null || keyword.isBlank())
                ? Collections.emptyList()
                : Arrays.asList(keyword.trim().split("\\s+"));

        // 검색 필터링
        List<Post> filtered = allPosts.stream()
                .filter(post -> {
                    if (keywords.isEmpty()) return true;

                    return keywords.stream()
                            .anyMatch(kw ->  // ← 변경됨!
                                    containsIgnoreCase(post.getTitle(), kw) ||
                                            containsIgnoreCase(post.getContent(), kw) ||
                                            containsIgnoreCase(post.getNickname(), kw) ||
                                            (post.getHashtags() != null && post.getHashtags().stream()
                                                    .anyMatch(tag -> containsIgnoreCase(tag, kw)))
                            );
                })
                .collect(Collectors.toList());

        // 정렬
        Comparator<Post> comparator = getComparator(sortKey);
        if (sortOrder.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }
        filtered.sort(comparator);

        // 페이징
        int fromIndex = Math.min(page * size, filtered.size());
        int toIndex = Math.min(fromIndex + size, filtered.size());
        List<Post> pagePosts = filtered.subList(fromIndex, toIndex);

        return new ApiResponse(pagePosts, filtered.size());
    }

    private static boolean containsIgnoreCase(String source, String target) {
        return source != null && source.toLowerCase().contains(target.toLowerCase());
    }

    private static Comparator<Post> getComparator(String sortKey) {
        switch (sortKey) {
            case "title":
                return Comparator.comparing(Post::getTitle, Comparator.nullsLast(String::compareToIgnoreCase));
            case "nickname":
                return Comparator.comparing(Post::getNickname, Comparator.nullsLast(String::compareToIgnoreCase));
            case "views":
                return Comparator.comparingInt(Post::getViews);
            case "create_at":
                return Comparator.comparing(Post::getCreate_at, Comparator.nullsLast(String::compareTo));
            case "update_at":
                return Comparator.comparing(Post::getUpdate_at, Comparator.nullsLast(String::compareTo));
            default:
                return Comparator.comparingInt(Post::getPostId);
        }
    }

    private static List<Post> createDummyPosts() {
        // 임시 데이터 더미
        return Arrays.asList(
                new Post(1, "첫 번째 게시글", "내용1", "user1", 100, "2023-01-01", "2023-01-02", Arrays.asList("#프로그래밍", "#스프링")),
                new Post(2, "두 번째 게시글 test 테스트", "내용2", "programmer", 50, "2023-01-03", "2023-01-04", Arrays.asList("#좋은아침")),
                new Post(3, "세 번째 게시글", "내용3", "user3", 70, "2023-01-05", "2023-01-06", Arrays.asList("#테스트")),
                new Post(4, "네 번째 게시글", "내용4", "user4", 80, "2023-01-07", "2023-01-08", Arrays.asList("#프로그래밍", "#아침")),
                new Post(5, "첫사랑", "내용5", "nickname5", 120, "2023-01-09", "2023-01-10", Arrays.asList("#테스트", "#스프링")),
                new Post(6, "첫 두번 실험", "내용5", "nickname5", 120, "2023-01-09", "2023-01-10", Arrays.asList("#테스트", "#스프링")),
                new Post(7, "다섯 번째 게시글", "내용5", "nickname5", 120, "2023-01-09", "2023-01-10", Arrays.asList("#테스트", "#1")),
                new Post(8, "다섯 번째 게시글", "내용5", "nickname5", 120, "2023-01-09", "2023-01-10", Arrays.asList("#테스트", "#2")),
                new Post(9, "다섯 번째 게시글", "내용5", "nickname5", 120, "2023-01-09", "2023-01-10", Arrays.asList("#테스트", "#2")),
                new Post(10, "다섯 번째 게시글", "내용5", "nickname5", 120, "2023-01-09", "2023-01-10", Arrays.asList("#테스트", "#3"))
                
                
        );
    }
}
