package com.sprintpay.core.notification.repository;

import com.sprintpay.core.notification.domain.EmailNotification;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmailNotification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailNotificationRepository extends JpaRepository<EmailNotification, Long>, JpaSpecificationExecutor<EmailNotification> {
}
