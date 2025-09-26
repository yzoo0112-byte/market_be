//package com.market_be.service;
//
//import com.market_be.entity.AppUser;
//import com.market_be.repository.AppUserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class ManageDetailService implements UserDetailsService {
//
//    private final AppUserRepository appUserRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
//        AppUser appUser = appUserRepository.findByLoginId(loginId)
//                .orElseThrow(() -> new UsernameNotFoundException("해당 로그인 ID가 존재하지 않습니다: " + loginId));
//
//        return User.builder()
//                .username(appUser.getLoginId())
//                .password(appUser.getPassword())
//                .roles(appUser.getRole().name()) // 예: ROLE_ADMIN, ROLE_USER
//                .build();
//    }
//}
