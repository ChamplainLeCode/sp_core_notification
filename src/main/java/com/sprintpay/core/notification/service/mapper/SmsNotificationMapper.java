package com.sprintpay.core.notification.service.mapper;


import com.sprintpay.core.notification.domain.*;
import com.sprintpay.core.notification.service.dto.SmsNotificationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SmsNotification} and its DTO {@link SmsNotificationDTO}.
 */
@Mapper(componentModel = "spring", uses = {MicroServiceParticipantMapper.class})
public interface SmsNotificationMapper extends EntityMapper<SmsNotificationDTO, SmsNotification> {

    @Mapping(source = "emetteur.id", target = "emetteurId")
    SmsNotificationDTO toDto(SmsNotification smsNotification);

    @Mapping(source = "emetteurId", target = "emetteur")
    @Mapping(target = "destinataires", ignore = true)
    @Mapping(target = "removeDestinataires", ignore = true)
    @Mapping(target = "paramValues", ignore = true)
    @Mapping(target = "removeParamValues", ignore = true)
    SmsNotification toEntity(SmsNotificationDTO smsNotificationDTO);

    default SmsNotification fromId(Long id) {
        if (id == null) {
            return null;
        }
        SmsNotification smsNotification = new SmsNotification();
        smsNotification.setId(id);
        return smsNotification;
    }
}
