package com.sprintpay.core.notification.web.rest;

import com.sprintpay.core.notification.CorenotificationApp;
import com.sprintpay.core.notification.domain.TemplateParam;
import com.sprintpay.core.notification.domain.Channel;
import com.sprintpay.core.notification.repository.TemplateParamRepository;
import com.sprintpay.core.notification.service.TemplateParamService;
import com.sprintpay.core.notification.service.dto.TemplateParamDTO;
import com.sprintpay.core.notification.service.mapper.TemplateParamMapper;
import com.sprintpay.core.notification.service.dto.TemplateParamCriteria;
import com.sprintpay.core.notification.service.TemplateParamQueryService;

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
 * Integration tests for the {@link TemplateParamResource} REST controller.
 */
@SpringBootTest(classes = CorenotificationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TemplateParamResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TemplateParamRepository templateParamRepository;

    @Autowired
    private TemplateParamMapper templateParamMapper;

    @Autowired
    private TemplateParamService templateParamService;

    @Autowired
    private TemplateParamQueryService templateParamQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTemplateParamMockMvc;

    private TemplateParam templateParam;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TemplateParam createEntity(EntityManager em) {
        TemplateParam templateParam = new TemplateParam()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION);
        return templateParam;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TemplateParam createUpdatedEntity(EntityManager em) {
        TemplateParam templateParam = new TemplateParam()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION);
        return templateParam;
    }

    @BeforeEach
    public void initTest() {
        templateParam = createEntity(em);
    }

    @Test
    @Transactional
    public void createTemplateParam() throws Exception {
        int databaseSizeBeforeCreate = templateParamRepository.findAll().size();
        // Create the TemplateParam
        TemplateParamDTO templateParamDTO = templateParamMapper.toDto(templateParam);
        restTemplateParamMockMvc.perform(post("/api/template-params")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(templateParamDTO)))
            .andExpect(status().isCreated());

        // Validate the TemplateParam in the database
        List<TemplateParam> templateParamList = templateParamRepository.findAll();
        assertThat(templateParamList).hasSize(databaseSizeBeforeCreate + 1);
        TemplateParam testTemplateParam = templateParamList.get(templateParamList.size() - 1);
        assertThat(testTemplateParam.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTemplateParam.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testTemplateParam.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTemplateParamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = templateParamRepository.findAll().size();

        // Create the TemplateParam with an existing ID
        templateParam.setId(1L);
        TemplateParamDTO templateParamDTO = templateParamMapper.toDto(templateParam);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTemplateParamMockMvc.perform(post("/api/template-params")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(templateParamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TemplateParam in the database
        List<TemplateParam> templateParamList = templateParamRepository.findAll();
        assertThat(templateParamList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateParamRepository.findAll().size();
        // set the field null
        templateParam.setName(null);

        // Create the TemplateParam, which fails.
        TemplateParamDTO templateParamDTO = templateParamMapper.toDto(templateParam);


        restTemplateParamMockMvc.perform(post("/api/template-params")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(templateParamDTO)))
            .andExpect(status().isBadRequest());

        List<TemplateParam> templateParamList = templateParamRepository.findAll();
        assertThat(templateParamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateParamRepository.findAll().size();
        // set the field null
        templateParam.setValue(null);

        // Create the TemplateParam, which fails.
        TemplateParamDTO templateParamDTO = templateParamMapper.toDto(templateParam);


        restTemplateParamMockMvc.perform(post("/api/template-params")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(templateParamDTO)))
            .andExpect(status().isBadRequest());

        List<TemplateParam> templateParamList = templateParamRepository.findAll();
        assertThat(templateParamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTemplateParams() throws Exception {
        // Initialize the database
        templateParamRepository.saveAndFlush(templateParam);

        // Get all the templateParamList
        restTemplateParamMockMvc.perform(get("/api/template-params?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(templateParam.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getTemplateParam() throws Exception {
        // Initialize the database
        templateParamRepository.saveAndFlush(templateParam);

        // Get the templateParam
        restTemplateParamMockMvc.perform(get("/api/template-params/{id}", templateParam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(templateParam.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }


    @Test
    @Transactional
    public void getTemplateParamsByIdFiltering() throws Exception {
        // Initialize the database
        templateParamRepository.saveAndFlush(templateParam);

        Long id = templateParam.getId();

        defaultTemplateParamShouldBeFound("id.equals=" + id);
        defaultTemplateParamShouldNotBeFound("id.notEquals=" + id);

        defaultTemplateParamShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTemplateParamShouldNotBeFound("id.greaterThan=" + id);

        defaultTemplateParamShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTemplateParamShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTemplateParamsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        templateParamRepository.saveAndFlush(templateParam);

        // Get all the templateParamList where name equals to DEFAULT_NAME
        defaultTemplateParamShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the templateParamList where name equals to UPDATED_NAME
        defaultTemplateParamShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTemplateParamsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        templateParamRepository.saveAndFlush(templateParam);

        // Get all the templateParamList where name not equals to DEFAULT_NAME
        defaultTemplateParamShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the templateParamList where name not equals to UPDATED_NAME
        defaultTemplateParamShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTemplateParamsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        templateParamRepository.saveAndFlush(templateParam);

        // Get all the templateParamList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTemplateParamShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the templateParamList where name equals to UPDATED_NAME
        defaultTemplateParamShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTemplateParamsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateParamRepository.saveAndFlush(templateParam);

        // Get all the templateParamList where name is not null
        defaultTemplateParamShouldBeFound("name.specified=true");

        // Get all the templateParamList where name is null
        defaultTemplateParamShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllTemplateParamsByNameContainsSomething() throws Exception {
        // Initialize the database
        templateParamRepository.saveAndFlush(templateParam);

        // Get all the templateParamList where name contains DEFAULT_NAME
        defaultTemplateParamShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the templateParamList where name contains UPDATED_NAME
        defaultTemplateParamShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTemplateParamsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        templateParamRepository.saveAndFlush(templateParam);

        // Get all the templateParamList where name does not contain DEFAULT_NAME
        defaultTemplateParamShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the templateParamList where name does not contain UPDATED_NAME
        defaultTemplateParamShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllTemplateParamsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        templateParamRepository.saveAndFlush(templateParam);

        // Get all the templateParamList where value equals to DEFAULT_VALUE
        defaultTemplateParamShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the templateParamList where value equals to UPDATED_VALUE
        defaultTemplateParamShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllTemplateParamsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        templateParamRepository.saveAndFlush(templateParam);

        // Get all the templateParamList where value not equals to DEFAULT_VALUE
        defaultTemplateParamShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the templateParamList where value not equals to UPDATED_VALUE
        defaultTemplateParamShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllTemplateParamsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        templateParamRepository.saveAndFlush(templateParam);

        // Get all the templateParamList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultTemplateParamShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the templateParamList where value equals to UPDATED_VALUE
        defaultTemplateParamShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllTemplateParamsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateParamRepository.saveAndFlush(templateParam);

        // Get all the templateParamList where value is not null
        defaultTemplateParamShouldBeFound("value.specified=true");

        // Get all the templateParamList where value is null
        defaultTemplateParamShouldNotBeFound("value.specified=false");
    }
                @Test
    @Transactional
    public void getAllTemplateParamsByValueContainsSomething() throws Exception {
        // Initialize the database
        templateParamRepository.saveAndFlush(templateParam);

        // Get all the templateParamList where value contains DEFAULT_VALUE
        defaultTemplateParamShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the templateParamList where value contains UPDATED_VALUE
        defaultTemplateParamShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllTemplateParamsByValueNotContainsSomething() throws Exception {
        // Initialize the database
        templateParamRepository.saveAndFlush(templateParam);

        // Get all the templateParamList where value does not contain DEFAULT_VALUE
        defaultTemplateParamShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the templateParamList where value does not contain UPDATED_VALUE
        defaultTemplateParamShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }


    @Test
    @Transactional
    public void getAllTemplateParamsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        templateParamRepository.saveAndFlush(templateParam);

        // Get all the templateParamList where description equals to DEFAULT_DESCRIPTION
        defaultTemplateParamShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the templateParamList where description equals to UPDATED_DESCRIPTION
        defaultTemplateParamShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTemplateParamsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        templateParamRepository.saveAndFlush(templateParam);

        // Get all the templateParamList where description not equals to DEFAULT_DESCRIPTION
        defaultTemplateParamShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the templateParamList where description not equals to UPDATED_DESCRIPTION
        defaultTemplateParamShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTemplateParamsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        templateParamRepository.saveAndFlush(templateParam);

        // Get all the templateParamList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultTemplateParamShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the templateParamList where description equals to UPDATED_DESCRIPTION
        defaultTemplateParamShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTemplateParamsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateParamRepository.saveAndFlush(templateParam);

        // Get all the templateParamList where description is not null
        defaultTemplateParamShouldBeFound("description.specified=true");

        // Get all the templateParamList where description is null
        defaultTemplateParamShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllTemplateParamsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        templateParamRepository.saveAndFlush(templateParam);

        // Get all the templateParamList where description contains DEFAULT_DESCRIPTION
        defaultTemplateParamShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the templateParamList where description contains UPDATED_DESCRIPTION
        defaultTemplateParamShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTemplateParamsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        templateParamRepository.saveAndFlush(templateParam);

        // Get all the templateParamList where description does not contain DEFAULT_DESCRIPTION
        defaultTemplateParamShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the templateParamList where description does not contain UPDATED_DESCRIPTION
        defaultTemplateParamShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllTemplateParamsByChannelIsEqualToSomething() throws Exception {
        // Initialize the database
        templateParamRepository.saveAndFlush(templateParam);
        Channel channel = ChannelResourceIT.createEntity(em);
        em.persist(channel);
        em.flush();
        templateParam.setChannel(channel);
        templateParamRepository.saveAndFlush(templateParam);
        Long channelId = channel.getId();

        // Get all the templateParamList where channel equals to channelId
        defaultTemplateParamShouldBeFound("channelId.equals=" + channelId);

        // Get all the templateParamList where channel equals to channelId + 1
        defaultTemplateParamShouldNotBeFound("channelId.equals=" + (channelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTemplateParamShouldBeFound(String filter) throws Exception {
        restTemplateParamMockMvc.perform(get("/api/template-params?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(templateParam.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restTemplateParamMockMvc.perform(get("/api/template-params/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTemplateParamShouldNotBeFound(String filter) throws Exception {
        restTemplateParamMockMvc.perform(get("/api/template-params?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTemplateParamMockMvc.perform(get("/api/template-params/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTemplateParam() throws Exception {
        // Get the templateParam
        restTemplateParamMockMvc.perform(get("/api/template-params/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTemplateParam() throws Exception {
        // Initialize the database
        templateParamRepository.saveAndFlush(templateParam);

        int databaseSizeBeforeUpdate = templateParamRepository.findAll().size();

        // Update the templateParam
        TemplateParam updatedTemplateParam = templateParamRepository.findById(templateParam.getId()).get();
        // Disconnect from session so that the updates on updatedTemplateParam are not directly saved in db
        em.detach(updatedTemplateParam);
        updatedTemplateParam
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION);
        TemplateParamDTO templateParamDTO = templateParamMapper.toDto(updatedTemplateParam);

        restTemplateParamMockMvc.perform(put("/api/template-params")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(templateParamDTO)))
            .andExpect(status().isOk());

        // Validate the TemplateParam in the database
        List<TemplateParam> templateParamList = templateParamRepository.findAll();
        assertThat(templateParamList).hasSize(databaseSizeBeforeUpdate);
        TemplateParam testTemplateParam = templateParamList.get(templateParamList.size() - 1);
        assertThat(testTemplateParam.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTemplateParam.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testTemplateParam.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTemplateParam() throws Exception {
        int databaseSizeBeforeUpdate = templateParamRepository.findAll().size();

        // Create the TemplateParam
        TemplateParamDTO templateParamDTO = templateParamMapper.toDto(templateParam);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTemplateParamMockMvc.perform(put("/api/template-params")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(templateParamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TemplateParam in the database
        List<TemplateParam> templateParamList = templateParamRepository.findAll();
        assertThat(templateParamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTemplateParam() throws Exception {
        // Initialize the database
        templateParamRepository.saveAndFlush(templateParam);

        int databaseSizeBeforeDelete = templateParamRepository.findAll().size();

        // Delete the templateParam
        restTemplateParamMockMvc.perform(delete("/api/template-params/{id}", templateParam.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TemplateParam> templateParamList = templateParamRepository.findAll();
        assertThat(templateParamList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
