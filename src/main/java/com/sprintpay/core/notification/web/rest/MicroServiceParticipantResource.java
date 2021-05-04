package com.sprintpay.core.notification.web.rest;

import com.sprintpay.core.notification.service.MicroServiceParticipantService;
import com.sprintpay.core.notification.web.rest.errors.BadRequestAlertException;
import com.sprintpay.core.notification.service.dto.MicroServiceParticipantDTO;
import com.sprintpay.core.notification.service.dto.MicroServiceParticipantCriteria;
import com.sprintpay.core.notification.service.MicroServiceParticipantQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sprintpay.core.notification.domain.MicroServiceParticipant}.
 */
@RestController
@RequestMapping("/api")
public class MicroServiceParticipantResource {

    private final Logger log = LoggerFactory.getLogger(MicroServiceParticipantResource.class);

    private static final String ENTITY_NAME = "corenotificationMicroServiceParticipant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MicroServiceParticipantService microServiceParticipantService;

    private final MicroServiceParticipantQueryService microServiceParticipantQueryService;

    public MicroServiceParticipantResource(MicroServiceParticipantService microServiceParticipantService, MicroServiceParticipantQueryService microServiceParticipantQueryService) {
        this.microServiceParticipantService = microServiceParticipantService;
        this.microServiceParticipantQueryService = microServiceParticipantQueryService;
    }

    /**
     * {@code POST  /micro-service-participants} : Create a new microServiceParticipant.
     *
     * @param microServiceParticipantDTO the microServiceParticipantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new microServiceParticipantDTO, or with status {@code 400 (Bad Request)} if the microServiceParticipant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/micro-service-participants")
    public ResponseEntity<MicroServiceParticipantDTO> createMicroServiceParticipant(@Valid @RequestBody MicroServiceParticipantDTO microServiceParticipantDTO) throws URISyntaxException {
        log.debug("REST request to save MicroServiceParticipant : {}", microServiceParticipantDTO);
        if (microServiceParticipantDTO.getId() != null) {
            throw new BadRequestAlertException("A new microServiceParticipant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MicroServiceParticipantDTO result = microServiceParticipantService.save(microServiceParticipantDTO);
        return ResponseEntity.created(new URI("/api/micro-service-participants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /micro-service-participants} : Updates an existing microServiceParticipant.
     *
     * @param microServiceParticipantDTO the microServiceParticipantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated microServiceParticipantDTO,
     * or with status {@code 400 (Bad Request)} if the microServiceParticipantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the microServiceParticipantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/micro-service-participants")
    public ResponseEntity<MicroServiceParticipantDTO> updateMicroServiceParticipant(@Valid @RequestBody MicroServiceParticipantDTO microServiceParticipantDTO) throws URISyntaxException {
        log.debug("REST request to update MicroServiceParticipant : {}", microServiceParticipantDTO);
        if (microServiceParticipantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MicroServiceParticipantDTO result = microServiceParticipantService.save(microServiceParticipantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, microServiceParticipantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /micro-service-participants} : get all the microServiceParticipants.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of microServiceParticipants in body.
     */
    @GetMapping("/micro-service-participants")
    public ResponseEntity<List<MicroServiceParticipantDTO>> getAllMicroServiceParticipants(MicroServiceParticipantCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MicroServiceParticipants by criteria: {}", criteria);
        Page<MicroServiceParticipantDTO> page = microServiceParticipantQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /micro-service-participants/count} : count all the microServiceParticipants.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/micro-service-participants/count")
    public ResponseEntity<Long> countMicroServiceParticipants(MicroServiceParticipantCriteria criteria) {
        log.debug("REST request to count MicroServiceParticipants by criteria: {}", criteria);
        return ResponseEntity.ok().body(microServiceParticipantQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /micro-service-participants/:id} : get the "id" microServiceParticipant.
     *
     * @param id the id of the microServiceParticipantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the microServiceParticipantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/micro-service-participants/{id}")
    public ResponseEntity<MicroServiceParticipantDTO> getMicroServiceParticipant(@PathVariable Long id) {
        log.debug("REST request to get MicroServiceParticipant : {}", id);
        Optional<MicroServiceParticipantDTO> microServiceParticipantDTO = microServiceParticipantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(microServiceParticipantDTO);
    }

    /**
     * {@code DELETE  /micro-service-participants/:id} : delete the "id" microServiceParticipant.
     *
     * @param id the id of the microServiceParticipantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/micro-service-participants/{id}")
    public ResponseEntity<Void> deleteMicroServiceParticipant(@PathVariable Long id) {
        log.debug("REST request to delete MicroServiceParticipant : {}", id);
        microServiceParticipantService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
