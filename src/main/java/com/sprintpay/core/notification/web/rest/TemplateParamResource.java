package com.sprintpay.core.notification.web.rest;

import com.sprintpay.core.notification.service.TemplateParamService;
import com.sprintpay.core.notification.web.rest.errors.BadRequestAlertException;
import com.sprintpay.core.notification.service.dto.TemplateParamDTO;
import com.sprintpay.core.notification.service.dto.TemplateParamCriteria;
import com.sprintpay.core.notification.service.TemplateParamQueryService;

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
 * REST controller for managing {@link com.sprintpay.core.notification.domain.TemplateParam}.
 */
@RestController
@RequestMapping("/api")
public class TemplateParamResource {

    private final Logger log = LoggerFactory.getLogger(TemplateParamResource.class);

    private static final String ENTITY_NAME = "corenotificationTemplateParam";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TemplateParamService templateParamService;

    private final TemplateParamQueryService templateParamQueryService;

    public TemplateParamResource(TemplateParamService templateParamService, TemplateParamQueryService templateParamQueryService) {
        this.templateParamService = templateParamService;
        this.templateParamQueryService = templateParamQueryService;
    }

    /**
     * {@code POST  /template-params} : Create a new templateParam.
     *
     * @param templateParamDTO the templateParamDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new templateParamDTO, or with status {@code 400 (Bad Request)} if the templateParam has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/template-params")
    public ResponseEntity<TemplateParamDTO> createTemplateParam(@Valid @RequestBody TemplateParamDTO templateParamDTO) throws URISyntaxException {
        log.debug("REST request to save TemplateParam : {}", templateParamDTO);
        if (templateParamDTO.getId() != null) {
            throw new BadRequestAlertException("A new templateParam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TemplateParamDTO result = templateParamService.save(templateParamDTO);
        return ResponseEntity.created(new URI("/api/template-params/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /template-params} : Updates an existing templateParam.
     *
     * @param templateParamDTO the templateParamDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated templateParamDTO,
     * or with status {@code 400 (Bad Request)} if the templateParamDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the templateParamDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/template-params")
    public ResponseEntity<TemplateParamDTO> updateTemplateParam(@Valid @RequestBody TemplateParamDTO templateParamDTO) throws URISyntaxException {
        log.debug("REST request to update TemplateParam : {}", templateParamDTO);
        if (templateParamDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TemplateParamDTO result = templateParamService.save(templateParamDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, templateParamDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /template-params} : get all the templateParams.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of templateParams in body.
     */
    @GetMapping("/template-params")
    public ResponseEntity<List<TemplateParamDTO>> getAllTemplateParams(TemplateParamCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TemplateParams by criteria: {}", criteria);
        Page<TemplateParamDTO> page = templateParamQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /template-params/count} : count all the templateParams.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/template-params/count")
    public ResponseEntity<Long> countTemplateParams(TemplateParamCriteria criteria) {
        log.debug("REST request to count TemplateParams by criteria: {}", criteria);
        return ResponseEntity.ok().body(templateParamQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /template-params/:id} : get the "id" templateParam.
     *
     * @param id the id of the templateParamDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the templateParamDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/template-params/{id}")
    public ResponseEntity<TemplateParamDTO> getTemplateParam(@PathVariable Long id) {
        log.debug("REST request to get TemplateParam : {}", id);
        Optional<TemplateParamDTO> templateParamDTO = templateParamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(templateParamDTO);
    }

    /**
     * {@code DELETE  /template-params/:id} : delete the "id" templateParam.
     *
     * @param id the id of the templateParamDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/template-params/{id}")
    public ResponseEntity<Void> deleteTemplateParam(@PathVariable Long id) {
        log.debug("REST request to delete TemplateParam : {}", id);
        templateParamService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
