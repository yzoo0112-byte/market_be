package com.market_be.repository;

import com.market_be.entity.Files;
import com.market_be.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilesRepository extends JpaRepository<Files, Long> {


    List<Files> findByPost(Posts postId);
}