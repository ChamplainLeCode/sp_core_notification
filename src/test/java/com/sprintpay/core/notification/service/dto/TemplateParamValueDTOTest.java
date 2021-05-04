package com.sprintpay.core.notification.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sprintpay.core.notification.web.rest.TestUtil;

public class TemplateParamValueDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TemplateParamValueDTO.class);
        TemplateParamValueDTO templateParamValueDTO1 = new TemplateParamValueDTO();
        templateParamValueDTO1.setId(1L);
        TemplateParamValueDTO templateParamValueDTO2 = new TemplateParamValueDTO();
        assertThat(templateParamValueDTO1).isNotEqualTo(templateParamValueDTO2);
        templateParamValueDTO2.setId(templateParamValueDTO1.getId());
        assertThat(templateParamValueDTO1).isEqualTo(templateParamValueDTO2);
        templateParamValueDTO2.setId(2L);
        assertThat(templateParamValueDTO1).isNotEqualTo(templateParamValueDTO2);
        templateParamValueDTO1.setId(null);
        assertThat(templateParamValueDTO1).isNotEqualTo(templateParamValueDTO2);
    }
}
