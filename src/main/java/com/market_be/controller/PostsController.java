package com.market_be.controller;

import com.market_be.dto.PostRequestDto;
import com.market_be.dto.PostResponseDto;
import com.market_be.dto.PostsDto;
import com.market_be.entity.AppUser;
import com.market_be.entity.Files;
import com.market_be.entity.Posts;
import com.market_be.repository.AppUserRepository;
import com.market_be.repository.FilesRepository;
import com.market_be.repository.LikesRepository;
import com.market_be.repository.PostsRepository;
import com.market_be.service.PostService;
import com.market_be.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostsController {

    private final PostsRepository postsRepository;
    private final PostsService postsService;
    private final PostService postService;
    private final FilesRepository filesRepository;
    private final AppUserRepository appUserRepository;
    private final LikesRepository likesRepository;

    // ✅ 게시글 단일 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostsDto> findById(@PathVariable Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다."));
        PostsDto dto = PostsDto.fromEntity(posts);

        //1. post id로 좋아요 수 찾기
        Long likeCount = likesRepository.countByPostId(id);

        // 2. dto set likecount하기
        dto.setLikeCount(likeCount);

        return ResponseEntity.ok(dto);
    }

    // ✅ 게시글 삭제(휴지통으로)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> softDelete(@PathVariable Long id) {
        postsService.softDelete(id);
        return ResponseEntity.ok().body(id);
    }

    //영구삭제
    @DeleteMapping("/manage/trash/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        postsService.deletePost(id);
        return ResponseEntity.ok().body("영구삭제되었습니다");
    }


    // 게시글 복구
    @PatchMapping("/manage/{id}/restore")
    public ResponseEntity<?> restore(@PathVariable Long id) {
        postsService.restore(id);
        return ResponseEntity.ok().body("게시글이 복구되었습니다.");
    }

    // 휴지통 게시글 조회
    @GetMapping("/manage/trash")
    public ResponseEntity<List<PostsDto>> getDeletedPosts() {
        List<PostsDto> deletedPosts = postsRepository.findByDeletedTrue()
                .stream()
                .map(PostsDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(deletedPosts);
    }



    // ✅ 게시글 등록
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@ModelAttribute PostRequestDto dto,
                                                      @AuthenticationPrincipal String loginId) throws IOException {
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
                filePaths.add("/uploads/" + fileName);

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

    // ✅ 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long id,
            @ModelAttribute PostRequestDto dto,
            @AuthenticationPrincipal String loginId,
            @RequestParam(value = "removedFiles", required = false) List<Long> removedFileIds
    ) throws IOException {

        AppUser user = appUserRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자 정보 없음"));

        Posts post = postsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다."));

        if (!post.getUserId().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "수정 권한이 없습니다.");
        }

        post.setTitle(dto.getTitle());
        post.setHashtag(dto.getHashtag());
        post.setContent(dto.getContent());
        post.setUpdateAt(LocalDateTime.now());

        Posts updatedPost = postService.save(post);

        // 삭제할 파일만 삭제
        if (removedFileIds != null) {
            for (Long fileId : removedFileIds) {
                Files fileEntity = filesRepository.findById(fileId).orElse(null);
                if (fileEntity != null) {
                    File file = new File(System.getProperty("user.dir") + "/uploads/" + fileEntity.getFileName());
                    if (file.exists()) file.delete();
                    filesRepository.delete(fileEntity);
                }
            }
        }

        // 새로 업로드된 파일 저장
        if (dto.getFiles() != null) {
            for (MultipartFile file : dto.getFiles()) {
                String uploadDir = System.getProperty("user.dir") + "/uploads";
                File directory = new File(uploadDir);
                if (!directory.exists()) directory.mkdirs();

                String orgName = file.getOriginalFilename();
                String fileName = System.currentTimeMillis() + "_" + orgName;
                file.transferTo(new File(uploadDir + "/" + fileName));

                Files fileEntity = new Files();
                fileEntity.setPost(updatedPost);
                fileEntity.setFileName(fileName);
                fileEntity.setFileOrgname(orgName);
                fileEntity.setFileUrl("/uploads/" + fileName);
                fileEntity.setFileSize(file.getSize());
                String contentType = file.getContentType();
                fileEntity.setImageYn(contentType != null && contentType.startsWith("image") ? "Y" : "N");

                filesRepository.save(fileEntity);
            }
        }

        // 모든 파일 URL 반환
        List<String> fileUrls = updatedPost.getFileList().stream()
                .map(Files::getFileUrl)
                .collect(Collectors.toList());

        PostResponseDto response = new PostResponseDto(
                updatedPost.getId(),
                updatedPost.getTitle(),
                updatedPost.getContent(),
                updatedPost.getHashtag(),
                fileUrls
        );

        return ResponseEntity.ok(response);
    }



}