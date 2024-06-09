package com.ismhac.jspace.model.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum JobType {
    FULL_TIME("Full time"),
    PART_TIME("Part time"),
    TEMPORARY("Temporary"),
    CONTRACT("Contract"),
    INTERNSHIP("Internship"),
    COMMISSION("Commission"),
    NEW_GRAD("New grad"),
    PERMANENT("Permanent")
    ;

    String code;
}
