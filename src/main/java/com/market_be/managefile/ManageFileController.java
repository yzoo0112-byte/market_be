package com.market_be.managefile;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("manage/fileSetting")
@RequiredArgsConstructor
public class ManageFileController {


    private final ManageFileService manageFileService;

    // 설정 불러오기
    @GetMapping
    public ManageFileDto getManageFileSettings() {
        return manageFileService.getManageSettings();
    }

    // 설정 업데이트
    @PutMapping
    public void updateManageFileSettings(@RequestBody ManageFileDto dto) {
        manageFileService.updateManageSettings(dto);
    }
}
