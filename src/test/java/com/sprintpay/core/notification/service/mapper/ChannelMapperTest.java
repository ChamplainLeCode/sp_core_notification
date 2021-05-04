package com.sprintpay.core.notification.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ChannelMapperTest {

    private ChannelMapper channelMapper;

    @BeforeEach
    public void setUp() {
        channelMapper = new ChannelMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(channelMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(channelMapper.fromId(null)).isNull();
    }
}
