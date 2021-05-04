package com.sprintpay.core.notification.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SmsParticipantMapperTest {

    private SmsParticipantMapper smsParticipantMapper;

    @BeforeEach
    public void setUp() {
        smsParticipantMapper = new SmsParticipantMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(smsParticipantMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(smsParticipantMapper.fromId(null)).isNull();
    }
}
