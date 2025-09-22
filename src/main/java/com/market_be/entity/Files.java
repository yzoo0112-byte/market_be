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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long file_id;

    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Posts post_id;

    @Column(unique = true)
    private String file_name;

    @Column(unique = true)
    private String file_url;

    private String file_orgname;

    @Column(columnDefinition = "CHAR")
    private String image_yn;

    private Long file_size;
}
