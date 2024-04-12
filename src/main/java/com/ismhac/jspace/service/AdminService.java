package com.ismhac.jspace.service;

import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.user.response.UserDto;
import com.ismhac.jspace.dto.user.admin.request.AdminCreateRequest;
import com.ismhac.jspace.dto.user.admin.response.AdminDto;
import org.springframework.data.domain.Pageable;

public interface AdminService {
    void initRootAdmin();

    AdminDto create(AdminCreateRequest adminCreateRequest);

    AdminDto getAdminInfoFromToken();

    PageResponse<AdminDto> getPageAdminByTypeFilterByNameAndActivated(String name, Boolean activated, Pageable pageable);

//    PageResponse<>

    PageResponse<UserDto> getPageUserAndFilterByRoleIdNameAndEmailAndActivated(Integer roleId, String name, String email, Boolean activated, Pageable pageable);
}
