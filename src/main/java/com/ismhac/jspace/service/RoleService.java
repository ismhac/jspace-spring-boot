package com.ismhac.jspace.service;

import com.ismhac.jspace.dto.role.RoleDTO;

import java.util.List;

public interface RoleService {
    void initRoles();

    List<RoleDTO> getList();

//    List<RoleDTO> getListWhenRegister();
}
