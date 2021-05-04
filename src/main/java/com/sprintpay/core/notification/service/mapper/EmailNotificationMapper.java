package com.sprintpay.core.notification.service.mapper;


import com.sprintpay.core.notification.domain.*;
import com.sprintpay.core.notification.service.dto.EmailNotificationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmailNotification} and its DTO {@link EmailNotificationDTO}.
 */
@Mapper(componentModel = "spring", uses = {MicroServiceParticipantMapper.class})
public interface EmailNotificationMapper extends EntityMapper<EmailNotificationDTO, EmailNotification> {

    @Mapping(source = "emetteur.id", target = "emetteurId")
    EmailNotificationDTO toDto(EmailNotification emailNotification);

    @Mapping(source = "emetteurId", target = "emetteur")
    @Mapping(target = "destinataires", ignore = true)
    @Mapping(target = "removeDestinataires", ignore = true)
    @Mapping(target = "paramValues", ignore = true)
    @Mapping(target = "removeParamValues", ignore = true)
    EmailNotification toEntity(EmailNotificationDTO emailNotificationDTO);

    default EmailNotification fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmailNotification emailNotification = new EmailNotification();
        emailNotification.setId(id);
        return emailNotification;
    }
}
