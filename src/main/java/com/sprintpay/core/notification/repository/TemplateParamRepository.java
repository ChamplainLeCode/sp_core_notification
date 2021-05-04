package com.sprintpay.core.notification.repository;

import com.sprintpay.core.notification.domain.TemplateParam;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TemplateParam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TemplateParamRepository extends JpaRepository<TemplateParam, Long>, JpaSpecificationExecutor<TemplateParam> {
}
