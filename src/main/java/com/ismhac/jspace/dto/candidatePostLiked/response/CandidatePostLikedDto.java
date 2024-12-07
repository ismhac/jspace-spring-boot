package com.ismhac.jspace.dto.candidatePostLiked.response;

import com.ismhac.jspace.dto.post.response.PostDto;
import com.ismhac.jspace.dto.user.response.UserDto;
import lombok.Data;

@Data
public class CandidatePostLikedDto {
    UserDto candidate;
    PostDto post;
}
