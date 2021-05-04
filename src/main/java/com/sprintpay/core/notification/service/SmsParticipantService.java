package com.sprintpay.core.notification.service;

import com.sprintpay.core.notification.service.dto.SmsParticipantDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.sprintpay.core.notification.domain.SmsParticipant}.
 */
public interface SmsParticipantService {

    /**
     * Save a smsParticipant.
     *
     * @param smsParticipantDTO the entity to save.
     * @return the persisted entity.
     */
    SmsParticipantDTO save(SmsParticipantDTO smsParticipantDTO);

    /**
     * Get all the smsParticipants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SmsParticipantDTO> findAll(Pageable pageable);


    /**
     * Get the "id" smsParticipant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SmsParticipantDTO> findOne(Long id);

    /**
     * Delete the "id" smsParticipant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
