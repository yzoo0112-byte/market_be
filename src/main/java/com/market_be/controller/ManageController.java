package com.market_be.controller;

import com.market_be.dto.AppUserDto;
import com.market_be.entity.Visit;
import com.market_be.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/manage")
@RequiredArgsConstructor
public class ManageController {

    private final VisitRepository visitRepository;

    @GetMapping("/visit")
    public List<Visit> getVisitCount() {
        List<Visit> visits = visitRepository.findAll();
        return visits;
    }
}
