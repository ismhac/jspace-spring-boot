package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.mapper.UserMapper;
import com.ismhac.jspace.repository.UserRepository;
import com.ismhac.jspace.util.PageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PageUtils pageUtils;
}