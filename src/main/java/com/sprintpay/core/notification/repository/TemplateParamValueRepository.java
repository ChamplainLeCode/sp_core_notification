package com.sprintpay.core.notification.repository;

import com.sprintpay.core.notification.domain.TemplateParamValue;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TemplateParamValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TemplateParamValueRepository extends JpaRepository<TemplateParamValue, Long> {
}
