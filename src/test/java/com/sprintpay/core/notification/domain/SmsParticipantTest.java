package com.sprintpay.core.notification.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sprintpay.core.notification.web.rest.TestUtil;

public class SmsParticipantTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SmsParticipant.class);
        SmsParticipant smsParticipant1 = new SmsParticipant();
        smsParticipant1.setId(1L);
        SmsParticipant smsParticipant2 = new SmsParticipant();
        smsParticipant2.setId(smsParticipant1.getId());
        assertThat(smsParticipant1).isEqualTo(smsParticipant2);
        smsParticipant2.setId(2L);
        assertThat(smsParticipant1).isNotEqualTo(smsParticipant2);
        smsParticipant1.setId(null);
        assertThat(smsParticipant1).isNotEqualTo(smsParticipant2);
    }
}
