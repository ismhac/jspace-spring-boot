package com.ismhac.jspace.service;

import com.ismhac.jspace.dto.common.PageResponse;
import com.ismhac.jspace.dto.user.admin.AdminCreateRequest;
import com.ismhac.jspace.dto.user.admin.AdminDto;
import com.ismhac.jspace.model.Admin;
import org.springframework.data.domain.Page;

public interface AdminService {
    void initRootAdmin();

    AdminDto create(AdminCreateRequest adminCreateRequest);

    PageResponse<AdminDto> getPageAdmin(String name, int pageNumber, int pageSize);
}
