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

import com.sprintpay.core.notification.domain.MicroServiceParticipant;
import com.sprintpay.core.notification.domain.*; // for static metamodels
import com.sprintpay.core.notification.repository.MicroServiceParticipantRepository;
import com.sprintpay.core.notification.service.dto.MicroServiceParticipantCriteria;
import com.sprintpay.core.notification.service.dto.MicroServiceParticipantDTO;
import com.sprintpay.core.notification.service.mapper.MicroServiceParticipantMapper;

/**
 * Service for executing complex queries for {@link MicroServiceParticipant} entities in the database.
 * The main input is a {@link MicroServiceParticipantCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MicroServiceParticipantDTO} or a {@link Page} of {@link MicroServiceParticipantDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MicroServiceParticipantQueryService extends QueryService<MicroServiceParticipant> {

    private final Logger log = LoggerFactory.getLogger(MicroServiceParticipantQueryService.class);

    private final MicroServiceParticipantRepository microServiceParticipantRepository;

    private final MicroServiceParticipantMapper microServiceParticipantMapper;

    public MicroServiceParticipantQueryService(MicroServiceParticipantRepository microServiceParticipantRepository, MicroServiceParticipantMapper microServiceParticipantMapper) {
        this.microServiceParticipantRepository = microServiceParticipantRepository;
        this.microServiceParticipantMapper = microServiceParticipantMapper;
    }

    /**
     * Return a {@link List} of {@link MicroServiceParticipantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MicroServiceParticipantDTO> findByCriteria(MicroServiceParticipantCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MicroServiceParticipant> specification = createSpecification(criteria);
        return microServiceParticipantMapper.toDto(microServiceParticipantRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MicroServiceParticipantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MicroServiceParticipantDTO> findByCriteria(MicroServiceParticipantCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MicroServiceParticipant> specification = createSpecification(criteria);
        return microServiceParticipantRepository.findAll(specification, page)
            .map(microServiceParticipantMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MicroServiceParticipantCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MicroServiceParticipant> specification = createSpecification(criteria);
        return microServiceParticipantRepository.count(specification);
    }

    /**
     * Function to convert {@link MicroServiceParticipantCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MicroServiceParticipant> createSpecification(MicroServiceParticipantCriteria criteria) {
        Specification<MicroServiceParticipant> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MicroServiceParticipant_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), MicroServiceParticipant_.name));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserId(), MicroServiceParticipant_.userId));
            }
            if (criteria.getUserName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserName(), MicroServiceParticipant_.userName));
            }
            if (criteria.getUserRole() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserRole(), MicroServiceParticipant_.userRole));
            }
        }
        return specification;
    }
}
