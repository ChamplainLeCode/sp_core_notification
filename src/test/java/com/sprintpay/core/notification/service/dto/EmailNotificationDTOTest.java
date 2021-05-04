package com.sprintpay.core.notification.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sprintpay.core.notification.web.rest.TestUtil;

public class EmailNotificationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailNotificationDTO.class);
        EmailNotificationDTO emailNotificationDTO1 = new EmailNotificationDTO();
        emailNotificationDTO1.setId(1L);
        EmailNotificationDTO emailNotificationDTO2 = new EmailNotificationDTO();
        assertThat(emailNotificationDTO1).isNotEqualTo(emailNotificationDTO2);
        emailNotificationDTO2.setId(emailNotificationDTO1.getId());
        assertThat(emailNotificationDTO1).isEqualTo(emailNotificationDTO2);
        emailNotificationDTO2.setId(2L);
        assertThat(emailNotificationDTO1).isNotEqualTo(emailNotificationDTO2);
        emailNotificationDTO1.setId(null);
        assertThat(emailNotificationDTO1).isNotEqualTo(emailNotificationDTO2);
    }
}
