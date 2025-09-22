package com.market_be.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_Id;

    @Column(unique = true, length = 20, nullable = false)
    private String login_id;

    @Column(unique = true, length = 20, nullable = false)
    private String nickname;

    @Column(length = 10, nullable = false)
    private String user_name;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, length = 11, columnDefinition = "CHARACTER(11)", nullable = false)
    private String phone_num;

    @Column(length = 8, columnDefinition = "CHARACTER(8)", nullable = false)
    private String birth;

    @Column(length = 50, unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String addr;

    private Date last_visite_date;
}
