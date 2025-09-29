package com.market_be.managefile;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManageFileDto {

    private String fileExtension;   // 예: "jpg,png,exe"
    private Long fileMaxSize;       // 예: 524288000 (500MB)
    private int fileCount;          // 예: 5

}
