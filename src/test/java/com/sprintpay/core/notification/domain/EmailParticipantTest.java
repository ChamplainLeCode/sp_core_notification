package com.sprintpay.core.notification.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sprintpay.core.notification.web.rest.TestUtil;

public class EmailParticipantTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailParticipant.class);
        EmailParticipant emailParticipant1 = new EmailParticipant();
        emailParticipant1.setId(1L);
        EmailParticipant emailParticipant2 = new EmailParticipant();
        emailParticipant2.setId(emailParticipant1.getId());
        assertThat(emailParticipant1).isEqualTo(emailParticipant2);
        emailParticipant2.setId(2L);
        assertThat(emailParticipant1).isNotEqualTo(emailParticipant2);
        emailParticipant1.setId(null);
        assertThat(emailParticipant1).isNotEqualTo(emailParticipant2);
    }
}
