package com.market_be.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "manage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Manage {
    @Id
    @Column(name = "manage_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int fileCount;

    @Column(nullable = false)
    private Long fileMaxSize;

    @Column(nullable = false)
    private String fileExtension;
}
