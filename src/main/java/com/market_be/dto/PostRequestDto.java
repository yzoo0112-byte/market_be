package com.market_be.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
// 요청 DTO
@Getter @Setter
public class PostRequestDto {
    private String title;
    private String content;
    private String hashtag;
    private List<MultipartFile> files;
}
