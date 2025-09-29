package com.market_be.repository;

import com.market_be.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    List<Posts> findByDeletedTrue();

    @Query("SELECT p FROM Posts p WHERE p.id = :id")
    Optional<Posts> findByIdIncludingDeleted(@Param("id") Long id);



}
