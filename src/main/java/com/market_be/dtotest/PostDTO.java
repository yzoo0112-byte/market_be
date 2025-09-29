package com.market_be.dtotest;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter

public class PostDTO {
    private Long postId;
    private String title;
    private String content;
    private LocalDateTime create_at;
    private LocalDateTime update_at;
    private Long views;
    private List<String> hashtags;  // String -> List<String> 형태로 변환
    private String nickname;
    private boolean deleted;

    // Constructor
    public PostDTO(Long postId, String title, String content, LocalDateTime create_at,
                   LocalDateTime update_at, Long views, List<String> hashtags, String nickname, boolean deleted) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.create_at = create_at;
        this.update_at = update_at;
        this.views = views;
        this.hashtags = hashtags;
        this.nickname = nickname;
        this.deleted = deleted;
    }


}

