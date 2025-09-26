package com.market_be.service;

import com.market_be.dto.MyPageDto;

public interface UserService {
    boolean checkPassword(String loginId, String password);
    MyPageDto getUserInfo(String loginId);
    void updateUserInfo(String loginId, MyPageDto dto);
    void deleteUser(String loginId);

}