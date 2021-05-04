package com.sprintpay.core.notification.service.impl;

import com.sprintpay.core.notification.service.SmsParticipantService;
import com.sprintpay.core.notification.domain.SmsParticipant;
import com.sprintpay.core.notification.repository.SmsParticipantRepository;
import com.sprintpay.core.notification.service.dto.SmsParticipantDTO;
import com.sprintpay.core.notification.service.mapper.SmsParticipantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SmsParticipant}.
 */
@Service
@Transactional
public class SmsParticipantServiceImpl implements SmsParticipantService {

    private final Logger log = LoggerFactory.getLogger(SmsParticipantServiceImpl.class);

    private final SmsParticipantRepository smsParticipantRepository;

    private final SmsParticipantMapper smsParticipantMapper;

    public SmsParticipantServiceImpl(SmsParticipantRepository smsParticipantRepository, SmsParticipantMapper smsParticipantMapper) {
        this.smsParticipantRepository = smsParticipantRepository;
        this.smsParticipantMapper = smsParticipantMapper;
    }

    @Override
    public SmsParticipantDTO save(SmsParticipantDTO smsParticipantDTO) {
        log.debug("Request to save SmsParticipant : {}", smsParticipantDTO);
        SmsParticipant smsParticipant = smsParticipantMapper.toEntity(smsParticipantDTO);
        smsParticipant = smsParticipantRepository.save(smsParticipant);
        return smsParticipantMapper.toDto(smsParticipant);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SmsParticipantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SmsParticipants");
        return smsParticipantRepository.findAll(pageable)
            .map(smsParticipantMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SmsParticipantDTO> findOne(Long id) {
        log.debug("Request to get SmsParticipant : {}", id);
        return smsParticipantRepository.findById(id)
            .map(smsParticipantMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SmsParticipant : {}", id);
        smsParticipantRepository.deleteById(id);
    }
}
