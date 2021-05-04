package com.sprintpay.core.notification.repository;

import com.sprintpay.core.notification.domain.EmailParticipant;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmailParticipant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailParticipantRepository extends JpaRepository<EmailParticipant, Long>, JpaSpecificationExecutor<EmailParticipant> {
}
