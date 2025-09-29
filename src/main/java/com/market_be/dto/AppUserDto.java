package com.market_be.dto;

import com.market_be.content.Role;
import com.market_be.entity.AppUser;
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
    private Role role;
    private String birth;
    private String email;
    private String addr;

    private LocalDate lastVisiteDate;



    public static AppUserDto fromEntity(AppUser appUser) {
        return AppUserDto.builder()
                .userId(appUser.getId())
                .loginId(appUser.getLoginId())
                .nickname(appUser.getNickname())
                .userName(appUser.getUserName())
                .password(appUser.getPassword())
                .phoneNum(appUser.getPhoneNum())
                .birth(appUser.getBirth())
                .email(appUser.getEmail())
                .addr(appUser.getAddr())
                .lastVisiteDate(appUser.getLastVisitDate())
                .role(appUser.getRole()) // ← 여기 추가 enum 정의한 user와 admin을 손 대지 않고 관리자 권한을 정하려면 필요함
                .build();

        // fromEntity 메서드 추가
        //
        // 원래 DTO에는 단순히 필드 선언만 있고, 엔티티 → DTO 변환 로직이 없었음
        //
        // 프론트에서 users.map(AppUserDto::fromEntity)처럼 사용하려면 엔티티를 DTO로 변환하는 메서드가 반드시 필요
        //
        // 따라서, fromEntity를 추가한 이유는 백엔드에서 DB 조회 후 DTO 형태로 프론트에 바로 전달 가능하게 만들기 위해넣음.

    };
}
