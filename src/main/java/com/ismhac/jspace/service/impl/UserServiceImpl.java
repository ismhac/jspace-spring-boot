package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.dto.user.response.UserDto;
import com.ismhac.jspace.mapper.UserMapper;
import com.ismhac.jspace.repository.UserRepository;
import com.ismhac.jspace.service.UserService;
import com.ismhac.jspace.util.PageUtils;
import com.nimbusds.jwt.JWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PageUtils pageUtils;
}