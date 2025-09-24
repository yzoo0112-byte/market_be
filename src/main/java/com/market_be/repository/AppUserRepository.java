package com.market_be.repository;

import com.market_be.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

}
