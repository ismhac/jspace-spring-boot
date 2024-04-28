package com.ismhac.jspace.dto.companyRequestReview.response;

import com.ismhac.jspace.dto.company.response.CompanyDto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CompanyRequestReviewDto {
    private CompanyDto company;
    private LocalDate requestDate;
    private Boolean reviewed;
}
