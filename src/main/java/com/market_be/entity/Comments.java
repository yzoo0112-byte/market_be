package com.market_be.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Entity
@Table(name = "comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comments {
    @Id
    @Column(name = "comments_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Posts postId;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser userId;

    @Column(nullable = false, columnDefinition = "text")
    private String comment;

    @Column(updatable = false, nullable = false)
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;
}
