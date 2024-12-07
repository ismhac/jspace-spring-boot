package com.ismhac.jspace.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ismhac.jspace.dto.common.request.SendMailRequest;
import com.ismhac.jspace.dto.skill.response.SkillDto;
import com.ismhac.jspace.event.SuggestJobs;
import com.ismhac.jspace.exception.AppException;
import com.ismhac.jspace.exception.ErrorCode;
import com.ismhac.jspace.mapper.SkillMapper;
import com.ismhac.jspace.model.*;
import com.ismhac.jspace.repository.*;
import com.ismhac.jspace.service.SkillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;
    private final CandidateRepository candidateRepository;
    private final PostSkillRepository postSkillRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    @Autowired
    private ResourceLoader resourceLoader;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public List<SkillDto> getAllSkills() {
        return SkillMapper.instance.eListToDtoList(skillRepository.findAll());
    }

    @Override
    public void suggestJobs() {
        LocalDate now = LocalDate.now();
        List<PostSkill> posts = postSkillRepository.findAllByDate(now);
        List<Candidate> candidates = candidateRepository.findAll();

        Gson gson = new Gson();
        Map<Integer, CandidateProfile> candidateProfileCache = new ConcurrentHashMap<>();

        String suggestMain = readEmailTemplate("classpath:templates/SuggestionTemplate.txt");
        String postTemplateMain = readEmailTemplate("classpath:templates/PostItem.txt");

        candidates.parallelStream().forEach(candidate -> {
            CandidateProfile candidateProfile = candidateProfileCache.computeIfAbsent(candidate.getId().getUser().getId(), id -> {
                return candidateProfileRepository.findCandidateProfileById_Candidate_Id_User_Id(id).orElse(null);
            });

            if (candidateProfile == null) {
                return;
            }

            Set<Integer> skillIDs = new HashSet<>();

            if (StringUtils.isNotBlank(candidateProfile.getSkills())) {
                List<Skill> skillList = gson.fromJson(candidateProfile.getSkills(), new TypeToken<List<Skill>>() {}.getType());
                skillIDs.addAll(skillList.stream().map(Skill::getId).collect(Collectors.toSet()));
            }

            if (skillIDs.isEmpty()) return;

            List<Post> matchSkills = posts.stream()
                    .filter(item -> skillIDs.contains(item.getId().getSkill().getId()) ||
                                    candidateProfile.getRank() == item.getId().getPost().getRank() ||
                                    candidateProfile.getExperience() == item.getId().getPost().getExperience() ||
                                    candidateProfile.getLocation() == item.getId().getPost().getLocation() ||
                                    candidateProfile.getGender() == item.getId().getPost().getGender())
                    .map(postSkill -> postSkill.getId().getPost())
                    .distinct()
                    .collect(Collectors.toList());

//            String suggestionTemplate = readEmailTemplate("classpath:templates/SuggestionTemplate.txt");
            String suggestionTemplate = suggestMain;
            String content = "";
            for (Post post : matchSkills) {
//                String postTemplate = readEmailTemplate("classpath:templates/PostItem.txt");
                String postTemplate = postTemplateMain;
                String postItemString = postTemplate
                        .replace("#{companyLogo}", post.getCompany().getLogo())
                        .replace("#{postId}", String.valueOf(post.getId()))
                        .replace("#{postTitle}", post.getTitle())
                        .replace("#{companyName}", post.getCompany().getName())
                        .replace("#{postLocation}", post.getLocation().getProvince());

                content = content.concat(postItemString).concat("");
            }

            suggestionTemplate = suggestionTemplate
                    .replace("#{currentDate}", LocalDate.now().toString())
                    .replace("#{content}", content);

            SendMailRequest sendMailRequest = SendMailRequest.builder()
                    .email(candidate.getId().getUser().getEmail())
                    .body(suggestionTemplate)
                    .subject("New Jobs")
                    .build();

            SuggestJobs suggestJobs = new SuggestJobs(
                    this, sendMailRequest);

            applicationEventPublisher.publishEvent(suggestJobs);
        });
    }

    private String readEmailTemplate(String filePath) {
        Resource resource = resourceLoader.getResource(filePath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }
}
