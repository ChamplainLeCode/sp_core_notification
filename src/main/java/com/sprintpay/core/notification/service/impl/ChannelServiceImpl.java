package com.sprintpay.core.notification.service.impl;

import com.sprintpay.core.notification.service.ChannelService;
import com.sprintpay.core.notification.domain.Channel;
import com.sprintpay.core.notification.repository.ChannelRepository;
import com.sprintpay.core.notification.service.dto.ChannelDTO;
import com.sprintpay.core.notification.service.mapper.ChannelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Channel}.
 */
@Service
@Transactional
public class ChannelServiceImpl implements ChannelService {

    private final Logger log = LoggerFactory.getLogger(ChannelServiceImpl.class);

    private final ChannelRepository channelRepository;

    private final ChannelMapper channelMapper;

    public ChannelServiceImpl(ChannelRepository channelRepository, ChannelMapper channelMapper) {
        this.channelRepository = channelRepository;
        this.channelMapper = channelMapper;
    }

    @Override
    public ChannelDTO save(ChannelDTO channelDTO) {
        log.debug("Request to save Channel : {}", channelDTO);
        Channel channel = channelMapper.toEntity(channelDTO);
        channel = channelRepository.save(channel);
        return channelMapper.toDto(channel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChannelDTO> findAll() {
        log.debug("Request to get all Channels");
        return channelRepository.findAll().stream()
            .map(channelMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ChannelDTO> findOne(Long id) {
        log.debug("Request to get Channel : {}", id);
        return channelRepository.findById(id)
            .map(channelMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Channel : {}", id);
        channelRepository.deleteById(id);
    }
}
