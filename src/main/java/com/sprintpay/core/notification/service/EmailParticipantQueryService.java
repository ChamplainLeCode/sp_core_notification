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

import com.sprintpay.core.notification.domain.EmailParticipant;
import com.sprintpay.core.notification.domain.*; // for static metamodels
import com.sprintpay.core.notification.repository.EmailParticipantRepository;
import com.sprintpay.core.notification.service.dto.EmailParticipantCriteria;
import com.sprintpay.core.notification.service.dto.EmailParticipantDTO;
import com.sprintpay.core.notification.service.mapper.EmailParticipantMapper;

/**
 * Service for executing complex queries for {@link EmailParticipant} entities in the database.
 * The main input is a {@link EmailParticipantCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmailParticipantDTO} or a {@link Page} of {@link EmailParticipantDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmailParticipantQueryService extends QueryService<EmailParticipant> {

    private final Logger log = LoggerFactory.getLogger(EmailParticipantQueryService.class);

    private final EmailParticipantRepository emailParticipantRepository;

    private final EmailParticipantMapper emailParticipantMapper;

    public EmailParticipantQueryService(EmailParticipantRepository emailParticipantRepository, EmailParticipantMapper emailParticipantMapper) {
        this.emailParticipantRepository = emailParticipantRepository;
        this.emailParticipantMapper = emailParticipantMapper;
    }

    /**
     * Return a {@link List} of {@link EmailParticipantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmailParticipantDTO> findByCriteria(EmailParticipantCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmailParticipant> specification = createSpecification(criteria);
        return emailParticipantMapper.toDto(emailParticipantRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmailParticipantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmailParticipantDTO> findByCriteria(EmailParticipantCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmailParticipant> specification = createSpecification(criteria);
        return emailParticipantRepository.findAll(specification, page)
            .map(emailParticipantMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmailParticipantCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmailParticipant> specification = createSpecification(criteria);
        return emailParticipantRepository.count(specification);
    }

    /**
     * Function to convert {@link EmailParticipantCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmailParticipant> createSpecification(EmailParticipantCriteria criteria) {
        Specification<EmailParticipant> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmailParticipant_.id));
            }
            if (criteria.getMail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMail(), EmailParticipant_.mail));
            }
            if (criteria.getEmailNotificationId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmailNotificationId(),
                    root -> root.join(EmailParticipant_.emailNotification, JoinType.LEFT).get(EmailNotification_.id)));
            }
        }
        return specification;
    }
}
