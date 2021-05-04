package com.sprintpay.core.notification.web.rest;

import com.sprintpay.core.notification.service.EmailParticipantService;
import com.sprintpay.core.notification.web.rest.errors.BadRequestAlertException;
import com.sprintpay.core.notification.service.dto.EmailParticipantDTO;
import com.sprintpay.core.notification.service.dto.EmailParticipantCriteria;
import com.sprintpay.core.notification.service.EmailParticipantQueryService;

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
 * REST controller for managing {@link com.sprintpay.core.notification.domain.EmailParticipant}.
 */
@RestController
@RequestMapping("/api")
public class EmailParticipantResource {

    private final Logger log = LoggerFactory.getLogger(EmailParticipantResource.class);

    private static final String ENTITY_NAME = "corenotificationEmailParticipant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmailParticipantService emailParticipantService;

    private final EmailParticipantQueryService emailParticipantQueryService;

    public EmailParticipantResource(EmailParticipantService emailParticipantService, EmailParticipantQueryService emailParticipantQueryService) {
        this.emailParticipantService = emailParticipantService;
        this.emailParticipantQueryService = emailParticipantQueryService;
    }

    /**
     * {@code POST  /email-participants} : Create a new emailParticipant.
     *
     * @param emailParticipantDTO the emailParticipantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emailParticipantDTO, or with status {@code 400 (Bad Request)} if the emailParticipant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/email-participants")
    public ResponseEntity<EmailParticipantDTO> createEmailParticipant(@Valid @RequestBody EmailParticipantDTO emailParticipantDTO) throws URISyntaxException {
        log.debug("REST request to save EmailParticipant : {}", emailParticipantDTO);
        if (emailParticipantDTO.getId() != null) {
            throw new BadRequestAlertException("A new emailParticipant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmailParticipantDTO result = emailParticipantService.save(emailParticipantDTO);
        return ResponseEntity.created(new URI("/api/email-participants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /email-participants} : Updates an existing emailParticipant.
     *
     * @param emailParticipantDTO the emailParticipantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emailParticipantDTO,
     * or with status {@code 400 (Bad Request)} if the emailParticipantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emailParticipantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/email-participants")
    public ResponseEntity<EmailParticipantDTO> updateEmailParticipant(@Valid @RequestBody EmailParticipantDTO emailParticipantDTO) throws URISyntaxException {
        log.debug("REST request to update EmailParticipant : {}", emailParticipantDTO);
        if (emailParticipantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmailParticipantDTO result = emailParticipantService.save(emailParticipantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emailParticipantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /email-participants} : get all the emailParticipants.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emailParticipants in body.
     */
    @GetMapping("/email-participants")
    public ResponseEntity<List<EmailParticipantDTO>> getAllEmailParticipants(EmailParticipantCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EmailParticipants by criteria: {}", criteria);
        Page<EmailParticipantDTO> page = emailParticipantQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /email-participants/count} : count all the emailParticipants.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/email-participants/count")
    public ResponseEntity<Long> countEmailParticipants(EmailParticipantCriteria criteria) {
        log.debug("REST request to count EmailParticipants by criteria: {}", criteria);
        return ResponseEntity.ok().body(emailParticipantQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /email-participants/:id} : get the "id" emailParticipant.
     *
     * @param id the id of the emailParticipantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emailParticipantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/email-participants/{id}")
    public ResponseEntity<EmailParticipantDTO> getEmailParticipant(@PathVariable Long id) {
        log.debug("REST request to get EmailParticipant : {}", id);
        Optional<EmailParticipantDTO> emailParticipantDTO = emailParticipantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emailParticipantDTO);
    }

    /**
     * {@code DELETE  /email-participants/:id} : delete the "id" emailParticipant.
     *
     * @param id the id of the emailParticipantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/email-participants/{id}")
    public ResponseEntity<Void> deleteEmailParticipant(@PathVariable Long id) {
        log.debug("REST request to delete EmailParticipant : {}", id);
        emailParticipantService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
