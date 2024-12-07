package com.ismhac.jspace.controller.common;

import com.ismhac.jspace.dto.common.response.ApiResponse;
import com.ismhac.jspace.dto.other.*;
import com.ismhac.jspace.service.common.CommonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/commons")
@RequiredArgsConstructor
@Tag(name = "Common")
public class CommonController {
    private final CommonService commonService;

    @GetMapping("/locations")
    public ApiResponse<List<LocationDto>> getLocations() {
        return ApiResponse.<List<LocationDto>>builder().result(commonService.getLLocation()).build();
    }

    @GetMapping("/jobTypes")
    public ApiResponse<List<JobTypeDto>> getJobTypes() {
        return ApiResponse.<List<JobTypeDto>>builder().result(commonService.getJobType()).build();
    }

    @GetMapping("/genders")
    public ApiResponse<List<GenderDto>> getGenders() {
        return ApiResponse.<List<GenderDto>>builder().result(commonService.getGender()).build();
    }

    @GetMapping("/applyStatus")
    public ApiResponse<List<ApplyStatusDto>> getApplyStatus() {
        return ApiResponse.<List<ApplyStatusDto>>builder().result(commonService.getApplyStatus()).build();
    }

    @GetMapping("/ranks")
    public ApiResponse<List<RankDto>> getRanks() {
        return ApiResponse.<List<RankDto>>builder().result(commonService.getRank()).build();
    }

    @GetMapping("/experiences")
    public ApiResponse<List<ExperienceDto>> getExperiences() {
        return ApiResponse.<List<ExperienceDto>>builder().result(commonService.getExperience()).build();
    }
}
