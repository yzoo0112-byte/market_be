package com.market_be.controller;

import com.market_be.dto.AppUserDto;
import com.market_be.entity.Visit;
import com.market_be.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/manage")
@RequiredArgsConstructor
public class ManageController {

    private final VisitRepository visitRepository;

    @GetMapping("/visit")
    public List<Visit> getVisitCount() {
        List<Visit> visits = visitRepository.findAll();
        return visits;

    private final ManageUserService manageUserService;

    // 회원 전체 조회 API (관리자만 접근 가능)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public List<AppUserDto> getAllUsers() {
        return manageUserService.getAllUsers();
    }
}
