package com.sprintpay.core.notification.web.rest;

import com.sprintpay.core.notification.CorenotificationApp;
import com.sprintpay.core.notification.domain.SmsNotification;
import com.sprintpay.core.notification.domain.MicroServiceParticipant;
import com.sprintpay.core.notification.domain.SmsParticipant;
import com.sprintpay.core.notification.domain.TemplateParamValue;
import com.sprintpay.core.notification.repository.SmsNotificationRepository;
import com.sprintpay.core.notification.service.SmsNotificationService;
import com.sprintpay.core.notification.service.dto.SmsNotificationDTO;
import com.sprintpay.core.notification.service.mapper.SmsNotificationMapper;
import com.sprintpay.core.notification.service.dto.SmsNotificationCriteria;
import com.sprintpay.core.notification.service.SmsNotificationQueryService;

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
 * Integration tests for the {@link SmsNotificationResource} REST controller.
 */
@SpringBootTest(classes = CorenotificationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SmsNotificationResourceIT {

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    @Autowired
    private SmsNotificationRepository smsNotificationRepository;

    @Autowired
    private SmsNotificationMapper smsNotificationMapper;

    @Autowired
    private SmsNotificationService smsNotificationService;

    @Autowired
    private SmsNotificationQueryService smsNotificationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSmsNotificationMockMvc;

    private SmsNotification smsNotification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SmsNotification createEntity(EntityManager em) {
        SmsNotification smsNotification = new SmsNotification()
            .message(DEFAULT_MESSAGE);
        return smsNotification;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SmsNotification createUpdatedEntity(EntityManager em) {
        SmsNotification smsNotification = new SmsNotification()
            .message(UPDATED_MESSAGE);
        return smsNotification;
    }

    @BeforeEach
    public void initTest() {
        smsNotification = createEntity(em);
    }

    @Test
    @Transactional
    public void createSmsNotification() throws Exception {
        int databaseSizeBeforeCreate = smsNotificationRepository.findAll().size();
        // Create the SmsNotification
        SmsNotificationDTO smsNotificationDTO = smsNotificationMapper.toDto(smsNotification);
        restSmsNotificationMockMvc.perform(post("/api/sms-notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(smsNotificationDTO)))
            .andExpect(status().isCreated());

        // Validate the SmsNotification in the database
        List<SmsNotification> smsNotificationList = smsNotificationRepository.findAll();
        assertThat(smsNotificationList).hasSize(databaseSizeBeforeCreate + 1);
        SmsNotification testSmsNotification = smsNotificationList.get(smsNotificationList.size() - 1);
        assertThat(testSmsNotification.getMessage()).isEqualTo(DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    public void createSmsNotificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = smsNotificationRepository.findAll().size();

        // Create the SmsNotification with an existing ID
        smsNotification.setId(1L);
        SmsNotificationDTO smsNotificationDTO = smsNotificationMapper.toDto(smsNotification);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSmsNotificationMockMvc.perform(post("/api/sms-notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(smsNotificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SmsNotification in the database
        List<SmsNotification> smsNotificationList = smsNotificationRepository.findAll();
        assertThat(smsNotificationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSmsNotifications() throws Exception {
        // Initialize the database
        smsNotificationRepository.saveAndFlush(smsNotification);

        // Get all the smsNotificationList
        restSmsNotificationMockMvc.perform(get("/api/sms-notifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(smsNotification.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)));
    }
    
    @Test
    @Transactional
    public void getSmsNotification() throws Exception {
        // Initialize the database
        smsNotificationRepository.saveAndFlush(smsNotification);

        // Get the smsNotification
        restSmsNotificationMockMvc.perform(get("/api/sms-notifications/{id}", smsNotification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(smsNotification.getId().intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE));
    }


    @Test
    @Transactional
    public void getSmsNotificationsByIdFiltering() throws Exception {
        // Initialize the database
        smsNotificationRepository.saveAndFlush(smsNotification);

        Long id = smsNotification.getId();

        defaultSmsNotificationShouldBeFound("id.equals=" + id);
        defaultSmsNotificationShouldNotBeFound("id.notEquals=" + id);

        defaultSmsNotificationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSmsNotificationShouldNotBeFound("id.greaterThan=" + id);

        defaultSmsNotificationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSmsNotificationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSmsNotificationsByMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        smsNotificationRepository.saveAndFlush(smsNotification);

        // Get all the smsNotificationList where message equals to DEFAULT_MESSAGE
        defaultSmsNotificationShouldBeFound("message.equals=" + DEFAULT_MESSAGE);

        // Get all the smsNotificationList where message equals to UPDATED_MESSAGE
        defaultSmsNotificationShouldNotBeFound("message.equals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllSmsNotificationsByMessageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        smsNotificationRepository.saveAndFlush(smsNotification);

        // Get all the smsNotificationList where message not equals to DEFAULT_MESSAGE
        defaultSmsNotificationShouldNotBeFound("message.notEquals=" + DEFAULT_MESSAGE);

        // Get all the smsNotificationList where message not equals to UPDATED_MESSAGE
        defaultSmsNotificationShouldBeFound("message.notEquals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllSmsNotificationsByMessageIsInShouldWork() throws Exception {
        // Initialize the database
        smsNotificationRepository.saveAndFlush(smsNotification);

        // Get all the smsNotificationList where message in DEFAULT_MESSAGE or UPDATED_MESSAGE
        defaultSmsNotificationShouldBeFound("message.in=" + DEFAULT_MESSAGE + "," + UPDATED_MESSAGE);

        // Get all the smsNotificationList where message equals to UPDATED_MESSAGE
        defaultSmsNotificationShouldNotBeFound("message.in=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllSmsNotificationsByMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        smsNotificationRepository.saveAndFlush(smsNotification);

        // Get all the smsNotificationList where message is not null
        defaultSmsNotificationShouldBeFound("message.specified=true");

        // Get all the smsNotificationList where message is null
        defaultSmsNotificationShouldNotBeFound("message.specified=false");
    }
                @Test
    @Transactional
    public void getAllSmsNotificationsByMessageContainsSomething() throws Exception {
        // Initialize the database
        smsNotificationRepository.saveAndFlush(smsNotification);

        // Get all the smsNotificationList where message contains DEFAULT_MESSAGE
        defaultSmsNotificationShouldBeFound("message.contains=" + DEFAULT_MESSAGE);

        // Get all the smsNotificationList where message contains UPDATED_MESSAGE
        defaultSmsNotificationShouldNotBeFound("message.contains=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllSmsNotificationsByMessageNotContainsSomething() throws Exception {
        // Initialize the database
        smsNotificationRepository.saveAndFlush(smsNotification);

        // Get all the smsNotificationList where message does not contain DEFAULT_MESSAGE
        defaultSmsNotificationShouldNotBeFound("message.doesNotContain=" + DEFAULT_MESSAGE);

        // Get all the smsNotificationList where message does not contain UPDATED_MESSAGE
        defaultSmsNotificationShouldBeFound("message.doesNotContain=" + UPDATED_MESSAGE);
    }


    @Test
    @Transactional
    public void getAllSmsNotificationsByEmetteurIsEqualToSomething() throws Exception {
        // Initialize the database
        smsNotificationRepository.saveAndFlush(smsNotification);
        MicroServiceParticipant emetteur = MicroServiceParticipantResourceIT.createEntity(em);
        em.persist(emetteur);
        em.flush();
        smsNotification.setEmetteur(emetteur);
        smsNotificationRepository.saveAndFlush(smsNotification);
        Long emetteurId = emetteur.getId();

        // Get all the smsNotificationList where emetteur equals to emetteurId
        defaultSmsNotificationShouldBeFound("emetteurId.equals=" + emetteurId);

        // Get all the smsNotificationList where emetteur equals to emetteurId + 1
        defaultSmsNotificationShouldNotBeFound("emetteurId.equals=" + (emetteurId + 1));
    }


    @Test
    @Transactional
    public void getAllSmsNotificationsByDestinatairesIsEqualToSomething() throws Exception {
        // Initialize the database
        smsNotificationRepository.saveAndFlush(smsNotification);
        SmsParticipant destinataires = SmsParticipantResourceIT.createEntity(em);
        em.persist(destinataires);
        em.flush();
        smsNotification.addDestinataires(destinataires);
        smsNotificationRepository.saveAndFlush(smsNotification);
        Long destinatairesId = destinataires.getId();

        // Get all the smsNotificationList where destinataires equals to destinatairesId
        defaultSmsNotificationShouldBeFound("destinatairesId.equals=" + destinatairesId);

        // Get all the smsNotificationList where destinataires equals to destinatairesId + 1
        defaultSmsNotificationShouldNotBeFound("destinatairesId.equals=" + (destinatairesId + 1));
    }


    @Test
    @Transactional
    public void getAllSmsNotificationsByParamValuesIsEqualToSomething() throws Exception {
        // Initialize the database
        smsNotificationRepository.saveAndFlush(smsNotification);
        TemplateParamValue paramValues = TemplateParamValueResourceIT.createEntity(em);
        em.persist(paramValues);
        em.flush();
        smsNotification.addParamValues(paramValues);
        smsNotificationRepository.saveAndFlush(smsNotification);
        Long paramValuesId = paramValues.getId();

        // Get all the smsNotificationList where paramValues equals to paramValuesId
        defaultSmsNotificationShouldBeFound("paramValuesId.equals=" + paramValuesId);

        // Get all the smsNotificationList where paramValues equals to paramValuesId + 1
        defaultSmsNotificationShouldNotBeFound("paramValuesId.equals=" + (paramValuesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSmsNotificationShouldBeFound(String filter) throws Exception {
        restSmsNotificationMockMvc.perform(get("/api/sms-notifications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(smsNotification.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)));

        // Check, that the count call also returns 1
        restSmsNotificationMockMvc.perform(get("/api/sms-notifications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSmsNotificationShouldNotBeFound(String filter) throws Exception {
        restSmsNotificationMockMvc.perform(get("/api/sms-notifications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSmsNotificationMockMvc.perform(get("/api/sms-notifications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSmsNotification() throws Exception {
        // Get the smsNotification
        restSmsNotificationMockMvc.perform(get("/api/sms-notifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSmsNotification() throws Exception {
        // Initialize the database
        smsNotificationRepository.saveAndFlush(smsNotification);

        int databaseSizeBeforeUpdate = smsNotificationRepository.findAll().size();

        // Update the smsNotification
        SmsNotification updatedSmsNotification = smsNotificationRepository.findById(smsNotification.getId()).get();
        // Disconnect from session so that the updates on updatedSmsNotification are not directly saved in db
        em.detach(updatedSmsNotification);
        updatedSmsNotification
            .message(UPDATED_MESSAGE);
        SmsNotificationDTO smsNotificationDTO = smsNotificationMapper.toDto(updatedSmsNotification);

        restSmsNotificationMockMvc.perform(put("/api/sms-notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(smsNotificationDTO)))
            .andExpect(status().isOk());

        // Validate the SmsNotification in the database
        List<SmsNotification> smsNotificationList = smsNotificationRepository.findAll();
        assertThat(smsNotificationList).hasSize(databaseSizeBeforeUpdate);
        SmsNotification testSmsNotification = smsNotificationList.get(smsNotificationList.size() - 1);
        assertThat(testSmsNotification.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingSmsNotification() throws Exception {
        int databaseSizeBeforeUpdate = smsNotificationRepository.findAll().size();

        // Create the SmsNotification
        SmsNotificationDTO smsNotificationDTO = smsNotificationMapper.toDto(smsNotification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSmsNotificationMockMvc.perform(put("/api/sms-notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(smsNotificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SmsNotification in the database
        List<SmsNotification> smsNotificationList = smsNotificationRepository.findAll();
        assertThat(smsNotificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSmsNotification() throws Exception {
        // Initialize the database
        smsNotificationRepository.saveAndFlush(smsNotification);

        int databaseSizeBeforeDelete = smsNotificationRepository.findAll().size();

        // Delete the smsNotification
        restSmsNotificationMockMvc.perform(delete("/api/sms-notifications/{id}", smsNotification.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SmsNotification> smsNotificationList = smsNotificationRepository.findAll();
        assertThat(smsNotificationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
