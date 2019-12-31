package com.data.service.web.rest;

import com.data.service.DataserviceApp;
import com.data.service.domain.NotificationHistory;
import com.data.service.repository.NotificationHistoryRepository;
import com.data.service.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.data.service.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link NotificationHistoryResource} REST controller.
 */
@SpringBootTest(classes = DataserviceApp.class)
public class NotificationHistoryResourceIT {

    private static final String DEFAULT_FROM_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_FROM_EMAIL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TO_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_TO_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CC_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_CC_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_NOTIFICATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_NOTIFICATION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJECT_LINE = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT_LINE = "BBBBBBBBBB";

    @Autowired
    private NotificationHistoryRepository notificationHistoryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restNotificationHistoryMockMvc;

    private NotificationHistory notificationHistory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NotificationHistoryResource notificationHistoryResource = new NotificationHistoryResource(notificationHistoryRepository);
        this.restNotificationHistoryMockMvc = MockMvcBuilders.standaloneSetup(notificationHistoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotificationHistory createEntity(EntityManager em) {
        NotificationHistory notificationHistory = new NotificationHistory()
            .fromEmail(DEFAULT_FROM_EMAIL)
            .sentDate(DEFAULT_SENT_DATE)
            .toAddress(DEFAULT_TO_ADDRESS)
            .ccAddress(DEFAULT_CC_ADDRESS)
            .notificationType(DEFAULT_NOTIFICATION_TYPE)
            .subjectLine(DEFAULT_SUBJECT_LINE);
        return notificationHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotificationHistory createUpdatedEntity(EntityManager em) {
        NotificationHistory notificationHistory = new NotificationHistory()
            .fromEmail(UPDATED_FROM_EMAIL)
            .sentDate(UPDATED_SENT_DATE)
            .toAddress(UPDATED_TO_ADDRESS)
            .ccAddress(UPDATED_CC_ADDRESS)
            .notificationType(UPDATED_NOTIFICATION_TYPE)
            .subjectLine(UPDATED_SUBJECT_LINE);
        return notificationHistory;
    }

    @BeforeEach
    public void initTest() {
        notificationHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createNotificationHistory() throws Exception {
        int databaseSizeBeforeCreate = notificationHistoryRepository.findAll().size();

        // Create the NotificationHistory
        restNotificationHistoryMockMvc.perform(post("/api/notification-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificationHistory)))
            .andExpect(status().isCreated());

        // Validate the NotificationHistory in the database
        List<NotificationHistory> notificationHistoryList = notificationHistoryRepository.findAll();
        assertThat(notificationHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        NotificationHistory testNotificationHistory = notificationHistoryList.get(notificationHistoryList.size() - 1);
        assertThat(testNotificationHistory.getFromEmail()).isEqualTo(DEFAULT_FROM_EMAIL);
        assertThat(testNotificationHistory.getSentDate()).isEqualTo(DEFAULT_SENT_DATE);
        assertThat(testNotificationHistory.getToAddress()).isEqualTo(DEFAULT_TO_ADDRESS);
        assertThat(testNotificationHistory.getCcAddress()).isEqualTo(DEFAULT_CC_ADDRESS);
        assertThat(testNotificationHistory.getNotificationType()).isEqualTo(DEFAULT_NOTIFICATION_TYPE);
        assertThat(testNotificationHistory.getSubjectLine()).isEqualTo(DEFAULT_SUBJECT_LINE);
    }

    @Test
    @Transactional
    public void createNotificationHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = notificationHistoryRepository.findAll().size();

        // Create the NotificationHistory with an existing ID
        notificationHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificationHistoryMockMvc.perform(post("/api/notification-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificationHistory)))
            .andExpect(status().isBadRequest());

        // Validate the NotificationHistory in the database
        List<NotificationHistory> notificationHistoryList = notificationHistoryRepository.findAll();
        assertThat(notificationHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFromEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationHistoryRepository.findAll().size();
        // set the field null
        notificationHistory.setFromEmail(null);

        // Create the NotificationHistory, which fails.

        restNotificationHistoryMockMvc.perform(post("/api/notification-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificationHistory)))
            .andExpect(status().isBadRequest());

        List<NotificationHistory> notificationHistoryList = notificationHistoryRepository.findAll();
        assertThat(notificationHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNotificationHistories() throws Exception {
        // Initialize the database
        notificationHistoryRepository.saveAndFlush(notificationHistory);

        // Get all the notificationHistoryList
        restNotificationHistoryMockMvc.perform(get("/api/notification-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificationHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromEmail").value(hasItem(DEFAULT_FROM_EMAIL)))
            .andExpect(jsonPath("$.[*].sentDate").value(hasItem(DEFAULT_SENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].toAddress").value(hasItem(DEFAULT_TO_ADDRESS)))
            .andExpect(jsonPath("$.[*].ccAddress").value(hasItem(DEFAULT_CC_ADDRESS)))
            .andExpect(jsonPath("$.[*].notificationType").value(hasItem(DEFAULT_NOTIFICATION_TYPE)))
            .andExpect(jsonPath("$.[*].subjectLine").value(hasItem(DEFAULT_SUBJECT_LINE)));
    }
    
    @Test
    @Transactional
    public void getNotificationHistory() throws Exception {
        // Initialize the database
        notificationHistoryRepository.saveAndFlush(notificationHistory);

        // Get the notificationHistory
        restNotificationHistoryMockMvc.perform(get("/api/notification-histories/{id}", notificationHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(notificationHistory.getId().intValue()))
            .andExpect(jsonPath("$.fromEmail").value(DEFAULT_FROM_EMAIL))
            .andExpect(jsonPath("$.sentDate").value(DEFAULT_SENT_DATE.toString()))
            .andExpect(jsonPath("$.toAddress").value(DEFAULT_TO_ADDRESS))
            .andExpect(jsonPath("$.ccAddress").value(DEFAULT_CC_ADDRESS))
            .andExpect(jsonPath("$.notificationType").value(DEFAULT_NOTIFICATION_TYPE))
            .andExpect(jsonPath("$.subjectLine").value(DEFAULT_SUBJECT_LINE));
    }

    @Test
    @Transactional
    public void getNonExistingNotificationHistory() throws Exception {
        // Get the notificationHistory
        restNotificationHistoryMockMvc.perform(get("/api/notification-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNotificationHistory() throws Exception {
        // Initialize the database
        notificationHistoryRepository.saveAndFlush(notificationHistory);

        int databaseSizeBeforeUpdate = notificationHistoryRepository.findAll().size();

        // Update the notificationHistory
        NotificationHistory updatedNotificationHistory = notificationHistoryRepository.findById(notificationHistory.getId()).get();
        // Disconnect from session so that the updates on updatedNotificationHistory are not directly saved in db
        em.detach(updatedNotificationHistory);
        updatedNotificationHistory
            .fromEmail(UPDATED_FROM_EMAIL)
            .sentDate(UPDATED_SENT_DATE)
            .toAddress(UPDATED_TO_ADDRESS)
            .ccAddress(UPDATED_CC_ADDRESS)
            .notificationType(UPDATED_NOTIFICATION_TYPE)
            .subjectLine(UPDATED_SUBJECT_LINE);

        restNotificationHistoryMockMvc.perform(put("/api/notification-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNotificationHistory)))
            .andExpect(status().isOk());

        // Validate the NotificationHistory in the database
        List<NotificationHistory> notificationHistoryList = notificationHistoryRepository.findAll();
        assertThat(notificationHistoryList).hasSize(databaseSizeBeforeUpdate);
        NotificationHistory testNotificationHistory = notificationHistoryList.get(notificationHistoryList.size() - 1);
        assertThat(testNotificationHistory.getFromEmail()).isEqualTo(UPDATED_FROM_EMAIL);
        assertThat(testNotificationHistory.getSentDate()).isEqualTo(UPDATED_SENT_DATE);
        assertThat(testNotificationHistory.getToAddress()).isEqualTo(UPDATED_TO_ADDRESS);
        assertThat(testNotificationHistory.getCcAddress()).isEqualTo(UPDATED_CC_ADDRESS);
        assertThat(testNotificationHistory.getNotificationType()).isEqualTo(UPDATED_NOTIFICATION_TYPE);
        assertThat(testNotificationHistory.getSubjectLine()).isEqualTo(UPDATED_SUBJECT_LINE);
    }

    @Test
    @Transactional
    public void updateNonExistingNotificationHistory() throws Exception {
        int databaseSizeBeforeUpdate = notificationHistoryRepository.findAll().size();

        // Create the NotificationHistory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationHistoryMockMvc.perform(put("/api/notification-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificationHistory)))
            .andExpect(status().isBadRequest());

        // Validate the NotificationHistory in the database
        List<NotificationHistory> notificationHistoryList = notificationHistoryRepository.findAll();
        assertThat(notificationHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNotificationHistory() throws Exception {
        // Initialize the database
        notificationHistoryRepository.saveAndFlush(notificationHistory);

        int databaseSizeBeforeDelete = notificationHistoryRepository.findAll().size();

        // Delete the notificationHistory
        restNotificationHistoryMockMvc.perform(delete("/api/notification-histories/{id}", notificationHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NotificationHistory> notificationHistoryList = notificationHistoryRepository.findAll();
        assertThat(notificationHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
