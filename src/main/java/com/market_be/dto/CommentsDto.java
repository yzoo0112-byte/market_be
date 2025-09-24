package com.market_be.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@Builder
public class CommentsDto {
    private Long postId;

    private Long userId;

    private String hashtag;

    private String comment;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
