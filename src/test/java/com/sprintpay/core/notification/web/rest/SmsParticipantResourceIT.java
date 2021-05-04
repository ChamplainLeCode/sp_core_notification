package com.sprintpay.core.notification.web.rest;

import com.sprintpay.core.notification.CorenotificationApp;
import com.sprintpay.core.notification.domain.SmsParticipant;
import com.sprintpay.core.notification.domain.SmsNotification;
import com.sprintpay.core.notification.repository.SmsParticipantRepository;
import com.sprintpay.core.notification.service.SmsParticipantService;
import com.sprintpay.core.notification.service.dto.SmsParticipantDTO;
import com.sprintpay.core.notification.service.mapper.SmsParticipantMapper;
import com.sprintpay.core.notification.service.dto.SmsParticipantCriteria;
import com.sprintpay.core.notification.service.SmsParticipantQueryService;

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

import com.sprintpay.core.notification.domain.enumeration.Operator;
/**
 * Integration tests for the {@link SmsParticipantResource} REST controller.
 */
@SpringBootTest(classes = CorenotificationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SmsParticipantResourceIT {

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final Operator DEFAULT_OPERATOR = Operator.MTN;
    private static final Operator UPDATED_OPERATOR = Operator.ORANGE;

    @Autowired
    private SmsParticipantRepository smsParticipantRepository;

    @Autowired
    private SmsParticipantMapper smsParticipantMapper;

    @Autowired
    private SmsParticipantService smsParticipantService;

    @Autowired
    private SmsParticipantQueryService smsParticipantQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSmsParticipantMockMvc;

    private SmsParticipant smsParticipant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SmsParticipant createEntity(EntityManager em) {
        SmsParticipant smsParticipant = new SmsParticipant()
            .phone(DEFAULT_PHONE)
            .operator(DEFAULT_OPERATOR);
        return smsParticipant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SmsParticipant createUpdatedEntity(EntityManager em) {
        SmsParticipant smsParticipant = new SmsParticipant()
            .phone(UPDATED_PHONE)
            .operator(UPDATED_OPERATOR);
        return smsParticipant;
    }

    @BeforeEach
    public void initTest() {
        smsParticipant = createEntity(em);
    }

    @Test
    @Transactional
    public void createSmsParticipant() throws Exception {
        int databaseSizeBeforeCreate = smsParticipantRepository.findAll().size();
        // Create the SmsParticipant
        SmsParticipantDTO smsParticipantDTO = smsParticipantMapper.toDto(smsParticipant);
        restSmsParticipantMockMvc.perform(post("/api/sms-participants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(smsParticipantDTO)))
            .andExpect(status().isCreated());

        // Validate the SmsParticipant in the database
        List<SmsParticipant> smsParticipantList = smsParticipantRepository.findAll();
        assertThat(smsParticipantList).hasSize(databaseSizeBeforeCreate + 1);
        SmsParticipant testSmsParticipant = smsParticipantList.get(smsParticipantList.size() - 1);
        assertThat(testSmsParticipant.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testSmsParticipant.getOperator()).isEqualTo(DEFAULT_OPERATOR);
    }

    @Test
    @Transactional
    public void createSmsParticipantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = smsParticipantRepository.findAll().size();

        // Create the SmsParticipant with an existing ID
        smsParticipant.setId(1L);
        SmsParticipantDTO smsParticipantDTO = smsParticipantMapper.toDto(smsParticipant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSmsParticipantMockMvc.perform(post("/api/sms-participants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(smsParticipantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SmsParticipant in the database
        List<SmsParticipant> smsParticipantList = smsParticipantRepository.findAll();
        assertThat(smsParticipantList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = smsParticipantRepository.findAll().size();
        // set the field null
        smsParticipant.setPhone(null);

        // Create the SmsParticipant, which fails.
        SmsParticipantDTO smsParticipantDTO = smsParticipantMapper.toDto(smsParticipant);


        restSmsParticipantMockMvc.perform(post("/api/sms-participants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(smsParticipantDTO)))
            .andExpect(status().isBadRequest());

        List<SmsParticipant> smsParticipantList = smsParticipantRepository.findAll();
        assertThat(smsParticipantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSmsParticipants() throws Exception {
        // Initialize the database
        smsParticipantRepository.saveAndFlush(smsParticipant);

        // Get all the smsParticipantList
        restSmsParticipantMockMvc.perform(get("/api/sms-participants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(smsParticipant.getId().intValue())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].operator").value(hasItem(DEFAULT_OPERATOR.toString())));
    }
    
    @Test
    @Transactional
    public void getSmsParticipant() throws Exception {
        // Initialize the database
        smsParticipantRepository.saveAndFlush(smsParticipant);

        // Get the smsParticipant
        restSmsParticipantMockMvc.perform(get("/api/sms-participants/{id}", smsParticipant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(smsParticipant.getId().intValue()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.operator").value(DEFAULT_OPERATOR.toString()));
    }


    @Test
    @Transactional
    public void getSmsParticipantsByIdFiltering() throws Exception {
        // Initialize the database
        smsParticipantRepository.saveAndFlush(smsParticipant);

        Long id = smsParticipant.getId();

        defaultSmsParticipantShouldBeFound("id.equals=" + id);
        defaultSmsParticipantShouldNotBeFound("id.notEquals=" + id);

        defaultSmsParticipantShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSmsParticipantShouldNotBeFound("id.greaterThan=" + id);

        defaultSmsParticipantShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSmsParticipantShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSmsParticipantsByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        smsParticipantRepository.saveAndFlush(smsParticipant);

        // Get all the smsParticipantList where phone equals to DEFAULT_PHONE
        defaultSmsParticipantShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the smsParticipantList where phone equals to UPDATED_PHONE
        defaultSmsParticipantShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllSmsParticipantsByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        smsParticipantRepository.saveAndFlush(smsParticipant);

        // Get all the smsParticipantList where phone not equals to DEFAULT_PHONE
        defaultSmsParticipantShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the smsParticipantList where phone not equals to UPDATED_PHONE
        defaultSmsParticipantShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllSmsParticipantsByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        smsParticipantRepository.saveAndFlush(smsParticipant);

        // Get all the smsParticipantList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultSmsParticipantShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the smsParticipantList where phone equals to UPDATED_PHONE
        defaultSmsParticipantShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllSmsParticipantsByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        smsParticipantRepository.saveAndFlush(smsParticipant);

        // Get all the smsParticipantList where phone is not null
        defaultSmsParticipantShouldBeFound("phone.specified=true");

        // Get all the smsParticipantList where phone is null
        defaultSmsParticipantShouldNotBeFound("phone.specified=false");
    }
                @Test
    @Transactional
    public void getAllSmsParticipantsByPhoneContainsSomething() throws Exception {
        // Initialize the database
        smsParticipantRepository.saveAndFlush(smsParticipant);

        // Get all the smsParticipantList where phone contains DEFAULT_PHONE
        defaultSmsParticipantShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the smsParticipantList where phone contains UPDATED_PHONE
        defaultSmsParticipantShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllSmsParticipantsByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        smsParticipantRepository.saveAndFlush(smsParticipant);

        // Get all the smsParticipantList where phone does not contain DEFAULT_PHONE
        defaultSmsParticipantShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the smsParticipantList where phone does not contain UPDATED_PHONE
        defaultSmsParticipantShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }


    @Test
    @Transactional
    public void getAllSmsParticipantsByOperatorIsEqualToSomething() throws Exception {
        // Initialize the database
        smsParticipantRepository.saveAndFlush(smsParticipant);

        // Get all the smsParticipantList where operator equals to DEFAULT_OPERATOR
        defaultSmsParticipantShouldBeFound("operator.equals=" + DEFAULT_OPERATOR);

        // Get all the smsParticipantList where operator equals to UPDATED_OPERATOR
        defaultSmsParticipantShouldNotBeFound("operator.equals=" + UPDATED_OPERATOR);
    }

    @Test
    @Transactional
    public void getAllSmsParticipantsByOperatorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        smsParticipantRepository.saveAndFlush(smsParticipant);

        // Get all the smsParticipantList where operator not equals to DEFAULT_OPERATOR
        defaultSmsParticipantShouldNotBeFound("operator.notEquals=" + DEFAULT_OPERATOR);

        // Get all the smsParticipantList where operator not equals to UPDATED_OPERATOR
        defaultSmsParticipantShouldBeFound("operator.notEquals=" + UPDATED_OPERATOR);
    }

    @Test
    @Transactional
    public void getAllSmsParticipantsByOperatorIsInShouldWork() throws Exception {
        // Initialize the database
        smsParticipantRepository.saveAndFlush(smsParticipant);

        // Get all the smsParticipantList where operator in DEFAULT_OPERATOR or UPDATED_OPERATOR
        defaultSmsParticipantShouldBeFound("operator.in=" + DEFAULT_OPERATOR + "," + UPDATED_OPERATOR);

        // Get all the smsParticipantList where operator equals to UPDATED_OPERATOR
        defaultSmsParticipantShouldNotBeFound("operator.in=" + UPDATED_OPERATOR);
    }

    @Test
    @Transactional
    public void getAllSmsParticipantsByOperatorIsNullOrNotNull() throws Exception {
        // Initialize the database
        smsParticipantRepository.saveAndFlush(smsParticipant);

        // Get all the smsParticipantList where operator is not null
        defaultSmsParticipantShouldBeFound("operator.specified=true");

        // Get all the smsParticipantList where operator is null
        defaultSmsParticipantShouldNotBeFound("operator.specified=false");
    }

    @Test
    @Transactional
    public void getAllSmsParticipantsBySmsNotificationIsEqualToSomething() throws Exception {
        // Initialize the database
        smsParticipantRepository.saveAndFlush(smsParticipant);
        SmsNotification smsNotification = SmsNotificationResourceIT.createEntity(em);
        em.persist(smsNotification);
        em.flush();
        smsParticipant.setSmsNotification(smsNotification);
        smsParticipantRepository.saveAndFlush(smsParticipant);
        Long smsNotificationId = smsNotification.getId();

        // Get all the smsParticipantList where smsNotification equals to smsNotificationId
        defaultSmsParticipantShouldBeFound("smsNotificationId.equals=" + smsNotificationId);

        // Get all the smsParticipantList where smsNotification equals to smsNotificationId + 1
        defaultSmsParticipantShouldNotBeFound("smsNotificationId.equals=" + (smsNotificationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSmsParticipantShouldBeFound(String filter) throws Exception {
        restSmsParticipantMockMvc.perform(get("/api/sms-participants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(smsParticipant.getId().intValue())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].operator").value(hasItem(DEFAULT_OPERATOR.toString())));

        // Check, that the count call also returns 1
        restSmsParticipantMockMvc.perform(get("/api/sms-participants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSmsParticipantShouldNotBeFound(String filter) throws Exception {
        restSmsParticipantMockMvc.perform(get("/api/sms-participants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSmsParticipantMockMvc.perform(get("/api/sms-participants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSmsParticipant() throws Exception {
        // Get the smsParticipant
        restSmsParticipantMockMvc.perform(get("/api/sms-participants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSmsParticipant() throws Exception {
        // Initialize the database
        smsParticipantRepository.saveAndFlush(smsParticipant);

        int databaseSizeBeforeUpdate = smsParticipantRepository.findAll().size();

        // Update the smsParticipant
        SmsParticipant updatedSmsParticipant = smsParticipantRepository.findById(smsParticipant.getId()).get();
        // Disconnect from session so that the updates on updatedSmsParticipant are not directly saved in db
        em.detach(updatedSmsParticipant);
        updatedSmsParticipant
            .phone(UPDATED_PHONE)
            .operator(UPDATED_OPERATOR);
        SmsParticipantDTO smsParticipantDTO = smsParticipantMapper.toDto(updatedSmsParticipant);

        restSmsParticipantMockMvc.perform(put("/api/sms-participants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(smsParticipantDTO)))
            .andExpect(status().isOk());

        // Validate the SmsParticipant in the database
        List<SmsParticipant> smsParticipantList = smsParticipantRepository.findAll();
        assertThat(smsParticipantList).hasSize(databaseSizeBeforeUpdate);
        SmsParticipant testSmsParticipant = smsParticipantList.get(smsParticipantList.size() - 1);
        assertThat(testSmsParticipant.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testSmsParticipant.getOperator()).isEqualTo(UPDATED_OPERATOR);
    }

    @Test
    @Transactional
    public void updateNonExistingSmsParticipant() throws Exception {
        int databaseSizeBeforeUpdate = smsParticipantRepository.findAll().size();

        // Create the SmsParticipant
        SmsParticipantDTO smsParticipantDTO = smsParticipantMapper.toDto(smsParticipant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSmsParticipantMockMvc.perform(put("/api/sms-participants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(smsParticipantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SmsParticipant in the database
        List<SmsParticipant> smsParticipantList = smsParticipantRepository.findAll();
        assertThat(smsParticipantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSmsParticipant() throws Exception {
        // Initialize the database
        smsParticipantRepository.saveAndFlush(smsParticipant);

        int databaseSizeBeforeDelete = smsParticipantRepository.findAll().size();

        // Delete the smsParticipant
        restSmsParticipantMockMvc.perform(delete("/api/sms-participants/{id}", smsParticipant.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SmsParticipant> smsParticipantList = smsParticipantRepository.findAll();
        assertThat(smsParticipantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
