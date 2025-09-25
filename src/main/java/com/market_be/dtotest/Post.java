package com.market_be.dtotest;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
// 게시글 DTO
public class Post {
    private int postId;
    private String title;
    private String content;
    private String nickname;
    private int views;
    private String create_at;
    private String update_at;
    private List<String> hashtags;

    // 생성자, 게터/세터

    public Post(int postId, String title, String content, String nickname,
                int views, String create_at, String update_at, List<String> hashtags) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.nickname = nickname;
        this.views = views;
        this.create_at = create_at;
        this.update_at = update_at;
        this.hashtags = hashtags;
    }

    // Getters & Setters 생략 (필요하면 추가)

    public int getPostId() { return postId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getNickname() { return nickname; }
    public int getViews() { return views; }
    public String getCreate_at() { return create_at; }
    public String getUpdate_at() { return update_at; }
    public List<String> getHashtags() { return hashtags; }
}
