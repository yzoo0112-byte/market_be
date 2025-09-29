package com.market_be.service;

import com.market_be.repository.FilesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FilesService {
    private final FilesRepository filesRepository;


}
