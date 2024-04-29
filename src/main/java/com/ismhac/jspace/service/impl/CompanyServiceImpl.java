package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.exception.AppException;
import com.ismhac.jspace.exception.ErrorCode;
import com.ismhac.jspace.mapper.CompanyMapper;
import com.ismhac.jspace.model.Company;
import com.ismhac.jspace.model.CompanyVerifyEmailRequestHistory;
import com.ismhac.jspace.model.Employee;
import com.ismhac.jspace.model.EmployeeHistoryRequestCompanyVerify;
import com.ismhac.jspace.repository.CompanyRepository;
import com.ismhac.jspace.repository.CompanyVerifyEmailRequestHistoryRepository;
import com.ismhac.jspace.repository.EmployeeHistoryRequestCompanyVerifyRepository;
import com.ismhac.jspace.repository.EmployeeRepository;
import com.ismhac.jspace.service.CompanyService;
import com.ismhac.jspace.util.PageUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyVerifyEmailRequestHistoryRepository
            companyVerifyEmailRequestHistoryRepository;
    private final EmployeeRepository employeeRepository;


    private final EmployeeHistoryRequestCompanyVerifyRepository
            employeeHistoryRequestCompanyVerifyRepository;

    private final PageUtils pageUtils;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void verifyEmail(String token, HttpServletResponse httpServletResponse) throws IOException {

        Optional<CompanyVerifyEmailRequestHistory> companyVerifyEmailRequestHistory =
                companyVerifyEmailRequestHistoryRepository.findByToken(token);

        if (companyVerifyEmailRequestHistory.isEmpty()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.getWriter().write("Invalid token. Verification failed.");
            return;
        }
        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(companyVerifyEmailRequestHistory.get().getExpiryTime())) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.getWriter().write("Token has expired. Verification failed.");
            return;
        }

        Company company = companyVerifyEmailRequestHistory.get().getCompany();

        company.setEmailVerified(true);

        companyRepository.save(company);

        String redirectUrl = "https://jspace-fe.vercel.app/";
        httpServletResponse.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
        httpServletResponse.setHeader("Location", redirectUrl);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void verifyEmployee(String token, HttpServletResponse httpServletResponse) throws IOException {
        Optional<EmployeeHistoryRequestCompanyVerify> employeeHistoryRequestCompanyVerify =
                employeeHistoryRequestCompanyVerifyRepository.findByToken(token);

        if (employeeHistoryRequestCompanyVerify.isEmpty()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.getWriter().write("Invalid token. Verification failed.");
            return;
        }
        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(employeeHistoryRequestCompanyVerify.get().getExpiryTime())) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.getWriter().write("Token has expired. Verification failed.");
            return;
        }

        Employee employee = employeeHistoryRequestCompanyVerify.get().getEmployee();

        employee.setVerifiedByCompany(true);

        employeeRepository.save(employee);

        String redirectUrl = "https://jspace-fe.vercel.app/";
        httpServletResponse.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
        httpServletResponse.setHeader("Location", redirectUrl);
    }

    @Override
    public CompanyDto getCompanyById(int id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(()->new AppException(ErrorCode.NOT_FOUND_COMPANY));
        return CompanyMapper.instance.eToDto(company);
    }
}
