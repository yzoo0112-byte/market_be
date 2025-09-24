package com.market_be.controller;

import com.market_be.dto.PostRequestDto;
import com.market_be.dto.PostResponseDto;
import com.market_be.entity.Posts;
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

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@ModelAttribute PostRequestDto dto) throws IOException {
        System.out.println("createPost 호출됨");
        List<String> filePaths = new ArrayList<>();

        if (dto.getFiles() != null) {
            for (MultipartFile file : dto.getFiles()) {
                String uploadDir = System.getProperty("user.dir") + "/uploads";
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                String filePath = uploadDir + "/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
                file.transferTo(new File(filePath));
                filePaths.add(filePath);
            }
        }

        Posts posts = new Posts();
        posts.setTitle(dto.getTitle());
        posts.setHashtag(dto.getHashtag());
        posts.setContent(dto.getContent());
        posts.setFilePaths(filePaths);
        posts.setCreateAt(LocalDateTime.now());



        Posts saved = postService.save(posts);

        PostResponseDto response = new PostResponseDto(
                saved.getId(),
                saved.getTitle(),
                saved.getHashtag(),
                saved.getContent(),
                saved.getFilePaths()
        );

        return ResponseEntity.ok(response);
    }

}
