package com.ismhac.jspace.service.impl;

import com.cloudinary.Cloudinary;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyVerifyEmailRequestHistoryRepository companyVerifyEmailRequestHistoryRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeHistoryRequestCompanyVerifyRepository employeeHistoryRequestCompanyVerifyRepository;
    private final Cloudinary cloudinary;
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
    public Map<String, Object> getCompanyById(int id, Integer candidateId) {
        Map<String, Object> result = companyRepository.findByIdAndOptionalCandidateId(id, candidateId);
        Map<String, Object> response = new HashMap<>(result);
        response.put("company", CompanyMapper.instance.eToDto((Company) result.get("company")));
        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompanyDto updateLogo(int id, MultipartFile logo) {

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_COMPANY));

        if (logo == null || logo.isEmpty()) {
            throw new IllegalArgumentException("logo must not be empty");
        }

        Map<String, Object> options = new HashMap<>();

        Map uploadResult;

        try {
            uploadResult = cloudinary.uploader().upload(logo.getBytes(), options);
            cloudinary.uploader().upload(logo.getBytes(), options);
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }

        String logoPath = (String) uploadResult.get("secure_url");
        String logoId = (String) uploadResult.get("public_id");

        company.setLogo(logoPath);
        company.setLogoId(logoId);

        return CompanyMapper.instance.eToDto(companyRepository.save(company));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompanyDto updateBackground(int id, MultipartFile background) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_COMPANY));

        if (background == null || background.isEmpty()) {
            throw new IllegalArgumentException("logo must not be empty");
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

        company.setBackground(backgroundPath);
        company.setBackgroundId(backgroundId);

        return CompanyMapper.instance.eToDto(companyRepository.save(company));
    }

    @Override
    public PageResponse<Map<String, Object>> getPageAndFilter(String name, String address, String email, String phone, String companySize, Integer candidateId, Pageable pageable) {
        Page<Map<String, Object>> results = companyRepository.findAllAndFilter(name, address, email, phone, companySize, candidateId, pageUtils.adjustPageable(pageable));
        List<Map<String, Object>> contents = results.getContent().stream().map(result -> {
            Map<String, Object> map = new HashMap<>(result);
            map.put("company", CompanyMapper.instance.eToDto((Company) result.get("company")));
            return map;
        }).toList();
        return pageUtils.toPageResponse(new PageImpl<>(contents, results.getPageable(), results.getTotalElements()));
    }
}
