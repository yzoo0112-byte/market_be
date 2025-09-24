package com.market_be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
// 응답 Dto
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private Long post_id;
    private String title;
    private String content;
    private String hashtag;
    private List<String> files;
}
