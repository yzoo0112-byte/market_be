package com.market_be.controller;

import com.market_be.dto.MyPageDto;
import com.market_be.exception.CustomException;
import com.market_be.service.JwtService;
import com.market_be.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final UserService userService;
    private final JwtService jwtService;

    // 1. 비밀번호 재확인
    @PostMapping("/pw")
    public ResponseEntity<?> verifyPassword(@RequestBody Map<String, String> body,
                                            @RequestHeader("Authorization") String authHeader) {
        String password = body.get("password");

        // Authorization 헤더 확인
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("토큰이 유효하지 않습니다.");
        }

        // "Bearer " 접두어 제거
        String token = authHeader.substring(7);
        String loginId = jwtService.parseToken(token);

        // 토큰 파싱 실패
        if (loginId == null || loginId.isBlank()) {
            return ResponseEntity.status(401).body("토큰 파싱 실패");
        }

        // 비밀번호 확인 + 예외 처리
        try {
            boolean isValid = userService.checkPassword(loginId, password);
            return ResponseEntity.ok(Map.of("valid", isValid));
        } catch (CustomException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    // 2. 개인정보 조회
    @GetMapping("/info")
    public ResponseEntity<MyPageDto> getUserInfo(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String loginId = jwtService.parseToken(token);
        MyPageDto user = userService.getUserInfo(loginId);
        return ResponseEntity.ok(user);
    }

    // 3. 개인정보 수정
    @PutMapping("/update")
    public ResponseEntity<?> updateUserInfo(@RequestBody MyPageDto myPageDto,
                                            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String loginId = jwtService.parseToken(token);
        userService.updateUserInfo(loginId, myPageDto);
        return ResponseEntity.ok().build();
    }
}