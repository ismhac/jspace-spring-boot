package com.ismhac.jspace.dto.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostCreateRequest {
    @JsonIgnore
    LocalDate openDate;

    LocalDate closeDate;

    @JsonIgnore
    PostStatus postStatus;

    @JsonIgnore
    String employeeEmail;
    int companyId;
    String description;
    JobType jobType;
    Location location;
    String title;
    int quantity;
    int minPay;
    int maxPay;
}
