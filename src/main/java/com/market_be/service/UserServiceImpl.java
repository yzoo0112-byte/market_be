package com.market_be.service;

import com.market_be.dto.MyPageDto;
import com.market_be.entity.AppUser;
import com.market_be.exception.CustomException;
import com.market_be.exception.ErrorCode;
import com.market_be.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    //마이페이지 접근시 비밀번호 재확인
    @Override
    public boolean checkPassword(String loginId, String rawPassword) {
        Optional<AppUser> optionalUser = appUserRepository.findByLoginId(loginId);

        if (optionalUser.isEmpty()) {
            // 사용자 조회 실패 → 401 또는 404
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        AppUser user = optionalUser.get();

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            // 비밀번호 불일치 → 401
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        return true;
    }


    //로그인된 사용자의 개인정보를 조회해서 프론트에 전달
    @Override
    public MyPageDto getUserInfo(String loginId) {
        AppUser user = appUserRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));
        
        MyPageDto dto = new MyPageDto();
        dto.setLoginId(user.getLoginId());
        dto.setPassword(user.getPassword());
        dto.setNickname(user.getNickname());
        dto.setUserName(user.getUserName());
        dto.setPhoneNum(user.getPhoneNum());
        dto.setBirth(user.getBirth());
        dto.setEmail(user.getEmail());
        dto.setAddr(user.getAddr());

        return dto;

    }
    //사용자가 마이페이지에서 개인정보를 수정했을 때, DB에 반영하는 기능
    @Override
    public void updateUserInfo(String loginId, MyPageDto dto) {
        AppUser user = appUserRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));
        user.setNickname(dto.getNickname());
        user.setUserName(dto.getUserName());
        user.setPhoneNum(dto.getPhoneNum());
        user.setBirth(dto.getBirth());
        user.setEmail(dto.getEmail());
        user.setAddr(dto.getAddr());
        appUserRepository.save(user);
    }
}