package com.ismhac.jspace.dto.post;

import com.ismhac.jspace.model.Company;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostDto {

    private int id;
    private String description;
//    @ManyToOne
//    @JoinColumn(name = "company_id", referencedColumnName = "id")
//    Company company;
}
