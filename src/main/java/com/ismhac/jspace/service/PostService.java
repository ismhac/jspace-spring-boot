package com.ismhac.jspace.service;

import com.ismhac.jspace.dto.post.PostCreateRequest;
import com.ismhac.jspace.dto.post.PostDto;

public interface PostService {

    PostDto create(PostCreateRequest postCreateRequest);
}
