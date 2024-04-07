package com.ismhac.jspace.controller;

import com.ismhac.jspace.dto.common.ApiResponse;
import com.ismhac.jspace.dto.common.PageResponse;
import com.ismhac.jspace.dto.user.UserDto;
import com.ismhac.jspace.service.UserService;
import com.ismhac.jspace.util.PageUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User")
public class UserController {

    private final PageUtils pageUtils;

    private final UserService userService;

    /* get page, filter by email, name */
    @GetMapping()
    public ApiResponse<PageResponse<UserDto>> getPageFilterByEmailAndName(
            @RequestParam("role_id") Integer roleId,
            @RequestParam("email") String email,
            @RequestParam("name") String name,
            Pageable pageable) {
        Pageable adjustedPageable = pageUtils.adjustPageable(pageable);
        var result = userService.getPageFilterByEmailAndName(roleId, email, name, adjustedPageable);
        return ApiResponse.<PageResponse<UserDto>>builder()
                .result(result)
                .build();
    }
}
