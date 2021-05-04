package com.sprintpay.core.notification.web.rest;

import com.sprintpay.core.notification.CorenotificationApp;
import com.sprintpay.core.notification.domain.EmailNotification;
import com.sprintpay.core.notification.domain.MicroServiceParticipant;
import com.sprintpay.core.notification.domain.EmailParticipant;
import com.sprintpay.core.notification.domain.TemplateParamValue;
import com.sprintpay.core.notification.repository.EmailNotificationRepository;
import com.sprintpay.core.notification.service.EmailNotificationService;
import com.sprintpay.core.notification.service.dto.EmailNotificationDTO;
import com.sprintpay.core.notification.service.mapper.EmailNotificationMapper;
import com.sprintpay.core.notification.service.dto.EmailNotificationCriteria;
import com.sprintpay.core.notification.service.EmailNotificationQueryService;

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
 * Integration tests for the {@link EmailNotificationResource} REST controller.
 */
@SpringBootTest(classes = CorenotificationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmailNotificationResourceIT {

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_SUJET = "AAAAAAAAAA";
    private static final String UPDATED_SUJET = "BBBBBBBBBB";

    @Autowired
    private EmailNotificationRepository emailNotificationRepository;

    @Autowired
    private EmailNotificationMapper emailNotificationMapper;

    @Autowired
    private EmailNotificationService emailNotificationService;

    @Autowired
    private EmailNotificationQueryService emailNotificationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmailNotificationMockMvc;

    private EmailNotification emailNotification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmailNotification createEntity(EntityManager em) {
        EmailNotification emailNotification = new EmailNotification()
            .message(DEFAULT_MESSAGE)
            .sujet(DEFAULT_SUJET);
        return emailNotification;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmailNotification createUpdatedEntity(EntityManager em) {
        EmailNotification emailNotification = new EmailNotification()
            .message(UPDATED_MESSAGE)
            .sujet(UPDATED_SUJET);
        return emailNotification;
    }

    @BeforeEach
    public void initTest() {
        emailNotification = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmailNotification() throws Exception {
        int databaseSizeBeforeCreate = emailNotificationRepository.findAll().size();
        // Create the EmailNotification
        EmailNotificationDTO emailNotificationDTO = emailNotificationMapper.toDto(emailNotification);
        restEmailNotificationMockMvc.perform(post("/api/email-notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailNotificationDTO)))
            .andExpect(status().isCreated());

        // Validate the EmailNotification in the database
        List<EmailNotification> emailNotificationList = emailNotificationRepository.findAll();
        assertThat(emailNotificationList).hasSize(databaseSizeBeforeCreate + 1);
        EmailNotification testEmailNotification = emailNotificationList.get(emailNotificationList.size() - 1);
        assertThat(testEmailNotification.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testEmailNotification.getSujet()).isEqualTo(DEFAULT_SUJET);
    }

    @Test
    @Transactional
    public void createEmailNotificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emailNotificationRepository.findAll().size();

        // Create the EmailNotification with an existing ID
        emailNotification.setId(1L);
        EmailNotificationDTO emailNotificationDTO = emailNotificationMapper.toDto(emailNotification);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailNotificationMockMvc.perform(post("/api/email-notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailNotificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmailNotification in the database
        List<EmailNotification> emailNotificationList = emailNotificationRepository.findAll();
        assertThat(emailNotificationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmailNotifications() throws Exception {
        // Initialize the database
        emailNotificationRepository.saveAndFlush(emailNotification);

        // Get all the emailNotificationList
        restEmailNotificationMockMvc.perform(get("/api/email-notifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailNotification.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].sujet").value(hasItem(DEFAULT_SUJET)));
    }
    
    @Test
    @Transactional
    public void getEmailNotification() throws Exception {
        // Initialize the database
        emailNotificationRepository.saveAndFlush(emailNotification);

        // Get the emailNotification
        restEmailNotificationMockMvc.perform(get("/api/email-notifications/{id}", emailNotification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emailNotification.getId().intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.sujet").value(DEFAULT_SUJET));
    }


    @Test
    @Transactional
    public void getEmailNotificationsByIdFiltering() throws Exception {
        // Initialize the database
        emailNotificationRepository.saveAndFlush(emailNotification);

        Long id = emailNotification.getId();

        defaultEmailNotificationShouldBeFound("id.equals=" + id);
        defaultEmailNotificationShouldNotBeFound("id.notEquals=" + id);

        defaultEmailNotificationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmailNotificationShouldNotBeFound("id.greaterThan=" + id);

        defaultEmailNotificationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmailNotificationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEmailNotificationsByMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        emailNotificationRepository.saveAndFlush(emailNotification);

        // Get all the emailNotificationList where message equals to DEFAULT_MESSAGE
        defaultEmailNotificationShouldBeFound("message.equals=" + DEFAULT_MESSAGE);

        // Get all the emailNotificationList where message equals to UPDATED_MESSAGE
        defaultEmailNotificationShouldNotBeFound("message.equals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllEmailNotificationsByMessageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emailNotificationRepository.saveAndFlush(emailNotification);

        // Get all the emailNotificationList where message not equals to DEFAULT_MESSAGE
        defaultEmailNotificationShouldNotBeFound("message.notEquals=" + DEFAULT_MESSAGE);

        // Get all the emailNotificationList where message not equals to UPDATED_MESSAGE
        defaultEmailNotificationShouldBeFound("message.notEquals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllEmailNotificationsByMessageIsInShouldWork() throws Exception {
        // Initialize the database
        emailNotificationRepository.saveAndFlush(emailNotification);

        // Get all the emailNotificationList where message in DEFAULT_MESSAGE or UPDATED_MESSAGE
        defaultEmailNotificationShouldBeFound("message.in=" + DEFAULT_MESSAGE + "," + UPDATED_MESSAGE);

        // Get all the emailNotificationList where message equals to UPDATED_MESSAGE
        defaultEmailNotificationShouldNotBeFound("message.in=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllEmailNotificationsByMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        emailNotificationRepository.saveAndFlush(emailNotification);

        // Get all the emailNotificationList where message is not null
        defaultEmailNotificationShouldBeFound("message.specified=true");

        // Get all the emailNotificationList where message is null
        defaultEmailNotificationShouldNotBeFound("message.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmailNotificationsByMessageContainsSomething() throws Exception {
        // Initialize the database
        emailNotificationRepository.saveAndFlush(emailNotification);

        // Get all the emailNotificationList where message contains DEFAULT_MESSAGE
        defaultEmailNotificationShouldBeFound("message.contains=" + DEFAULT_MESSAGE);

        // Get all the emailNotificationList where message contains UPDATED_MESSAGE
        defaultEmailNotificationShouldNotBeFound("message.contains=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllEmailNotificationsByMessageNotContainsSomething() throws Exception {
        // Initialize the database
        emailNotificationRepository.saveAndFlush(emailNotification);

        // Get all the emailNotificationList where message does not contain DEFAULT_MESSAGE
        defaultEmailNotificationShouldNotBeFound("message.doesNotContain=" + DEFAULT_MESSAGE);

        // Get all the emailNotificationList where message does not contain UPDATED_MESSAGE
        defaultEmailNotificationShouldBeFound("message.doesNotContain=" + UPDATED_MESSAGE);
    }


    @Test
    @Transactional
    public void getAllEmailNotificationsBySujetIsEqualToSomething() throws Exception {
        // Initialize the database
        emailNotificationRepository.saveAndFlush(emailNotification);

        // Get all the emailNotificationList where sujet equals to DEFAULT_SUJET
        defaultEmailNotificationShouldBeFound("sujet.equals=" + DEFAULT_SUJET);

        // Get all the emailNotificationList where sujet equals to UPDATED_SUJET
        defaultEmailNotificationShouldNotBeFound("sujet.equals=" + UPDATED_SUJET);
    }

    @Test
    @Transactional
    public void getAllEmailNotificationsBySujetIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emailNotificationRepository.saveAndFlush(emailNotification);

        // Get all the emailNotificationList where sujet not equals to DEFAULT_SUJET
        defaultEmailNotificationShouldNotBeFound("sujet.notEquals=" + DEFAULT_SUJET);

        // Get all the emailNotificationList where sujet not equals to UPDATED_SUJET
        defaultEmailNotificationShouldBeFound("sujet.notEquals=" + UPDATED_SUJET);
    }

    @Test
    @Transactional
    public void getAllEmailNotificationsBySujetIsInShouldWork() throws Exception {
        // Initialize the database
        emailNotificationRepository.saveAndFlush(emailNotification);

        // Get all the emailNotificationList where sujet in DEFAULT_SUJET or UPDATED_SUJET
        defaultEmailNotificationShouldBeFound("sujet.in=" + DEFAULT_SUJET + "," + UPDATED_SUJET);

        // Get all the emailNotificationList where sujet equals to UPDATED_SUJET
        defaultEmailNotificationShouldNotBeFound("sujet.in=" + UPDATED_SUJET);
    }

    @Test
    @Transactional
    public void getAllEmailNotificationsBySujetIsNullOrNotNull() throws Exception {
        // Initialize the database
        emailNotificationRepository.saveAndFlush(emailNotification);

        // Get all the emailNotificationList where sujet is not null
        defaultEmailNotificationShouldBeFound("sujet.specified=true");

        // Get all the emailNotificationList where sujet is null
        defaultEmailNotificationShouldNotBeFound("sujet.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmailNotificationsBySujetContainsSomething() throws Exception {
        // Initialize the database
        emailNotificationRepository.saveAndFlush(emailNotification);

        // Get all the emailNotificationList where sujet contains DEFAULT_SUJET
        defaultEmailNotificationShouldBeFound("sujet.contains=" + DEFAULT_SUJET);

        // Get all the emailNotificationList where sujet contains UPDATED_SUJET
        defaultEmailNotificationShouldNotBeFound("sujet.contains=" + UPDATED_SUJET);
    }

    @Test
    @Transactional
    public void getAllEmailNotificationsBySujetNotContainsSomething() throws Exception {
        // Initialize the database
        emailNotificationRepository.saveAndFlush(emailNotification);

        // Get all the emailNotificationList where sujet does not contain DEFAULT_SUJET
        defaultEmailNotificationShouldNotBeFound("sujet.doesNotContain=" + DEFAULT_SUJET);

        // Get all the emailNotificationList where sujet does not contain UPDATED_SUJET
        defaultEmailNotificationShouldBeFound("sujet.doesNotContain=" + UPDATED_SUJET);
    }


    @Test
    @Transactional
    public void getAllEmailNotificationsByEmetteurIsEqualToSomething() throws Exception {
        // Initialize the database
        emailNotificationRepository.saveAndFlush(emailNotification);
        MicroServiceParticipant emetteur = MicroServiceParticipantResourceIT.createEntity(em);
        em.persist(emetteur);
        em.flush();
        emailNotification.setEmetteur(emetteur);
        emailNotificationRepository.saveAndFlush(emailNotification);
        Long emetteurId = emetteur.getId();

        // Get all the emailNotificationList where emetteur equals to emetteurId
        defaultEmailNotificationShouldBeFound("emetteurId.equals=" + emetteurId);

        // Get all the emailNotificationList where emetteur equals to emetteurId + 1
        defaultEmailNotificationShouldNotBeFound("emetteurId.equals=" + (emetteurId + 1));
    }


    @Test
    @Transactional
    public void getAllEmailNotificationsByDestinatairesIsEqualToSomething() throws Exception {
        // Initialize the database
        emailNotificationRepository.saveAndFlush(emailNotification);
        EmailParticipant destinataires = EmailParticipantResourceIT.createEntity(em);
        em.persist(destinataires);
        em.flush();
        emailNotification.addDestinataires(destinataires);
        emailNotificationRepository.saveAndFlush(emailNotification);
        Long destinatairesId = destinataires.getId();

        // Get all the emailNotificationList where destinataires equals to destinatairesId
        defaultEmailNotificationShouldBeFound("destinatairesId.equals=" + destinatairesId);

        // Get all the emailNotificationList where destinataires equals to destinatairesId + 1
        defaultEmailNotificationShouldNotBeFound("destinatairesId.equals=" + (destinatairesId + 1));
    }


    @Test
    @Transactional
    public void getAllEmailNotificationsByParamValuesIsEqualToSomething() throws Exception {
        // Initialize the database
        emailNotificationRepository.saveAndFlush(emailNotification);
        TemplateParamValue paramValues = TemplateParamValueResourceIT.createEntity(em);
        em.persist(paramValues);
        em.flush();
        emailNotification.addParamValues(paramValues);
        emailNotificationRepository.saveAndFlush(emailNotification);
        Long paramValuesId = paramValues.getId();

        // Get all the emailNotificationList where paramValues equals to paramValuesId
        defaultEmailNotificationShouldBeFound("paramValuesId.equals=" + paramValuesId);

        // Get all the emailNotificationList where paramValues equals to paramValuesId + 1
        defaultEmailNotificationShouldNotBeFound("paramValuesId.equals=" + (paramValuesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmailNotificationShouldBeFound(String filter) throws Exception {
        restEmailNotificationMockMvc.perform(get("/api/email-notifications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailNotification.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].sujet").value(hasItem(DEFAULT_SUJET)));

        // Check, that the count call also returns 1
        restEmailNotificationMockMvc.perform(get("/api/email-notifications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmailNotificationShouldNotBeFound(String filter) throws Exception {
        restEmailNotificationMockMvc.perform(get("/api/email-notifications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmailNotificationMockMvc.perform(get("/api/email-notifications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEmailNotification() throws Exception {
        // Get the emailNotification
        restEmailNotificationMockMvc.perform(get("/api/email-notifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmailNotification() throws Exception {
        // Initialize the database
        emailNotificationRepository.saveAndFlush(emailNotification);

        int databaseSizeBeforeUpdate = emailNotificationRepository.findAll().size();

        // Update the emailNotification
        EmailNotification updatedEmailNotification = emailNotificationRepository.findById(emailNotification.getId()).get();
        // Disconnect from session so that the updates on updatedEmailNotification are not directly saved in db
        em.detach(updatedEmailNotification);
        updatedEmailNotification
            .message(UPDATED_MESSAGE)
            .sujet(UPDATED_SUJET);
        EmailNotificationDTO emailNotificationDTO = emailNotificationMapper.toDto(updatedEmailNotification);

        restEmailNotificationMockMvc.perform(put("/api/email-notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailNotificationDTO)))
            .andExpect(status().isOk());

        // Validate the EmailNotification in the database
        List<EmailNotification> emailNotificationList = emailNotificationRepository.findAll();
        assertThat(emailNotificationList).hasSize(databaseSizeBeforeUpdate);
        EmailNotification testEmailNotification = emailNotificationList.get(emailNotificationList.size() - 1);
        assertThat(testEmailNotification.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testEmailNotification.getSujet()).isEqualTo(UPDATED_SUJET);
    }

    @Test
    @Transactional
    public void updateNonExistingEmailNotification() throws Exception {
        int databaseSizeBeforeUpdate = emailNotificationRepository.findAll().size();

        // Create the EmailNotification
        EmailNotificationDTO emailNotificationDTO = emailNotificationMapper.toDto(emailNotification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailNotificationMockMvc.perform(put("/api/email-notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailNotificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmailNotification in the database
        List<EmailNotification> emailNotificationList = emailNotificationRepository.findAll();
        assertThat(emailNotificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmailNotification() throws Exception {
        // Initialize the database
        emailNotificationRepository.saveAndFlush(emailNotification);

        int databaseSizeBeforeDelete = emailNotificationRepository.findAll().size();

        // Delete the emailNotification
        restEmailNotificationMockMvc.perform(delete("/api/email-notifications/{id}", emailNotification.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmailNotification> emailNotificationList = emailNotificationRepository.findAll();
        assertThat(emailNotificationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
