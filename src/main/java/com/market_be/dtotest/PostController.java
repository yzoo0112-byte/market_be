package com.market_be.dtotest;

import com.market_be.dtotest.ApiResponse;
import com.market_be.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {


    private final PostService postService;

    @GetMapping
    public ApiResponse getPosts(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int size,
                                @RequestParam(defaultValue = "postId") String sortKey,
                                @RequestParam(defaultValue = "asc") String sortOrder,
                                @RequestParam(required = false) String keyword) {
        return postService.getPosts(page, size, sortKey, sortOrder, keyword);
    }
}
