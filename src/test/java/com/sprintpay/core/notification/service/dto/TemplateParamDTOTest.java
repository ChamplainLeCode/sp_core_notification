package com.sprintpay.core.notification.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sprintpay.core.notification.web.rest.TestUtil;

public class TemplateParamDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TemplateParamDTO.class);
        TemplateParamDTO templateParamDTO1 = new TemplateParamDTO();
        templateParamDTO1.setId(1L);
        TemplateParamDTO templateParamDTO2 = new TemplateParamDTO();
        assertThat(templateParamDTO1).isNotEqualTo(templateParamDTO2);
        templateParamDTO2.setId(templateParamDTO1.getId());
        assertThat(templateParamDTO1).isEqualTo(templateParamDTO2);
        templateParamDTO2.setId(2L);
        assertThat(templateParamDTO1).isNotEqualTo(templateParamDTO2);
        templateParamDTO1.setId(null);
        assertThat(templateParamDTO1).isNotEqualTo(templateParamDTO2);
    }
}
