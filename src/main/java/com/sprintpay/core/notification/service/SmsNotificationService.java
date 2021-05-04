package com.sprintpay.core.notification.service;

import com.sprintpay.core.notification.service.dto.SmsNotificationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.sprintpay.core.notification.domain.SmsNotification}.
 */
public interface SmsNotificationService {

    /**
     * Save a smsNotification.
     *
     * @param smsNotificationDTO the entity to save.
     * @return the persisted entity.
     */
    SmsNotificationDTO save(SmsNotificationDTO smsNotificationDTO);

    /**
     * Get all the smsNotifications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SmsNotificationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" smsNotification.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SmsNotificationDTO> findOne(Long id);

    /**
     * Delete the "id" smsNotification.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
