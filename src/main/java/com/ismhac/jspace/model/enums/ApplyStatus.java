package com.ismhac.jspace.model.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ApplyStatus {
    PROGRESS("Progress"),
    APPROVE("Approve"),
    REJECT("Reject");
    String status;

    public static ApplyStatus resolve(String enumString) {
        for (ApplyStatus applyStatus : ApplyStatus.values()) {
            if (applyStatus.status.equalsIgnoreCase(enumString)) {
                return applyStatus;
            }
        }
        throw new IllegalArgumentException("No enum constant for " + enumString);
    }
}
