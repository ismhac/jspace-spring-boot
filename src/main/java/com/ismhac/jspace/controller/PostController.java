package com.ismhac.jspace.controller;

import com.ismhac.jspace.dto.common.ApiResponse;
import com.ismhac.jspace.dto.post.PostCreateRequest;
import com.ismhac.jspace.dto.post.PostDto;
import com.ismhac.jspace.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    @PostMapping("/create")
    public ApiResponse<PostDto> create(@RequestBody @Valid PostCreateRequest postCreateRequest){
        PostDto result = postService.create(postCreateRequest);
        return ApiResponse.<PostDto>builder()
                .result(result)
                .build();
    }
}
