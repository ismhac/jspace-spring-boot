package com.ismhac.jspace.dto.common.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendMailResponse {
    String email;
    LocalDateTime otpCreatedDateTime;
}
