package com.market_be.config;

import com.market_be.entity.AppUser;
import com.market_be.repository.AppUserRepository;
import com.market_be.service.JwtService;
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

        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            String token = jwtToken.replace("Bearer ", "");
            String loginId = jwtService.parseTokenFromTokenOnly(token);

            if (loginId != null) {
                AppUser appUser = appUserRepository.findByLoginId(loginId).orElse(null);
                List<SimpleGrantedAuthority> authorities = Collections.emptyList();

                if (appUser != null) {
                    authorities = List.of(
                            new SimpleGrantedAuthority("ROLE_" + appUser.getRole().name())
                    );
                }

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        loginId,
                        null,
                        authorities
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
