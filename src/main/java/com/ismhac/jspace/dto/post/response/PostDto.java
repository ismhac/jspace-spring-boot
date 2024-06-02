package com.ismhac.jspace.dto.post.response;

import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.dto.other.*;
import com.ismhac.jspace.dto.skill.response.SkillDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PostDto {
    int id;
    CompanyDto company;
    String title;
    JobTypeDto jobType;
    LocationDto location;
    RankDto rank;
    String description;
    int minPay;
    int maxPay;
    ExperienceDto experience;
    int quantity;
    GenderDto gender;
    LocalDate openDate;
    LocalDate closeDate;
    PostStatusDto postStatus;
    List<SkillDto> skills;
}
