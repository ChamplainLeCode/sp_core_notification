package com.sprintpay.core.notification.service;

import com.sprintpay.core.notification.service.dto.MicroServiceParticipantDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.sprintpay.core.notification.domain.MicroServiceParticipant}.
 */
public interface MicroServiceParticipantService {

    /**
     * Save a microServiceParticipant.
     *
     * @param microServiceParticipantDTO the entity to save.
     * @return the persisted entity.
     */
    MicroServiceParticipantDTO save(MicroServiceParticipantDTO microServiceParticipantDTO);

    /**
     * Get all the microServiceParticipants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MicroServiceParticipantDTO> findAll(Pageable pageable);


    /**
     * Get the "id" microServiceParticipant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MicroServiceParticipantDTO> findOne(Long id);

    /**
     * Delete the "id" microServiceParticipant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
