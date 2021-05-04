package com.sprintpay.core.notification.web.rest;

import com.sprintpay.core.notification.service.TemplateParamValueService;
import com.sprintpay.core.notification.web.rest.errors.BadRequestAlertException;
import com.sprintpay.core.notification.service.dto.TemplateParamValueDTO;

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
 * REST controller for managing {@link com.sprintpay.core.notification.domain.TemplateParamValue}.
 */
@RestController
@RequestMapping("/api")
public class TemplateParamValueResource {

    private final Logger log = LoggerFactory.getLogger(TemplateParamValueResource.class);

    private static final String ENTITY_NAME = "corenotificationTemplateParamValue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TemplateParamValueService templateParamValueService;

    public TemplateParamValueResource(TemplateParamValueService templateParamValueService) {
        this.templateParamValueService = templateParamValueService;
    }

    /**
     * {@code POST  /template-param-values} : Create a new templateParamValue.
     *
     * @param templateParamValueDTO the templateParamValueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new templateParamValueDTO, or with status {@code 400 (Bad Request)} if the templateParamValue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/template-param-values")
    public ResponseEntity<TemplateParamValueDTO> createTemplateParamValue(@Valid @RequestBody TemplateParamValueDTO templateParamValueDTO) throws URISyntaxException {
        log.debug("REST request to save TemplateParamValue : {}", templateParamValueDTO);
        if (templateParamValueDTO.getId() != null) {
            throw new BadRequestAlertException("A new templateParamValue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TemplateParamValueDTO result = templateParamValueService.save(templateParamValueDTO);
        return ResponseEntity.created(new URI("/api/template-param-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /template-param-values} : Updates an existing templateParamValue.
     *
     * @param templateParamValueDTO the templateParamValueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated templateParamValueDTO,
     * or with status {@code 400 (Bad Request)} if the templateParamValueDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the templateParamValueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/template-param-values")
    public ResponseEntity<TemplateParamValueDTO> updateTemplateParamValue(@Valid @RequestBody TemplateParamValueDTO templateParamValueDTO) throws URISyntaxException {
        log.debug("REST request to update TemplateParamValue : {}", templateParamValueDTO);
        if (templateParamValueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TemplateParamValueDTO result = templateParamValueService.save(templateParamValueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, templateParamValueDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /template-param-values} : get all the templateParamValues.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of templateParamValues in body.
     */
    @GetMapping("/template-param-values")
    public ResponseEntity<List<TemplateParamValueDTO>> getAllTemplateParamValues(Pageable pageable) {
        log.debug("REST request to get a page of TemplateParamValues");
        Page<TemplateParamValueDTO> page = templateParamValueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /template-param-values/:id} : get the "id" templateParamValue.
     *
     * @param id the id of the templateParamValueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the templateParamValueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/template-param-values/{id}")
    public ResponseEntity<TemplateParamValueDTO> getTemplateParamValue(@PathVariable Long id) {
        log.debug("REST request to get TemplateParamValue : {}", id);
        Optional<TemplateParamValueDTO> templateParamValueDTO = templateParamValueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(templateParamValueDTO);
    }

    /**
     * {@code DELETE  /template-param-values/:id} : delete the "id" templateParamValue.
     *
     * @param id the id of the templateParamValueDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/template-param-values/{id}")
    public ResponseEntity<Void> deleteTemplateParamValue(@PathVariable Long id) {
        log.debug("REST request to delete TemplateParamValue : {}", id);
        templateParamValueService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
