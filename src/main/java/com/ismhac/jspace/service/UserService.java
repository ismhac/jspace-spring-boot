package com.ismhac.jspace.service;

import com.ismhac.jspace.dto.common.PageResponse;
import com.ismhac.jspace.dto.user.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    /* get page and filter by role, email, name */
//    PageResponse<UserDto> getPageFilterByEmailAndName(Integer roleId, String email, String name, Pageable pageable);
}
