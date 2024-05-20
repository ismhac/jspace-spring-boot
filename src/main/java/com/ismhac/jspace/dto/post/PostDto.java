package com.ismhac.jspace.dto.post;

import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.dto.other.GenderDto;
import com.ismhac.jspace.dto.other.JobTypeDto;
import com.ismhac.jspace.dto.other.LocationDto;
import com.ismhac.jspace.dto.other.PostStatusDto;
import com.ismhac.jspace.dto.skill.response.SkillDto;
import com.ismhac.jspace.model.Company;
import com.ismhac.jspace.model.converter.GenderConverter;
import com.ismhac.jspace.model.converter.JobTypeConverter;
import com.ismhac.jspace.model.converter.LocationConverter;
import com.ismhac.jspace.model.converter.PostStatusConverter;
import com.ismhac.jspace.model.enums.Gender;
import com.ismhac.jspace.model.enums.JobType;
import com.ismhac.jspace.model.enums.Location;
import com.ismhac.jspace.model.enums.PostStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Data
public class PostDto {
    int id;
    CompanyDto company;
    String title;
    JobTypeDto jobType;
    LocationDto location;
    String rank;
    String description;
    int minPay;
    int maxPay;
    String experience;
    int quantity;
    GenderDto gender;
    LocalDate openDate;
    LocalDate closeDate;
    PostStatusDto postStatus;
    List<SkillDto> skills;
}
