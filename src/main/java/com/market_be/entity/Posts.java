package com.market_be.entity;

import jakarta.persistence.*;
import lombok.*;

import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Posts {
    //게시글 생성 번호
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //회원 생성 번호
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser userId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(updatable = false, nullable = false, length = 0)
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updateAt;

    private Long views;

    @Column(length = 50)
    private String hashtag;

    @Transient
    private List<String> hashtags;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true ,fetch = FetchType.LAZY)
    private List<Files> fileList;
}
