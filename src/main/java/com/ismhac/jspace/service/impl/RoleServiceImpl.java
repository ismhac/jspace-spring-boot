package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.model.Role;
import com.ismhac.jspace.model.enums.RoleCode;
import com.ismhac.jspace.repository.RoleRepository;
import com.ismhac.jspace.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void initRoles() {
        Arrays.stream(RoleCode.values())
                .filter(roleCode -> !roleRepository.existsByCode(roleCode))
                .forEach(roleCode -> {
                    Role role = new Role();
                    role.setCode(roleCode);
                    role.setName(roleCode.getName());
                    roleRepository.save(role);
//                    log.info(String.format("Created the %s role",roleCode.getName()));
                });
    }
}
