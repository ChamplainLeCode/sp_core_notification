package com.sprintpay.core.notification.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TemplateParamMapperTest {

    private TemplateParamMapper templateParamMapper;

    @BeforeEach
    public void setUp() {
        templateParamMapper = new TemplateParamMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(templateParamMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(templateParamMapper.fromId(null)).isNull();
    }
}
