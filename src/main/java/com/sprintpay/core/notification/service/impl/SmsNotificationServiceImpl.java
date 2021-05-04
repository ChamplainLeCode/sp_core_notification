package com.sprintpay.core.notification.service.impl;

import com.sprintpay.core.notification.service.SmsNotificationService;
import com.sprintpay.core.notification.domain.SmsNotification;
import com.sprintpay.core.notification.repository.SmsNotificationRepository;
import com.sprintpay.core.notification.service.dto.SmsNotificationDTO;
import com.sprintpay.core.notification.service.mapper.SmsNotificationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SmsNotification}.
 */
@Service
@Transactional
public class SmsNotificationServiceImpl implements SmsNotificationService {

    private final Logger log = LoggerFactory.getLogger(SmsNotificationServiceImpl.class);

    private final SmsNotificationRepository smsNotificationRepository;

    private final SmsNotificationMapper smsNotificationMapper;

    public SmsNotificationServiceImpl(SmsNotificationRepository smsNotificationRepository, SmsNotificationMapper smsNotificationMapper) {
        this.smsNotificationRepository = smsNotificationRepository;
        this.smsNotificationMapper = smsNotificationMapper;
    }

    @Override
    public SmsNotificationDTO save(SmsNotificationDTO smsNotificationDTO) {
        log.debug("Request to save SmsNotification : {}", smsNotificationDTO);
        SmsNotification smsNotification = smsNotificationMapper.toEntity(smsNotificationDTO);
        smsNotification = smsNotificationRepository.save(smsNotification);
        return smsNotificationMapper.toDto(smsNotification);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SmsNotificationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SmsNotifications");
        return smsNotificationRepository.findAll(pageable)
            .map(smsNotificationMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SmsNotificationDTO> findOne(Long id) {
        log.debug("Request to get SmsNotification : {}", id);
        return smsNotificationRepository.findById(id)
            .map(smsNotificationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SmsNotification : {}", id);
        smsNotificationRepository.deleteById(id);
    }
}
