package com.market_be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyPageDto {
    private String loginId;
    private String password;
    private String nickname;
    private String userName;
    private String phoneNum;
    private String birth;
    private String email;
    private String addr;
}