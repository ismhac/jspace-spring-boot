package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.candidateFollowCompany.response.CandidateFollowCompanyDto;
import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.dto.user.response.UserDto;
import com.ismhac.jspace.model.CandidateFollowCompany;
import com.ismhac.jspace.model.Company;
import com.ismhac.jspace.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CandidateFollowCompanyMapper {
    CandidateFollowCompanyMapper instance = Mappers.getMapper(CandidateFollowCompanyMapper.class);

    @Mapping(target = "company", source = "id.company", qualifiedByName = "convertCompanyToDto")
    @Mapping(target = "candidate", source = "id.candidate.id.user", qualifiedByName = "convertUserToDto")
    CandidateFollowCompanyDto eToDto (CandidateFollowCompany e);

    @Named("convertUserToDto")
    default UserDto convertUserToDto(User user){
        return UserMapper.instance.toUserDto(user);
    }

    @Named("convertCompanyToDto")
    default CompanyDto convertCompanyToDto(Company company){
        return CompanyMapper.instance.eToDto(company);
    }
}
