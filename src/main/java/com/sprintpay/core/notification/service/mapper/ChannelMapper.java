package com.sprintpay.core.notification.service.mapper;


import com.sprintpay.core.notification.domain.*;
import com.sprintpay.core.notification.service.dto.ChannelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Channel} and its DTO {@link ChannelDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ChannelMapper extends EntityMapper<ChannelDTO, Channel> {


    @Mapping(target = "params", ignore = true)
    @Mapping(target = "removeParams", ignore = true)
    Channel toEntity(ChannelDTO channelDTO);

    default Channel fromId(Long id) {
        if (id == null) {
            return null;
        }
        Channel channel = new Channel();
        channel.setId(id);
        return channel;
    }
}
