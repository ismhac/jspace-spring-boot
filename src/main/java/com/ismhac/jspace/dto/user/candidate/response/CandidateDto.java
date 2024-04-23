package com.ismhac.jspace.dto.user.candidate.response;

import com.ismhac.jspace.dto.user.response.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDto {
    UserDto user;
}
