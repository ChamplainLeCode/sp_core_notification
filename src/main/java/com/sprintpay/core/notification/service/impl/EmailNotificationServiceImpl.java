package com.sprintpay.core.notification.service.impl;

import com.sprintpay.core.notification.service.EmailNotificationService;
import com.sprintpay.core.notification.domain.EmailNotification;
import com.sprintpay.core.notification.repository.EmailNotificationRepository;
import com.sprintpay.core.notification.service.dto.EmailNotificationDTO;
import com.sprintpay.core.notification.service.mapper.EmailNotificationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EmailNotification}.
 */
@Service
@Transactional
public class EmailNotificationServiceImpl implements EmailNotificationService {

    private final Logger log = LoggerFactory.getLogger(EmailNotificationServiceImpl.class);

    private final EmailNotificationRepository emailNotificationRepository;

    private final EmailNotificationMapper emailNotificationMapper;

    public EmailNotificationServiceImpl(EmailNotificationRepository emailNotificationRepository, EmailNotificationMapper emailNotificationMapper) {
        this.emailNotificationRepository = emailNotificationRepository;
        this.emailNotificationMapper = emailNotificationMapper;
    }

    @Override
    public EmailNotificationDTO save(EmailNotificationDTO emailNotificationDTO) {
        log.debug("Request to save EmailNotification : {}", emailNotificationDTO);
        EmailNotification emailNotification = emailNotificationMapper.toEntity(emailNotificationDTO);
        emailNotification = emailNotificationRepository.save(emailNotification);
        return emailNotificationMapper.toDto(emailNotification);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmailNotificationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmailNotifications");
        return emailNotificationRepository.findAll(pageable)
            .map(emailNotificationMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<EmailNotificationDTO> findOne(Long id) {
        log.debug("Request to get EmailNotification : {}", id);
        return emailNotificationRepository.findById(id)
            .map(emailNotificationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmailNotification : {}", id);
        emailNotificationRepository.deleteById(id);
    }
}
