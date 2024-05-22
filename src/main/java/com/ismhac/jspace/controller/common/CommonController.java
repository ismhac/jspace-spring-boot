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
    public ApiResponse<List<LocationDto>> getLocations(){
        var result = commonService.getLLocation();
        return ApiResponse.<List<LocationDto>>builder()
                .result(result)
                .build();
    }

    @GetMapping("/jobTypes")
    public ApiResponse<List<JobTypeDto>> getJobTypes(){
        var result = commonService.getJobType();
        return ApiResponse.<List<JobTypeDto>>builder()
                .result(result)
                .build();
    }

    @GetMapping("/genders")
    public ApiResponse<List<GenderDto>> getGenders(){
        var result = commonService.getGender();
        return ApiResponse.<List<GenderDto>>builder()
                .result(result)
                .build();
    }

    @GetMapping("/applyStatus")
    public ApiResponse<List<ApplyStatusDto>> getApplyStatus(){
        var result = commonService.getApplyStatus();
        return ApiResponse.<List<ApplyStatusDto>>builder()
                .result(result)
                .build();
    }

    @GetMapping("/ranks")
    public ApiResponse<List<RankDto>> getRanks(){
        var result = commonService.getRank();
        return ApiResponse.<List<RankDto>>builder()
                .result(result)
                .build();
    }

    @GetMapping("/experiences")
    public ApiResponse<List<ExperienceDto>> getExperiences(){
        var result = commonService.getExperience();
        return ApiResponse.<List<ExperienceDto>>builder()
                .result(result)
                .build();
    }
}
