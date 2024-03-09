package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.dto.role.RoleDTO;
import com.ismhac.jspace.mapper.RoleMapper;
import com.ismhac.jspace.model.Role;
import com.ismhac.jspace.model.enums.RoleCode;
import com.ismhac.jspace.repository.RoleRepository;
import com.ismhac.jspace.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

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
                    role.setName(roleCode.name());
                    roleRepository.save(role);
                    log.info("Created the " + roleCode.name() + " role");
                });
    }

    @Override
    public List<RoleDTO> getList() {
        List<Role> roleList = roleRepository.findAll();
        List<RoleDTO> roleDTOList = RoleMapper.INSTANCE.toDTOList(roleList);
        return roleDTOList;
    }

//    @Override
//    public List<RoleDTO> getListWhenRegister() {
//        List<Role> roleList = roleRepository.getListRoleIsNotInAdmins(RoleCode.SUPER_ADMIN, RoleCode.ADMIN);
//        List<RoleDTO> roleDTOList = RoleMapper.INSTANCE.toDTOList(roleList);
//        return roleDTOList;
//    }
}
