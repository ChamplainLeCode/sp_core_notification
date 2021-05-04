package com.sprintpay.core.notification.service.mapper;


import com.sprintpay.core.notification.domain.*;
import com.sprintpay.core.notification.service.dto.SmsParticipantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SmsParticipant} and its DTO {@link SmsParticipantDTO}.
 */
@Mapper(componentModel = "spring", uses = {SmsNotificationMapper.class})
public interface SmsParticipantMapper extends EntityMapper<SmsParticipantDTO, SmsParticipant> {

    @Mapping(source = "smsNotification.id", target = "smsNotificationId")
    SmsParticipantDTO toDto(SmsParticipant smsParticipant);

    @Mapping(source = "smsNotificationId", target = "smsNotification")
    SmsParticipant toEntity(SmsParticipantDTO smsParticipantDTO);

    default SmsParticipant fromId(Long id) {
        if (id == null) {
            return null;
        }
        SmsParticipant smsParticipant = new SmsParticipant();
        smsParticipant.setId(id);
        return smsParticipant;
    }
}
