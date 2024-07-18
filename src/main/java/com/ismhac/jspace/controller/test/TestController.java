package com.ismhac.jspace.controller.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ismhac.jspace.dto.common.request.SendMailRequest;
import com.ismhac.jspace.event.SuggestJobs;
import com.ismhac.jspace.exception.AppException;
import com.ismhac.jspace.exception.ErrorCode;
import com.ismhac.jspace.model.*;
import com.ismhac.jspace.model.enums.RoleCode;
import com.ismhac.jspace.model.primaryKey.EmployeeId;
import com.ismhac.jspace.repository.*;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
@Tag(name = "Test")
@Slf4j
@Hidden
public class TestController {
    @Autowired
    private UserRepository userRepository;
    private final CandidateRepository candidateRepository;
    private final PostSkillRepository postSkillRepository;
    private final SkillRepository skillRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    @Autowired
    private ResourceLoader resourceLoader;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final RoleRepository roleRepository;
    private final EmployeeRepository employeeRepository;

    @PostMapping("/check-send-mail-suggest-jobs")
    public void suggestJobs() {
        LocalDate now = LocalDate.now();
        List<PostSkill> posts = postSkillRepository.findAllByDate(now);
        List<Candidate> candidates = candidateRepository.findAll();

        Gson gson = new Gson();
        Map<Integer, CandidateProfile> candidateProfileCache = new ConcurrentHashMap<>();

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

            String suggestionTemplate = readEmailTemplate("classpath:templates/suggestions/SuggestionTemplate.txt");
            String content = "";
            for (Post post : matchSkills) {
                String postTemplate = readEmailTemplate("classpath:templates/suggestions/PostItem.txt");
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

//    @Scheduled(cron = "0 0 19 * * *")
//    @PostMapping("/check-send-mail-suggest-jobs")
//    public void suggestJobs() {
//        LocalDate now = LocalDate.now();
//        List<PostSkill> posts = postSkillRepository.findAllByDate(now);
//        List<Candidate> candidates = candidateRepository.findAll();
////        candidates.forEach(candidate -> suggestForOneCandidate(candidate, posts));
//        candidates.forEach(candidate -> suggestForCandidate(candidate, posts));
//    }

    @PostMapping("/save-skills")
    @Transactional(rollbackFor = Exception.class)
    public void saveSkills(@RequestBody List<String> skillsName){
        List<Skill> skills = skillsName.stream().map(item->Skill.builder().name(item).build()).toList();
        skillRepository.saveAll(skills);
    }

    @PostMapping("/save-employees")
    public void saveEmployee(){
        var role = roleRepository.findRoleByCode(RoleCode.EMPLOYEE);

        List<User> users = new ArrayList<>();
        for(int i = 3; i <= 33; i++){
            User user = User.builder()
                    .activated(true)
                    .email(String.format("employer%s@gmail.com", i))
                    .name(String.format("Employer%s", i))
                    .password("$2a$10$0HzWws07Q9b/oTILcGzLGuKPE69xR4/3CZ4S414.IXZu5fsEIa8jm")
                    .role(role.get())
                    .build();
            users.add(user);
        }

        List<User> saveUsers = userRepository.saveAll(users);

        List<Employee> employees = saveUsers.stream().map(user-> Employee.builder()
                .id(EmployeeId.builder()
                        .user(user)
                        .build())
                .build()).toList();

        employeeRepository.saveAll(employees);
    }

    private void suggestForCandidate(Candidate candidate, List<PostSkill> postSkills) {
        CandidateProfile candidateProfile = candidateProfileRepository.findCandidateProfileById_Candidate_Id_User_Id(candidate.getId()
                .getUser().getId()).orElse(null);
        if(candidateProfile == null){
            return;
        }

        List<Skill> skillList = null;

        Gson gsonSkill = new Gson();
        if(StringUtils.isNotBlank(candidateProfile.getSkills())){
            skillList= gsonSkill.fromJson(candidateProfile.getSkills(), new TypeToken<List<Skill>>() {
            }.getType());
        }

        List<Integer> skillIDs;
        if(skillList != null){
            skillIDs = skillList.stream().map(Skill::getId).toList();
        } else {
            skillIDs = new ArrayList<>();
        }

        if(skillIDs.isEmpty()) return;

        List<Post> matchSkills = postSkills
                .stream().filter(item ->
                        skillIDs.contains(item.getId().getSkill().getId()) ||
                        candidateProfile.getRank() == item.getId().getPost().getRank() ||
                        candidateProfile.getExperience() == item.getId().getPost().getExperience() ||
                        candidateProfile.getLocation() == item.getId().getPost().getLocation() ||
                        candidateProfile.getGender() == item.getId().getPost().getGender()
                ).toList()
                .stream().map(postSkill -> postSkill.getId().getPost()).toList().stream().distinct().toList();

        String suggestionTemplate = readEmailTemplate("classpath:templates/suggestions/SuggestionTemplate.txt");
        String content = "";
        for (Post post : matchSkills) {
            String postTemplate = readEmailTemplate("classpath:templates/suggestions/PostItem.txt");
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
    }

    private void suggestForOneCandidate(Candidate candidate, List<PostSkill> postSkills) {
        Gson gson = new Gson();
        String skills = candidate.getSkills();
        if (StringUtils.isBlank(skills)) return;
        List<Integer> skillsId = gson.fromJson(skills, new TypeToken<List<Integer>>() {
        }.getType());
        if (skillsId.isEmpty()) return;
        List<Post> matchSkills = postSkills
                .stream().filter(item -> skillsId.contains(item.getId().getSkill().getId())).toList()
                .stream().map(postSkill -> postSkill.getId().getPost()).toList().stream().distinct().toList();

        String suggestionTemplate = readEmailTemplate("classpath:templates/suggestions/SuggestionTemplate.txt");
        String content = "";
        for (Post post : matchSkills) {
            String postTemplate = readEmailTemplate("classpath:templates/suggestions/PostItem.txt");
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
