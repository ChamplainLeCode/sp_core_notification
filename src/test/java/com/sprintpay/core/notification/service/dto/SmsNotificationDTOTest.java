package com.sprintpay.core.notification.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sprintpay.core.notification.web.rest.TestUtil;

public class SmsNotificationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SmsNotificationDTO.class);
        SmsNotificationDTO smsNotificationDTO1 = new SmsNotificationDTO();
        smsNotificationDTO1.setId(1L);
        SmsNotificationDTO smsNotificationDTO2 = new SmsNotificationDTO();
        assertThat(smsNotificationDTO1).isNotEqualTo(smsNotificationDTO2);
        smsNotificationDTO2.setId(smsNotificationDTO1.getId());
        assertThat(smsNotificationDTO1).isEqualTo(smsNotificationDTO2);
        smsNotificationDTO2.setId(2L);
        assertThat(smsNotificationDTO1).isNotEqualTo(smsNotificationDTO2);
        smsNotificationDTO1.setId(null);
        assertThat(smsNotificationDTO1).isNotEqualTo(smsNotificationDTO2);
    }
}
