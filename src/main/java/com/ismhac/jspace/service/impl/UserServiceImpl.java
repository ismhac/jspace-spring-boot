package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.dto.common.PageResponse;
import com.ismhac.jspace.dto.user.UserDto;
import com.ismhac.jspace.mapper.UserMapper;
import com.ismhac.jspace.model.User;
import com.ismhac.jspace.repository.UserRepository;
import com.ismhac.jspace.service.UserService;
import com.ismhac.jspace.util.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PageUtils pageUtils;

    @Override
    public PageResponse<UserDto> getPageFilterByEmailAndName(Integer roleId, String email, String name, Pageable pageable) {
        Page<User> userPage = userRepository.getPageFilterByEmailAndName(roleId, email, name, pageable);
        return pageUtils.toPageResponse(userMapper.toUserDtoPage(userPage));
    }
}
