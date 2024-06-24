package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.companyNotification.response.CompanyNotificationDto;
import com.ismhac.jspace.exception.AppException;
import com.ismhac.jspace.exception.ErrorCode;
import com.ismhac.jspace.mapper.CompanyNotificationMapper;
import com.ismhac.jspace.model.CompanyNotification;
import com.ismhac.jspace.repository.CompanyNotificationRepository;
import com.ismhac.jspace.service.CompanyNotificationService;
import com.ismhac.jspace.util.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyNotificationServiceImpl implements CompanyNotificationService {
    private final CompanyNotificationRepository companyNotificationRepository;
    private final PageUtils pageUtils;
    @Override
    public PageResponse<CompanyNotificationDto> getCompanyNotifications(int companyId, Pageable pageable) {
        return pageUtils.toPageResponse(CompanyNotificationMapper.INSTANCE.toDtoPage(companyNotificationRepository.findByCompanyIdOrderByCreatedAtDesc(companyId, pageUtils.adjustPageable(pageable))));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompanyNotificationDto updateCompanyNotificationReadStatus(long id, boolean read) {
        CompanyNotification companyNotification = companyNotificationRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_COMPANY_NOTIFICATION));
        companyNotification.setRead(read);
        return CompanyNotificationMapper.INSTANCE.toDto(companyNotificationRepository.save(companyNotification));
    }
}
