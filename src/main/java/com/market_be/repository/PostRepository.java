package com.market_be.repository;

import com.market_be.entity.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Posts, Long>, JpaSpecificationExecutor<Posts> {

        // 1 keyword
        @Query("SELECT p FROM Posts p WHERE " +
                        "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
                        "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
                        "LOWER(p.hashtag) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
                        "LOWER(p.userId.nickname) LIKE LOWER(CONCAT('%', :keyword, '%'))")
        Page<Posts> searchPostsByKeyword(@Param("keyword") String keyword, Pageable pageable);

        // 2 keywords
        @Query("SELECT p FROM Posts p WHERE " +
                        "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword1, '%')) OR " +
                        "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword1, '%')) OR " +
                        "LOWER(p.hashtag) LIKE LOWER(CONCAT('%', :keyword1, '%')) OR " +
                        "LOWER(p.userId.nickname) LIKE LOWER(CONCAT('%', :keyword1, '%')) OR " +
                        "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword2, '%')) OR " +
                        "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword2, '%')) OR " +
                        "LOWER(p.hashtag) LIKE LOWER(CONCAT('%', :keyword2, '%')) OR " +
                        "LOWER(p.userId.nickname) LIKE LOWER(CONCAT('%', :keyword2, '%'))")
        Page<Posts> searchPostsByMultipleKeywords2(String keyword1, String keyword2, Pageable pageable);

        // 3 keywords
        @Query("SELECT p FROM Posts p WHERE " +
                        "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword1, '%')) OR " +
                        "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword1, '%')) OR " +
                        "LOWER(p.hashtag) LIKE LOWER(CONCAT('%', :keyword1, '%')) OR " +
                        "LOWER(p.userId.nickname) LIKE LOWER(CONCAT('%', :keyword1, '%')) OR " +
                        "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword2, '%')) OR " +
                        "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword2, '%')) OR " +
                        "LOWER(p.hashtag) LIKE LOWER(CONCAT('%', :keyword2, '%')) OR " +
                        "LOWER(p.userId.nickname) LIKE LOWER(CONCAT('%', :keyword2, '%')) OR " +
                        "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword3, '%')) OR " +
                        "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword3, '%')) OR " +
                        "LOWER(p.hashtag) LIKE LOWER(CONCAT('%', :keyword3, '%')) OR " +
                        "LOWER(p.userId.nickname) LIKE LOWER(CONCAT('%', :keyword3, '%'))")
        Page<Posts> searchPostsByMultipleKeywords3(String keyword1, String keyword2, String keyword3,
                        Pageable pageable);

        // 4 keywords
        @Query("SELECT p FROM Posts p WHERE " +
                        "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword1, '%')) OR " +
                        "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword1, '%')) OR " +
                        "LOWER(p.hashtag) LIKE LOWER(CONCAT('%', :keyword1, '%')) OR " +
                        "LOWER(p.userId.nickname) LIKE LOWER(CONCAT('%', :keyword1, '%')) OR " +
                        "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword2, '%')) OR " +
                        "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword2, '%')) OR " +
                        "LOWER(p.hashtag) LIKE LOWER(CONCAT('%', :keyword2, '%')) OR " +
                        "LOWER(p.userId.nickname) LIKE LOWER(CONCAT('%', :keyword2, '%')) OR " +
                        "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword3, '%')) OR " +
                        "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword3, '%')) OR " +
                        "LOWER(p.hashtag) LIKE LOWER(CONCAT('%', :keyword3, '%')) OR " +
                        "LOWER(p.userId.nickname) LIKE LOWER(CONCAT('%', :keyword3, '%')) OR " +
                        "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword4, '%')) OR " +
                        "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword4, '%')) OR " +
                        "LOWER(p.hashtag) LIKE LOWER(CONCAT('%', :keyword4, '%')) OR " +
                        "LOWER(p.userId.nickname) LIKE LOWER(CONCAT('%', :keyword4, '%'))")
        Page<Posts> searchPostsByMultipleKeywords4(String keyword1, String keyword2, String keyword3, String keyword4,
                        Pageable pageable);

        @Query("SELECT p FROM Posts p WHERE " +
                        "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword1, '%')) OR " +
                        "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword1, '%')) OR " +
                        "LOWER(p.hashtag) LIKE LOWER(CONCAT('%', :keyword1, '%')) OR " +
                        "LOWER(p.userId.nickname) LIKE LOWER(CONCAT('%', :keyword1, '%')) OR " +

                        "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword2, '%')) OR " +
                        "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword2, '%')) OR " +
                        "LOWER(p.hashtag) LIKE LOWER(CONCAT('%', :keyword2, '%')) OR " +
                        "LOWER(p.userId.nickname) LIKE LOWER(CONCAT('%', :keyword2, '%')) OR " +

                        "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword3, '%')) OR " +
                        "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword3, '%')) OR " +
                        "LOWER(p.hashtag) LIKE LOWER(CONCAT('%', :keyword3, '%')) OR " +
                        "LOWER(p.userId.nickname) LIKE LOWER(CONCAT('%', :keyword3, '%')) OR " +

                        "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword4, '%')) OR " +
                        "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword4, '%')) OR " +
                        "LOWER(p.hashtag) LIKE LOWER(CONCAT('%', :keyword4, '%')) OR " +
                        "LOWER(p.userId.nickname) LIKE LOWER(CONCAT('%', :keyword4, '%')) OR " +

                        "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword5, '%')) OR " +
                        "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword5, '%')) OR " +
                        "LOWER(p.hashtag) LIKE LOWER(CONCAT('%', :keyword5, '%')) OR " +
                        "LOWER(p.userId.nickname) LIKE LOWER(CONCAT('%', :keyword5, '%'))")
        Page<Posts> searchPostsByMultipleKeywords5(String keyword1, String keyword2, String keyword3, String keyword4,
                        String keyword5, Pageable pageable);

        @Query("SELECT p FROM Posts p WHERE " +
                        "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword1, '%')) OR " +
                        "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword1, '%')) OR " +
                        "LOWER(p.hashtag) LIKE LOWER(CONCAT('%', :keyword1, '%')) OR " +
                        "LOWER(p.userId.nickname) LIKE LOWER(CONCAT('%', :keyword1, '%')) OR " +

                        "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword2, '%')) OR " +
                        "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword2, '%')) OR " +
                        "LOWER(p.hashtag) LIKE LOWER(CONCAT('%', :keyword2, '%')) OR " +
                        "LOWER(p.userId.nickname) LIKE LOWER(CONCAT('%', :keyword2, '%')) OR " +

                        "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword3, '%')) OR " +
                        "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword3, '%')) OR " +
                        "LOWER(p.hashtag) LIKE LOWER(CONCAT('%', :keyword3, '%')) OR " +
                        "LOWER(p.userId.nickname) LIKE LOWER(CONCAT('%', :keyword3, '%')) OR " +

                        "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword4, '%')) OR " +
                        "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword4, '%')) OR " +
                        "LOWER(p.hashtag) LIKE LOWER(CONCAT('%', :keyword4, '%')) OR " +
                        "LOWER(p.userId.nickname) LIKE LOWER(CONCAT('%', :keyword4, '%')) OR " +

                        "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword5, '%')) OR " +
                        "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword5, '%')) OR " +
                        "LOWER(p.hashtag) LIKE LOWER(CONCAT('%', :keyword5, '%')) OR " +
                        "LOWER(p.userId.nickname) LIKE LOWER(CONCAT('%', :keyword5, '%')) OR " +

                        "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword6, '%')) OR " +
                        "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword6, '%')) OR " +
                        "LOWER(p.hashtag) LIKE LOWER(CONCAT('%', :keyword6, '%')) OR " +
                        "LOWER(p.userId.nickname) LIKE LOWER(CONCAT('%', :keyword6, '%'))")
        Page<Posts> searchPostsByMultipleKeywords6(String keyword1, String keyword2, String keyword3, String keyword4,
                        String keyword5, String keyword6, Pageable pageable);

        @Query("SELECT p FROM Posts p WHERE " +
                        "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword1, '%')) OR " +
                        "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword1, '%')) OR " +
                        "LOWER(p.hashtag) LIKE LOWER(CONCAT('%', :keyword1, '%')) OR " +
                        "LOWER(p.userId.nickname) LIKE LOWER(CONCAT('%', :keyword1, '%')) OR " +

                        "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword2, '%')) OR " +
                        "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword2, '%')) OR " +
                        "LOWER(p.hashtag) LIKE LOWER(CONCAT('%', :keyword2, '%')) OR " +
                        "LOWER(p.userId.nickname) LIKE LOWER(CONCAT('%', :keyword2, '%')) OR " +

                        "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword3, '%')) OR " +
                        "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword3, '%')) OR " +
                        "LOWER(p.hashtag) LIKE LOWER(CONCAT('%', :keyword3, '%')) OR " +
                        "LOWER(p.userId.nickname) LIKE LOWER(CONCAT('%', :keyword3, '%')) OR " +

                        "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword4, '%')) OR " +
                        "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword4, '%')) OR " +
                        "LOWER(p.hashtag) LIKE LOWER(CONCAT('%', :keyword4, '%')) OR " +
                        "LOWER(p.userId.nickname) LIKE LOWER(CONCAT('%', :keyword4, '%')) OR " +

                        "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword5, '%')) OR " +
                        "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword5, '%')) OR " +
                        "LOWER(p.hashtag) LIKE LOWER(CONCAT('%', :keyword5, '%')) OR " +
                        "LOWER(p.userId.nickname) LIKE LOWER(CONCAT('%', :keyword5, '%')) OR " +

                        "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword6, '%')) OR " +
                        "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword6, '%')) OR " +
                        "LOWER(p.hashtag) LIKE LOWER(CONCAT('%', :keyword6, '%')) OR " +
                        "LOWER(p.userId.nickname) LIKE LOWER(CONCAT('%', :keyword6, '%')) OR " +

                        "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword7, '%')) OR " +
                        "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword7, '%')) OR " +
                        "LOWER(p.hashtag) LIKE LOWER(CONCAT('%', :keyword7, '%')) OR " +
                        "LOWER(p.userId.nickname) LIKE LOWER(CONCAT('%', :keyword7, '%'))")
        Page<Posts> searchPostsByMultipleKeywords7(String keyword1, String keyword2, String keyword3, String keyword4,
                        String keyword5, String keyword6, String keyword7, Pageable pageable);

        Page<Posts> searchPostsByMultipleKeywords(String keyword1, String keyword2, Pageable pageable);

        Page<Posts> findByDeletedFalseAndTitleContaining(String keyword, Pageable pageable);
}