package com.ismhac.jspace.service;

import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.post.response.PostDto;
import com.ismhac.jspace.model.enums.*;
import org.springframework.data.domain.Pageable;

import java.util.LinkedHashMap;
import java.util.Map;

public interface PostService {

    LinkedHashMap<String, Object> getById(int id, Integer candidateId);

    PageResponse<Map<String, Object>> getAllAndFilter(Integer candidateId, Experience experience, Gender gender, JobType jobType, Location location, Rank rank, Integer quantity, String title, String companyName, Integer maxPay, Integer minPay, Pageable pageable);
}
