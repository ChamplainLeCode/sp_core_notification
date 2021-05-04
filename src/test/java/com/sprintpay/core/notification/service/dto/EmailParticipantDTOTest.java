package com.sprintpay.core.notification.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sprintpay.core.notification.web.rest.TestUtil;

public class EmailParticipantDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailParticipantDTO.class);
        EmailParticipantDTO emailParticipantDTO1 = new EmailParticipantDTO();
        emailParticipantDTO1.setId(1L);
        EmailParticipantDTO emailParticipantDTO2 = new EmailParticipantDTO();
        assertThat(emailParticipantDTO1).isNotEqualTo(emailParticipantDTO2);
        emailParticipantDTO2.setId(emailParticipantDTO1.getId());
        assertThat(emailParticipantDTO1).isEqualTo(emailParticipantDTO2);
        emailParticipantDTO2.setId(2L);
        assertThat(emailParticipantDTO1).isNotEqualTo(emailParticipantDTO2);
        emailParticipantDTO1.setId(null);
        assertThat(emailParticipantDTO1).isNotEqualTo(emailParticipantDTO2);
    }
}
