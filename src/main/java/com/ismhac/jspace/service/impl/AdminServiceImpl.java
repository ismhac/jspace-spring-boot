package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.model.Admin;
import com.ismhac.jspace.model.BaseUser;
import com.ismhac.jspace.model.Role;
import com.ismhac.jspace.model.primaryKey.AdminID;
import com.ismhac.jspace.repository.AdminRepository;
import com.ismhac.jspace.repository.BaseUserRepository;
import com.ismhac.jspace.repository.RoleRepository;
import com.ismhac.jspace.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    @Value("${init.admin.email}")
    private String adminEmail;

    @Value("${init.admin.password}")
    private String adminPassword;

    @Value("${init.role.code.admin}")
    private String adminRoleCode;

    private final PasswordEncoder passwordEncoder;

    private final AdminRepository adminRepository;

    private final BaseUserRepository baseUserRepository;

    private final RoleRepository roleRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void initRootAdmin() {
        Optional<BaseUser> baseUser =  baseUserRepository.findByEmailAndRoleCode(adminEmail, adminRoleCode);
        if(baseUser.isEmpty()){

            Role adminRole =  roleRepository.getRoleByCode(adminRoleCode);

            BaseUser newBaseUser = BaseUser.builder()
                    .email(adminEmail)
                    .password(passwordEncoder.encode(adminPassword))
                    .isActivated(true)
                    .role(adminRole)
                    .build();

            BaseUser saveBaseUser = baseUserRepository.save(newBaseUser);

            AdminID adminID = AdminID.builder()
                    .baseUser(saveBaseUser)
                    .build();

            Admin admin = Admin.builder()
                    .adminID(adminID)
                    .name("jspace")
                    .build();

            adminRepository.save(admin);
        } else {
            AdminID adminID = AdminID.builder()
                    .baseUser(baseUser.get())
                    .build();

            Admin admin = Admin.builder()
                    .adminID(adminID)
                    .name("jspace")
                    .build();

            adminRepository.save(admin);
        }
    }
}
