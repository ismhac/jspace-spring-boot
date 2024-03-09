package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.model.Admin;
import com.ismhac.jspace.model.Role;
import com.ismhac.jspace.model.User;
import com.ismhac.jspace.model.enums.AdminType;
import com.ismhac.jspace.model.enums.RoleCode;
import com.ismhac.jspace.model.primaryKey.AdminId;
import com.ismhac.jspace.repository.AdminRepository;
import com.ismhac.jspace.repository.RoleRepository;
import com.ismhac.jspace.repository.UserRepository;
import com.ismhac.jspace.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    @Value("${init.admin.email}")
    private String superAdminEmail;

    @Value("${init.admin.password}")
    private String superAdminPassword;

    private final PasswordEncoder passwordEncoder;

    private final AdminRepository adminRepository;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void initRootAdmin() {
        Admin admin = adminRepository
                .findAdminByEmailAndAdminTypeAndRoleCode(superAdminEmail, AdminType.super_admin, RoleCode.super_admin)
                .orElseGet(() -> {
                    Role role = roleRepository.getRoleByCode(RoleCode.super_admin).get();

                    User user = new User();
                    user.setEmail(superAdminEmail);
                    user.setPassword(passwordEncoder.encode(superAdminPassword));
                    user.setActivated(true);
                    user.setRole(role);

                    User savedUser = userRepository.save(user);

                    AdminId adminId = AdminId.builder()
                            .user(savedUser)
                            .build();

                    Admin newAdmin = new Admin();
                    newAdmin.setId(adminId);
                    newAdmin.setType(AdminType.super_admin);

                    return adminRepository.save(newAdmin);
                });
    }
}
