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

    @Value("${init.admin.username}")
    private String superAdminUsername;

    @Value("${init.admin.password}")
    private String superAdminPassword;

    private final PasswordEncoder passwordEncoder;

    private final AdminRepository adminRepository;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void initRootAdmin() {
        Role superAdminRole = roleRepository.findRoleByCode(RoleCode.SUPER_ADMIN).orElseGet(()->{
           Role newRole = new Role();

           newRole.setCode(RoleCode.SUPER_ADMIN);
           newRole.setName(RoleCode.SUPER_ADMIN.getName());

           return roleRepository.save(newRole);
        });

        User user = userRepository.findUserByUsername(superAdminUsername).orElseGet(()->{
            User newUser = new User();

            newUser.setUsername(superAdminUsername);
            newUser.setPassword(passwordEncoder.encode(superAdminPassword));
            newUser.setActivated(true);
            newUser.setRole(superAdminRole);

            return userRepository.save(newUser);
        });

        AdminId adminId = AdminId.builder()
                .user(user)
                .build();

        Admin admin = adminRepository.findAdminById(adminId).orElseGet(()->{
           Admin newAdmin = new Admin();

           newAdmin.setId(adminId);
           newAdmin.setType(AdminType.ROOT);

           return adminRepository.save(newAdmin);
        });
    }
}
