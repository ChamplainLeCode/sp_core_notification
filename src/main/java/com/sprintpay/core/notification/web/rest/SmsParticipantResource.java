package com.sprintpay.core.notification.web.rest;

import com.sprintpay.core.notification.service.SmsParticipantService;
import com.sprintpay.core.notification.web.rest.errors.BadRequestAlertException;
import com.sprintpay.core.notification.service.dto.SmsParticipantDTO;
import com.sprintpay.core.notification.service.dto.SmsParticipantCriteria;
import com.sprintpay.core.notification.service.SmsParticipantQueryService;

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
 * REST controller for managing {@link com.sprintpay.core.notification.domain.SmsParticipant}.
 */
@RestController
@RequestMapping("/api")
public class SmsParticipantResource {

    private final Logger log = LoggerFactory.getLogger(SmsParticipantResource.class);

    private static final String ENTITY_NAME = "corenotificationSmsParticipant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SmsParticipantService smsParticipantService;

    private final SmsParticipantQueryService smsParticipantQueryService;

    public SmsParticipantResource(SmsParticipantService smsParticipantService, SmsParticipantQueryService smsParticipantQueryService) {
        this.smsParticipantService = smsParticipantService;
        this.smsParticipantQueryService = smsParticipantQueryService;
    }

    /**
     * {@code POST  /sms-participants} : Create a new smsParticipant.
     *
     * @param smsParticipantDTO the smsParticipantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new smsParticipantDTO, or with status {@code 400 (Bad Request)} if the smsParticipant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sms-participants")
    public ResponseEntity<SmsParticipantDTO> createSmsParticipant(@Valid @RequestBody SmsParticipantDTO smsParticipantDTO) throws URISyntaxException {
        log.debug("REST request to save SmsParticipant : {}", smsParticipantDTO);
        if (smsParticipantDTO.getId() != null) {
            throw new BadRequestAlertException("A new smsParticipant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SmsParticipantDTO result = smsParticipantService.save(smsParticipantDTO);
        return ResponseEntity.created(new URI("/api/sms-participants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sms-participants} : Updates an existing smsParticipant.
     *
     * @param smsParticipantDTO the smsParticipantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated smsParticipantDTO,
     * or with status {@code 400 (Bad Request)} if the smsParticipantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the smsParticipantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sms-participants")
    public ResponseEntity<SmsParticipantDTO> updateSmsParticipant(@Valid @RequestBody SmsParticipantDTO smsParticipantDTO) throws URISyntaxException {
        log.debug("REST request to update SmsParticipant : {}", smsParticipantDTO);
        if (smsParticipantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SmsParticipantDTO result = smsParticipantService.save(smsParticipantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, smsParticipantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sms-participants} : get all the smsParticipants.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of smsParticipants in body.
     */
    @GetMapping("/sms-participants")
    public ResponseEntity<List<SmsParticipantDTO>> getAllSmsParticipants(SmsParticipantCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SmsParticipants by criteria: {}", criteria);
        Page<SmsParticipantDTO> page = smsParticipantQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sms-participants/count} : count all the smsParticipants.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/sms-participants/count")
    public ResponseEntity<Long> countSmsParticipants(SmsParticipantCriteria criteria) {
        log.debug("REST request to count SmsParticipants by criteria: {}", criteria);
        return ResponseEntity.ok().body(smsParticipantQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sms-participants/:id} : get the "id" smsParticipant.
     *
     * @param id the id of the smsParticipantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the smsParticipantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sms-participants/{id}")
    public ResponseEntity<SmsParticipantDTO> getSmsParticipant(@PathVariable Long id) {
        log.debug("REST request to get SmsParticipant : {}", id);
        Optional<SmsParticipantDTO> smsParticipantDTO = smsParticipantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(smsParticipantDTO);
    }

    /**
     * {@code DELETE  /sms-participants/:id} : delete the "id" smsParticipant.
     *
     * @param id the id of the smsParticipantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sms-participants/{id}")
    public ResponseEntity<Void> deleteSmsParticipant(@PathVariable Long id) {
        log.debug("REST request to delete SmsParticipant : {}", id);
        smsParticipantService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
