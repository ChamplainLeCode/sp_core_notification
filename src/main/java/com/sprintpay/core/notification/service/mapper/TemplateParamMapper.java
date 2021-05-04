package com.sprintpay.core.notification.service.mapper;


import com.sprintpay.core.notification.domain.*;
import com.sprintpay.core.notification.service.dto.TemplateParamDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TemplateParam} and its DTO {@link TemplateParamDTO}.
 */
@Mapper(componentModel = "spring", uses = {ChannelMapper.class})
public interface TemplateParamMapper extends EntityMapper<TemplateParamDTO, TemplateParam> {

    @Mapping(source = "channel.id", target = "channelId")
    TemplateParamDTO toDto(TemplateParam templateParam);

    @Mapping(source = "channelId", target = "channel")
    TemplateParam toEntity(TemplateParamDTO templateParamDTO);

    default TemplateParam fromId(Long id) {
        if (id == null) {
            return null;
        }
        TemplateParam templateParam = new TemplateParam();
        templateParam.setId(id);
        return templateParam;
    }
}
