package com.market_be.dto;

import com.market_be.entity.AppUser;
import com.market_be.entity.Posts;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@ToString
@Builder
public class AppUserDto {
    private Long id;

    private String loginId;

    private String nickname;

    private String userName;

    private String password;

    private String phoneNum;

    private String birth;

    private String email;

    private String addr;

    private Date lastVisiteDate;

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
