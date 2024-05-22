package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.dto.skill.response.SkillDto;
import com.ismhac.jspace.mapper.SkillMapper;
import com.ismhac.jspace.repository.SkillRepository;
import com.ismhac.jspace.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {
   private final SkillRepository skillRepository;

    @Override
    public List<SkillDto> getAllSkills() {
        return SkillMapper.instance.eListToDtoList(skillRepository.findAll());
    }
}
