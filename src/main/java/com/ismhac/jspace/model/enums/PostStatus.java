package com.ismhac.jspace.model.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum PostStatus {
    OPEN("open"),
    CLOSE("close");
    String status;

    public static PostStatus resolve(String enumString) {
        for (PostStatus postStatus : PostStatus.values()) {
            if (postStatus.status.equalsIgnoreCase(enumString)) {
                return postStatus;
            }
        }
        throw new IllegalArgumentException("No enum constant for " + enumString);
    }
}
