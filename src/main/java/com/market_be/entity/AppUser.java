package com.market_be.entity;

import com.market_be.content.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Table(name = "appuser")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 20, nullable = false)
    private String loginId;

    @Column(unique = true, length = 20, nullable = false)
    private String nickname;

    @Column(length = 10, nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, length = 11, columnDefinition = "CHARACTER(11)", nullable = false)
    private String phoneNum;

    @Column(length = 8, columnDefinition = "CHARACTER(8)", nullable = false)
    private String birth;

    @Column(length = 50, unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String addr;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private Date lastVisiteDate;

}
