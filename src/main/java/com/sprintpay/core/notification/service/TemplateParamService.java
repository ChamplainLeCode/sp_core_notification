package com.sprintpay.core.notification.service;

import com.sprintpay.core.notification.domain.TemplateParam;
import com.sprintpay.core.notification.repository.TemplateParamRepository;
import com.sprintpay.core.notification.service.dto.TemplateParamDTO;
import com.sprintpay.core.notification.service.mapper.TemplateParamMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TemplateParam}.
 */
@Service
@Transactional
public class TemplateParamService {

    private final Logger log = LoggerFactory.getLogger(TemplateParamService.class);

    private final TemplateParamRepository templateParamRepository;

    private final TemplateParamMapper templateParamMapper;

    public TemplateParamService(TemplateParamRepository templateParamRepository, TemplateParamMapper templateParamMapper) {
        this.templateParamRepository = templateParamRepository;
        this.templateParamMapper = templateParamMapper;
    }

    /**
     * Save a templateParam.
     *
     * @param templateParamDTO the entity to save.
     * @return the persisted entity.
     */
    public TemplateParamDTO save(TemplateParamDTO templateParamDTO) {
        log.debug("Request to save TemplateParam : {}", templateParamDTO);
        TemplateParam templateParam = templateParamMapper.toEntity(templateParamDTO);
        templateParam = templateParamRepository.save(templateParam);
        return templateParamMapper.toDto(templateParam);
    }

    /**
     * Get all the templateParams.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TemplateParamDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TemplateParams");
        return templateParamRepository.findAll(pageable)
            .map(templateParamMapper::toDto);
    }


    /**
     * Get one templateParam by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TemplateParamDTO> findOne(Long id) {
        log.debug("Request to get TemplateParam : {}", id);
        return templateParamRepository.findById(id)
            .map(templateParamMapper::toDto);
    }

    /**
     * Delete the templateParam by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TemplateParam : {}", id);
        templateParamRepository.deleteById(id);
    }
}
