package com.market_be.repository;

import com.market_be.entity.Files;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilesRepository extends JpaRepository<Files, Long> {


    List<Files> findByPostId(Long postId);
}