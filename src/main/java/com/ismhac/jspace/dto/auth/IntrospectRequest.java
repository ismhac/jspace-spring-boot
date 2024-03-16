package com.ismhac.jspace.dto.auth;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class IntrospectRequest {
    private String token;
}
