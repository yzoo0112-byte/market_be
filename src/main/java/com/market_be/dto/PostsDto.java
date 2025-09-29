package com.market_be.dto;

import com.market_be.entity.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@ToString
public class PostsDto {

    private Long postId;

    private Long userId;

    private String title;

    private String content;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private Long views;

    private List<FilesDto> fileList;

    private String hashtag;
    private boolean deleted;

    public static PostsDto fromEntity(Posts posts) {
        return PostsDto.builder()
                .postId(posts.getId())
                .title(posts.getTitle())
                .content(posts.getContent())
                .hashtag(posts.getHashtag())
                .views(posts.getViews())
                .createAt(posts.getCreateAt())
                .updateAt(posts.getUpdateAt())
                .userId(posts.getUserId().getId())
                .fileList(
                        posts.getFileList().stream()
                                .map(files -> FilesDto.builder()
                                        .fileId(files.getFileId())
                                        .fileOrgname(files.getFileOrgname())
                                        .fileUrl(files.getFileUrl())
                                        .fileName(files.getFileName())
                                        .fileSize(files.getFileSize())
                                        .imageYn(files.getImageYn())
                                        .build()
                                )
                                .collect(Collectors.toList())
                )
                .deleted(posts.isDeleted())
                .build();
    }

}
