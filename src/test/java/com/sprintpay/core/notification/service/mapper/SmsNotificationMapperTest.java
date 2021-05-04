package com.sprintpay.core.notification.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SmsNotificationMapperTest {

    private SmsNotificationMapper smsNotificationMapper;

    @BeforeEach
    public void setUp() {
        smsNotificationMapper = new SmsNotificationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(smsNotificationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(smsNotificationMapper.fromId(null)).isNull();
    }
}
