package com.sprintpay.core.notification.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.sprintpay.core.notification.domain.TemplateParam;
import com.sprintpay.core.notification.domain.*; // for static metamodels
import com.sprintpay.core.notification.repository.TemplateParamRepository;
import com.sprintpay.core.notification.service.dto.TemplateParamCriteria;
import com.sprintpay.core.notification.service.dto.TemplateParamDTO;
import com.sprintpay.core.notification.service.mapper.TemplateParamMapper;

/**
 * Service for executing complex queries for {@link TemplateParam} entities in the database.
 * The main input is a {@link TemplateParamCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TemplateParamDTO} or a {@link Page} of {@link TemplateParamDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TemplateParamQueryService extends QueryService<TemplateParam> {

    private final Logger log = LoggerFactory.getLogger(TemplateParamQueryService.class);

    private final TemplateParamRepository templateParamRepository;

    private final TemplateParamMapper templateParamMapper;

    public TemplateParamQueryService(TemplateParamRepository templateParamRepository, TemplateParamMapper templateParamMapper) {
        this.templateParamRepository = templateParamRepository;
        this.templateParamMapper = templateParamMapper;
    }

    /**
     * Return a {@link List} of {@link TemplateParamDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TemplateParamDTO> findByCriteria(TemplateParamCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TemplateParam> specification = createSpecification(criteria);
        return templateParamMapper.toDto(templateParamRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TemplateParamDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TemplateParamDTO> findByCriteria(TemplateParamCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TemplateParam> specification = createSpecification(criteria);
        return templateParamRepository.findAll(specification, page)
            .map(templateParamMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TemplateParamCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TemplateParam> specification = createSpecification(criteria);
        return templateParamRepository.count(specification);
    }

    /**
     * Function to convert {@link TemplateParamCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TemplateParam> createSpecification(TemplateParamCriteria criteria) {
        Specification<TemplateParam> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TemplateParam_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TemplateParam_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), TemplateParam_.value));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), TemplateParam_.description));
            }
            if (criteria.getChannelId() != null) {
                specification = specification.and(buildSpecification(criteria.getChannelId(),
                    root -> root.join(TemplateParam_.channel, JoinType.LEFT).get(Channel_.id)));
            }
        }
        return specification;
    }
}
