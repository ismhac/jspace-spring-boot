package com.ismhac.jspace.dto.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseEntityDto {
    Instant createdAt;
    Instant updatedAt;
    @JsonIgnore
    Integer createdBy;
    @JsonIgnore
    Integer updatedBy;
}
