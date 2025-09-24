package com.market_be;

import com.market_be.content.Role;
import com.market_be.entity.AppUser;
import com.market_be.entity.Posts;
import com.market_be.repository.AppUserRepository;
import com.market_be.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

//
@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class MarketBeApplication implements CommandLineRunner {

    private final PostsRepository postsRepository;
    private final AppUserRepository appUserRepository;

    public static void main(String[] args) {
        SpringApplication.run(MarketBeApplication.class, args);
    }


    public void run(String... args) throws Exception {
        AppUser user = appUserRepository.save(AppUser.builder()
                .loginId("test")
                .nickname("hong")
                .userName("홍길동")
                .password("test")
                .phoneNum("01012345678")
                .birth("000000")
                .email("test@test.com")
                .addr("창원대")
                .role(Role.USER)
                .build());

        postsRepository.save(Posts.builder()
                .title("test")
                .content("test")
                .hashtag("")
                .createAt(LocalDateTime.now())
                .userId(user)
                .views(0L)
                .build());
    }
}
