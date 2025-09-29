package com.market_be.service;

import com.market_be.dto.AppUserDto;
import com.market_be.entity.AppUser;
import com.market_be.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeUserService {
    private final AppUserRepository appUserRepository;

    public Optional<AppUser> findById(Long userId) {
        return appUserRepository.findById(userId);
    }

    public Optional<AppUserDto> findDtoById(Long userId) {
        return appUserRepository.findById(userId)
                .map(this::convertToDto);
    }

    private AppUserDto convertToDto(AppUser user) {
        return AppUserDto.builder()
                .userId(user.getId())
                .loginId(user.getLoginId())
                .nickname(user.getNickname())
                .userName(user.getUserName())
                .password(user.getPassword())
                .phoneNum(user.getPhoneNum())
                .birth(user.getBirth())
                .email(user.getEmail())
                .addr(user.getAddr())
                .lastVisiteDate(user.getLastVisitDate())
                .build();
    }

}


