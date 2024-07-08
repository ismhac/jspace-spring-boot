package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.config.security.jwt.JwtService;
import com.ismhac.jspace.dto.common.request.SendMailRequest;
import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.dto.companyRequestReview.response.CompanyRequestReviewDto;
import com.ismhac.jspace.dto.product.request.ProductCreateRequest;
import com.ismhac.jspace.dto.product.request.ProductUpdateRequest;
import com.ismhac.jspace.dto.product.response.ProductDto;
import com.ismhac.jspace.dto.purchaseHistory.response.PurchaseHistoryDto;
import com.ismhac.jspace.dto.user.admin.request.AdminCreateRequest;
import com.ismhac.jspace.dto.user.admin.response.AdminDto;
import com.ismhac.jspace.dto.user.request.UpdateActivatedUserRequest;
import com.ismhac.jspace.dto.user.response.UserDto;
import com.ismhac.jspace.event.CreateAdminEvent;
import com.ismhac.jspace.event.RequestCompanyToVerifyForEmployeeEvent;
import com.ismhac.jspace.event.RequestCompanyVerifyEmailEvent;
import com.ismhac.jspace.exception.AppException;
import com.ismhac.jspace.exception.ErrorCode;
import com.ismhac.jspace.exception.NotFoundException;
import com.ismhac.jspace.mapper.*;
import com.ismhac.jspace.model.*;
import com.ismhac.jspace.model.enums.AdminType;
import com.ismhac.jspace.model.enums.RoleCode;
import com.ismhac.jspace.model.primaryKey.AdminId;
import com.ismhac.jspace.repository.*;
import com.ismhac.jspace.service.AdminService;
import com.ismhac.jspace.util.PageUtils;
import com.ismhac.jspace.util.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private final CompanyRepository companyRepository;
    private final ProductRepository productRepository;
    private final CompanyRequestReviewRepository companyRequestReviewRepository;
    private final PageUtils pageUtils;
    private final AdminMapper adminMapper;
    private final UserMapper userMapper;
    private final UserUtils userUtils;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final AdminRequestVerifyEmailRepository adminRequestVerifyEmailRepository;
    private final JwtService jwtService;
    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final CandidateFollowCompanyRepository candidateFollowCompanyRepository;
    private final CompanyVerifyEmailRequestHistoryRepository companyVerifyEmailRequestHistoryRepository;
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private com.ismhac.jspace.util.BeanUtils beanUtils;

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
    @Transactional(rollbackFor = Exception.class)
    public AdminDto create(AdminCreateRequest adminCreateRequest) {
        String username = adminCreateRequest.getUsername().trim();
        String password = adminCreateRequest.getPassword().trim();
        String email = adminCreateRequest.getEmail().trim();
        AdminType adminType = AdminType.BASIC;

        Optional<Admin> admin = adminRepository.findAdminByAdminTypeAndUsernameAndEmail(adminType, username, email);
        if (admin.isPresent()) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        if (adminRepository.findAdminByAdminTypeAndEmail(adminType, email).isPresent()) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        if (adminRepository.findAdminByAdminTypeAndUsername(adminType, username).isPresent()) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        Role role = roleRepository.findRoleByCode(RoleCode.ADMIN).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ROLE));
        User user = User.builder().username(username).password(passwordEncoder.encode(password)).email(email).role(role).activated(true).build();
        User savedUser = userRepository.save(user);
        AdminId adminId = AdminId.builder().user(savedUser).build();
        Admin newAdmin = Admin.builder().id(adminId).type(adminType).build();
        Admin savedAdmin = adminRepository.save(newAdmin);
        LocalDateTime otpCreatedDateTime = LocalDateTime.now().plusMinutes(10);
        String token = jwtService.generateAdminRequestVerifyEmailToken(savedAdmin);
        AdminRequestVerifyEmail adminRequestVerifyEmail = AdminRequestVerifyEmail.builder().admin(savedAdmin).otpCreatedDateTime(otpCreatedDateTime).token(token).build();
        adminRequestVerifyEmailRepository.save(adminRequestVerifyEmail);
        String subject = "Verify Email";
        String body = adminCreateRequest.getReturnUrl().concat(String.format("?token=%s", token));
        adminCreateRequest.setSubject(subject);
        adminCreateRequest.setBody(body);
        CreateAdminEvent createAdminEvent = new CreateAdminEvent(this, adminCreateRequest);
        applicationEventPublisher.publishEvent(createAdminEvent);
        return AdminMapper.instance.toAdminDto(savedAdmin);
    }

    @Override
    public PageResponse<AdminDto> getPageAdminByTypeFilterByNameAndActivated(String name, Boolean activated, Pageable pageable) {
        Page<Admin> adminPage = adminRepository.getPageAdminByTypeFilterByNameAndActivated(AdminType.BASIC, name, activated, pageable);
        return pageUtils.toPageResponse(adminMapper.toAdminDtoPage(adminPage));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public PageResponse<UserDto> getPageUserAndFilterByRoleIdNameAndEmailAndActivated(Integer roleId, String name, String email, Boolean activated, Pageable pageable) {
        User user = userUtils.getUserFromToken();
        Page<User> userPage;
        if (user.getRole().getCode().equals(RoleCode.SUPER_ADMIN)) {
            userPage = userRepository.superAdminGetPageUserAndFilterByNameAndEmailAndActivated(roleId, name, email, activated, pageable);
        } else {
            userPage = userRepository.adminGetPageUserAndFilterByNameAndEmailAndActivated(roleId, name, email, activated, pageable);
        }
        return pageUtils.toPageResponse(userMapper.toUserDtoPage(userPage));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAnyRole({'SUPER_ADMIN', 'ADMIN'})")
    public UserDto updateActivatedUser(UpdateActivatedUserRequest updateActivatedUserRequest) {
        User currentUser = userUtils.getUserFromToken();
        int userId = updateActivatedUserRequest.getUserId();
        Boolean activated = updateActivatedUserRequest.getActivated();
        User updatedUser = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_USER));
        if (currentUser.getRole().getCode().equals(RoleCode.SUPER_ADMIN)) {
            if (updatedUser.getRole().getCode().equals(RoleCode.SUPER_ADMIN)) {
                throw new AppException(ErrorCode.UNAUTHORIZED);
            }
        } else {
            if (updatedUser.getRole().getCode().equals(RoleCode.SUPER_ADMIN)
                    || updatedUser.getRole().getCode().equals(RoleCode.ADMIN)) {
                throw new AppException(ErrorCode.UNAUTHORIZED);
            }
        }
        updatedUser.setActivated(activated);
        return userMapper.toUserDto(userRepository.save(updatedUser));
    }

    @Override
    @PreAuthorize("hasAnyRole({'SUPER_ADMIN', 'ADMIN'})")
    public PageResponse<CompanyRequestReviewDto> getRequestReviewDtoPageResponse(Boolean reviewed, Pageable pageable) {
        return pageUtils.toPageResponse(CompanyRequestReviewMapper.instance.ePageToDtoPage(companyRequestReviewRepository.getPageFilterByReviewed(reviewed, pageable), candidateFollowCompanyRepository));
    }

    @Override
    @PreAuthorize("hasAnyRole({'SUPER_ADMIN', 'ADMIN'})")
    @Transactional(rollbackFor = Exception.class)
    public CompanyRequestReviewDto adminVerifyForCompany(Integer companyId, Boolean reviewed) {
        CompanyRequestReview companyRequestReview = companyRequestReviewRepository.findByCompanyId(companyId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_COMPANY));
        companyRequestReview.setReviewed(true);
        Company company = companyRequestReview.getId().getCompany();
        company.setVerifiedByAdmin(true);
        companyRequestReviewRepository.save(companyRequestReview);
        companyRepository.save(company);
        return CompanyRequestReviewMapper.instance.eToDto(companyRequestReview, candidateFollowCompanyRepository);
    }

    @Override
    @PreAuthorize("hasAnyRole({'SUPER_ADMIN', 'ADMIN'})")
    public PageResponse<CompanyDto> getPageCompanyAndFilter(String name, String address, String email, String phone, Boolean emailVerified, Boolean verifiedByAdmin, Pageable pageable) {
        Page<Company> companyPage = companyRepository.getPageAndFilter(name, address, email, phone, emailVerified, verifiedByAdmin, pageable);
        return pageUtils.toPageResponse(CompanyMapper.instance.ePageToDtoPage(companyPage, candidateFollowCompanyRepository));
    }

    @Override
    @PreAuthorize("hasAnyRole({'SUPER_ADMIN', 'ADMIN'})")
    @Transactional(rollbackFor = Exception.class)
    public CompanyDto updateCompanyActivateStatus(int id, boolean activateStatus) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_COMPANY));
        company.setActivateStatus(activateStatus);
        return CompanyMapper.instance.eToDto(companyRepository.save(company), candidateFollowCompanyRepository);
    }

    @Override
    @PreAuthorize("hasAnyRole({'SUPER_ADMIN', 'ADMIN'})")
    @Transactional(rollbackFor = Exception.class)
    public ProductDto createProduct(ProductCreateRequest request) {
        Product product = ProductMapper.instance.createReqToE(request);
        product.setDeleted(false);
        return ProductMapper.instance.eToDto(productRepository.save(product));
    }

    @Override
    @PreAuthorize("hasAnyRole({'SUPER_ADMIN', 'ADMIN'})")
    @Transactional(rollbackFor = Exception.class)
    public ProductDto updateProduct(int id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_PRODUCT));
        BeanUtils.copyProperties(request, product, beanUtils.getNullPropertyNames(request));
        return ProductMapper.instance.eToDto(productRepository.save(product));
    }

    @Override
    public PageResponse<PurchaseHistoryDto> getPagePurchaseHistory(String companyName, String productName, Pageable pageable) {
        return pageUtils.toPageResponse(PurchaseHistoryMapper.instance.ePageToDtoPage(purchaseHistoryRepository.findAndFilter(companyName, productName, pageable), candidateFollowCompanyRepository));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteProduct(int productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_PRODUCT));
        product.setDeleted(true);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean requestCompanyVerifyInfo(int companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow(()-> new AppException(ErrorCode.NOT_FOUND_COMPANY));
        sendMailRequestCompanyVerifyInformation(company);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    protected void sendMailRequestCompanyVerifyInformation(Company company) {

        String token = String.valueOf(UUID.randomUUID());
        LocalDateTime expiryTime = LocalDateTime.now().plusHours(24);

        String template = readEmailTemplate("classpath:templates/VerifyCompanyInformation.txt");
        String bodyMailRequestCompanyVerifyEmail = template
                .replace("#{companyName}", company.getName())
                .replace("#{address}", StringUtils.isBlank(company.getAddress()) ? "" : company.getAddress())
                .replace("#{email}", StringUtils.isBlank(company.getEmail()) ? "" : company.getEmail())
                .replace("#{phone}", StringUtils.isBlank(company.getPhone()) ? "" : company.getPhone())
                .replace("#{companySize}", StringUtils.isBlank(company.getCompanySize()) ? "" : company.getCompanySize())
                .replace("#{expiryTime}", expiryTime.toString())
                .replace("#{token}", token);

        SendMailRequest sendMailRequestCompanyVerifyEmail = SendMailRequest.builder()
                .email(company.getEmail())
                .body(bodyMailRequestCompanyVerifyEmail)
                .subject("Verification of Company Information")
                .build();

        RequestCompanyVerifyEmailEvent requestCompanyVerifyEmailEvent = new RequestCompanyVerifyEmailEvent(
                this, sendMailRequestCompanyVerifyEmail);

        CompanyVerifyEmailRequestHistory companyVerifyEmailRequestHistory = CompanyVerifyEmailRequestHistory.builder()
                .company(company)
                .token(token)
                .expiryTime(expiryTime)
                .build();

        companyVerifyEmailRequestHistoryRepository.save(companyVerifyEmailRequestHistory);

        applicationEventPublisher.publishEvent(requestCompanyVerifyEmailEvent);
    }

    private String readEmailTemplate(String filePath) {
        Resource resource = resourceLoader.getResource(filePath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }
}
