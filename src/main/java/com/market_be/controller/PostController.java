package com.market_be.controller;

import com.market_be.dto.PostRequestDto;
import com.market_be.dto.PostResponseDto;
import com.market_be.entity.Files;
import com.market_be.entity.Posts;
import com.market_be.repository.FilesRepository;
import com.market_be.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final FilesRepository filesRepository;

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@ModelAttribute PostRequestDto dto) throws IOException {
        System.out.println("createPost 호출됨");

        Posts posts = new Posts();
        posts.setTitle(dto.getTitle());
        posts.setHashtag(dto.getHashtag());
        posts.setContent(dto.getContent());
        posts.setCreateAt(LocalDateTime.now());

        Posts savedPost = postService.save(posts);

        List<String> filePaths = new ArrayList<>();

        if (dto.getFiles() != null) {
            for (MultipartFile file : dto.getFiles()) {
                String uploadDir = System.getProperty("user.dir") + "/uploads";
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                String orgName = file.getOriginalFilename();
                String fileName = System.currentTimeMillis() + "_" + orgName;
                String filePath = uploadDir + "/" + fileName;
                file.transferTo(new File(filePath));
                filePaths.add(filePath);

                Files fileEntity = new Files();
                fileEntity.setPost(savedPost);
                fileEntity.setFileName(fileName);
                fileEntity.setFileOrgname(orgName);
                fileEntity.setFileUrl("/uploads/" + fileName);
                fileEntity.setFileSize(file.getSize());
                fileEntity.setImageYn(file.getContentType().startsWith("image") ? "Y" : "N");

                filesRepository.save(fileEntity);

            }
        }

        PostResponseDto response = new PostResponseDto(
                savedPost.getId(),
                savedPost.getTitle(),
                savedPost.getHashtag(),
                savedPost.getContent(),
                filePaths
        );

        return ResponseEntity.ok(response);
    }

}
