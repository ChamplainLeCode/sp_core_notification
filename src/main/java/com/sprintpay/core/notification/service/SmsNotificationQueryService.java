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

import com.sprintpay.core.notification.domain.SmsNotification;
import com.sprintpay.core.notification.domain.*; // for static metamodels
import com.sprintpay.core.notification.repository.SmsNotificationRepository;
import com.sprintpay.core.notification.service.dto.SmsNotificationCriteria;
import com.sprintpay.core.notification.service.dto.SmsNotificationDTO;
import com.sprintpay.core.notification.service.mapper.SmsNotificationMapper;

/**
 * Service for executing complex queries for {@link SmsNotification} entities in the database.
 * The main input is a {@link SmsNotificationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SmsNotificationDTO} or a {@link Page} of {@link SmsNotificationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SmsNotificationQueryService extends QueryService<SmsNotification> {

    private final Logger log = LoggerFactory.getLogger(SmsNotificationQueryService.class);

    private final SmsNotificationRepository smsNotificationRepository;

    private final SmsNotificationMapper smsNotificationMapper;

    public SmsNotificationQueryService(SmsNotificationRepository smsNotificationRepository, SmsNotificationMapper smsNotificationMapper) {
        this.smsNotificationRepository = smsNotificationRepository;
        this.smsNotificationMapper = smsNotificationMapper;
    }

    /**
     * Return a {@link List} of {@link SmsNotificationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SmsNotificationDTO> findByCriteria(SmsNotificationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SmsNotification> specification = createSpecification(criteria);
        return smsNotificationMapper.toDto(smsNotificationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SmsNotificationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SmsNotificationDTO> findByCriteria(SmsNotificationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SmsNotification> specification = createSpecification(criteria);
        return smsNotificationRepository.findAll(specification, page)
            .map(smsNotificationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SmsNotificationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SmsNotification> specification = createSpecification(criteria);
        return smsNotificationRepository.count(specification);
    }

    /**
     * Function to convert {@link SmsNotificationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SmsNotification> createSpecification(SmsNotificationCriteria criteria) {
        Specification<SmsNotification> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SmsNotification_.id));
            }
            if (criteria.getMessage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMessage(), SmsNotification_.message));
            }
            if (criteria.getEmetteurId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmetteurId(),
                    root -> root.join(SmsNotification_.emetteur, JoinType.LEFT).get(MicroServiceParticipant_.id)));
            }
            if (criteria.getDestinatairesId() != null) {
                specification = specification.and(buildSpecification(criteria.getDestinatairesId(),
                    root -> root.join(SmsNotification_.destinataires, JoinType.LEFT).get(SmsParticipant_.id)));
            }
            if (criteria.getParamValuesId() != null) {
                specification = specification.and(buildSpecification(criteria.getParamValuesId(),
                    root -> root.join(SmsNotification_.paramValues, JoinType.LEFT).get(TemplateParamValue_.id)));
            }
        }
        return specification;
    }
}
