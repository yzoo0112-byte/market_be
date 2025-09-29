package com.market_be.config;

import com.market_be.entity.AppUser;
import com.market_be.repository.AppUserRepository;
import com.market_be.service.JwtService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AppUserRepository appUserRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // 공개 경로는 필터 건너뛰기
        if (path.equals("/") || path.equals("/login") || path.equals("/signup") || path.startsWith("/main")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            String loginId = jwtService.parseToken(request); // ✅ 올바른 호출
            AppUser user = appUserRepository.findByLoginId(loginId)
                    .orElseThrow(EntityNotFoundException::new);
            if (loginId != null) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        loginId,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString()))
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }


        filterChain.doFilter(request, response);
    }

}
