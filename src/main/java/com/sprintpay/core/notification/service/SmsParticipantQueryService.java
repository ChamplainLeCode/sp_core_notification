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

import com.sprintpay.core.notification.domain.SmsParticipant;
import com.sprintpay.core.notification.domain.*; // for static metamodels
import com.sprintpay.core.notification.repository.SmsParticipantRepository;
import com.sprintpay.core.notification.service.dto.SmsParticipantCriteria;
import com.sprintpay.core.notification.service.dto.SmsParticipantDTO;
import com.sprintpay.core.notification.service.mapper.SmsParticipantMapper;

/**
 * Service for executing complex queries for {@link SmsParticipant} entities in the database.
 * The main input is a {@link SmsParticipantCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SmsParticipantDTO} or a {@link Page} of {@link SmsParticipantDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SmsParticipantQueryService extends QueryService<SmsParticipant> {

    private final Logger log = LoggerFactory.getLogger(SmsParticipantQueryService.class);

    private final SmsParticipantRepository smsParticipantRepository;

    private final SmsParticipantMapper smsParticipantMapper;

    public SmsParticipantQueryService(SmsParticipantRepository smsParticipantRepository, SmsParticipantMapper smsParticipantMapper) {
        this.smsParticipantRepository = smsParticipantRepository;
        this.smsParticipantMapper = smsParticipantMapper;
    }

    /**
     * Return a {@link List} of {@link SmsParticipantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SmsParticipantDTO> findByCriteria(SmsParticipantCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SmsParticipant> specification = createSpecification(criteria);
        return smsParticipantMapper.toDto(smsParticipantRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SmsParticipantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SmsParticipantDTO> findByCriteria(SmsParticipantCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SmsParticipant> specification = createSpecification(criteria);
        return smsParticipantRepository.findAll(specification, page)
            .map(smsParticipantMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SmsParticipantCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SmsParticipant> specification = createSpecification(criteria);
        return smsParticipantRepository.count(specification);
    }

    /**
     * Function to convert {@link SmsParticipantCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SmsParticipant> createSpecification(SmsParticipantCriteria criteria) {
        Specification<SmsParticipant> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SmsParticipant_.id));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), SmsParticipant_.phone));
            }
            if (criteria.getOperator() != null) {
                specification = specification.and(buildSpecification(criteria.getOperator(), SmsParticipant_.operator));
            }
            if (criteria.getSmsNotificationId() != null) {
                specification = specification.and(buildSpecification(criteria.getSmsNotificationId(),
                    root -> root.join(SmsParticipant_.smsNotification, JoinType.LEFT).get(SmsNotification_.id)));
            }
        }
        return specification;
    }
}
