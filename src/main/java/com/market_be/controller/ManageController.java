package com.market_be.controller;

import com.market_be.dto.UserListDto;
import com.market_be.entity.AppUser;
import com.market_be.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manage")
public class ManageController {

    private final AppUserRepository appUserRepository;

    @GetMapping("/users")
    public ResponseEntity<List<UserListDto>> getAllUsers() {
        List<AppUser> users = appUserRepository.findAll();
        List<UserListDto> dtos = users.stream()
                .map(UserListDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}