package com.ismhac.jspace.dto.company;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyUpdateRequest {
    @JsonProperty("name")
    private String name;

    @JsonProperty("background")
    private String background;

    @JsonProperty("logo")
    private String logo;

    @JsonProperty("address")
    private String address;
}
