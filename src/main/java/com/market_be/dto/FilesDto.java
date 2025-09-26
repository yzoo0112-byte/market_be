package com.market_be.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class FilesDto {
    private Long fileId;

    private Long post;

    private String fileName;

    private String fileUrl;

    private String fileOrgname;

    private String imageYn;

    private Long fileSize;
}
