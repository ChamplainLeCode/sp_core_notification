package com.sprintpay.core.notification.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TemplateParamValueMapperTest {

    private TemplateParamValueMapper templateParamValueMapper;

    @BeforeEach
    public void setUp() {
        templateParamValueMapper = new TemplateParamValueMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(templateParamValueMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(templateParamValueMapper.fromId(null)).isNull();
    }
}
