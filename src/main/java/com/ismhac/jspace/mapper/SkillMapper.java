package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.skill.response.SkillDto;
import com.ismhac.jspace.model.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SkillMapper {
    SkillMapper instance = Mappers.getMapper(SkillMapper.class);

    SkillDto eToDto(Skill e);

    default List<SkillDto> eListToDtoList(List<Skill> eList){
        return eList.stream().map(this::eToDto).toList();
    }

    default Page<SkillDto> ePageToDtoPage(Page<Skill> ePage){
        return ePage.map(this::eToDto);
    }
}
