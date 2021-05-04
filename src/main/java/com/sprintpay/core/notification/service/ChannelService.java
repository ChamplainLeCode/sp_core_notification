package com.sprintpay.core.notification.service;

import com.sprintpay.core.notification.service.dto.ChannelDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.sprintpay.core.notification.domain.Channel}.
 */
public interface ChannelService {

    /**
     * Save a channel.
     *
     * @param channelDTO the entity to save.
     * @return the persisted entity.
     */
    ChannelDTO save(ChannelDTO channelDTO);

    /**
     * Get all the channels.
     *
     * @return the list of entities.
     */
    List<ChannelDTO> findAll();


    /**
     * Get the "id" channel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChannelDTO> findOne(Long id);

    /**
     * Delete the "id" channel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
