//package com.ismhac.jspace.service.impl;
//
//import com.ismhac.jspace.model.Admin;
//import com.ismhac.jspace.repository.AdminRepository;
//import com.ismhac.jspace.service.AdminService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//public class AdminServiceImpl implements AdminService {
//
//    @Value("${init.admin.username}")
//    private String initAdminUsername;
//
//    @Value("${init.admin.password}")
//    private String initAdminPassword;
//
//    private final PasswordEncoder passwordEncoder;
//
//    private final AdminRepository adminRepository;
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public boolean initRootAdmin() {
//        boolean isExisted = adminRepository.existsAdminByUsername(initAdminUsername);
//        if(isExisted) return false;
//        else {
//            String password = passwordEncoder.encode(initAdminPassword);
//            Admin admin = Admin.builder()
//                    .username(initAdminUsername)
//                    .password(password)
//                    .build();
//            adminRepository.save(admin);
//            return true;
//        }
//    }
//}
