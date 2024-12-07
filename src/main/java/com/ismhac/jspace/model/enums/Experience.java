package com.ismhac.jspace.model.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum Experience {
    NO_EXPERIENCE("No Experience"),
    LESS_THAN_ONE_YEAR("Less than one year"),
    ONE_TO_THREE_YEARS("One to Three year"),
    THREE_TO_FIVE_YEARS("Three to five year"),
    MORE_THAN_FIVE_YEARS("More than five year");
    String code;
}
