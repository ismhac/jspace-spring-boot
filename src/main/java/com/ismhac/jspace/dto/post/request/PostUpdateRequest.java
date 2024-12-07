package com.ismhac.jspace.dto.post.request;

import com.ismhac.jspace.model.enums.*;
import lombok.Data;

import java.util.List;

@Data
public class PostUpdateRequest {
    String title;
    JobType jobType;
    Location location;
    Rank rank;
    String description;
    int minPay;
    int maxPay;
    Experience experience;
    int quantity;
    Gender gender;
    List<Integer> skillIdList;
    List<String> newSkills;
}
