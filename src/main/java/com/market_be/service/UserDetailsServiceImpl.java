package com.market_be.service;

import com.market_be.entity.AppUser;
import com.market_be.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        Optional<AppUser> user = appUserRepository.findByLoginId(loginId);

        UserDetails userDetails = null;

        if(user.isPresent()) {
            AppUser appUser = user.get();
            userDetails = User.withUsername(loginId)
                    .password(appUser.getPassword())
                    .roles(appUser.getRole().name())
                    .build();
        }else {
            throw new UsernameNotFoundException("User not found");
        }
        return userDetails;
    }
}