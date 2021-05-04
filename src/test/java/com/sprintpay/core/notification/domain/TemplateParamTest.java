package com.sprintpay.core.notification.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sprintpay.core.notification.web.rest.TestUtil;

public class TemplateParamTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TemplateParam.class);
        TemplateParam templateParam1 = new TemplateParam();
        templateParam1.setId(1L);
        TemplateParam templateParam2 = new TemplateParam();
        templateParam2.setId(templateParam1.getId());
        assertThat(templateParam1).isEqualTo(templateParam2);
        templateParam2.setId(2L);
        assertThat(templateParam1).isNotEqualTo(templateParam2);
        templateParam1.setId(null);
        assertThat(templateParam1).isNotEqualTo(templateParam2);
    }
}
