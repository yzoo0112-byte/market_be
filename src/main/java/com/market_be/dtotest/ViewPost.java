package com.market_be.dtotest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ViewPost {
    private Long postId;
    private String title;
    private String nickname;
    private Integer views;
    private String create_at;
    private String update_at;
    private String contents;
    private String hashtag;
}
