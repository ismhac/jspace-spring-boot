package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.dto.common.PageResponse;
import com.ismhac.jspace.dto.user.UserDto;
import com.ismhac.jspace.dto.user.admin.AdminCreateRequest;
import com.ismhac.jspace.dto.user.admin.AdminDto;
import com.ismhac.jspace.exception.BadRequestException;
import com.ismhac.jspace.exception.ErrorCode;
import com.ismhac.jspace.exception.NotFoundException;
import com.ismhac.jspace.mapper.AdminMapper;
import com.ismhac.jspace.mapper.UserMapper;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
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
    private final UserMapper userMapper;

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

        adminRepository.findAdminById(adminId).orElseGet(() -> {
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
        String email = adminCreateRequest.getEmail().trim();
        AdminType adminType = AdminType.BASIC;

        /* */

        /* check exist */
        Optional<Admin> admin = adminRepository.findAdminByAdminTypeAndUsernameAndEmail(adminType,username, email);
        if (admin.isPresent()) {
            throw new BadRequestException(ErrorCode.USER_EXISTED);
        }

        if(adminRepository.findAdminByAdminTypeAndEmail(adminType, email).isPresent()){
            throw new BadRequestException(ErrorCode.USER_EXISTED);
        }

        if(adminRepository.findAdminByAdminTypeAndUsername(adminType, username).isPresent()){
            throw new BadRequestException(ErrorCode.USER_EXISTED);
        }


        Role role = roleRepository.findRoleByCode(RoleCode.ADMIN).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ROLE));

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .role(role)
                .activated(true)
                .build();

        User savedUser = userRepository.save(user);

        AdminId adminId = AdminId.builder()
                .user(savedUser)
                .build();

        Admin newAdmin = Admin.builder()
                .id(adminId)
                .type(adminType)
                .build();

        return AdminMapper.INSTANCE.toAdminDto(adminRepository.save(newAdmin));
    }

    @Override
    public AdminDto getAdminInfoFromToken() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = (String) jwt.getClaims().get("sub");

        Admin admin = adminRepository.findAdminByUsername(username)
                .orElseThrow(()-> new BadRequestException(ErrorCode.INVALID_TOKEN));
        log.info("{}", jwt.getClaims());
         return adminMapper.toAdminDto(admin);
    }

    @Override
    public PageResponse<AdminDto> getPageAdminByTypeFilterByNameAndActivated(String name, Boolean activated, Pageable pageable) {
        Page<Admin> adminPage = adminRepository.getPageAdminByTypeFilterByNameAndActivated(AdminType.BASIC, name, activated, pageable);
        return pageUtils.toPageResponse(adminMapper.toAdminDtoPage(adminPage));
    }

    @Override
    public PageResponse<UserDto> getPageUserAndFilterByRoleIdNameAndEmailAndActivated(Integer roleId, String name, String email, Boolean activated, Pageable pageable) {
        Page<User> userPage = userRepository.getPageUserAndFilterByNameAndEmailAndActivated(roleId, name, email, activated, pageable);
        return pageUtils.toPageResponse(userMapper.toUserDtoPage(userPage));
    }
}
