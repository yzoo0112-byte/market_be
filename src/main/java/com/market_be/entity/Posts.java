package com.market_be.entity;

import jakarta.persistence.*;
import lombok.*;

import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Posts {
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long post_id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user_id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(updatable = false, nullable = false)
    private LocalDateTime create_at;

    @LastModifiedDate
    private LocalDateTime update_at;

    private Long views;

    @Column(length = 50)
    private String hashtag;

}
