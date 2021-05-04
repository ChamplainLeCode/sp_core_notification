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

import com.sprintpay.core.notification.domain.EmailNotification;
import com.sprintpay.core.notification.domain.*; // for static metamodels
import com.sprintpay.core.notification.repository.EmailNotificationRepository;
import com.sprintpay.core.notification.service.dto.EmailNotificationCriteria;
import com.sprintpay.core.notification.service.dto.EmailNotificationDTO;
import com.sprintpay.core.notification.service.mapper.EmailNotificationMapper;

/**
 * Service for executing complex queries for {@link EmailNotification} entities in the database.
 * The main input is a {@link EmailNotificationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmailNotificationDTO} or a {@link Page} of {@link EmailNotificationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmailNotificationQueryService extends QueryService<EmailNotification> {

    private final Logger log = LoggerFactory.getLogger(EmailNotificationQueryService.class);

    private final EmailNotificationRepository emailNotificationRepository;

    private final EmailNotificationMapper emailNotificationMapper;

    public EmailNotificationQueryService(EmailNotificationRepository emailNotificationRepository, EmailNotificationMapper emailNotificationMapper) {
        this.emailNotificationRepository = emailNotificationRepository;
        this.emailNotificationMapper = emailNotificationMapper;
    }

    /**
     * Return a {@link List} of {@link EmailNotificationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmailNotificationDTO> findByCriteria(EmailNotificationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmailNotification> specification = createSpecification(criteria);
        return emailNotificationMapper.toDto(emailNotificationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmailNotificationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmailNotificationDTO> findByCriteria(EmailNotificationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmailNotification> specification = createSpecification(criteria);
        return emailNotificationRepository.findAll(specification, page)
            .map(emailNotificationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmailNotificationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmailNotification> specification = createSpecification(criteria);
        return emailNotificationRepository.count(specification);
    }

    /**
     * Function to convert {@link EmailNotificationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmailNotification> createSpecification(EmailNotificationCriteria criteria) {
        Specification<EmailNotification> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmailNotification_.id));
            }
            if (criteria.getMessage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMessage(), EmailNotification_.message));
            }
            if (criteria.getSujet() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSujet(), EmailNotification_.sujet));
            }
            if (criteria.getEmetteurId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmetteurId(),
                    root -> root.join(EmailNotification_.emetteur, JoinType.LEFT).get(MicroServiceParticipant_.id)));
            }
            if (criteria.getDestinatairesId() != null) {
                specification = specification.and(buildSpecification(criteria.getDestinatairesId(),
                    root -> root.join(EmailNotification_.destinataires, JoinType.LEFT).get(EmailParticipant_.id)));
            }
            if (criteria.getParamValuesId() != null) {
                specification = specification.and(buildSpecification(criteria.getParamValuesId(),
                    root -> root.join(EmailNotification_.paramValues, JoinType.LEFT).get(TemplateParamValue_.id)));
            }
        }
        return specification;
    }
}
