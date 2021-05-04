package com.sprintpay.core.notification.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EmailParticipantMapperTest {

    private EmailParticipantMapper emailParticipantMapper;

    @BeforeEach
    public void setUp() {
        emailParticipantMapper = new EmailParticipantMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(emailParticipantMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(emailParticipantMapper.fromId(null)).isNull();
    }
}
