package com.market_be.repository;

import com.market_be.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> id(Long id);
}
