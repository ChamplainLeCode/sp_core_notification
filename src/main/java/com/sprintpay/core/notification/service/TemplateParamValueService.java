package com.sprintpay.core.notification.service;

import com.sprintpay.core.notification.domain.TemplateParamValue;
import com.sprintpay.core.notification.repository.TemplateParamValueRepository;
import com.sprintpay.core.notification.service.dto.TemplateParamValueDTO;
import com.sprintpay.core.notification.service.mapper.TemplateParamValueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TemplateParamValue}.
 */
@Service
@Transactional
public class TemplateParamValueService {

    private final Logger log = LoggerFactory.getLogger(TemplateParamValueService.class);

    private final TemplateParamValueRepository templateParamValueRepository;

    private final TemplateParamValueMapper templateParamValueMapper;

    public TemplateParamValueService(TemplateParamValueRepository templateParamValueRepository, TemplateParamValueMapper templateParamValueMapper) {
        this.templateParamValueRepository = templateParamValueRepository;
        this.templateParamValueMapper = templateParamValueMapper;
    }

    /**
     * Save a templateParamValue.
     *
     * @param templateParamValueDTO the entity to save.
     * @return the persisted entity.
     */
    public TemplateParamValueDTO save(TemplateParamValueDTO templateParamValueDTO) {
        log.debug("Request to save TemplateParamValue : {}", templateParamValueDTO);
        TemplateParamValue templateParamValue = templateParamValueMapper.toEntity(templateParamValueDTO);
        templateParamValue = templateParamValueRepository.save(templateParamValue);
        return templateParamValueMapper.toDto(templateParamValue);
    }

    /**
     * Get all the templateParamValues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TemplateParamValueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TemplateParamValues");
        return templateParamValueRepository.findAll(pageable)
            .map(templateParamValueMapper::toDto);
    }


    /**
     * Get one templateParamValue by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TemplateParamValueDTO> findOne(Long id) {
        log.debug("Request to get TemplateParamValue : {}", id);
        return templateParamValueRepository.findById(id)
            .map(templateParamValueMapper::toDto);
    }

    /**
     * Delete the templateParamValue by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TemplateParamValue : {}", id);
        templateParamValueRepository.deleteById(id);
    }
}
