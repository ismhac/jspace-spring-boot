package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.model.Role;
import com.ismhac.jspace.repository.RoleRepository;
import com.ismhac.jspace.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Value("${init.role.code.admin}")
    private String adminRoleCode;

    @Value("${init.role.code.employee}")
    private String employeeRoleCode;

    @Value("${init.role.code.candidate}")
    private String candidateRoleCode;


    private final RoleRepository roleRepository;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void initRoles() {
        boolean isExistedAdminRoleCode = roleRepository.existsByCode(adminRoleCode);
        boolean isExistedEmployeeRoleCode = roleRepository.existsByCode(employeeRoleCode);
        boolean isExistedCandidateRoleCode = roleRepository.existsByCode(candidateRoleCode);

        if(!isExistedAdminRoleCode){
            Role adminRole = Role.builder()
                    .code(adminRoleCode)
                    .name("Super Admin")
                    .build();
            roleRepository.save(adminRole);
        }else {
            log.info("Already have the Super Admin role");
        }

        if(!isExistedEmployeeRoleCode){
            Role employeeRole = Role.builder()
                    .code(employeeRoleCode)
                    .name("Employee")
                    .build();
            roleRepository.save(employeeRole);
        }else {
            log.info("Already have the Employee role");
        }

        if(!isExistedCandidateRoleCode){
            Role candidateRole = Role.builder()
                    .code(candidateRoleCode)
                    .name("Candidate")
                    .build();
            roleRepository.save(candidateRole);
        } else {
            log.info("Already have the Candidate role");
        }
    }
}
