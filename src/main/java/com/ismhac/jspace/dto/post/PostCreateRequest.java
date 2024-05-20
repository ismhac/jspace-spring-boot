package com.ismhac.jspace.dto.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ismhac.jspace.model.enums.Gender;
import com.ismhac.jspace.model.enums.JobType;
import com.ismhac.jspace.model.enums.Location;
import com.ismhac.jspace.model.enums.PostStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostCreateRequest {
    int companyId;
    String title;
    JobType jobType;
    Location location;
    String rank;
    String description;
    int minPay;
    int maxPay;
    String experience;
    int quantity;
    Gender gender;
    List<Integer> skillIdList;
    List<String> newSkills;
}
