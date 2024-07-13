package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.mapper.PostMapper;
import com.ismhac.jspace.model.Post;
import com.ismhac.jspace.model.enums.*;
import com.ismhac.jspace.repository.CandidateFollowCompanyRepository;
import com.ismhac.jspace.repository.PostRepository;
import com.ismhac.jspace.repository.PostSkillRepository;
import com.ismhac.jspace.service.PostService;
import com.ismhac.jspace.util.PageUtils;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostSkillRepository postSkillRepository;
    private final PageUtils pageUtils;
    private final CandidateFollowCompanyRepository candidateFollowCompanyRepository;

    @Override
    public LinkedHashMap<String, Object> getById(int id, Integer candidateId) {
        Tuple result = postRepository.findPostByIdAndCandidateId(id, candidateId);
        return new LinkedHashMap<>() {{
            put("post", PostMapper.instance.eToDto((Post) result.get("post"), postSkillRepository, candidateFollowCompanyRepository));
            put("userMode", result.get("userMode"));
            put("liked", result.get("liked"));
            put("applied", result.get("applied"));
        }};
    }

    @Override
    public PageResponse<Map<String, Object>> getAllAndFilter(Integer candidateId, Experience experience, Gender gender, JobType jobType, Location location, Rank rank, Integer quantity, String title, String companyName, Integer maxPay, Integer minPay, List<Integer> skills_id,Pageable pageable) {
        Page<Map<String, Object>> resultPage = postRepository.getPageAndFilter(
                candidateId,
                Objects.isNull(experience) ? null : experience.getCode(),
                Objects.isNull(gender) ? null : gender.getCode(),
                Objects.isNull(jobType) ? null : jobType.getCode(),
                Objects.isNull(location) ? null : location,
                Objects.isNull(rank) ? null : rank.getCode(),
                quantity,
                title,
                companyName,
                maxPay,
                minPay,
                LocalDate.now(),
                PostStatus.OPEN,
                skills_id,
                pageable);
        List<Map<String, Object>> results = resultPage.getContent().stream().map(result -> {
            Map<String, Object> map = new HashMap<>(result);
            map.put("post", PostMapper.instance.eToDto((Post) result.get("post"), postSkillRepository, candidateFollowCompanyRepository));
            return map;
        }).toList();
        return pageUtils.toPageResponse(new PageImpl<>(results, resultPage.getPageable(), resultPage.getTotalElements()));
    }
}
