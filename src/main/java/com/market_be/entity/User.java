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

    @Column(unique = true, length = 20)
    private String login_id;

    @Column(unique = true, length = 20)
    private String nickname;

    @Column(length = 10)
    private String user_name;

    private String password;

    @Column(unique = true, length = 11, columnDefinition = "CHARACTER(11)")
    private String phone_num;

    @Column(length = 8, columnDefinition = "CHARACTER(8)")
    private String birth;

    @Column(length = 50, unique = true)
    private String email;

    private String addr;

    private Date last_visite_date;
}
