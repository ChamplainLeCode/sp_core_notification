package com.sprintpay.core.notification.service;

import com.sprintpay.core.notification.service.dto.EmailParticipantDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.sprintpay.core.notification.domain.EmailParticipant}.
 */
public interface EmailParticipantService {

    /**
     * Save a emailParticipant.
     *
     * @param emailParticipantDTO the entity to save.
     * @return the persisted entity.
     */
    EmailParticipantDTO save(EmailParticipantDTO emailParticipantDTO);

    /**
     * Get all the emailParticipants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmailParticipantDTO> findAll(Pageable pageable);


    /**
     * Get the "id" emailParticipant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmailParticipantDTO> findOne(Long id);

    /**
     * Delete the "id" emailParticipant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
