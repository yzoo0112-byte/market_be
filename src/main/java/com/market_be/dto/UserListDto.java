package com.market_be.dto;

import com.market_be.entity.AppUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@ToString
public class UserListDto {
    private Long userId;
    private String loginId;
    private String nickname;
    private String phoneNum;
    private String birth;
    private String email;
    private String addr;
    private LocalDate lastVisitDate;

    public static UserListDto fromEntity(AppUser user) {
        UserListDto dto = new UserListDto();
        dto.userId = user.getId();
        dto.loginId = user.getLoginId();
        dto.nickname = user.getNickname();
        dto.phoneNum = user.getPhoneNum();
        dto.birth = user.getBirth();
        dto.email = user.getEmail();
        dto.addr = user.getAddr();
        dto.lastVisitDate = user.getLastVisitDate();
        return dto;
    }
}
