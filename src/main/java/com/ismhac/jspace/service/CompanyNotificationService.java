package com.ismhac.jspace.service;

import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.companyNotification.response.CompanyNotificationDto;
import org.springframework.data.domain.Pageable;

public interface CompanyNotificationService {
    PageResponse<CompanyNotificationDto> getCompanyNotifications(int companyId, Pageable pageable);
    CompanyNotificationDto updateCompanyNotificationReadStatus(long id, boolean read);
}
