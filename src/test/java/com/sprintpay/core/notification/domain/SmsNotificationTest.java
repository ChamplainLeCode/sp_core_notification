package com.sprintpay.core.notification.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sprintpay.core.notification.web.rest.TestUtil;

public class SmsNotificationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SmsNotification.class);
        SmsNotification smsNotification1 = new SmsNotification();
        smsNotification1.setId(1L);
        SmsNotification smsNotification2 = new SmsNotification();
        smsNotification2.setId(smsNotification1.getId());
        assertThat(smsNotification1).isEqualTo(smsNotification2);
        smsNotification2.setId(2L);
        assertThat(smsNotification1).isNotEqualTo(smsNotification2);
        smsNotification1.setId(null);
        assertThat(smsNotification1).isNotEqualTo(smsNotification2);
    }
}
