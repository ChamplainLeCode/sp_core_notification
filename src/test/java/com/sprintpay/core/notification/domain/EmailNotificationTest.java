package com.sprintpay.core.notification.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sprintpay.core.notification.web.rest.TestUtil;

public class EmailNotificationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailNotification.class);
        EmailNotification emailNotification1 = new EmailNotification();
        emailNotification1.setId(1L);
        EmailNotification emailNotification2 = new EmailNotification();
        emailNotification2.setId(emailNotification1.getId());
        assertThat(emailNotification1).isEqualTo(emailNotification2);
        emailNotification2.setId(2L);
        assertThat(emailNotification1).isNotEqualTo(emailNotification2);
        emailNotification1.setId(null);
        assertThat(emailNotification1).isNotEqualTo(emailNotification2);
    }
}
