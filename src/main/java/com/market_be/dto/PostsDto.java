package com.market_be.dto;


import com.market_be.constant.HashtagType;
import com.market_be.entity.AppUser;
import com.market_be.entity.Posts;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class PostsDto {

    private Long id;

    private Long userId;

    private String title;

    private String content;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private Long views;

    private String hashtag;

    public static PostsDto fromEntity(Posts posts) {
        return PostsDto.builder()
                .id(posts.getId())
                .title(posts.getTitle())
                .content(posts.getContent())
                .hashtag(posts.getHashtag())
                .views(posts.getViews())
                .createAt(posts.getCreateAt())
                .updateAt(posts.getUpdateAt())
                .userId(posts.getUserId().getId())
                .build();
    };

}
