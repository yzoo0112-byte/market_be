package com.market_be.service;

import com.market_be.content.Role;
import com.market_be.dto.AppUserDto;
import com.market_be.entity.AppUser;
import com.market_be.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManageUserService {

        private final AppUserRepository appUserRepository;

        // 전체 회원 조회
        public List<AppUserDto> getAllUsers() {
                List<AppUser> users = appUserRepository.findAll();

                return users.stream()
                                .map(user -> AppUserDto.builder()
                                                .userId(user.getId())
                                                .loginId(user.getLoginId())
                                                .nickname(user.getNickname())
                                                .userName(user.getUserName())
                                                .password(null) // ⚠️ 비밀번호는 절대 반환하지 않음
                                                .phoneNum(user.getPhoneNum())
                                                .birth(user.getBirth())
                                                .email(user.getEmail())
                                                .addr(user.getAddr())
//                                                .lastVisiteDate(
//                                                                user.getLastVisitDate() == null
//                                                                                ? null
//                                                                                : new java.time.LocalDate(user
//                                                                                                .getLastVisitDate()
//                                                                        .get)
                                                .role(Role.valueOf(user.getRole().name().replace("ROLE_", ""))) // 여기 추가
                                                .build())
                                .collect(Collectors.toList());
        }

}
