package com.market_be.controller;

import com.market_be.content.Role;
import com.market_be.dto.AccountCredentials;
import com.market_be.dto.AppUserDto;
import com.market_be.dto.SignupRequest;
import com.market_be.entity.AppUser;
import com.market_be.repository.AppUserRepository;
import com.market_be.service.JwtService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
public class LoginController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;



    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        if (appUserRepository.existsByLoginId(request.getLoginId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 ID입니다");
        }

        AppUser user = AppUser.builder()
                .loginId(request.getLoginId())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .userName(request.getUserName())
                .phoneNum(request.getPhoneNum())
                .birth(request.getBirth())
                .email(request.getEmail())
                .addr(request.getAddr())
                .role(Role.USER)
                .lastVisitDate(new Date())
                .build();

        appUserRepository.save(user);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AccountCredentials credentials) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                credentials.getLoginId(), credentials.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        String jwtToken = jwtService.generateToken(authentication.getName());
        AppUser appUser = appUserRepository.findByLoginId(credentials.getLoginId())
                .orElseThrow(EntityExistsException::new);
        Map<String, Object> result = new HashMap<>();
        result.put("userId", appUser.getId());
        result.put("nickname", appUser.getNickname());
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .body(result);

    }

    @GetMapping("/signup/echeck")
    public ResponseEntity<?> checkDuplicateEmail(@RequestParam String email) {
        boolean exists = appUserRepository.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/signup/ncheck")
    public ResponseEntity<?> checkDuplicateNickname(@RequestParam String nickname) {
        boolean exists = appUserRepository.existsByNickname(nickname);
        return ResponseEntity.ok(exists);
    }

}
