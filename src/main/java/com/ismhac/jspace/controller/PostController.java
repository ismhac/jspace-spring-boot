package com.ismhac.jspace.controller;

import com.ismhac.jspace.dto.common.response.ApiResponse;
import com.ismhac.jspace.dto.post.PostCreateRequest;
import com.ismhac.jspace.dto.post.PostDto;
import com.ismhac.jspace.service.PostService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Tag(name ="Post")
public class PostController {

    private final PostService postService;

    @Hidden
    @PostMapping("/create")
    public ApiResponse<PostDto> create(@RequestBody @Valid PostCreateRequest postCreateRequest){
        PostDto result = postService.create(postCreateRequest);
        return ApiResponse.<PostDto>builder()
                .result(result)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<PostDto> getById(@PathVariable("id") int id){
        PostDto result = postService.getById(id);
        return ApiResponse.<PostDto>builder()
                .result(result)
                .build();
    }
}
