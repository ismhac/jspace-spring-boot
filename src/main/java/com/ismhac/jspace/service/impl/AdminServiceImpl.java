package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.dto.common.PageResponse;
import com.ismhac.jspace.dto.user.admin.AdminCreateRequest;
import com.ismhac.jspace.dto.user.admin.AdminDto;
import com.ismhac.jspace.exception.BadRequestException;
import com.ismhac.jspace.exception.ErrorCode;
import com.ismhac.jspace.mapper.AdminMapper;
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
import com.ismhac.jspace.util.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    private final PageUtils pageUtils;

    private final AdminMapper adminMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void initRootAdmin() {
        Role superAdminRole = roleRepository.findRoleByCode(RoleCode.SUPER_ADMIN).orElseGet(() -> {
            Role newRole = new Role();

            newRole.setCode(RoleCode.SUPER_ADMIN);
            newRole.setName(RoleCode.SUPER_ADMIN.getName());

            return roleRepository.save(newRole);
        });

        User user = userRepository.findUserByUsername(superAdminUsername).orElseGet(() -> {
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

        Admin admin = adminRepository.findAdminById(adminId).orElseGet(() -> {
            Admin newAdmin = new Admin();

            newAdmin.setId(adminId);
            newAdmin.setType(AdminType.ROOT);

            return adminRepository.save(newAdmin);
        });
    }

    @Override
    public AdminDto create(AdminCreateRequest adminCreateRequest) {

        /* prepare data */
        String username = adminCreateRequest.getUsername().trim();
        String password = adminCreateRequest.getPassword().trim();
        AdminType adminType = AdminType.BASIC;

        /* */

        /* check exist */
        Optional<Admin> admin = adminRepository.findAdminByUsername(username);
        if (admin.isPresent()) {
            throw new BadRequestException(ErrorCode.USER_EXISTED);
        }

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();

        AdminId adminId = AdminId.builder()
                .user(user)
                .build();

        Admin newAdmin = Admin.builder()
                .id(adminId)
                .type(adminType)
                .build();

        return AdminMapper.INSTANCE.toAdminDto(adminRepository.save(newAdmin));
    }

    @Override
    public PageResponse<AdminDto> getPageAdmin(String name, int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(Math.max(pageNumber - 1, 0), (pageSize > 0 ? pageSize : 10));
        Page<Admin> adminPage = adminRepository.getPageAdminByType(AdminType.BASIC, name, pageable);

        return pageUtils.toPageResponse(adminMapper.toAdminDtoPage(adminPage));
    }
}
