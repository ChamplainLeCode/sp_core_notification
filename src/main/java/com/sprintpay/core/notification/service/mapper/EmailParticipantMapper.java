package com.sprintpay.core.notification.service.mapper;


import com.sprintpay.core.notification.domain.*;
import com.sprintpay.core.notification.service.dto.EmailParticipantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmailParticipant} and its DTO {@link EmailParticipantDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmailNotificationMapper.class})
public interface EmailParticipantMapper extends EntityMapper<EmailParticipantDTO, EmailParticipant> {

    @Mapping(source = "emailNotification.id", target = "emailNotificationId")
    EmailParticipantDTO toDto(EmailParticipant emailParticipant);

    @Mapping(source = "emailNotificationId", target = "emailNotification")
    EmailParticipant toEntity(EmailParticipantDTO emailParticipantDTO);

    default EmailParticipant fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmailParticipant emailParticipant = new EmailParticipant();
        emailParticipant.setId(id);
        return emailParticipant;
    }
}
