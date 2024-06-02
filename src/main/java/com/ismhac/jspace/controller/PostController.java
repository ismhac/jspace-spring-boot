package com.ismhac.jspace.controller;

import com.ismhac.jspace.dto.common.response.ApiResponse;
import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.post.response.PostDto;
import com.ismhac.jspace.model.enums.*;
import com.ismhac.jspace.service.PostService;
import com.ismhac.jspace.util.PageUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Tag(name = "Post")
public class PostController {
    private final PostService postService;
    private final PageUtils pageUtils;

    @GetMapping("/{id}")
    public ApiResponse<PostDto> getById(@PathVariable("id") int id) {
        return ApiResponse.<PostDto>builder().result(postService.getById(id)).build();
    }

    @GetMapping()
    public ApiResponse<PageResponse<Map<String, Object>>> getAllAndFilter(
            @RequestParam(value = "candidateId",required = false) Integer candidateId,
            @RequestParam(value = "experience",required = false) Experience experience,
            @RequestParam(value = "gender",required = false)Gender gender,
            @RequestParam(value = "jobType",required = false)JobType jobType,
            @RequestParam(value = "location",required = false)Location location,
            @RequestParam(value = "rank",required = false)Rank rank,
            @RequestParam(value = "quantity",required = false)Integer quantity,
            @RequestParam(value = "title",required = false)String title,
            @RequestParam(value = "companyName",required = false)String companyName,
            @RequestParam(value = "maxPay",required = false)Integer maxPay,
            @RequestParam(value = "minPay",required = false)Integer minPay,
            Pageable pageable) {
        return ApiResponse.<PageResponse<Map<String, Object>>>builder().result(postService.getAllAndFilter(candidateId,experience, gender, jobType, location, rank, quantity, title, companyName, maxPay, minPay, pageUtils.adjustPageable(pageable))).build();
    }
}
