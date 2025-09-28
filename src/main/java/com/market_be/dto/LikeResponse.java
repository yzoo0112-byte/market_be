package com.market_be.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeResponse {
    private Long postId;
    private boolean liked;
    private Long likeCount;

}
