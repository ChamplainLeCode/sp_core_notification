package com.sprintpay.core.notification.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sprintpay.core.notification.web.rest.TestUtil;

public class MicroServiceParticipantTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MicroServiceParticipant.class);
        MicroServiceParticipant microServiceParticipant1 = new MicroServiceParticipant();
        microServiceParticipant1.setId(1L);
        MicroServiceParticipant microServiceParticipant2 = new MicroServiceParticipant();
        microServiceParticipant2.setId(microServiceParticipant1.getId());
        assertThat(microServiceParticipant1).isEqualTo(microServiceParticipant2);
        microServiceParticipant2.setId(2L);
        assertThat(microServiceParticipant1).isNotEqualTo(microServiceParticipant2);
        microServiceParticipant1.setId(null);
        assertThat(microServiceParticipant1).isNotEqualTo(microServiceParticipant2);
    }
}
