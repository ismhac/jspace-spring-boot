package com.ismhac.jspace.service;

import com.ismhac.jspace.dto.common.PageResponse;
import com.ismhac.jspace.dto.user.UserDto;
import com.ismhac.jspace.dto.user.admin.AdminCreateRequest;
import com.ismhac.jspace.dto.user.admin.AdminDto;
import com.ismhac.jspace.model.Admin;
import com.ismhac.jspace.model.enums.RoleCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {
    void initRootAdmin();

    AdminDto create(AdminCreateRequest adminCreateRequest);

    AdminDto getAdminInfoFromToken();

    PageResponse<AdminDto> getPageAdminByTypeFilterByNameAndActivated(String name, Boolean activated, Pageable pageable);

//    PageResponse<>

    PageResponse<UserDto> getPageUserAndFilterByRoleIdNameAndEmailAndActivated(Integer roleId, String name, String email, Boolean activated, Pageable pageable);
}
