package com.sprintpay.core.notification.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sprintpay.core.notification.web.rest.TestUtil;

public class MicroServiceParticipantDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MicroServiceParticipantDTO.class);
        MicroServiceParticipantDTO microServiceParticipantDTO1 = new MicroServiceParticipantDTO();
        microServiceParticipantDTO1.setId(1L);
        MicroServiceParticipantDTO microServiceParticipantDTO2 = new MicroServiceParticipantDTO();
        assertThat(microServiceParticipantDTO1).isNotEqualTo(microServiceParticipantDTO2);
        microServiceParticipantDTO2.setId(microServiceParticipantDTO1.getId());
        assertThat(microServiceParticipantDTO1).isEqualTo(microServiceParticipantDTO2);
        microServiceParticipantDTO2.setId(2L);
        assertThat(microServiceParticipantDTO1).isNotEqualTo(microServiceParticipantDTO2);
        microServiceParticipantDTO1.setId(null);
        assertThat(microServiceParticipantDTO1).isNotEqualTo(microServiceParticipantDTO2);
    }
}
