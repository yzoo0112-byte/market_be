package com.market_be.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Files {
    @Id
    @Column(name = "file_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileid;

    @JoinColumn(name = "post_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Posts post;

    @Column(unique = true)
    private String fileName;

    @Column(unique = true)
    private String fileUrl;

    private String fileOrgname;

    @Column(columnDefinition = "CHAR")
    private String imageYn;

    private Long fileSize;
}
