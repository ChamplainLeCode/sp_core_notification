package com.sprintpay.core.notification.repository;

import com.sprintpay.core.notification.domain.MicroServiceParticipant;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MicroServiceParticipant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MicroServiceParticipantRepository extends JpaRepository<MicroServiceParticipant, Long>, JpaSpecificationExecutor<MicroServiceParticipant> {
}
