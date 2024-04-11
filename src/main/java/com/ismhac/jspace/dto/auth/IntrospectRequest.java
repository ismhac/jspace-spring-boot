package com.ismhac.jspace.dto.auth;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntrospectRequest {
    private String token;
}
