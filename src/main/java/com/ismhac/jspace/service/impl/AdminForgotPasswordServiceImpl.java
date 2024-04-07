package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.repository.AdminForgotPasswordTokenRepository;
import com.ismhac.jspace.service.AdminForgotPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminForgotPasswordServiceImpl implements AdminForgotPasswordService {
    private final AdminForgotPasswordTokenRepository adminForgotPasswordTokenRepository;

}
