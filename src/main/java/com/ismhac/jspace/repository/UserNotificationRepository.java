package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.UserNotification;
import com.ismhac.jspace.model.primaryKey.UserNotificationId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserNotificationRepository extends JpaRepository<UserNotification, UserNotificationId> {
    Page<UserNotification> findAllById_UserId(int userId, Pageable pageable);
    Optional<UserNotification> findById_UserIdAndId_NotificationId(int id_user_id, long id_notification_id);
}
