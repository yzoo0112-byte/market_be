package com.market_be.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@Builder
public class AppUserDto {
    private Long userId;

    private String loginId;

    private String nickname;

    private String userName;

    private String password;

    private String phoneNum;
    private String role;
    private String birth;

    private String email;

    private String addr;

    private LocalDate lastVisiteDate;

//    public static AppUserDto fromEntity(AppUser appUser) {
//        return AppUserDto.builder()
//                .id()
//                .loginId()
//                .userName()
//                .password()
//                .phoneNum()
//                .birth()
//                .email()
//                .addr()
//                .lastVisiteDate()
//                .build();
//
//    };
}
