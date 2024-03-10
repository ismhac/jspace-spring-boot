package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.dto.role.RoleDto;
import com.ismhac.jspace.mapper.RoleMapper;
import com.ismhac.jspace.model.Role;
import com.ismhac.jspace.model.enums.RoleCode;
import com.ismhac.jspace.repository.RoleRepository;
import com.ismhac.jspace.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

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
                    log.info(String.format("Created the %s role",roleCode.getName()));
                });
    }
}
