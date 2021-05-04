package com.sprintpay.core.notification.web.rest;

import com.sprintpay.core.notification.CorenotificationApp;
import com.sprintpay.core.notification.domain.MicroServiceParticipant;
import com.sprintpay.core.notification.repository.MicroServiceParticipantRepository;
import com.sprintpay.core.notification.service.MicroServiceParticipantService;
import com.sprintpay.core.notification.service.dto.MicroServiceParticipantDTO;
import com.sprintpay.core.notification.service.mapper.MicroServiceParticipantMapper;
import com.sprintpay.core.notification.service.dto.MicroServiceParticipantCriteria;
import com.sprintpay.core.notification.service.MicroServiceParticipantQueryService;

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
 * Integration tests for the {@link MicroServiceParticipantResource} REST controller.
 */
@SpringBootTest(classes = CorenotificationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MicroServiceParticipantResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_USER_ROLE = "BBBBBBBBBB";

    @Autowired
    private MicroServiceParticipantRepository microServiceParticipantRepository;

    @Autowired
    private MicroServiceParticipantMapper microServiceParticipantMapper;

    @Autowired
    private MicroServiceParticipantService microServiceParticipantService;

    @Autowired
    private MicroServiceParticipantQueryService microServiceParticipantQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMicroServiceParticipantMockMvc;

    private MicroServiceParticipant microServiceParticipant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MicroServiceParticipant createEntity(EntityManager em) {
        MicroServiceParticipant microServiceParticipant = new MicroServiceParticipant()
            .name(DEFAULT_NAME)
            .userId(DEFAULT_USER_ID)
            .userName(DEFAULT_USER_NAME)
            .userRole(DEFAULT_USER_ROLE);
        return microServiceParticipant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MicroServiceParticipant createUpdatedEntity(EntityManager em) {
        MicroServiceParticipant microServiceParticipant = new MicroServiceParticipant()
            .name(UPDATED_NAME)
            .userId(UPDATED_USER_ID)
            .userName(UPDATED_USER_NAME)
            .userRole(UPDATED_USER_ROLE);
        return microServiceParticipant;
    }

    @BeforeEach
    public void initTest() {
        microServiceParticipant = createEntity(em);
    }

    @Test
    @Transactional
    public void createMicroServiceParticipant() throws Exception {
        int databaseSizeBeforeCreate = microServiceParticipantRepository.findAll().size();
        // Create the MicroServiceParticipant
        MicroServiceParticipantDTO microServiceParticipantDTO = microServiceParticipantMapper.toDto(microServiceParticipant);
        restMicroServiceParticipantMockMvc.perform(post("/api/micro-service-participants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(microServiceParticipantDTO)))
            .andExpect(status().isCreated());

        // Validate the MicroServiceParticipant in the database
        List<MicroServiceParticipant> microServiceParticipantList = microServiceParticipantRepository.findAll();
        assertThat(microServiceParticipantList).hasSize(databaseSizeBeforeCreate + 1);
        MicroServiceParticipant testMicroServiceParticipant = microServiceParticipantList.get(microServiceParticipantList.size() - 1);
        assertThat(testMicroServiceParticipant.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMicroServiceParticipant.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testMicroServiceParticipant.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testMicroServiceParticipant.getUserRole()).isEqualTo(DEFAULT_USER_ROLE);
    }

    @Test
    @Transactional
    public void createMicroServiceParticipantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = microServiceParticipantRepository.findAll().size();

        // Create the MicroServiceParticipant with an existing ID
        microServiceParticipant.setId(1L);
        MicroServiceParticipantDTO microServiceParticipantDTO = microServiceParticipantMapper.toDto(microServiceParticipant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMicroServiceParticipantMockMvc.perform(post("/api/micro-service-participants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(microServiceParticipantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MicroServiceParticipant in the database
        List<MicroServiceParticipant> microServiceParticipantList = microServiceParticipantRepository.findAll();
        assertThat(microServiceParticipantList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = microServiceParticipantRepository.findAll().size();
        // set the field null
        microServiceParticipant.setName(null);

        // Create the MicroServiceParticipant, which fails.
        MicroServiceParticipantDTO microServiceParticipantDTO = microServiceParticipantMapper.toDto(microServiceParticipant);


        restMicroServiceParticipantMockMvc.perform(post("/api/micro-service-participants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(microServiceParticipantDTO)))
            .andExpect(status().isBadRequest());

        List<MicroServiceParticipant> microServiceParticipantList = microServiceParticipantRepository.findAll();
        assertThat(microServiceParticipantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMicroServiceParticipants() throws Exception {
        // Initialize the database
        microServiceParticipantRepository.saveAndFlush(microServiceParticipant);

        // Get all the microServiceParticipantList
        restMicroServiceParticipantMockMvc.perform(get("/api/micro-service-participants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(microServiceParticipant.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].userRole").value(hasItem(DEFAULT_USER_ROLE)));
    }
    
    @Test
    @Transactional
    public void getMicroServiceParticipant() throws Exception {
        // Initialize the database
        microServiceParticipantRepository.saveAndFlush(microServiceParticipant);

        // Get the microServiceParticipant
        restMicroServiceParticipantMockMvc.perform(get("/api/micro-service-participants/{id}", microServiceParticipant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(microServiceParticipant.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME))
            .andExpect(jsonPath("$.userRole").value(DEFAULT_USER_ROLE));
    }


    @Test
    @Transactional
    public void getMicroServiceParticipantsByIdFiltering() throws Exception {
        // Initialize the database
        microServiceParticipantRepository.saveAndFlush(microServiceParticipant);

        Long id = microServiceParticipant.getId();

        defaultMicroServiceParticipantShouldBeFound("id.equals=" + id);
        defaultMicroServiceParticipantShouldNotBeFound("id.notEquals=" + id);

        defaultMicroServiceParticipantShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMicroServiceParticipantShouldNotBeFound("id.greaterThan=" + id);

        defaultMicroServiceParticipantShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMicroServiceParticipantShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMicroServiceParticipantsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        microServiceParticipantRepository.saveAndFlush(microServiceParticipant);

        // Get all the microServiceParticipantList where name equals to DEFAULT_NAME
        defaultMicroServiceParticipantShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the microServiceParticipantList where name equals to UPDATED_NAME
        defaultMicroServiceParticipantShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMicroServiceParticipantsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        microServiceParticipantRepository.saveAndFlush(microServiceParticipant);

        // Get all the microServiceParticipantList where name not equals to DEFAULT_NAME
        defaultMicroServiceParticipantShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the microServiceParticipantList where name not equals to UPDATED_NAME
        defaultMicroServiceParticipantShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMicroServiceParticipantsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        microServiceParticipantRepository.saveAndFlush(microServiceParticipant);

        // Get all the microServiceParticipantList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMicroServiceParticipantShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the microServiceParticipantList where name equals to UPDATED_NAME
        defaultMicroServiceParticipantShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMicroServiceParticipantsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        microServiceParticipantRepository.saveAndFlush(microServiceParticipant);

        // Get all the microServiceParticipantList where name is not null
        defaultMicroServiceParticipantShouldBeFound("name.specified=true");

        // Get all the microServiceParticipantList where name is null
        defaultMicroServiceParticipantShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllMicroServiceParticipantsByNameContainsSomething() throws Exception {
        // Initialize the database
        microServiceParticipantRepository.saveAndFlush(microServiceParticipant);

        // Get all the microServiceParticipantList where name contains DEFAULT_NAME
        defaultMicroServiceParticipantShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the microServiceParticipantList where name contains UPDATED_NAME
        defaultMicroServiceParticipantShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMicroServiceParticipantsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        microServiceParticipantRepository.saveAndFlush(microServiceParticipant);

        // Get all the microServiceParticipantList where name does not contain DEFAULT_NAME
        defaultMicroServiceParticipantShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the microServiceParticipantList where name does not contain UPDATED_NAME
        defaultMicroServiceParticipantShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllMicroServiceParticipantsByUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        microServiceParticipantRepository.saveAndFlush(microServiceParticipant);

        // Get all the microServiceParticipantList where userId equals to DEFAULT_USER_ID
        defaultMicroServiceParticipantShouldBeFound("userId.equals=" + DEFAULT_USER_ID);

        // Get all the microServiceParticipantList where userId equals to UPDATED_USER_ID
        defaultMicroServiceParticipantShouldNotBeFound("userId.equals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllMicroServiceParticipantsByUserIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        microServiceParticipantRepository.saveAndFlush(microServiceParticipant);

        // Get all the microServiceParticipantList where userId not equals to DEFAULT_USER_ID
        defaultMicroServiceParticipantShouldNotBeFound("userId.notEquals=" + DEFAULT_USER_ID);

        // Get all the microServiceParticipantList where userId not equals to UPDATED_USER_ID
        defaultMicroServiceParticipantShouldBeFound("userId.notEquals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllMicroServiceParticipantsByUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        microServiceParticipantRepository.saveAndFlush(microServiceParticipant);

        // Get all the microServiceParticipantList where userId in DEFAULT_USER_ID or UPDATED_USER_ID
        defaultMicroServiceParticipantShouldBeFound("userId.in=" + DEFAULT_USER_ID + "," + UPDATED_USER_ID);

        // Get all the microServiceParticipantList where userId equals to UPDATED_USER_ID
        defaultMicroServiceParticipantShouldNotBeFound("userId.in=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllMicroServiceParticipantsByUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        microServiceParticipantRepository.saveAndFlush(microServiceParticipant);

        // Get all the microServiceParticipantList where userId is not null
        defaultMicroServiceParticipantShouldBeFound("userId.specified=true");

        // Get all the microServiceParticipantList where userId is null
        defaultMicroServiceParticipantShouldNotBeFound("userId.specified=false");
    }
                @Test
    @Transactional
    public void getAllMicroServiceParticipantsByUserIdContainsSomething() throws Exception {
        // Initialize the database
        microServiceParticipantRepository.saveAndFlush(microServiceParticipant);

        // Get all the microServiceParticipantList where userId contains DEFAULT_USER_ID
        defaultMicroServiceParticipantShouldBeFound("userId.contains=" + DEFAULT_USER_ID);

        // Get all the microServiceParticipantList where userId contains UPDATED_USER_ID
        defaultMicroServiceParticipantShouldNotBeFound("userId.contains=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllMicroServiceParticipantsByUserIdNotContainsSomething() throws Exception {
        // Initialize the database
        microServiceParticipantRepository.saveAndFlush(microServiceParticipant);

        // Get all the microServiceParticipantList where userId does not contain DEFAULT_USER_ID
        defaultMicroServiceParticipantShouldNotBeFound("userId.doesNotContain=" + DEFAULT_USER_ID);

        // Get all the microServiceParticipantList where userId does not contain UPDATED_USER_ID
        defaultMicroServiceParticipantShouldBeFound("userId.doesNotContain=" + UPDATED_USER_ID);
    }


    @Test
    @Transactional
    public void getAllMicroServiceParticipantsByUserNameIsEqualToSomething() throws Exception {
        // Initialize the database
        microServiceParticipantRepository.saveAndFlush(microServiceParticipant);

        // Get all the microServiceParticipantList where userName equals to DEFAULT_USER_NAME
        defaultMicroServiceParticipantShouldBeFound("userName.equals=" + DEFAULT_USER_NAME);

        // Get all the microServiceParticipantList where userName equals to UPDATED_USER_NAME
        defaultMicroServiceParticipantShouldNotBeFound("userName.equals=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllMicroServiceParticipantsByUserNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        microServiceParticipantRepository.saveAndFlush(microServiceParticipant);

        // Get all the microServiceParticipantList where userName not equals to DEFAULT_USER_NAME
        defaultMicroServiceParticipantShouldNotBeFound("userName.notEquals=" + DEFAULT_USER_NAME);

        // Get all the microServiceParticipantList where userName not equals to UPDATED_USER_NAME
        defaultMicroServiceParticipantShouldBeFound("userName.notEquals=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllMicroServiceParticipantsByUserNameIsInShouldWork() throws Exception {
        // Initialize the database
        microServiceParticipantRepository.saveAndFlush(microServiceParticipant);

        // Get all the microServiceParticipantList where userName in DEFAULT_USER_NAME or UPDATED_USER_NAME
        defaultMicroServiceParticipantShouldBeFound("userName.in=" + DEFAULT_USER_NAME + "," + UPDATED_USER_NAME);

        // Get all the microServiceParticipantList where userName equals to UPDATED_USER_NAME
        defaultMicroServiceParticipantShouldNotBeFound("userName.in=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllMicroServiceParticipantsByUserNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        microServiceParticipantRepository.saveAndFlush(microServiceParticipant);

        // Get all the microServiceParticipantList where userName is not null
        defaultMicroServiceParticipantShouldBeFound("userName.specified=true");

        // Get all the microServiceParticipantList where userName is null
        defaultMicroServiceParticipantShouldNotBeFound("userName.specified=false");
    }
                @Test
    @Transactional
    public void getAllMicroServiceParticipantsByUserNameContainsSomething() throws Exception {
        // Initialize the database
        microServiceParticipantRepository.saveAndFlush(microServiceParticipant);

        // Get all the microServiceParticipantList where userName contains DEFAULT_USER_NAME
        defaultMicroServiceParticipantShouldBeFound("userName.contains=" + DEFAULT_USER_NAME);

        // Get all the microServiceParticipantList where userName contains UPDATED_USER_NAME
        defaultMicroServiceParticipantShouldNotBeFound("userName.contains=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllMicroServiceParticipantsByUserNameNotContainsSomething() throws Exception {
        // Initialize the database
        microServiceParticipantRepository.saveAndFlush(microServiceParticipant);

        // Get all the microServiceParticipantList where userName does not contain DEFAULT_USER_NAME
        defaultMicroServiceParticipantShouldNotBeFound("userName.doesNotContain=" + DEFAULT_USER_NAME);

        // Get all the microServiceParticipantList where userName does not contain UPDATED_USER_NAME
        defaultMicroServiceParticipantShouldBeFound("userName.doesNotContain=" + UPDATED_USER_NAME);
    }


    @Test
    @Transactional
    public void getAllMicroServiceParticipantsByUserRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        microServiceParticipantRepository.saveAndFlush(microServiceParticipant);

        // Get all the microServiceParticipantList where userRole equals to DEFAULT_USER_ROLE
        defaultMicroServiceParticipantShouldBeFound("userRole.equals=" + DEFAULT_USER_ROLE);

        // Get all the microServiceParticipantList where userRole equals to UPDATED_USER_ROLE
        defaultMicroServiceParticipantShouldNotBeFound("userRole.equals=" + UPDATED_USER_ROLE);
    }

    @Test
    @Transactional
    public void getAllMicroServiceParticipantsByUserRoleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        microServiceParticipantRepository.saveAndFlush(microServiceParticipant);

        // Get all the microServiceParticipantList where userRole not equals to DEFAULT_USER_ROLE
        defaultMicroServiceParticipantShouldNotBeFound("userRole.notEquals=" + DEFAULT_USER_ROLE);

        // Get all the microServiceParticipantList where userRole not equals to UPDATED_USER_ROLE
        defaultMicroServiceParticipantShouldBeFound("userRole.notEquals=" + UPDATED_USER_ROLE);
    }

    @Test
    @Transactional
    public void getAllMicroServiceParticipantsByUserRoleIsInShouldWork() throws Exception {
        // Initialize the database
        microServiceParticipantRepository.saveAndFlush(microServiceParticipant);

        // Get all the microServiceParticipantList where userRole in DEFAULT_USER_ROLE or UPDATED_USER_ROLE
        defaultMicroServiceParticipantShouldBeFound("userRole.in=" + DEFAULT_USER_ROLE + "," + UPDATED_USER_ROLE);

        // Get all the microServiceParticipantList where userRole equals to UPDATED_USER_ROLE
        defaultMicroServiceParticipantShouldNotBeFound("userRole.in=" + UPDATED_USER_ROLE);
    }

    @Test
    @Transactional
    public void getAllMicroServiceParticipantsByUserRoleIsNullOrNotNull() throws Exception {
        // Initialize the database
        microServiceParticipantRepository.saveAndFlush(microServiceParticipant);

        // Get all the microServiceParticipantList where userRole is not null
        defaultMicroServiceParticipantShouldBeFound("userRole.specified=true");

        // Get all the microServiceParticipantList where userRole is null
        defaultMicroServiceParticipantShouldNotBeFound("userRole.specified=false");
    }
                @Test
    @Transactional
    public void getAllMicroServiceParticipantsByUserRoleContainsSomething() throws Exception {
        // Initialize the database
        microServiceParticipantRepository.saveAndFlush(microServiceParticipant);

        // Get all the microServiceParticipantList where userRole contains DEFAULT_USER_ROLE
        defaultMicroServiceParticipantShouldBeFound("userRole.contains=" + DEFAULT_USER_ROLE);

        // Get all the microServiceParticipantList where userRole contains UPDATED_USER_ROLE
        defaultMicroServiceParticipantShouldNotBeFound("userRole.contains=" + UPDATED_USER_ROLE);
    }

    @Test
    @Transactional
    public void getAllMicroServiceParticipantsByUserRoleNotContainsSomething() throws Exception {
        // Initialize the database
        microServiceParticipantRepository.saveAndFlush(microServiceParticipant);

        // Get all the microServiceParticipantList where userRole does not contain DEFAULT_USER_ROLE
        defaultMicroServiceParticipantShouldNotBeFound("userRole.doesNotContain=" + DEFAULT_USER_ROLE);

        // Get all the microServiceParticipantList where userRole does not contain UPDATED_USER_ROLE
        defaultMicroServiceParticipantShouldBeFound("userRole.doesNotContain=" + UPDATED_USER_ROLE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMicroServiceParticipantShouldBeFound(String filter) throws Exception {
        restMicroServiceParticipantMockMvc.perform(get("/api/micro-service-participants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(microServiceParticipant.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].userRole").value(hasItem(DEFAULT_USER_ROLE)));

        // Check, that the count call also returns 1
        restMicroServiceParticipantMockMvc.perform(get("/api/micro-service-participants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMicroServiceParticipantShouldNotBeFound(String filter) throws Exception {
        restMicroServiceParticipantMockMvc.perform(get("/api/micro-service-participants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMicroServiceParticipantMockMvc.perform(get("/api/micro-service-participants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingMicroServiceParticipant() throws Exception {
        // Get the microServiceParticipant
        restMicroServiceParticipantMockMvc.perform(get("/api/micro-service-participants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMicroServiceParticipant() throws Exception {
        // Initialize the database
        microServiceParticipantRepository.saveAndFlush(microServiceParticipant);

        int databaseSizeBeforeUpdate = microServiceParticipantRepository.findAll().size();

        // Update the microServiceParticipant
        MicroServiceParticipant updatedMicroServiceParticipant = microServiceParticipantRepository.findById(microServiceParticipant.getId()).get();
        // Disconnect from session so that the updates on updatedMicroServiceParticipant are not directly saved in db
        em.detach(updatedMicroServiceParticipant);
        updatedMicroServiceParticipant
            .name(UPDATED_NAME)
            .userId(UPDATED_USER_ID)
            .userName(UPDATED_USER_NAME)
            .userRole(UPDATED_USER_ROLE);
        MicroServiceParticipantDTO microServiceParticipantDTO = microServiceParticipantMapper.toDto(updatedMicroServiceParticipant);

        restMicroServiceParticipantMockMvc.perform(put("/api/micro-service-participants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(microServiceParticipantDTO)))
            .andExpect(status().isOk());

        // Validate the MicroServiceParticipant in the database
        List<MicroServiceParticipant> microServiceParticipantList = microServiceParticipantRepository.findAll();
        assertThat(microServiceParticipantList).hasSize(databaseSizeBeforeUpdate);
        MicroServiceParticipant testMicroServiceParticipant = microServiceParticipantList.get(microServiceParticipantList.size() - 1);
        assertThat(testMicroServiceParticipant.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMicroServiceParticipant.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testMicroServiceParticipant.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testMicroServiceParticipant.getUserRole()).isEqualTo(UPDATED_USER_ROLE);
    }

    @Test
    @Transactional
    public void updateNonExistingMicroServiceParticipant() throws Exception {
        int databaseSizeBeforeUpdate = microServiceParticipantRepository.findAll().size();

        // Create the MicroServiceParticipant
        MicroServiceParticipantDTO microServiceParticipantDTO = microServiceParticipantMapper.toDto(microServiceParticipant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMicroServiceParticipantMockMvc.perform(put("/api/micro-service-participants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(microServiceParticipantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MicroServiceParticipant in the database
        List<MicroServiceParticipant> microServiceParticipantList = microServiceParticipantRepository.findAll();
        assertThat(microServiceParticipantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMicroServiceParticipant() throws Exception {
        // Initialize the database
        microServiceParticipantRepository.saveAndFlush(microServiceParticipant);

        int databaseSizeBeforeDelete = microServiceParticipantRepository.findAll().size();

        // Delete the microServiceParticipant
        restMicroServiceParticipantMockMvc.perform(delete("/api/micro-service-participants/{id}", microServiceParticipant.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MicroServiceParticipant> microServiceParticipantList = microServiceParticipantRepository.findAll();
        assertThat(microServiceParticipantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
