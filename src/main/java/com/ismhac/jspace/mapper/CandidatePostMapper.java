package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.candidatePost.response.CandidatePostDto;
import com.ismhac.jspace.dto.other.ApplyStatusDto;
import com.ismhac.jspace.dto.post.response.PostDto;
import com.ismhac.jspace.dto.resume.response.ResumeDto;
import com.ismhac.jspace.dto.user.candidate.response.CandidateDto;
import com.ismhac.jspace.model.Candidate;
import com.ismhac.jspace.model.CandidatePost;
import com.ismhac.jspace.model.Post;
import com.ismhac.jspace.model.Resume;
import com.ismhac.jspace.model.enums.ApplyStatus;
import com.ismhac.jspace.repository.CandidateFollowCompanyRepository;
import com.ismhac.jspace.repository.PostSkillRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface CandidatePostMapper {
    CandidatePostMapper instance = Mappers.getMapper(CandidatePostMapper.class);

    @Mapping(target = "candidate", source = "id.candidate", qualifiedByName = "convertCandidateToDto")
    @Mapping(target = "post", expression = "java(convertPostToDto(e.getId().getPost(), postSkillRepository, candidateFollowCompanyRepository))")
    @Mapping(target = "resume", source = "resume", qualifiedByName = "convertResumeToDto")
    @Mapping(target = "applyStatus", source = "applyStatus", qualifiedByName = "convertApplyStatusToDto")
    CandidatePostDto eToDto(CandidatePost e, @Context PostSkillRepository postSkillRepository, @Context CandidateFollowCompanyRepository candidateFollowCompanyRepository);

    default Page<CandidatePostDto> ePageToDtoPage(Page<CandidatePost> ePage, @Context PostSkillRepository postSkillRepository, @Context CandidateFollowCompanyRepository candidateFollowCompanyRepository){
        return ePage.map(item-> this.eToDto(item, postSkillRepository, candidateFollowCompanyRepository));
    }

    @Named("convertCandidateToDto")
    default CandidateDto convertCandidateToDto(Candidate candidate){
        return CandidateMapper.instance.eToDto(candidate);
    }

    default PostDto convertPostToDto(Post post, @Context PostSkillRepository postSkillRepository, @Context CandidateFollowCompanyRepository candidateFollowCompanyRepository){
        return PostMapper.instance.eToDto(post, postSkillRepository, candidateFollowCompanyRepository);
    }

    @Named("convertResumeToDto")
    default ResumeDto convertResumeToDto(Resume resume){
        return ResumeMapper.instance.toResumeDto(resume);
    }

    @Named("convertApplyStatusToDto")
    default ApplyStatusDto convertApplyStatusToDto(ApplyStatus applyStatus){
        return ApplyStatusDto.builder().value(applyStatus.name()).code(applyStatus.getStatus()).build();
    }
}
