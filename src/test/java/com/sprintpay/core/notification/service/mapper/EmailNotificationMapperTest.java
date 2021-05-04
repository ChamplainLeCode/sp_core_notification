package com.sprintpay.core.notification.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EmailNotificationMapperTest {

    private EmailNotificationMapper emailNotificationMapper;

    @BeforeEach
    public void setUp() {
        emailNotificationMapper = new EmailNotificationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(emailNotificationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(emailNotificationMapper.fromId(null)).isNull();
    }
}
