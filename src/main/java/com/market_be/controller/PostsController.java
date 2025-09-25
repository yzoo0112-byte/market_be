package com.market_be.controller;

import com.market_be.dto.PostRequestDto;
import com.market_be.dto.PostResponseDto;
import com.market_be.dto.PostsDto;
import com.market_be.entity.AppUser;
import com.market_be.entity.Files;
import com.market_be.entity.Posts;
import com.market_be.repository.AppUserRepository;
import com.market_be.repository.FilesRepository;
import com.market_be.repository.PostsRepository;
import com.market_be.service.PostService;
import com.market_be.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostsController {
    private final PostsRepository postsRepository;
    private final PostsService postsService;
    private final PostService postService;
    private final FilesRepository filesRepository;
    private final AppUserRepository appUserRepository;

    @GetMapping("/{id}")
    public ResponseEntity<PostsDto> findById(@PathVariable Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다."));

        PostsDto dto = PostsDto.fromEntity(posts);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        postsService.deletePost(id);
        return ResponseEntity.ok().body(id);
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@ModelAttribute PostRequestDto dto,
                                                      @AuthenticationPrincipal String loginId
    ) throws IOException {
        System.out.println("createPost 호출됨");
        AppUser user = appUserRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자 정보 없음"));

        Posts posts = new Posts();
        posts.setTitle(dto.getTitle());
        posts.setHashtag(dto.getHashtag());
        posts.setContent(dto.getContent());
        posts.setCreateAt(LocalDateTime.now());
        posts.setUserId(user);

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
                String contentType = file.getContentType();
                fileEntity.setImageYn(contentType != null && contentType.startsWith("image") ? "Y" : "N");

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
