package com.market_be.controller;

import com.market_be.dto.AppUserDto;
import com.market_be.service.ManageUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manage")
@RequiredArgsConstructor
public class ManageController {

    private final ManageUserService manageUserService;

    // 회원 전체 조회 API (관리자만 접근 가능)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public List<AppUserDto> getAllUsers() {
        return manageUserService.getAllUsers();
    }
}
