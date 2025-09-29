package com.market_be.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class CommentRequest {
    private Long postId;
    private Long commentId;
    private String comment;
    private String loginId;
}
