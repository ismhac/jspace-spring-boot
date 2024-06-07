package com.ismhac.jspace.controller;

import com.ismhac.jspace.dto.common.response.ApiResponse;
import com.ismhac.jspace.dto.skill.response.SkillDto;
import com.ismhac.jspace.service.SkillService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/skills")
@RequiredArgsConstructor
@Tag(name = "Skill")
public class SkillController {
    private final SkillService skillService;

    @GetMapping()
    public ApiResponse<List<SkillDto>> getAllSkills(){
        return ApiResponse.<List<SkillDto>>builder().result(skillService.getAllSkills()).build();
    }
}
