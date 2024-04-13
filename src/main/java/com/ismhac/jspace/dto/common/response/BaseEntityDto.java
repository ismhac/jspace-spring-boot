package com.ismhac.jspace.dto.common.response;

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
    Integer createdBy;
    Integer updatedBy;
}
