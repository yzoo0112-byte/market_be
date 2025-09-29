package com.market_be.managefile;


import com.market_be.entity.Manage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManageFileService {
    private final ManageFileRepository manageFileRepository;

    public ManageFileDto getManageSettings() {
        Manage manage = manageFileRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("설정이 존재하지 않습니다."));

        return ManageFileDto.builder()
                .fileCount(manage.getFileCount())
                .fileExtension(manage.getFileExtension())
                .fileMaxSize(manage.getFileMaxSize())
                .build();
    }

    public void updateManageSettings(ManageFileDto dto) {
        Manage manage = manageFileRepository.findById(1)
                .orElse(new Manage());

        manage.setFileCount(dto.getFileCount());
        manage.setFileExtension(dto.getFileExtension());
        manage.setFileMaxSize(dto.getFileMaxSize());

        manageFileRepository.save(manage);
    }

}
