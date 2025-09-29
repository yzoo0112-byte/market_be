package com.market_be.repository;

import com.market_be.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface VisitRepository extends JpaRepository<Visit, LocalDate> {
    Visit findByVisitDate(LocalDate visitDate);
}
