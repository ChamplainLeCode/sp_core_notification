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

import com.sprintpay.core.notification.domain.Channel;
import com.sprintpay.core.notification.domain.*; // for static metamodels
import com.sprintpay.core.notification.repository.ChannelRepository;
import com.sprintpay.core.notification.service.dto.ChannelCriteria;
import com.sprintpay.core.notification.service.dto.ChannelDTO;
import com.sprintpay.core.notification.service.mapper.ChannelMapper;

/**
 * Service for executing complex queries for {@link Channel} entities in the database.
 * The main input is a {@link ChannelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ChannelDTO} or a {@link Page} of {@link ChannelDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ChannelQueryService extends QueryService<Channel> {

    private final Logger log = LoggerFactory.getLogger(ChannelQueryService.class);

    private final ChannelRepository channelRepository;

    private final ChannelMapper channelMapper;

    public ChannelQueryService(ChannelRepository channelRepository, ChannelMapper channelMapper) {
        this.channelRepository = channelRepository;
        this.channelMapper = channelMapper;
    }

    /**
     * Return a {@link List} of {@link ChannelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ChannelDTO> findByCriteria(ChannelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Channel> specification = createSpecification(criteria);
        return channelMapper.toDto(channelRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ChannelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ChannelDTO> findByCriteria(ChannelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Channel> specification = createSpecification(criteria);
        return channelRepository.findAll(specification, page)
            .map(channelMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ChannelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Channel> specification = createSpecification(criteria);
        return channelRepository.count(specification);
    }

    /**
     * Function to convert {@link ChannelCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Channel> createSpecification(ChannelCriteria criteria) {
        Specification<Channel> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Channel_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Channel_.type));
            }
            if (criteria.getTemplate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTemplate(), Channel_.template));
            }
            if (criteria.getParamsId() != null) {
                specification = specification.and(buildSpecification(criteria.getParamsId(),
                    root -> root.join(Channel_.params, JoinType.LEFT).get(TemplateParam_.id)));
            }
        }
        return specification;
    }
}
