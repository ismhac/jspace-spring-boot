package com.ismhac.jspace.dto.post;

import com.ismhac.jspace.model.enums.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostCreateRequest {
    boolean useTrialPost;
    Integer purchasedProductId;
    int companyId;
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
