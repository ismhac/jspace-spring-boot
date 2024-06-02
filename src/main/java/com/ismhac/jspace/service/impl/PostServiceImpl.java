package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.post.response.PostDto;
import com.ismhac.jspace.exception.AppException;
import com.ismhac.jspace.exception.ErrorCode;
import com.ismhac.jspace.mapper.PostMapper;
import com.ismhac.jspace.model.Post;
import com.ismhac.jspace.model.enums.*;
import com.ismhac.jspace.repository.PostRepository;
import com.ismhac.jspace.repository.PostSkillRepository;
import com.ismhac.jspace.service.PostService;
import com.ismhac.jspace.util.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostSkillRepository postSkillRepository;
    private final PageUtils pageUtils;

    @Override
    public PostDto getById(int id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_POST));
        return PostMapper.instance.eToDto(post, postSkillRepository);
    }

    @Override
    public PageResponse<Map<String, Object>> getAllAndFilter(Integer candidateId, Experience experience, Gender gender, JobType jobType, Location location, Rank rank, Integer quantity, String title, String companyName, Integer maxPay, Integer minPay, Pageable pageable) {
        Page<Map<String, Object>> resultPage = postRepository.getPageAndFilter(
                candidateId, Objects.isNull(experience) ? null : experience.getCode(), Objects.isNull(gender) ? null : gender.getCode(),
                Objects.isNull(jobType) ? null : jobType.getCode(), Objects.isNull(location) ? null : location.getAreaCode(),
                Objects.isNull(rank) ? null : rank.getCode(), quantity, title, companyName, maxPay, minPay, pageable);

        List<Map<String, Object>> results = resultPage.getContent().stream().map(result -> {
            Map<String, Object> map = new HashMap<>(result);
            return map;
        }).toList();

        return pageUtils.toPageResponse(new PageImpl<>(results, resultPage.getPageable(), resultPage.getTotalPages()));
    }
}
