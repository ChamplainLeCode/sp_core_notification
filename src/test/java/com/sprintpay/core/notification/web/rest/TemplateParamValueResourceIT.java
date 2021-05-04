package com.sprintpay.core.notification.web.rest;

import com.sprintpay.core.notification.CorenotificationApp;
import com.sprintpay.core.notification.domain.TemplateParamValue;
import com.sprintpay.core.notification.repository.TemplateParamValueRepository;
import com.sprintpay.core.notification.service.TemplateParamValueService;
import com.sprintpay.core.notification.service.dto.TemplateParamValueDTO;
import com.sprintpay.core.notification.service.mapper.TemplateParamValueMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TemplateParamValueResource} REST controller.
 */
@SpringBootTest(classes = CorenotificationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TemplateParamValueResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private TemplateParamValueRepository templateParamValueRepository;

    @Autowired
    private TemplateParamValueMapper templateParamValueMapper;

    @Autowired
    private TemplateParamValueService templateParamValueService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTemplateParamValueMockMvc;

    private TemplateParamValue templateParamValue;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TemplateParamValue createEntity(EntityManager em) {
        TemplateParamValue templateParamValue = new TemplateParamValue()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE);
        return templateParamValue;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TemplateParamValue createUpdatedEntity(EntityManager em) {
        TemplateParamValue templateParamValue = new TemplateParamValue()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE);
        return templateParamValue;
    }

    @BeforeEach
    public void initTest() {
        templateParamValue = createEntity(em);
    }

    @Test
    @Transactional
    public void createTemplateParamValue() throws Exception {
        int databaseSizeBeforeCreate = templateParamValueRepository.findAll().size();
        // Create the TemplateParamValue
        TemplateParamValueDTO templateParamValueDTO = templateParamValueMapper.toDto(templateParamValue);
        restTemplateParamValueMockMvc.perform(post("/api/template-param-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(templateParamValueDTO)))
            .andExpect(status().isCreated());

        // Validate the TemplateParamValue in the database
        List<TemplateParamValue> templateParamValueList = templateParamValueRepository.findAll();
        assertThat(templateParamValueList).hasSize(databaseSizeBeforeCreate + 1);
        TemplateParamValue testTemplateParamValue = templateParamValueList.get(templateParamValueList.size() - 1);
        assertThat(testTemplateParamValue.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTemplateParamValue.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createTemplateParamValueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = templateParamValueRepository.findAll().size();

        // Create the TemplateParamValue with an existing ID
        templateParamValue.setId(1L);
        TemplateParamValueDTO templateParamValueDTO = templateParamValueMapper.toDto(templateParamValue);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTemplateParamValueMockMvc.perform(post("/api/template-param-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(templateParamValueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TemplateParamValue in the database
        List<TemplateParamValue> templateParamValueList = templateParamValueRepository.findAll();
        assertThat(templateParamValueList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateParamValueRepository.findAll().size();
        // set the field null
        templateParamValue.setName(null);

        // Create the TemplateParamValue, which fails.
        TemplateParamValueDTO templateParamValueDTO = templateParamValueMapper.toDto(templateParamValue);


        restTemplateParamValueMockMvc.perform(post("/api/template-param-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(templateParamValueDTO)))
            .andExpect(status().isBadRequest());

        List<TemplateParamValue> templateParamValueList = templateParamValueRepository.findAll();
        assertThat(templateParamValueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateParamValueRepository.findAll().size();
        // set the field null
        templateParamValue.setValue(null);

        // Create the TemplateParamValue, which fails.
        TemplateParamValueDTO templateParamValueDTO = templateParamValueMapper.toDto(templateParamValue);


        restTemplateParamValueMockMvc.perform(post("/api/template-param-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(templateParamValueDTO)))
            .andExpect(status().isBadRequest());

        List<TemplateParamValue> templateParamValueList = templateParamValueRepository.findAll();
        assertThat(templateParamValueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTemplateParamValues() throws Exception {
        // Initialize the database
        templateParamValueRepository.saveAndFlush(templateParamValue);

        // Get all the templateParamValueList
        restTemplateParamValueMockMvc.perform(get("/api/template-param-values?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(templateParamValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getTemplateParamValue() throws Exception {
        // Initialize the database
        templateParamValueRepository.saveAndFlush(templateParamValue);

        // Get the templateParamValue
        restTemplateParamValueMockMvc.perform(get("/api/template-param-values/{id}", templateParamValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(templateParamValue.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }
    @Test
    @Transactional
    public void getNonExistingTemplateParamValue() throws Exception {
        // Get the templateParamValue
        restTemplateParamValueMockMvc.perform(get("/api/template-param-values/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTemplateParamValue() throws Exception {
        // Initialize the database
        templateParamValueRepository.saveAndFlush(templateParamValue);

        int databaseSizeBeforeUpdate = templateParamValueRepository.findAll().size();

        // Update the templateParamValue
        TemplateParamValue updatedTemplateParamValue = templateParamValueRepository.findById(templateParamValue.getId()).get();
        // Disconnect from session so that the updates on updatedTemplateParamValue are not directly saved in db
        em.detach(updatedTemplateParamValue);
        updatedTemplateParamValue
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE);
        TemplateParamValueDTO templateParamValueDTO = templateParamValueMapper.toDto(updatedTemplateParamValue);

        restTemplateParamValueMockMvc.perform(put("/api/template-param-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(templateParamValueDTO)))
            .andExpect(status().isOk());

        // Validate the TemplateParamValue in the database
        List<TemplateParamValue> templateParamValueList = templateParamValueRepository.findAll();
        assertThat(templateParamValueList).hasSize(databaseSizeBeforeUpdate);
        TemplateParamValue testTemplateParamValue = templateParamValueList.get(templateParamValueList.size() - 1);
        assertThat(testTemplateParamValue.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTemplateParamValue.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingTemplateParamValue() throws Exception {
        int databaseSizeBeforeUpdate = templateParamValueRepository.findAll().size();

        // Create the TemplateParamValue
        TemplateParamValueDTO templateParamValueDTO = templateParamValueMapper.toDto(templateParamValue);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTemplateParamValueMockMvc.perform(put("/api/template-param-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(templateParamValueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TemplateParamValue in the database
        List<TemplateParamValue> templateParamValueList = templateParamValueRepository.findAll();
        assertThat(templateParamValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTemplateParamValue() throws Exception {
        // Initialize the database
        templateParamValueRepository.saveAndFlush(templateParamValue);

        int databaseSizeBeforeDelete = templateParamValueRepository.findAll().size();

        // Delete the templateParamValue
        restTemplateParamValueMockMvc.perform(delete("/api/template-param-values/{id}", templateParamValue.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TemplateParamValue> templateParamValueList = templateParamValueRepository.findAll();
        assertThat(templateParamValueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
