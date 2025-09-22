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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int manage_id;

    @Column(nullable = false)
    private int file_count;

    @Column(nullable = false)
    private Long file_max_size;

    @Column(nullable = false)
    private String file_extension;
}
