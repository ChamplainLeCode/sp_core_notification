package com.sprintpay.core.notification.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sprintpay.core.notification.web.rest.TestUtil;

public class TemplateParamValueTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TemplateParamValue.class);
        TemplateParamValue templateParamValue1 = new TemplateParamValue();
        templateParamValue1.setId(1L);
        TemplateParamValue templateParamValue2 = new TemplateParamValue();
        templateParamValue2.setId(templateParamValue1.getId());
        assertThat(templateParamValue1).isEqualTo(templateParamValue2);
        templateParamValue2.setId(2L);
        assertThat(templateParamValue1).isNotEqualTo(templateParamValue2);
        templateParamValue1.setId(null);
        assertThat(templateParamValue1).isNotEqualTo(templateParamValue2);
    }
}
