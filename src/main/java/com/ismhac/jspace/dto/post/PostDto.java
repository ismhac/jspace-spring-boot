package com.ismhac.jspace.dto.post;

import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.dto.other.LocationDto;
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

@Data
public class PostDto {

    int id;

    String employeeEmail;

    CompanyDto company;

    String title; // ten cong viec

    JobType jobType; // loai cong viec

    LocationDto location; // using enum;

    String rank; // vi tri using emum

    String description; // mo ta

    int minPay;

    int maxPay;

    String experience; // using enum

    int quantity; //  so luong

    Gender gender;

    String skills; // skills is arrays

    LocalDate openDate;

    LocalDate closeDate;

    PostStatus postStatus;
}
