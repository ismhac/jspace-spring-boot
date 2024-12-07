package com.ismhac.jspace.service;

import com.ismhac.jspace.dto.skill.response.SkillDto;

import java.util.List;

public interface SkillService {
    List<SkillDto> getAllSkills();

    void suggestJobs();
}
