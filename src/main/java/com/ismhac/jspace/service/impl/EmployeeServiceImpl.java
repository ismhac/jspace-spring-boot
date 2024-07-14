package com.ismhac.jspace.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ismhac.jspace.dto.candidatePost.request.ApplyStatusUpdateRequest;
import com.ismhac.jspace.dto.candidatePost.response.CandidatePostDto;
import com.ismhac.jspace.dto.cart.request.CartCreateRequest;
import com.ismhac.jspace.dto.cart.response.CartDto;
import com.ismhac.jspace.dto.common.request.SendMailRequest;
import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.company.request.CompanyCreateRequest;
import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.dto.other.ApplyStatusDto;
import com.ismhac.jspace.dto.post.request.PostCreateRequest;
import com.ismhac.jspace.dto.post.request.PostUpdateRequest;
import com.ismhac.jspace.dto.post.response.PostDto;
import com.ismhac.jspace.dto.purchaseHistory.response.PurchaseHistoryDto;
import com.ismhac.jspace.dto.purchasedProduct.response.PurchasedProductDto;
import com.ismhac.jspace.dto.user.candidate.response.CandidateDto;
import com.ismhac.jspace.dto.user.employee.request.EmployeeUpdateRequest;
import com.ismhac.jspace.dto.user.employee.response.EmployeeDto;
import com.ismhac.jspace.dto.user.response.UserDto;
import com.ismhac.jspace.event.RequestCompanyToVerifyForEmployeeEvent;
import com.ismhac.jspace.event.RequestCompanyVerifyEmailEvent;
import com.ismhac.jspace.exception.AppException;
import com.ismhac.jspace.exception.ErrorCode;
import com.ismhac.jspace.mapper.*;
import com.ismhac.jspace.model.*;
import com.ismhac.jspace.model.enums.*;
import com.ismhac.jspace.model.primaryKey.CompanyRequestReviewId;
import com.ismhac.jspace.model.primaryKey.PostSkillId;
import com.ismhac.jspace.model.primaryKey.UserNotificationId;
import com.ismhac.jspace.repository.*;
import com.ismhac.jspace.service.EmployeeService;
import com.ismhac.jspace.util.BeanUtils;
import com.ismhac.jspace.util.PageUtils;
import com.ismhac.jspace.util.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final PageUtils pageUtils;
    private final UserUtils userUtils;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final CompanyRequestReviewRepository companyRequestReviewRepository;
    private final EmployeeHistoryRequestCompanyVerifyRepository employeeHistoryRequestCompanyVerifyRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final CompanyVerifyEmailRequestHistoryRepository companyVerifyEmailRequestHistoryRepository;
    private final Cloudinary cloudinary;
    private final PostRepository postRepository;
    private final PostSkillRepository postSkillRepository;
    private final SkillRepository skillRepository;
    private final PurchasedProductRepository purchasedProductRepository;
    private final PostHistoryRepository postHistoryRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final CandidatePostRepository candidatePostRepository;
    private final CandidateFollowCompanyRepository candidateFollowCompanyRepository;
    private final UserNotificationRepository userNotificationRepository;
    private final NotificationRepository notificationRepository;

    @Autowired
    private BeanUtils beanUtils;
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private CandidateRepository candidateRepository;

    @Override
    public PageResponse<EmployeeDto> getPageByCompanyIdFilterByEmailAndName(int companyId, String email, String name, Pageable pageable) {
        Page<Employee> employeePage = employeeRepository.getPageByCompanyIdFilterByEmailAndName(companyId, email, name, pageable);
        return pageUtils.toPageResponse(employeeMapper.toEmployeeDtoPage(employeePage));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasRole('EMPLOYEE')")
    public UserDto update(int id, EmployeeUpdateRequest request) {

        User tokenUser = userUtils.getUserFromToken();

        if (tokenUser.getId() != id) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        Employee employee = employeeRepository.findByUserId(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        User user = employee.getId().getUser();

        org.springframework.beans.BeanUtils.copyProperties(request, user, beanUtils.getNullPropertyNames(request));

        return userMapper.toUserDto(userRepository.save(user));
    }

    @Override
    @PreAuthorize("hasRole('EMPLOYEE')")
    public PageResponse<CompanyDto> getPageCompany(String name, String address, Pageable pageable) {
        return pageUtils.toPageResponse(CompanyMapper.instance.ePageToDtoPage(companyRepository.getPage(name, address, pageable), candidateFollowCompanyRepository));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompanyDto createCompany(CompanyCreateRequest request) {

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Employee employee = employeeRepository.findByEmail(String.valueOf(jwt.getClaims().get("email")))
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));

        String email = request.getEmail().trim();
        String phone = request.getPhone().trim();

        Optional<Company> company = companyRepository.findByEmailOrPhone(email, phone);

        if (company.isPresent()) {
            throw new AppException(ErrorCode.COMPANY_EXISTED);
        }

        Company newCompany = Company.builder()
                .name(request.getName())
                .address(request.getAddress())
                .email(email)
                .phone(phone)
                .companySize(request.getCompanySize())
                .description(request.getDescription())
                .companyLink(request.getCompanyLink())
                .background(request.getBackground())
                .backgroundId(request.getBackgroundId())
                .logo(request.getLogo())
                .logoId(request.getLogoId())
                .build();

        Company savedCompany = companyRepository.save(newCompany);

        employee.setCompany(savedCompany);
        employeeRepository.save(employee);

        CompanyRequestReview companyRequestReview = _createCompanyRequestAdminReview(savedCompany);

        List<User> users = userRepository.findUserInListRole(List.of(RoleCode.SUPER_ADMIN, RoleCode.ADMIN));
        Notification notification = Notification.builder()
                .title(NotificationTitle.NOTIFICATION_ADMIN_NEW_COMPANY.getTitle())
                .type(NotificationType.NEW_COMPANY)
                .content(String.format("Công ty %s được đăng ký trên hệ thống lúc %s", savedCompany.getName(), Instant.now().toString()))
                .build();

        Notification savedNotification = notificationRepository.save(notification);
        List<UserNotification> userNotifications = users.stream().map(user -> UserNotification.builder()
                .read(false)
                .id(UserNotificationId.builder()
                        .user(user)
                        .notification(savedNotification)
                        .build())
                .build()).toList();

        userNotificationRepository.saveAll(userNotifications);

        if (Objects.nonNull(companyRequestReview)) {
            _sendMailWhenCreateCompany(savedCompany, employee);
        }

        return CompanyMapper.instance.eToDto(savedCompany, candidateFollowCompanyRepository);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasRole('EMPLOYEE')")
    public String employeePickCompany(Integer companyId) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Employee employee = employeeRepository.findByEmail((String) jwt.getClaims().get("email"))
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));

        Company company = companyRepository.findById(companyId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_COMPANY));

        employee.setCompany(company);

        employeeRepository.save(employee);

        _sendMailWhenPickCompany(company, employee);

        return "Success";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EmployeeDto updateBackground(int id, MultipartFile background) {
        Employee employee = employeeRepository.findByUserId(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (background == null || background.isEmpty()) {
            throw new IllegalArgumentException("background must not be empty");
        }

        Map<String, Object> options = new HashMap<>();

        Map uploadResult;

        try {
            uploadResult = cloudinary.uploader().upload(background.getBytes(), options);
            cloudinary.uploader().upload(background.getBytes(), options);
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }

        String backgroundPath = (String) uploadResult.get("secure_url");
        String backgroundId = (String) uploadResult.get("public_id");

        employee.getId().getUser().setBackground(backgroundPath);
        employee.getId().getUser().setBackgroundId(backgroundId);
        return EmployeeMapper.instance.toEmployeeDto(employeeRepository.save(employee));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EmployeeDto updateAvatar(int id, MultipartFile avatar) {
        Employee employee = employeeRepository.findByUserId(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (avatar == null || avatar.isEmpty()) {
            throw new IllegalArgumentException("background must not be empty");
        }

        Map<String, Object> options = new HashMap<>();

        Map uploadResult;

        try {
            uploadResult = cloudinary.uploader().upload(avatar.getBytes(), options);
            cloudinary.uploader().upload(avatar.getBytes(), options);
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }

        String avatarPath = (String) uploadResult.get("secure_url");
        String avatarId = (String) uploadResult.get("public_id");

        employee.getId().getUser().setPicture(avatarPath);
        employee.getId().getUser().setPictureId(avatarId);
        return EmployeeMapper.instance.toEmployeeDto(employeeRepository.save(employee));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> deleteBackground(int id, String backgroundId) {
        try {
            Employee employee = employeeRepository.findByUserId(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

            if (!backgroundId.equals(employee.getId().getUser().getBackgroundId()) || backgroundId.isEmpty()) {
                throw new AppException(ErrorCode.INVALID_FILE_ID);
            }

            Map deleteResult = cloudinary.uploader().destroy(backgroundId, ObjectUtils.emptyMap());

            if (deleteResult.get("result").toString().equals("ok")) {
                employee.getId().getUser().setBackgroundId(null);
                employee.getId().getUser().setBackground(null);
                return new HashMap<>() {{
                    put("status", deleteResult);
                    put("user", UserMapper.instance.toUserDto(employeeRepository.save(employee).getId().getUser()));
                }};
            } else {
                return new HashMap<>() {{
                    put("status", deleteResult);
                    put("user", UserMapper.instance.toUserDto(employeeRepository.save(employee).getId().getUser()));
                }};
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(ErrorCode.DELETE_FILE_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> deleteAvatar(int id, String avatarId) {
        try {
            Employee employee = employeeRepository.findByUserId(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

            if (!avatarId.equals(employee.getId().getUser().getPictureId()) || avatarId.isEmpty()) {
                throw new AppException(ErrorCode.INVALID_FILE_ID);
            }

            Map deleteResult = cloudinary.uploader().destroy(avatarId, ObjectUtils.emptyMap());

            if (deleteResult.get("result").toString().equals("ok")) {
                employee.getId().getUser().setPictureId(null);
                employee.getId().getUser().setPicture(null);
                return new HashMap<>() {{
                    put("status", deleteResult);
                    put("user", UserMapper.instance.toUserDto(employeeRepository.save(employee).getId().getUser()));
                }};
            } else {
                return new HashMap<>() {{
                    put("status", deleteResult);
                    put("user", UserMapper.instance.toUserDto(employeeRepository.save(employee).getId().getUser()));
                }};
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(ErrorCode.DELETE_FILE_FAIL);
        }
    }

    @Override
    public List<Map<String, Object>> getListPurchasedByCompanyId(int companyId, String durationFilter) {
        LocalDate now = LocalDate.now();
        List<PurchasedProduct> purchasedProducts = purchasedProductRepository.getListByCompanyId(companyId, now);
        List<Map<String, Object>> results = purchasedProducts.stream().map(item -> {
            Map<String, Object> map = new HashMap<>();
            int remainingDate = (int) ChronoUnit.DAYS.between(now, (PurchasedProductMapper.instance.eToDto(item, candidateFollowCompanyRepository)).getExpiryDate());
            map.put("purchasedProduct", PurchasedProductMapper.instance.eToDto(item, candidateFollowCompanyRepository));
            map.put("remainingDate", remainingDate);
            return map;
        }).toList();

        if (durationFilter.equalsIgnoreCase("expired")) {
            return results.stream().filter(item -> ((int) item.get("remainingDate") == 0)).toList();
        } else if (durationFilter.equalsIgnoreCase("unexpired")) {
            return results.stream().filter(item -> ((int) item.get("remainingDate") > 0)).toList();
        } else {
            return results;
        }
    }

    @Override
    public PageResponse<Map<String, Object>> getPagePosted(int companyId, String title, PostStatus postStatus, String duration, Pageable pageable) {
        Page<Post> posts = postRepository.getPageByCompanyId(companyId, title, postStatus == null ? null : postStatus.getStatus(), duration, LocalDate.now(), pageable);

        List<Map<String, Object>> customContent = new ArrayList<>();
        for (Post post : posts.getContent()) {
            Map<String, Object> map = new HashMap<>();
            map.put("post", PostMapper.instance.eToDto(post, postSkillRepository, candidateFollowCompanyRepository));
            map.put("appliedCandidate", candidatePostRepository.getListCandidateByPostId(post.getId()).size());
            customContent.add(map);
        }

        return new PageResponse<>(posts.getNumber(), posts.getSize(), posts.getTotalElements(), posts.getTotalPages(), posts.getNumberOfElements(), customContent);
//        return pageUtils.toPageResponse(PostMapper.instance.ePageToDtoPage(posts, postSkillRepository, candidateFollowCompanyRepository));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CartDto addProductToCart(CartCreateRequest request) {
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_COMPANY));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_PRODUCT));

        Optional<Cart> cart = cartRepository.findByCompanyIdAndProductId(request.getCompanyId(), request.getProductId());
        if (cart.isEmpty()) {
            Cart newCart = Cart.builder()
                    .company(company)
                    .product(product)
                    .quantity(request.getQuantity())
                    .build();
            return CartMapper.instance.eToDto(cartRepository.save(newCart), candidateFollowCompanyRepository);
        } else {
            cart.get().setQuantity(cart.get().getQuantity() + request.getQuantity());
            return CartMapper.instance.eToDto(cartRepository.save(cart.get()), candidateFollowCompanyRepository);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CartDto updateCart(int cartId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_CART));
        cart.setQuantity(quantity);
        return CartMapper.instance.eToDto(cartRepository.save(cart), candidateFollowCompanyRepository);
    }

    @Override
    public PageResponse<CartDto> getCarts(int companyId, Pageable pageable) {
        return pageUtils.toPageResponse(CartMapper.instance.ePageToDtoPage(cartRepository.getPageByCompanyId(companyId, pageable), candidateFollowCompanyRepository));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteCart(int cartId) {
        if (cartRepository.deleteById(cartId) == 0) {
            return "Delete fail";
        }
        return "Delete success";
    }

    @Override
    public PageResponse<PurchaseHistoryDto> getPageAndFilterByProductName(int companyId, String productName, Pageable pageable) {
        return pageUtils.toPageResponse(PurchaseHistoryMapper.instance.ePageToDtoPage(purchaseHistoryRepository.findByCompanyIdAndFilterByProductName(companyId, productName, pageable), candidateFollowCompanyRepository));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PostDto updatePost(int postId, PostUpdateRequest request) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_POST));
        post.setTitle(request.getTitle());
        post.setJobType(request.getJobType());
        post.setLocation(request.getLocation());
        post.setRank(request.getRank());
        post.setDescription(request.getDescription());
        post.setMinPay(request.getMinPay());
        post.setMaxPay(request.getMaxPay());
        post.setExperience(request.getExperience());
        post.setQuantity(request.getQuantity());
        post.setGender(request.getGender());

        Post savedPost = postRepository.save(post);

        postSkillRepository.deleteByPostId(postId);

        List<Skill> skillList = skillRepository.findAllByIdList(request.getSkillIdList());

        if (skillList.size() != request.getSkillIdList().size()) {
            throw new AppException(ErrorCode.NOT_FOUND_SKILL);
        }

        List<Skill> newSkills = new ArrayList<>();

        for (String newSkillName : request.getNewSkills()) {
            Skill newSkill = Skill.builder()
                    .name(newSkillName)
                    .build();
            newSkills.add(newSkill);
        }

        List<Skill> savedNewSkills = skillRepository.saveAll(newSkills);

        List<PostSkill> postSkills = new ArrayList<>();

        for (Skill skill : skillList) {
            PostSkillId id = PostSkillId.builder().post(savedPost).skill(skill).build();
            PostSkill newPostSkill = PostSkill.builder().id(id).build();
            postSkills.add(newPostSkill);
        }
        for (Skill skill : savedNewSkills) {
            PostSkillId id = PostSkillId.builder().post(savedPost).skill(skill).build();
            PostSkill newPostSkill = PostSkill.builder().id(id).build();
            postSkills.add(newPostSkill);
        }

        postSkillRepository.saveAll(postSkills);

        return PostMapper.instance.eToDto(savedPost, postSkillRepository, candidateFollowCompanyRepository);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PostDto updatePostStatus(int postId, PostStatus postStatus) {
        var post = postRepository.findById(postId);
        if (post.isEmpty()) throw new AppException(ErrorCode.NOT_FOUND_POST);
        post.get().setPostStatus(postStatus);
        return PostMapper.instance.eToDto(postRepository.save(post.get()), postSkillRepository, candidateFollowCompanyRepository);
    }

    @Override
    public Object updateAppliedStatus(ApplyStatusUpdateRequest request) {
        var candidatePost = candidatePostRepository.findByCandidateIdAndPostId(request.getCandidateId(), request.getPostId());
        if (candidatePost.isEmpty()) throw new AppException(ErrorCode.NOT_FOUND_CANDIDATE_POST_APPLIED);

        candidatePost.get().setApplyStatus(request.getApplyStatus());
        candidatePost.get().setNote(request.getNotification());
        candidatePostRepository.save(candidatePost.get());

        Notification notification = Notification.builder()
                .title(NotificationTitle.NOTIFICATION_CANDIDATE_UPDATE_APPLY_STATUS.getTitle())
                .type(NotificationType.EMPLOYEE_UPDATE_STATUS_APPLIED)
                .content(request.getNotification())
                .build();

        Notification savedNotification = notificationRepository.save(notification);

        UserNotification userNotification = UserNotification.builder()
                .id(UserNotificationId.builder()
                        .notification(savedNotification)
                        .user(candidatePost.get().getId().getCandidate().getId().getUser())
                        .build())
                .read(false)
                .build();

        userNotificationRepository.save(userNotification);

        return new HashMap<>() {{
            put("notification", request.getNotification());
            put("applyStatus", ApplyStatusDto.builder()
                    .code(request.getApplyStatus().name())
                    .value(request.getApplyStatus().getStatus())
                    .build());
        }};
    }

    @Override
    public PageResponse<PurchasedProductDto> getPagePurchasedProduct(int companyId, String productName, Pageable pageable) {
        Page<PurchasedProduct> purchasedProducts = purchasedProductRepository.getPageByCompanyId(companyId, productName, pageUtils.adjustPageable(pageable));
        return pageUtils.toPageResponse(PurchasedProductMapper.instance.ePageToDtoPage(purchasedProducts, candidateFollowCompanyRepository));
    }

    @Override
    public PurchasedProductDto getPurchasedProductById(int companyId, int purchasedProductId) {
        PurchasedProduct purchasedProduct = purchasedProductRepository.findByIdAndCompanyId(purchasedProductId, companyId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_PURCHASED_PRODUCT));
        return PurchasedProductMapper.instance.eToDto(purchasedProduct, candidateFollowCompanyRepository);
    }

    @Override
    public PageResponse<CandidatePostDto> getPageCandidateAppliedPost(int companyId, Pageable pageable) {
        return pageUtils.toPageResponse(CandidatePostMapper.instance.ePageToDtoPage(candidatePostRepository.getPageCandidateAppliedPost(companyId, pageUtils.adjustPageable(pageable)), postSkillRepository, candidateFollowCompanyRepository));
    }

    @Override
    public PageResponse<CandidatePostDto> getPageCandidateAppliedByPostId(int postId, String candidateName, String candidateEmail, String candidatePhoneNumber, String postStatus, String applyStatus,Pageable pageable) {
        PostStatus postStatusFilter = null;
        if (StringUtils.isNotBlank(postStatus)) postStatusFilter = PostStatus.resolve(postStatus);
        ApplyStatus applyStatusFilter = null;
        if(StringUtils.isNotBlank(applyStatus)) applyStatusFilter = ApplyStatus.resolve(applyStatus);

        return pageUtils.toPageResponse(CandidatePostMapper.instance.ePageToDtoPage(candidatePostRepository.getPageCandidateAppliedByPostId(postId, candidateName, candidateEmail, candidatePhoneNumber, postStatusFilter, applyStatusFilter,pageUtils.adjustPageable(pageable)), postSkillRepository, candidateFollowCompanyRepository));
    }

    @Override
    public PageResponse<UserDto> getPageFollowedCandidate(int companyId, String name, String email, String phoneNumber,Pageable pageable) {
        var company = companyRepository.findById(companyId);
        if (company.isEmpty()) throw new AppException(ErrorCode.NOT_FOUND_COMPANY);
        Page<User> users = candidateFollowCompanyRepository.getPageUserFollowedCompanyByCompanyId(companyId, name,email, phoneNumber,pageUtils.adjustPageable(pageable));
        return pageUtils.toPageResponse(UserMapper.instance.toUserDtoPage(users));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deletePost(int postId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new AppException(ErrorCode.NOT_FOUND_POST));
        if(post.getDeleted()) throw new AppException(ErrorCode.NOT_FOUND_POST);
        post.setDeleted(true);
        return true;
    }

    @Override
    public PageResponse<CandidateDto> searchCandidate(String name, String email, String phoneNumber, Pageable pageable) {
        return pageUtils.toPageResponse(CandidateMapper.instance.ePageToDtoPage(candidateRepository.recruiterSearchCandidate(name, email, phoneNumber, pageUtils.adjustPageable(pageable))));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean requestCompanyVerifyEmployeeInformation(int companyId, int employeeId) {
        Employee employee = employeeRepository.findByUserId(employeeId).orElseThrow(()-> new AppException(ErrorCode.NOT_FOUND_USER));
        Company company = companyRepository.findById(companyId).orElseThrow(()-> new AppException(ErrorCode.NOT_FOUND_COMPANY));
        _sendMailWhenPickCompany(company, employee);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PostDto createPost(PostCreateRequest req) {
        Company company = companyRepository.findById(req.getCompanyId()).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_COMPANY));

        if (req.isUseTrialPost()) handleTrialPost(company);

        List<Skill> skillList = validateAndFetchSkills(req.getSkillIdList());
        List<Skill> newSkills = createNewSkills(req.getNewSkills());
        Post post = createPostEntity(req, company);
        Post savedPost = postRepository.save(post);

        createAndSavePostHistory(savedPost, company);
        createAndSavePostSkills(savedPost, skillList, newSkills);

        return PostMapper.instance.eToDto(savedPost, postSkillRepository, candidateFollowCompanyRepository);
    }

    private void handleTrialPost(Company company) {
        if (company.getTrialPost() < 1) {
            throw new AppException(ErrorCode.TRIAL_POST_EXPIRED);
        }
        company.setTrialPost(company.getTrialPost() - 1);
    }

    private PurchasedProduct handlePurchasedProduct(PostCreateRequest req) {
        PurchasedProduct purchasedProduct = purchasedProductRepository.findById(req.getPurchasedProductId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_PRODUCT));

        if (purchasedProduct.getCompany().getId() != req.getCompanyId() ||
            purchasedProduct.getProductNumberOfPost() < 1) {
            throw new AppException(ErrorCode.INVALID_PURCHASED_PRODUCT);
        }
        if (LocalDate.now().isAfter(purchasedProduct.getExpiryDate())) {
            throw new AppException(ErrorCode.PURCHASED_PRODUCT_EXPIRE);
        }

        purchasedProduct.setProductNumberOfPost(purchasedProduct.getProductNumberOfPost() - 1);
        purchasedProductRepository.save(purchasedProduct);

        return purchasedProduct;
    }

    private List<Skill> validateAndFetchSkills(List<Integer> skillIdList) {
        List<Skill> skillList = skillRepository.findAllByIdList(skillIdList);
        if (skillList.size() != skillIdList.size()) {
            throw new AppException(ErrorCode.NOT_FOUND_SKILL);
        }
        return skillList;
    }

    private List<Skill> createNewSkills(List<String> newSkillNames) {
        List<Skill> newSkills = new ArrayList<>();
        for (String newSkillName : newSkillNames) {
            Skill newSkill = Skill.builder().name(newSkillName).build();
            newSkills.add(newSkill);
        }
        return skillRepository.saveAll(newSkills);
    }

    private Post createPostEntity(PostCreateRequest req, Company company) {
        LocalDate closeDate;
        if (req.isUseTrialPost()) {
            closeDate = LocalDate.now().plusDays(30);
        } else {
            PurchasedProduct purchasedProduct = handlePurchasedProduct(req);
            closeDate = LocalDate.now().plusDays(purchasedProduct.getProductPostDuration());
        }

        return Post.builder()
                .company(company)
                .title(req.getTitle())
                .jobType(req.getJobType())
                .location(req.getLocation())
                .rank(req.getRank())
                .description(req.getDescription())
                .minPay(req.getMinPay())
                .maxPay(req.getMaxPay())
                .experience(req.getExperience())
                .quantity(req.getQuantity())
                .gender(req.getGender())
                .openDate(LocalDate.now())
                .closeDate(closeDate)
                .build();
    }

    private void createAndSavePostHistory(Post savedPost, Company company) {
        PostHistory postHistory = PostHistory.builder()
                .post(savedPost)
                .company(company)
                .build();
        postHistoryRepository.save(postHistory);
    }

    private void createAndSavePostSkills(Post savedPost, List<Skill> skillList, List<Skill> newSkills) {
        List<PostSkill> postSkills = new ArrayList<>();
        skillList.forEach(skill -> postSkills.add(createPostSkill(savedPost, skill)));
        newSkills.forEach(skill -> postSkills.add(createPostSkill(savedPost, skill)));
        postSkillRepository.saveAll(postSkills);
    }

    private PostSkill createPostSkill(Post post, Skill skill) {
        PostSkillId id = PostSkillId.builder().post(post).skill(skill).build();
        return PostSkill.builder().id(id).build();
    }


    @Transactional(rollbackFor = Exception.class)
    protected CompanyRequestReview _createCompanyRequestAdminReview(Company company) {
        CompanyRequestReviewId id = CompanyRequestReviewId.builder()
                .company(company)
                .build();

        CompanyRequestReview companyRequestReview = CompanyRequestReview.builder()
                .id(id)
                .requestDate(LocalDate.now())
                .build();

        return companyRequestReviewRepository.save(companyRequestReview);
    }

    @Transactional(rollbackFor = Exception.class)
    protected void _sendMailWhenCreateCompany(Company company, Employee employee) {

        String token = String.valueOf(UUID.randomUUID());
        LocalDateTime expiryTime = LocalDateTime.now().plusHours(24);

        String template = readEmailTemplate("classpath:templates/VerifyCompanyInformation.txt");
        String bodyMailRequestCompanyVerifyEmail = template
                .replace("#{date}", LocalDate.now().toString())
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

        String templateV2 = readEmailTemplate("classpath:templates/VerifyEmployeeInformation.txt");
        String bodyMailRequestCompanyToVerifyForEmployee = templateV2
                .replace("#{companyName}", company.getName())
                .replace("#{employeeName}", StringUtils.isBlank(employee.getId().getUser().getName()) ? "" : employee.getId().getUser().getName())
                .replace("#{employeeEmail}", StringUtils.isBlank(employee.getId().getUser().getEmail()) ? "" : employee.getId().getUser().getEmail())
                .replace("#{employeePhone}", StringUtils.isBlank(employee.getId().getUser().getPhone()) ? "" : employee.getId().getUser().getPhone())
                .replace("#{expiryTime}", expiryTime.toString())
                .replace("#{token}", token);

        SendMailRequest sendMailRequestCompanyToVerifyForEmployee = SendMailRequest.builder()
                .email(company.getEmail())
                .body(bodyMailRequestCompanyToVerifyForEmployee)
                .subject("Verification of Employee Information")
                .build();

        RequestCompanyToVerifyForEmployeeEvent requestCompanyToVerifyForEmployeeEvent = new RequestCompanyToVerifyForEmployeeEvent(
                this, sendMailRequestCompanyToVerifyForEmployee);


        CompanyVerifyEmailRequestHistory companyVerifyEmailRequestHistory = CompanyVerifyEmailRequestHistory.builder()
                .company(company)
                .token(token)
                .expiryTime(expiryTime)
                .build();


        EmployeeHistoryRequestCompanyVerify employeeHistoryRequestCompanyVerify = EmployeeHistoryRequestCompanyVerify.builder()
                .employee(employee)
                .token(token)
                .expiryTime(expiryTime)
                .build();

        companyVerifyEmailRequestHistoryRepository.save(companyVerifyEmailRequestHistory);
        employeeHistoryRequestCompanyVerifyRepository.save(employeeHistoryRequestCompanyVerify);


        applicationEventPublisher.publishEvent(requestCompanyVerifyEmailEvent);
        applicationEventPublisher.publishEvent(requestCompanyToVerifyForEmployeeEvent);
    }

    @Transactional(rollbackFor = Exception.class)
    protected void _sendMailWhenPickCompany(Company company, Employee employee) {

        String token = String.valueOf(UUID.randomUUID());
        LocalDateTime expiryTime = LocalDateTime.now().plusHours(24);

        String templateV2 = readEmailTemplate("classpath:templates/VerifyEmployeeInformation.txt");
        String bodyMailRequestCompanyToVerifyForEmployee = templateV2
                .replace("#{companyName}", company.getName())
                .replace("#{employeeName}", StringUtils.isBlank(employee.getId().getUser().getName()) ? "" : employee.getId().getUser().getName())
                .replace("#{employeeEmail}", StringUtils.isBlank(employee.getId().getUser().getEmail()) ? "" : employee.getId().getUser().getEmail())
                .replace("#{employeePhone}", StringUtils.isBlank(employee.getId().getUser().getPhone()) ? "" : employee.getId().getUser().getPhone())
                .replace("#{expiryTime}", expiryTime.toString())
                .replace("#{token}", token);

        SendMailRequest sendMailRequestCompanyToVerifyForEmployee = SendMailRequest.builder()
                .email(company.getEmail())
                .body(bodyMailRequestCompanyToVerifyForEmployee)
                .subject("Verification of Employee Information")
                .build();

        RequestCompanyToVerifyForEmployeeEvent requestCompanyToVerifyForEmployeeEvent = new RequestCompanyToVerifyForEmployeeEvent(
                this, sendMailRequestCompanyToVerifyForEmployee);

        EmployeeHistoryRequestCompanyVerify employeeHistoryRequestCompanyVerify = EmployeeHistoryRequestCompanyVerify.builder()
                .employee(employee)
                .expiryTime(expiryTime)
                .token(token)
                .build();

        employeeHistoryRequestCompanyVerifyRepository.save(employeeHistoryRequestCompanyVerify);

        applicationEventPublisher.publishEvent(requestCompanyToVerifyForEmployeeEvent);
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
