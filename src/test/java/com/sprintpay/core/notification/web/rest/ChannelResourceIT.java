package com.sprintpay.core.notification.web.rest;

import com.sprintpay.core.notification.CorenotificationApp;
import com.sprintpay.core.notification.domain.Channel;
import com.sprintpay.core.notification.domain.TemplateParam;
import com.sprintpay.core.notification.repository.ChannelRepository;
import com.sprintpay.core.notification.service.ChannelService;
import com.sprintpay.core.notification.service.dto.ChannelDTO;
import com.sprintpay.core.notification.service.mapper.ChannelMapper;
import com.sprintpay.core.notification.service.dto.ChannelCriteria;
import com.sprintpay.core.notification.service.ChannelQueryService;

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

import com.sprintpay.core.notification.domain.enumeration.ChannelType;
/**
 * Integration tests for the {@link ChannelResource} REST controller.
 */
@SpringBootTest(classes = CorenotificationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ChannelResourceIT {

    private static final ChannelType DEFAULT_TYPE = ChannelType.SMS;
    private static final ChannelType UPDATED_TYPE = ChannelType.EMAIL;

    private static final String DEFAULT_TEMPLATE = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE = "BBBBBBBBBB";

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private ChannelMapper channelMapper;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private ChannelQueryService channelQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChannelMockMvc;

    private Channel channel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Channel createEntity(EntityManager em) {
        Channel channel = new Channel()
            .type(DEFAULT_TYPE)
            .template(DEFAULT_TEMPLATE);
        return channel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Channel createUpdatedEntity(EntityManager em) {
        Channel channel = new Channel()
            .type(UPDATED_TYPE)
            .template(UPDATED_TEMPLATE);
        return channel;
    }

    @BeforeEach
    public void initTest() {
        channel = createEntity(em);
    }

    @Test
    @Transactional
    public void createChannel() throws Exception {
        int databaseSizeBeforeCreate = channelRepository.findAll().size();
        // Create the Channel
        ChannelDTO channelDTO = channelMapper.toDto(channel);
        restChannelMockMvc.perform(post("/api/channels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(channelDTO)))
            .andExpect(status().isCreated());

        // Validate the Channel in the database
        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeCreate + 1);
        Channel testChannel = channelList.get(channelList.size() - 1);
        assertThat(testChannel.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testChannel.getTemplate()).isEqualTo(DEFAULT_TEMPLATE);
    }

    @Test
    @Transactional
    public void createChannelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = channelRepository.findAll().size();

        // Create the Channel with an existing ID
        channel.setId(1L);
        ChannelDTO channelDTO = channelMapper.toDto(channel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChannelMockMvc.perform(post("/api/channels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(channelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Channel in the database
        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = channelRepository.findAll().size();
        // set the field null
        channel.setType(null);

        // Create the Channel, which fails.
        ChannelDTO channelDTO = channelMapper.toDto(channel);


        restChannelMockMvc.perform(post("/api/channels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(channelDTO)))
            .andExpect(status().isBadRequest());

        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTemplateIsRequired() throws Exception {
        int databaseSizeBeforeTest = channelRepository.findAll().size();
        // set the field null
        channel.setTemplate(null);

        // Create the Channel, which fails.
        ChannelDTO channelDTO = channelMapper.toDto(channel);


        restChannelMockMvc.perform(post("/api/channels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(channelDTO)))
            .andExpect(status().isBadRequest());

        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChannels() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);

        // Get all the channelList
        restChannelMockMvc.perform(get("/api/channels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(channel.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].template").value(hasItem(DEFAULT_TEMPLATE)));
    }
    
    @Test
    @Transactional
    public void getChannel() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);

        // Get the channel
        restChannelMockMvc.perform(get("/api/channels/{id}", channel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(channel.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.template").value(DEFAULT_TEMPLATE));
    }


    @Test
    @Transactional
    public void getChannelsByIdFiltering() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);

        Long id = channel.getId();

        defaultChannelShouldBeFound("id.equals=" + id);
        defaultChannelShouldNotBeFound("id.notEquals=" + id);

        defaultChannelShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultChannelShouldNotBeFound("id.greaterThan=" + id);

        defaultChannelShouldBeFound("id.lessThanOrEqual=" + id);
        defaultChannelShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllChannelsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);

        // Get all the channelList where type equals to DEFAULT_TYPE
        defaultChannelShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the channelList where type equals to UPDATED_TYPE
        defaultChannelShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllChannelsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);

        // Get all the channelList where type not equals to DEFAULT_TYPE
        defaultChannelShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the channelList where type not equals to UPDATED_TYPE
        defaultChannelShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllChannelsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);

        // Get all the channelList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultChannelShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the channelList where type equals to UPDATED_TYPE
        defaultChannelShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllChannelsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);

        // Get all the channelList where type is not null
        defaultChannelShouldBeFound("type.specified=true");

        // Get all the channelList where type is null
        defaultChannelShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllChannelsByTemplateIsEqualToSomething() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);

        // Get all the channelList where template equals to DEFAULT_TEMPLATE
        defaultChannelShouldBeFound("template.equals=" + DEFAULT_TEMPLATE);

        // Get all the channelList where template equals to UPDATED_TEMPLATE
        defaultChannelShouldNotBeFound("template.equals=" + UPDATED_TEMPLATE);
    }

    @Test
    @Transactional
    public void getAllChannelsByTemplateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);

        // Get all the channelList where template not equals to DEFAULT_TEMPLATE
        defaultChannelShouldNotBeFound("template.notEquals=" + DEFAULT_TEMPLATE);

        // Get all the channelList where template not equals to UPDATED_TEMPLATE
        defaultChannelShouldBeFound("template.notEquals=" + UPDATED_TEMPLATE);
    }

    @Test
    @Transactional
    public void getAllChannelsByTemplateIsInShouldWork() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);

        // Get all the channelList where template in DEFAULT_TEMPLATE or UPDATED_TEMPLATE
        defaultChannelShouldBeFound("template.in=" + DEFAULT_TEMPLATE + "," + UPDATED_TEMPLATE);

        // Get all the channelList where template equals to UPDATED_TEMPLATE
        defaultChannelShouldNotBeFound("template.in=" + UPDATED_TEMPLATE);
    }

    @Test
    @Transactional
    public void getAllChannelsByTemplateIsNullOrNotNull() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);

        // Get all the channelList where template is not null
        defaultChannelShouldBeFound("template.specified=true");

        // Get all the channelList where template is null
        defaultChannelShouldNotBeFound("template.specified=false");
    }
                @Test
    @Transactional
    public void getAllChannelsByTemplateContainsSomething() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);

        // Get all the channelList where template contains DEFAULT_TEMPLATE
        defaultChannelShouldBeFound("template.contains=" + DEFAULT_TEMPLATE);

        // Get all the channelList where template contains UPDATED_TEMPLATE
        defaultChannelShouldNotBeFound("template.contains=" + UPDATED_TEMPLATE);
    }

    @Test
    @Transactional
    public void getAllChannelsByTemplateNotContainsSomething() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);

        // Get all the channelList where template does not contain DEFAULT_TEMPLATE
        defaultChannelShouldNotBeFound("template.doesNotContain=" + DEFAULT_TEMPLATE);

        // Get all the channelList where template does not contain UPDATED_TEMPLATE
        defaultChannelShouldBeFound("template.doesNotContain=" + UPDATED_TEMPLATE);
    }


    @Test
    @Transactional
    public void getAllChannelsByParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);
        TemplateParam params = TemplateParamResourceIT.createEntity(em);
        em.persist(params);
        em.flush();
        channel.addParams(params);
        channelRepository.saveAndFlush(channel);
        Long paramsId = params.getId();

        // Get all the channelList where params equals to paramsId
        defaultChannelShouldBeFound("paramsId.equals=" + paramsId);

        // Get all the channelList where params equals to paramsId + 1
        defaultChannelShouldNotBeFound("paramsId.equals=" + (paramsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultChannelShouldBeFound(String filter) throws Exception {
        restChannelMockMvc.perform(get("/api/channels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(channel.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].template").value(hasItem(DEFAULT_TEMPLATE)));

        // Check, that the count call also returns 1
        restChannelMockMvc.perform(get("/api/channels/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultChannelShouldNotBeFound(String filter) throws Exception {
        restChannelMockMvc.perform(get("/api/channels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restChannelMockMvc.perform(get("/api/channels/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingChannel() throws Exception {
        // Get the channel
        restChannelMockMvc.perform(get("/api/channels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChannel() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);

        int databaseSizeBeforeUpdate = channelRepository.findAll().size();

        // Update the channel
        Channel updatedChannel = channelRepository.findById(channel.getId()).get();
        // Disconnect from session so that the updates on updatedChannel are not directly saved in db
        em.detach(updatedChannel);
        updatedChannel
            .type(UPDATED_TYPE)
            .template(UPDATED_TEMPLATE);
        ChannelDTO channelDTO = channelMapper.toDto(updatedChannel);

        restChannelMockMvc.perform(put("/api/channels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(channelDTO)))
            .andExpect(status().isOk());

        // Validate the Channel in the database
        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeUpdate);
        Channel testChannel = channelList.get(channelList.size() - 1);
        assertThat(testChannel.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testChannel.getTemplate()).isEqualTo(UPDATED_TEMPLATE);
    }

    @Test
    @Transactional
    public void updateNonExistingChannel() throws Exception {
        int databaseSizeBeforeUpdate = channelRepository.findAll().size();

        // Create the Channel
        ChannelDTO channelDTO = channelMapper.toDto(channel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChannelMockMvc.perform(put("/api/channels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(channelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Channel in the database
        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChannel() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);

        int databaseSizeBeforeDelete = channelRepository.findAll().size();

        // Delete the channel
        restChannelMockMvc.perform(delete("/api/channels/{id}", channel.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
