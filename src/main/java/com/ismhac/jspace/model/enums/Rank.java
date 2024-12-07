package com.ismhac.jspace.model.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum Rank {
    INTERN("Intern"),
    FRESHER("Fresher"),
    JUNIOR("Junior"),
    MIDDlE("Middle"),
    SENIOR("Senior"),
    TEAM_LEADER("Team leader"),
    DEPARTMENT_HEAD("Department head"),
    ALL_LEVELS("All levels");
    String code;
}
