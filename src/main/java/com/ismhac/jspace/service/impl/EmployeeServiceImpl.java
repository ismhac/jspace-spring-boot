package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.dto.common.request.SendMailRequest;
import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.company.request.CompanyCreateRequest;
import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.dto.user.employee.request.EmployeeUpdateRequest;
import com.ismhac.jspace.dto.user.employee.response.EmployeeDto;
import com.ismhac.jspace.dto.user.response.UserDto;
import com.ismhac.jspace.event.RequestCompanyToVerifyForEmployeeEvent;
import com.ismhac.jspace.event.RequestCompanyVerifyEmailEvent;
import com.ismhac.jspace.exception.AppException;
import com.ismhac.jspace.exception.ErrorCode;
import com.ismhac.jspace.mapper.CompanyMapper;
import com.ismhac.jspace.mapper.EmployeeMapper;
import com.ismhac.jspace.mapper.UserMapper;
import com.ismhac.jspace.model.*;
import com.ismhac.jspace.model.primaryKey.CompanyRequestReviewId;
import com.ismhac.jspace.repository.*;
import com.ismhac.jspace.service.EmployeeService;
import com.ismhac.jspace.util.BeanUtils;
import com.ismhac.jspace.util.PageUtils;
import com.ismhac.jspace.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
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


    @Autowired
    private BeanUtils beanUtils;

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
        return pageUtils.toPageResponse(CompanyMapper.instance.ePageToDtoPage(companyRepository.getPage(name, address, pageable)));
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
                .background(request.getBackground())
                .logo(request.getLogo())
                .address(request.getAddress())
                .email(email)
                .phone(phone)
                .companySize(request.getCompanySize())
                .description(request.getDescription())
                .companyLink(request.getCompanyLink())
                .build();

        Company savedCompany = companyRepository.save(newCompany);

        employee.setCompany(savedCompany);
        employeeRepository.save(employee);

        CompanyRequestReview companyRequestReview = _createCompanyRequestAdminReview(savedCompany);

        if (Objects.nonNull(companyRequestReview)) {
            _sendMailWhenCreateCompany(savedCompany, employee);
        }

        return CompanyMapper.instance.eToDto(savedCompany);
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
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(10);

        String bodyMailRequestCompanyVerifyEmail = String.format("""
                        <html>
                            <head>
                                <title>Verification of Company Information</title>
                                <style>
                                    table, tr {
                                        border: 1px solid #ccc;
                                        border-collapse: collapse;
                                    }
                                    th, td {
                                        padding: 8px;
                                        text-align: left;
                                    }
                                    th {
                                        background-color: #f2f2f2;
                                    }
                                    button {
                                        padding: 5px 5px;
                                        text-align: center;
                                        cursor: pointer;
                                    }
                                </style>
                            </head>
                            <body>
                                <p>Dear %s,</p>
                                <p>We hope this email finds you well. We recently received the following information associated with your company:</p>
                                <table>
                                    <tr>
                                        <td>Company Name:</td>
                                        <td>%s</td>
                                    </tr>
                                    <tr>
                                        <td>Address:</td>
                                        <td>%s</td>
                                    </tr>
                                    <tr>
                                        <td>Email:</td>
                                        <td>%s</td>
                                    </tr>
                                    <tr>
                                        <td>Phone:</td>
                                        <td>%s</td>
                                    </tr>
                                    <tr>
                                        <td>Company Size:</td>
                                    <td>%s</td>
                                </table>
                                <p>If this information pertains to your company, we kindly ask you to verify your email by clicking the link below:</p>
                                <button>
                                    <a href="https://jspace-804e64747ec6.herokuapp.com/jspace-service/api/v1/companies/verify-email?mac=%s"
                                        style="text-decoration: none; color: #000000;"
                                    >
                                        <span> Verify Company Information </span>
                                    </a>
                                </button>
                                <p>If this information does not correspond to your company, please disregard this email.</p>
                                <p>Thank you for your attention to this matter.</p>
                                <p>Best regards!</p>
                            </body>
                        </html>
                        """,
                company.getName(),
                StringUtils.isBlank(company.getName()) ? "" : company.getName(),
                StringUtils.isBlank(company.getAddress()) ? "" : company.getAddress(),
                StringUtils.isBlank(company.getEmail()) ? "" : company.getEmail(),
                StringUtils.isBlank(company.getPhone()) ? "" : company.getPhone(),
                StringUtils.isBlank(company.getCompanySize()) ? "" : company.getCompanySize(),
                token);

        SendMailRequest sendMailRequestCompanyVerifyEmail = SendMailRequest.builder()
                .email(company.getEmail())
                .body(bodyMailRequestCompanyVerifyEmail)
                .subject("Verification of Company Information")
                .build();

        RequestCompanyVerifyEmailEvent requestCompanyVerifyEmailEvent = new RequestCompanyVerifyEmailEvent(
                this, sendMailRequestCompanyVerifyEmail);


        String bodyMailRequestCompanyToVerifyForEmployee = String.format("""
                        <html lang="en">
                        <head>
                            <title>Employee Information Verification</title>
                            <style>
                                table, tr {
                                    border: 1px solid #ccc;
                                    border-collapse: collapse;
                                }
                                th, td {
                                    padding: 8px;
                                    text-align: left;
                                }
                                th {
                                    background-color: #f2f2f2;
                                }
                                button {
                                    padding: 5px 5px;
                                    text-align: center;
                                    cursor: pointer;
                                }
                            </style>
                        </head>
                        <body>
                            <p>Dear %s,</p>
                            <p>We trust this message finds you well.</p>
                            <p>We have received the following employee information associated with your company, registered for recruitment access on our platform:</p>
                            <table>
                                <tr>
                                    <td>Name:</td>
                                    <td>%s</td>
                                </tr>
                                <tr>
                                    <td>Email:</td>
                                    <td>%s</td>
                                </tr>
                                <tr>
                                    <td>Phone:</td>
                                    <td>%s</td>
                                </tr>
                            </table>
                            <p>If this information corresponds to an employee of your company authorized to recruit on our platform, we kindly request verification.</p>
                            <p>Please confirm the accuracy of the details provided by clicking the link below:</p>
                            <button>
                                <a href="https://jspace-804e64747ec6.herokuapp.com/jspace-service/api/v1/companies/verify-employee?mac=%s" 
                                    style="text-decoration: none; color: #000000;"
                                >
                                <span> Verify Employee Information </span>
                                </a></button>
                            <p>Should this information not align with your records, please disregard this message.</p>
                            <p>Thank you for your cooperation in ensuring the accuracy of our records.</p>
                            <p>Best regards!</p>
                        </body>
                        </html>
                         """,
                company.getName(),
                StringUtils.isBlank(employee.getId().getUser().getName()) ? "" : employee.getId().getUser().getName(),
                StringUtils.isBlank(employee.getId().getUser().getEmail()) ? "" : employee.getId().getUser().getEmail(),
                StringUtils.isBlank(employee.getId().getUser().getPhone()) ? "" : employee.getId().getUser().getPhone(),
                token);

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

        CompanyVerifyEmailRequestHistory savedCompanyVerifyEmailRequestHistory =
                companyVerifyEmailRequestHistoryRepository.save(companyVerifyEmailRequestHistory);

        EmployeeHistoryRequestCompanyVerify employeeHistoryRequestCompanyVerify = EmployeeHistoryRequestCompanyVerify.builder()
                .employee(employee)
                .token(token)
                .expiryTime(expiryTime)
                .build();

        EmployeeHistoryRequestCompanyVerify savedEmployeeHistoryRequestCompanyVerify =
                employeeHistoryRequestCompanyVerifyRepository.save(employeeHistoryRequestCompanyVerify);


        if (Objects.nonNull(savedCompanyVerifyEmailRequestHistory)
            && Objects.nonNull(savedEmployeeHistoryRequestCompanyVerify)) {
            applicationEventPublisher.publishEvent(requestCompanyVerifyEmailEvent);
            applicationEventPublisher.publishEvent(requestCompanyToVerifyForEmployeeEvent);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    protected void _sendMailWhenPickCompany(Company company, Employee employee) {

        String token = String.valueOf(UUID.randomUUID());
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(10);

        String bodyMailRequestCompanyToVerifyForEmployee = String.format("""
                        <html lang="en">
                        <head>
                            <title>Employee Information Verification</title>
                            <style>
                                table, tr {
                                    border: 1px solid #ccc;
                                    border-collapse: collapse;
                                }
                                th, td {
                                    padding: 8px;
                                    text-align: left;
                                }
                                th {
                                    background-color: #f2f2f2;
                                }
                                button {
                                    padding: 5px 5px;
                                    text-align: center;
                                    cursor: pointer;
                                }
                            </style>
                        </head>
                        <body>
                            <p>Dear %s,</p>
                            <p>We trust this message finds you well.</p>
                            <p>We have received the following employee information associated with your company, registered for recruitment access on our platform:</p>
                            <table>
                                <tr>
                                    <td>Name:</td>
                                    <td>%s</td>
                                </tr>
                                <tr>
                                    <td>Email:</td>
                                    <td>%s</td>
                                </tr>
                                <tr>
                                    <td>Phone:</td>
                                    <td>%s</td>
                                </tr>
                            </table>
                            <p>If this information corresponds to an employee of your company authorized to recruit on our platform, we kindly request verification.</p>
                            <p>Please confirm the accuracy of the details provided by clicking the link below:</p>
                            <button>
                                <a href="https://jspace-804e64747ec6.herokuapp.com/jspace-service/api/v1/companies/verify-employee?mac=%s" 
                                    style="text-decoration: none; color: #000000;"
                                >
                                <span> Verify Employee Information </span>
                                </a></button>
                            <p>Should this information not align with your records, please disregard this message.</p>
                            <p>Thank you for your cooperation in ensuring the accuracy of our records.</p>
                            <p>Best regards!</p>
                        </body>
                        </html>
                         """,
                company.getName(),
                StringUtils.isBlank(employee.getId().getUser().getName()) ? "" : employee.getId().getUser().getName(),
                StringUtils.isBlank(employee.getId().getUser().getEmail()) ? "" : employee.getId().getUser().getEmail(),
                StringUtils.isBlank(employee.getId().getUser().getPhone()) ? "" : employee.getId().getUser().getPhone(),
                token);

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
}
