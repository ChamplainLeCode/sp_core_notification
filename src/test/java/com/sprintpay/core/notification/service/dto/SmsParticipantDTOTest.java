package com.sprintpay.core.notification.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sprintpay.core.notification.web.rest.TestUtil;

public class SmsParticipantDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SmsParticipantDTO.class);
        SmsParticipantDTO smsParticipantDTO1 = new SmsParticipantDTO();
        smsParticipantDTO1.setId(1L);
        SmsParticipantDTO smsParticipantDTO2 = new SmsParticipantDTO();
        assertThat(smsParticipantDTO1).isNotEqualTo(smsParticipantDTO2);
        smsParticipantDTO2.setId(smsParticipantDTO1.getId());
        assertThat(smsParticipantDTO1).isEqualTo(smsParticipantDTO2);
        smsParticipantDTO2.setId(2L);
        assertThat(smsParticipantDTO1).isNotEqualTo(smsParticipantDTO2);
        smsParticipantDTO1.setId(null);
        assertThat(smsParticipantDTO1).isNotEqualTo(smsParticipantDTO2);
    }
}
