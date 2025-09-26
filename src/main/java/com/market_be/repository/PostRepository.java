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

    // 다중 검색을 위한 검색 쿼리
    @Query("SELECT p FROM Posts p WHERE " +
            "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.hashtag) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.userId.nickname) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Posts> searchPostsByKeyword(String keyword, Pageable pageable);

    // 여러 키워드를 다루는 방법 (여러 개의 키워드를 LIKE로 결합)
    @Query("SELECT p FROM Posts p WHERE " +
            "(LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword1, '%')) " +
            "OR LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword1, '%')) " +
            "OR LOWER(p.hashtag) LIKE LOWER(CONCAT('%', :keyword1, '%')) " +
            "OR LOWER(p.userId.nickname) LIKE LOWER(CONCAT('%', :keyword1, '%'))) " +
            "OR (LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword2, '%')) " +
            "OR LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword2, '%')) " +
            "OR LOWER(p.hashtag) LIKE LOWER(CONCAT('%', :keyword2, '%')) " +
            "OR LOWER(p.userId.nickname) LIKE LOWER(CONCAT('%', :keyword2, '%')))")

    Page<Posts> searchPostsByMultipleKeywords(String keyword1, String keyword2, Pageable pageable);
}