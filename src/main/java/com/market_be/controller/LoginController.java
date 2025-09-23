package com.market_be.controller;

import com.market_be.content.Role;
import com.market_be.dto.AccountCredentials;
import com.market_be.dto.SignupRequest;
import com.market_be.entity.AppUser;
import com.market_be.repository.AppUserRepository;
import com.market_be.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


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
                .lastVisiteDate(new Date())
                .build();

        appUserRepository.save(user);
        return ResponseEntity.ok("회원가입 성공");
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AccountCredentials credentials) {
        //1. 유저의 id/pw를 기반으로  usernamepasswordAuthenticationToken 생성
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                credentials.getLoginId(), credentials.getPassword());

        //2. 생성된 usernamepasswordAuthenticationToken을 authenticationManager을 호출
        //3. authenticationManager는 궁극적으로 UserDetailsService의 loadUserByUsername을 호출
        //4. 조회된 유저 정보(userDetail)와 usernamepasswordAuthenticationToken을 비교해 인증 처리
        Authentication authentication = authenticationManager.authenticate(token);
        //5. 최종 반환된 authentication(인증된 유저 정보)를 비잔으로 jwt token 발급
        String jwtToken = jwtService.generateToken(authentication.getName());
        //6. controller는 응답해더(authentication)에 <JWT TOKEN VALUE> 형태로 응답
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .build();
    }


}
