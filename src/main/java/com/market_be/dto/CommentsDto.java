package com.market_be.dto;

import com.market_be.entity.Comments;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class CommentsDto {
    private Long postId;
    private Long UId;
    private Long commentId;

    private String userId;

    private String comment;

    private LocalDateTime createdAt;
    private Long parentId;
    private LocalDateTime updatedAt;

    private String nickname;
    private String loginId;
    public static CommentsDto of(Comments comments){
        return CommentsDto.builder()
                .commentId(comments.getId())
                .postId(comments.getPostId().getId())
                .userId(comments.getUserId().getLoginId())
                .comment(comments.getComment())
                .createdAt(comments.getCreatedAt())
                .updatedAt(comments.getUpdatedAt())
                .nickname(comments.getUserId().getNickname())
                .parentId(comments.getParentId().getId())
                .build();
    }
}
