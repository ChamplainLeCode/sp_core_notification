package com.sprintpay.core.notification.service.impl;

import com.sprintpay.core.notification.service.MicroServiceParticipantService;
import com.sprintpay.core.notification.domain.MicroServiceParticipant;
import com.sprintpay.core.notification.repository.MicroServiceParticipantRepository;
import com.sprintpay.core.notification.service.dto.MicroServiceParticipantDTO;
import com.sprintpay.core.notification.service.mapper.MicroServiceParticipantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MicroServiceParticipant}.
 */
@Service
@Transactional
public class MicroServiceParticipantServiceImpl implements MicroServiceParticipantService {

    private final Logger log = LoggerFactory.getLogger(MicroServiceParticipantServiceImpl.class);

    private final MicroServiceParticipantRepository microServiceParticipantRepository;

    private final MicroServiceParticipantMapper microServiceParticipantMapper;

    public MicroServiceParticipantServiceImpl(MicroServiceParticipantRepository microServiceParticipantRepository, MicroServiceParticipantMapper microServiceParticipantMapper) {
        this.microServiceParticipantRepository = microServiceParticipantRepository;
        this.microServiceParticipantMapper = microServiceParticipantMapper;
    }

    @Override
    public MicroServiceParticipantDTO save(MicroServiceParticipantDTO microServiceParticipantDTO) {
        log.debug("Request to save MicroServiceParticipant : {}", microServiceParticipantDTO);
        MicroServiceParticipant microServiceParticipant = microServiceParticipantMapper.toEntity(microServiceParticipantDTO);
        microServiceParticipant = microServiceParticipantRepository.save(microServiceParticipant);
        return microServiceParticipantMapper.toDto(microServiceParticipant);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MicroServiceParticipantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MicroServiceParticipants");
        return microServiceParticipantRepository.findAll(pageable)
            .map(microServiceParticipantMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<MicroServiceParticipantDTO> findOne(Long id) {
        log.debug("Request to get MicroServiceParticipant : {}", id);
        return microServiceParticipantRepository.findById(id)
            .map(microServiceParticipantMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MicroServiceParticipant : {}", id);
        microServiceParticipantRepository.deleteById(id);
    }
}
