package com.sprintpay.core.notification.service.mapper;


import com.sprintpay.core.notification.domain.*;
import com.sprintpay.core.notification.service.dto.MicroServiceParticipantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MicroServiceParticipant} and its DTO {@link MicroServiceParticipantDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MicroServiceParticipantMapper extends EntityMapper<MicroServiceParticipantDTO, MicroServiceParticipant> {



    default MicroServiceParticipant fromId(Long id) {
        if (id == null) {
            return null;
        }
        MicroServiceParticipant microServiceParticipant = new MicroServiceParticipant();
        microServiceParticipant.setId(id);
        return microServiceParticipant;
    }
}
