package com.ismhac.jspace.dto.skill.response;

import com.ismhac.jspace.dto.common.response.BaseEntityDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class SkillDto{
    private int id;
    private String name;

    public SkillDto(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
