package com.sprintpay.core.notification.service.impl;

import com.sprintpay.core.notification.service.EmailParticipantService;
import com.sprintpay.core.notification.domain.EmailParticipant;
import com.sprintpay.core.notification.repository.EmailParticipantRepository;
import com.sprintpay.core.notification.service.dto.EmailParticipantDTO;
import com.sprintpay.core.notification.service.mapper.EmailParticipantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EmailParticipant}.
 */
@Service
@Transactional
public class EmailParticipantServiceImpl implements EmailParticipantService {

    private final Logger log = LoggerFactory.getLogger(EmailParticipantServiceImpl.class);

    private final EmailParticipantRepository emailParticipantRepository;

    private final EmailParticipantMapper emailParticipantMapper;

    public EmailParticipantServiceImpl(EmailParticipantRepository emailParticipantRepository, EmailParticipantMapper emailParticipantMapper) {
        this.emailParticipantRepository = emailParticipantRepository;
        this.emailParticipantMapper = emailParticipantMapper;
    }

    @Override
    public EmailParticipantDTO save(EmailParticipantDTO emailParticipantDTO) {
        log.debug("Request to save EmailParticipant : {}", emailParticipantDTO);
        EmailParticipant emailParticipant = emailParticipantMapper.toEntity(emailParticipantDTO);
        emailParticipant = emailParticipantRepository.save(emailParticipant);
        return emailParticipantMapper.toDto(emailParticipant);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmailParticipantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmailParticipants");
        return emailParticipantRepository.findAll(pageable)
            .map(emailParticipantMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<EmailParticipantDTO> findOne(Long id) {
        log.debug("Request to get EmailParticipant : {}", id);
        return emailParticipantRepository.findById(id)
            .map(emailParticipantMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmailParticipant : {}", id);
        emailParticipantRepository.deleteById(id);
    }
}
