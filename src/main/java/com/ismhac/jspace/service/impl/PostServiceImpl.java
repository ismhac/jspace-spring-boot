package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.dto.post.PostCreateRequest;
import com.ismhac.jspace.dto.post.PostDto;
import com.ismhac.jspace.exception.ErrorCode;
import com.ismhac.jspace.exception.NotFoundException;
import com.ismhac.jspace.mapper.PostMapper;
import com.ismhac.jspace.model.Company;
import com.ismhac.jspace.model.Employee;
import com.ismhac.jspace.model.Post;
import com.ismhac.jspace.model.User;
import com.ismhac.jspace.model.primaryKey.EmployeeId;
import com.ismhac.jspace.repository.EmployeeRepository;
import com.ismhac.jspace.repository.PostRepository;
import com.ismhac.jspace.repository.PostSkillRepository;
import com.ismhac.jspace.repository.UserRepository;
import com.ismhac.jspace.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    private final EmployeeRepository employeeRepository;

    private final UserRepository userRepository;

    private final PostSkillRepository postSkillRepository;

    @Override
    public PostDto create(PostCreateRequest postCreateRequest) {

        /* prepare data*/
        String description = postCreateRequest.getDescription().trim();
        String employeeEmail = getEmployeeEmailFromToken();
        Company company = getCompanyByEmployee(findEmployeeByEmail(employeeEmail));
        /**/

        Post post = Post.builder()
                .company(company)
                .build();
        Post savedPost = postRepository.save(post);
        return PostMapper.instance.eToDto(savedPost, postSkillRepository);
    }


    /* */
    private String getEmployeeEmailFromToken() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> claims = jwt.getClaims();
        String employeeEmail = (String) claims.get("sub");
        return employeeEmail;
    }

    private Employee findEmployeeByEmail(String email) {

        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER));

        EmployeeId id = EmployeeId.builder()
                .user(user)
                .build();

        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_EMPLOYEE));
        return employee;
    }

    private Company getCompanyByEmployee(Employee employee) {
        Company company = employee.getCompany();
        if (Objects.isNull(company)) {
            throw new NotFoundException(ErrorCode.NOT_FOUND_EMPLOYEE);
        }
        return company;
    }
}
