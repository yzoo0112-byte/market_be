package com.market_be.repository;


import com.market_be.entity.AppUser;
import com.market_be.entity.Likes;
import com.market_be.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    boolean existsByUserIdAndPostId(AppUser user, Posts post);

    void deleteByUserIdAndPostId(AppUser user, Posts post);

    @Query("SELECT COUNT(l) FROM Likes l WHERE l.postId.id = :postId")
    Long countByPostId(@Param("postId") Long postId);


}
