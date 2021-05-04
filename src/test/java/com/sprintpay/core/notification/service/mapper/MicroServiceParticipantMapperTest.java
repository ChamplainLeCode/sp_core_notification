package com.sprintpay.core.notification.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MicroServiceParticipantMapperTest {

    private MicroServiceParticipantMapper microServiceParticipantMapper;

    @BeforeEach
    public void setUp() {
        microServiceParticipantMapper = new MicroServiceParticipantMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(microServiceParticipantMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(microServiceParticipantMapper.fromId(null)).isNull();
    }
}
