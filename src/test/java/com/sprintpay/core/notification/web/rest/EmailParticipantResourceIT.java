package com.sprintpay.core.notification.web.rest;

import com.sprintpay.core.notification.CorenotificationApp;
import com.sprintpay.core.notification.domain.EmailParticipant;
import com.sprintpay.core.notification.domain.EmailNotification;
import com.sprintpay.core.notification.repository.EmailParticipantRepository;
import com.sprintpay.core.notification.service.EmailParticipantService;
import com.sprintpay.core.notification.service.dto.EmailParticipantDTO;
import com.sprintpay.core.notification.service.mapper.EmailParticipantMapper;
import com.sprintpay.core.notification.service.dto.EmailParticipantCriteria;
import com.sprintpay.core.notification.service.EmailParticipantQueryService;

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
 * Integration tests for the {@link EmailParticipantResource} REST controller.
 */
@SpringBootTest(classes = CorenotificationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmailParticipantResourceIT {

    private static final String DEFAULT_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_MAIL = "BBBBBBBBBB";

    @Autowired
    private EmailParticipantRepository emailParticipantRepository;

    @Autowired
    private EmailParticipantMapper emailParticipantMapper;

    @Autowired
    private EmailParticipantService emailParticipantService;

    @Autowired
    private EmailParticipantQueryService emailParticipantQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmailParticipantMockMvc;

    private EmailParticipant emailParticipant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmailParticipant createEntity(EntityManager em) {
        EmailParticipant emailParticipant = new EmailParticipant()
            .mail(DEFAULT_MAIL);
        return emailParticipant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmailParticipant createUpdatedEntity(EntityManager em) {
        EmailParticipant emailParticipant = new EmailParticipant()
            .mail(UPDATED_MAIL);
        return emailParticipant;
    }

    @BeforeEach
    public void initTest() {
        emailParticipant = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmailParticipant() throws Exception {
        int databaseSizeBeforeCreate = emailParticipantRepository.findAll().size();
        // Create the EmailParticipant
        EmailParticipantDTO emailParticipantDTO = emailParticipantMapper.toDto(emailParticipant);
        restEmailParticipantMockMvc.perform(post("/api/email-participants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailParticipantDTO)))
            .andExpect(status().isCreated());

        // Validate the EmailParticipant in the database
        List<EmailParticipant> emailParticipantList = emailParticipantRepository.findAll();
        assertThat(emailParticipantList).hasSize(databaseSizeBeforeCreate + 1);
        EmailParticipant testEmailParticipant = emailParticipantList.get(emailParticipantList.size() - 1);
        assertThat(testEmailParticipant.getMail()).isEqualTo(DEFAULT_MAIL);
    }

    @Test
    @Transactional
    public void createEmailParticipantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emailParticipantRepository.findAll().size();

        // Create the EmailParticipant with an existing ID
        emailParticipant.setId(1L);
        EmailParticipantDTO emailParticipantDTO = emailParticipantMapper.toDto(emailParticipant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailParticipantMockMvc.perform(post("/api/email-participants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailParticipantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmailParticipant in the database
        List<EmailParticipant> emailParticipantList = emailParticipantRepository.findAll();
        assertThat(emailParticipantList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMailIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailParticipantRepository.findAll().size();
        // set the field null
        emailParticipant.setMail(null);

        // Create the EmailParticipant, which fails.
        EmailParticipantDTO emailParticipantDTO = emailParticipantMapper.toDto(emailParticipant);


        restEmailParticipantMockMvc.perform(post("/api/email-participants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailParticipantDTO)))
            .andExpect(status().isBadRequest());

        List<EmailParticipant> emailParticipantList = emailParticipantRepository.findAll();
        assertThat(emailParticipantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmailParticipants() throws Exception {
        // Initialize the database
        emailParticipantRepository.saveAndFlush(emailParticipant);

        // Get all the emailParticipantList
        restEmailParticipantMockMvc.perform(get("/api/email-participants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailParticipant.getId().intValue())))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)));
    }
    
    @Test
    @Transactional
    public void getEmailParticipant() throws Exception {
        // Initialize the database
        emailParticipantRepository.saveAndFlush(emailParticipant);

        // Get the emailParticipant
        restEmailParticipantMockMvc.perform(get("/api/email-participants/{id}", emailParticipant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emailParticipant.getId().intValue()))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL));
    }


    @Test
    @Transactional
    public void getEmailParticipantsByIdFiltering() throws Exception {
        // Initialize the database
        emailParticipantRepository.saveAndFlush(emailParticipant);

        Long id = emailParticipant.getId();

        defaultEmailParticipantShouldBeFound("id.equals=" + id);
        defaultEmailParticipantShouldNotBeFound("id.notEquals=" + id);

        defaultEmailParticipantShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmailParticipantShouldNotBeFound("id.greaterThan=" + id);

        defaultEmailParticipantShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmailParticipantShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEmailParticipantsByMailIsEqualToSomething() throws Exception {
        // Initialize the database
        emailParticipantRepository.saveAndFlush(emailParticipant);

        // Get all the emailParticipantList where mail equals to DEFAULT_MAIL
        defaultEmailParticipantShouldBeFound("mail.equals=" + DEFAULT_MAIL);

        // Get all the emailParticipantList where mail equals to UPDATED_MAIL
        defaultEmailParticipantShouldNotBeFound("mail.equals=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllEmailParticipantsByMailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emailParticipantRepository.saveAndFlush(emailParticipant);

        // Get all the emailParticipantList where mail not equals to DEFAULT_MAIL
        defaultEmailParticipantShouldNotBeFound("mail.notEquals=" + DEFAULT_MAIL);

        // Get all the emailParticipantList where mail not equals to UPDATED_MAIL
        defaultEmailParticipantShouldBeFound("mail.notEquals=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllEmailParticipantsByMailIsInShouldWork() throws Exception {
        // Initialize the database
        emailParticipantRepository.saveAndFlush(emailParticipant);

        // Get all the emailParticipantList where mail in DEFAULT_MAIL or UPDATED_MAIL
        defaultEmailParticipantShouldBeFound("mail.in=" + DEFAULT_MAIL + "," + UPDATED_MAIL);

        // Get all the emailParticipantList where mail equals to UPDATED_MAIL
        defaultEmailParticipantShouldNotBeFound("mail.in=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllEmailParticipantsByMailIsNullOrNotNull() throws Exception {
        // Initialize the database
        emailParticipantRepository.saveAndFlush(emailParticipant);

        // Get all the emailParticipantList where mail is not null
        defaultEmailParticipantShouldBeFound("mail.specified=true");

        // Get all the emailParticipantList where mail is null
        defaultEmailParticipantShouldNotBeFound("mail.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmailParticipantsByMailContainsSomething() throws Exception {
        // Initialize the database
        emailParticipantRepository.saveAndFlush(emailParticipant);

        // Get all the emailParticipantList where mail contains DEFAULT_MAIL
        defaultEmailParticipantShouldBeFound("mail.contains=" + DEFAULT_MAIL);

        // Get all the emailParticipantList where mail contains UPDATED_MAIL
        defaultEmailParticipantShouldNotBeFound("mail.contains=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllEmailParticipantsByMailNotContainsSomething() throws Exception {
        // Initialize the database
        emailParticipantRepository.saveAndFlush(emailParticipant);

        // Get all the emailParticipantList where mail does not contain DEFAULT_MAIL
        defaultEmailParticipantShouldNotBeFound("mail.doesNotContain=" + DEFAULT_MAIL);

        // Get all the emailParticipantList where mail does not contain UPDATED_MAIL
        defaultEmailParticipantShouldBeFound("mail.doesNotContain=" + UPDATED_MAIL);
    }


    @Test
    @Transactional
    public void getAllEmailParticipantsByEmailNotificationIsEqualToSomething() throws Exception {
        // Initialize the database
        emailParticipantRepository.saveAndFlush(emailParticipant);
        EmailNotification emailNotification = EmailNotificationResourceIT.createEntity(em);
        em.persist(emailNotification);
        em.flush();
        emailParticipant.setEmailNotification(emailNotification);
        emailParticipantRepository.saveAndFlush(emailParticipant);
        Long emailNotificationId = emailNotification.getId();

        // Get all the emailParticipantList where emailNotification equals to emailNotificationId
        defaultEmailParticipantShouldBeFound("emailNotificationId.equals=" + emailNotificationId);

        // Get all the emailParticipantList where emailNotification equals to emailNotificationId + 1
        defaultEmailParticipantShouldNotBeFound("emailNotificationId.equals=" + (emailNotificationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmailParticipantShouldBeFound(String filter) throws Exception {
        restEmailParticipantMockMvc.perform(get("/api/email-participants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailParticipant.getId().intValue())))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)));

        // Check, that the count call also returns 1
        restEmailParticipantMockMvc.perform(get("/api/email-participants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmailParticipantShouldNotBeFound(String filter) throws Exception {
        restEmailParticipantMockMvc.perform(get("/api/email-participants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmailParticipantMockMvc.perform(get("/api/email-participants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEmailParticipant() throws Exception {
        // Get the emailParticipant
        restEmailParticipantMockMvc.perform(get("/api/email-participants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmailParticipant() throws Exception {
        // Initialize the database
        emailParticipantRepository.saveAndFlush(emailParticipant);

        int databaseSizeBeforeUpdate = emailParticipantRepository.findAll().size();

        // Update the emailParticipant
        EmailParticipant updatedEmailParticipant = emailParticipantRepository.findById(emailParticipant.getId()).get();
        // Disconnect from session so that the updates on updatedEmailParticipant are not directly saved in db
        em.detach(updatedEmailParticipant);
        updatedEmailParticipant
            .mail(UPDATED_MAIL);
        EmailParticipantDTO emailParticipantDTO = emailParticipantMapper.toDto(updatedEmailParticipant);

        restEmailParticipantMockMvc.perform(put("/api/email-participants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailParticipantDTO)))
            .andExpect(status().isOk());

        // Validate the EmailParticipant in the database
        List<EmailParticipant> emailParticipantList = emailParticipantRepository.findAll();
        assertThat(emailParticipantList).hasSize(databaseSizeBeforeUpdate);
        EmailParticipant testEmailParticipant = emailParticipantList.get(emailParticipantList.size() - 1);
        assertThat(testEmailParticipant.getMail()).isEqualTo(UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingEmailParticipant() throws Exception {
        int databaseSizeBeforeUpdate = emailParticipantRepository.findAll().size();

        // Create the EmailParticipant
        EmailParticipantDTO emailParticipantDTO = emailParticipantMapper.toDto(emailParticipant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailParticipantMockMvc.perform(put("/api/email-participants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailParticipantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmailParticipant in the database
        List<EmailParticipant> emailParticipantList = emailParticipantRepository.findAll();
        assertThat(emailParticipantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmailParticipant() throws Exception {
        // Initialize the database
        emailParticipantRepository.saveAndFlush(emailParticipant);

        int databaseSizeBeforeDelete = emailParticipantRepository.findAll().size();

        // Delete the emailParticipant
        restEmailParticipantMockMvc.perform(delete("/api/email-participants/{id}", emailParticipant.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmailParticipant> emailParticipantList = emailParticipantRepository.findAll();
        assertThat(emailParticipantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
