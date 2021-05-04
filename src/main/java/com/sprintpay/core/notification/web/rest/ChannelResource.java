package com.sprintpay.core.notification.web.rest;

import com.sprintpay.core.notification.service.ChannelService;
import com.sprintpay.core.notification.web.rest.errors.BadRequestAlertException;
import com.sprintpay.core.notification.service.dto.ChannelDTO;
import com.sprintpay.core.notification.service.dto.ChannelCriteria;
import com.sprintpay.core.notification.service.ChannelQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sprintpay.core.notification.domain.Channel}.
 */
@RestController
@RequestMapping("/api")
public class ChannelResource {

    private final Logger log = LoggerFactory.getLogger(ChannelResource.class);

    private static final String ENTITY_NAME = "corenotificationChannel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChannelService channelService;

    private final ChannelQueryService channelQueryService;

    public ChannelResource(ChannelService channelService, ChannelQueryService channelQueryService) {
        this.channelService = channelService;
        this.channelQueryService = channelQueryService;
    }

    /**
     * {@code POST  /channels} : Create a new channel.
     *
     * @param channelDTO the channelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new channelDTO, or with status {@code 400 (Bad Request)} if the channel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/channels")
    public ResponseEntity<ChannelDTO> createChannel(@Valid @RequestBody ChannelDTO channelDTO) throws URISyntaxException {
        log.debug("REST request to save Channel : {}", channelDTO);
        if (channelDTO.getId() != null) {
            throw new BadRequestAlertException("A new channel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChannelDTO result = channelService.save(channelDTO);
        return ResponseEntity.created(new URI("/api/channels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /channels} : Updates an existing channel.
     *
     * @param channelDTO the channelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated channelDTO,
     * or with status {@code 400 (Bad Request)} if the channelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the channelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/channels")
    public ResponseEntity<ChannelDTO> updateChannel(@Valid @RequestBody ChannelDTO channelDTO) throws URISyntaxException {
        log.debug("REST request to update Channel : {}", channelDTO);
        if (channelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChannelDTO result = channelService.save(channelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, channelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /channels} : get all the channels.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of channels in body.
     */
    @GetMapping("/channels")
    public ResponseEntity<List<ChannelDTO>> getAllChannels(ChannelCriteria criteria) {
        log.debug("REST request to get Channels by criteria: {}", criteria);
        List<ChannelDTO> entityList = channelQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /channels/count} : count all the channels.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/channels/count")
    public ResponseEntity<Long> countChannels(ChannelCriteria criteria) {
        log.debug("REST request to count Channels by criteria: {}", criteria);
        return ResponseEntity.ok().body(channelQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /channels/:id} : get the "id" channel.
     *
     * @param id the id of the channelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the channelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/channels/{id}")
    public ResponseEntity<ChannelDTO> getChannel(@PathVariable Long id) {
        log.debug("REST request to get Channel : {}", id);
        Optional<ChannelDTO> channelDTO = channelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(channelDTO);
    }

    /**
     * {@code DELETE  /channels/:id} : delete the "id" channel.
     *
     * @param id the id of the channelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/channels/{id}")
    public ResponseEntity<Void> deleteChannel(@PathVariable Long id) {
        log.debug("REST request to delete Channel : {}", id);
        channelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
