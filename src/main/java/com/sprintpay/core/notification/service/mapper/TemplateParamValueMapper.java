package com.sprintpay.core.notification.service.mapper;


import com.sprintpay.core.notification.domain.*;
import com.sprintpay.core.notification.service.dto.TemplateParamValueDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TemplateParamValue} and its DTO {@link TemplateParamValueDTO}.
 */
@Mapper(componentModel = "spring", uses = {TemplateParamMapper.class, SmsNotificationMapper.class, EmailNotificationMapper.class})
public interface TemplateParamValueMapper extends EntityMapper<TemplateParamValueDTO, TemplateParamValue> {

    @Mapping(source = "param.id", target = "paramId")
    @Mapping(source = "smsNotification.id", target = "smsNotificationId")
    @Mapping(source = "emailNotification.id", target = "emailNotificationId")
    TemplateParamValueDTO toDto(TemplateParamValue templateParamValue);

    @Mapping(source = "paramId", target = "param")
    @Mapping(source = "smsNotificationId", target = "smsNotification")
    @Mapping(source = "emailNotificationId", target = "emailNotification")
    TemplateParamValue toEntity(TemplateParamValueDTO templateParamValueDTO);

    default TemplateParamValue fromId(Long id) {
        if (id == null) {
            return null;
        }
        TemplateParamValue templateParamValue = new TemplateParamValue();
        templateParamValue.setId(id);
        return templateParamValue;
    }
}
