package com.sprintpay.core.notification.repository;

import com.sprintpay.core.notification.domain.SmsNotification;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SmsNotification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SmsNotificationRepository extends JpaRepository<SmsNotification, Long>, JpaSpecificationExecutor<SmsNotification> {
}
