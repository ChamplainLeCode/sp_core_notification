package com.sprintpay.core.notification.repository;

import com.sprintpay.core.notification.domain.SmsParticipant;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SmsParticipant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SmsParticipantRepository extends JpaRepository<SmsParticipant, Long>, JpaSpecificationExecutor<SmsParticipant> {
}
