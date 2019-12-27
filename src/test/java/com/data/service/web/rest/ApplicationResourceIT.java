package com.data.service.web.rest;

import com.data.service.DataserviceApp;
import com.data.service.domain.Application;
import com.data.service.repository.ApplicationRepository;
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
 * Integration tests for the {@link ApplicationResource} REST controller.
 */
@SpringBootTest(classes = DataserviceApp.class)
public class ApplicationResourceIT {

    private static final String DEFAULT_APPLICATION_NO = "AAAAAAAAAA";
    private static final String UPDATED_APPLICATION_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_APPLICATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APPLICATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_APPLICATION_REVIEW_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_APPLICATION_REVIEW_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAL_AMOUNT_REQUESTED = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_AMOUNT_REQUESTED = "BBBBBBBBBB";

    private static final String DEFAULT_MANAGER_DECISION = "AAAAAAAAAA";
    private static final String UPDATED_MANAGER_DECISION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MANAGER_REVIEW_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MANAGER_REVIEW_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TOTAL_REVIEW_TURN_TIME = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_REVIEW_TURN_TIME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_NOTIFICATION_SENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NOTIFICATION_SENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CERTIFICATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CERTIFICATION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CERTIFICATION_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_CERTIFICATION_TITLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CERTIFICATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CERTIFICATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PROJECT_SPECIFIC_APPLICATION = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_SPECIFIC_APPLICATION = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_REVIEW_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_REVIEW_STATUS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_COMPLETE_PER = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_COMPLETE_PER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_LAST_UPDATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_UPDATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ApplicationRepository applicationRepository;

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

    private MockMvc restApplicationMockMvc;

    private Application application;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApplicationResource applicationResource = new ApplicationResource(applicationRepository);
        this.restApplicationMockMvc = MockMvcBuilders.standaloneSetup(applicationResource)
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
    public static Application createEntity(EntityManager em) {
        Application application = new Application()
            .applicationNo(DEFAULT_APPLICATION_NO)
            .applicationDate(DEFAULT_APPLICATION_DATE)
            .applicationReviewStatus(DEFAULT_APPLICATION_REVIEW_STATUS)
            .totalAmountRequested(DEFAULT_TOTAL_AMOUNT_REQUESTED)
            .managerDecision(DEFAULT_MANAGER_DECISION)
            .managerReviewEndDate(DEFAULT_MANAGER_REVIEW_END_DATE)
            .totalReviewTurnTime(DEFAULT_TOTAL_REVIEW_TURN_TIME)
            .notificationSentDate(DEFAULT_NOTIFICATION_SENT_DATE)
            .certificationName(DEFAULT_CERTIFICATION_NAME)
            .certificationTitle(DEFAULT_CERTIFICATION_TITLE)
            .certificationDate(DEFAULT_CERTIFICATION_DATE)
            .projectSpecificApplication(DEFAULT_PROJECT_SPECIFIC_APPLICATION)
            .currentReviewStatus(DEFAULT_CURRENT_REVIEW_STATUS)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .currentCompletePer(DEFAULT_CURRENT_COMPLETE_PER)
            .lastUpdatedOn(DEFAULT_LAST_UPDATED_ON);
        return application;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Application createUpdatedEntity(EntityManager em) {
        Application application = new Application()
            .applicationNo(UPDATED_APPLICATION_NO)
            .applicationDate(UPDATED_APPLICATION_DATE)
            .applicationReviewStatus(UPDATED_APPLICATION_REVIEW_STATUS)
            .totalAmountRequested(UPDATED_TOTAL_AMOUNT_REQUESTED)
            .managerDecision(UPDATED_MANAGER_DECISION)
            .managerReviewEndDate(UPDATED_MANAGER_REVIEW_END_DATE)
            .totalReviewTurnTime(UPDATED_TOTAL_REVIEW_TURN_TIME)
            .notificationSentDate(UPDATED_NOTIFICATION_SENT_DATE)
            .certificationName(UPDATED_CERTIFICATION_NAME)
            .certificationTitle(UPDATED_CERTIFICATION_TITLE)
            .certificationDate(UPDATED_CERTIFICATION_DATE)
            .projectSpecificApplication(UPDATED_PROJECT_SPECIFIC_APPLICATION)
            .currentReviewStatus(UPDATED_CURRENT_REVIEW_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .currentCompletePer(UPDATED_CURRENT_COMPLETE_PER)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON);
        return application;
    }

    @BeforeEach
    public void initTest() {
        application = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplication() throws Exception {
        int databaseSizeBeforeCreate = applicationRepository.findAll().size();

        // Create the Application
        restApplicationMockMvc.perform(post("/api/applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(application)))
            .andExpect(status().isCreated());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeCreate + 1);
        Application testApplication = applicationList.get(applicationList.size() - 1);
        assertThat(testApplication.getApplicationNo()).isEqualTo(DEFAULT_APPLICATION_NO);
        assertThat(testApplication.getApplicationDate()).isEqualTo(DEFAULT_APPLICATION_DATE);
        assertThat(testApplication.getApplicationReviewStatus()).isEqualTo(DEFAULT_APPLICATION_REVIEW_STATUS);
        assertThat(testApplication.getTotalAmountRequested()).isEqualTo(DEFAULT_TOTAL_AMOUNT_REQUESTED);
        assertThat(testApplication.getManagerDecision()).isEqualTo(DEFAULT_MANAGER_DECISION);
        assertThat(testApplication.getManagerReviewEndDate()).isEqualTo(DEFAULT_MANAGER_REVIEW_END_DATE);
        assertThat(testApplication.getTotalReviewTurnTime()).isEqualTo(DEFAULT_TOTAL_REVIEW_TURN_TIME);
        assertThat(testApplication.getNotificationSentDate()).isEqualTo(DEFAULT_NOTIFICATION_SENT_DATE);
        assertThat(testApplication.getCertificationName()).isEqualTo(DEFAULT_CERTIFICATION_NAME);
        assertThat(testApplication.getCertificationTitle()).isEqualTo(DEFAULT_CERTIFICATION_TITLE);
        assertThat(testApplication.getCertificationDate()).isEqualTo(DEFAULT_CERTIFICATION_DATE);
        assertThat(testApplication.getProjectSpecificApplication()).isEqualTo(DEFAULT_PROJECT_SPECIFIC_APPLICATION);
        assertThat(testApplication.getCurrentReviewStatus()).isEqualTo(DEFAULT_CURRENT_REVIEW_STATUS);
        assertThat(testApplication.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testApplication.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testApplication.getCurrentCompletePer()).isEqualTo(DEFAULT_CURRENT_COMPLETE_PER);
        assertThat(testApplication.getLastUpdatedOn()).isEqualTo(DEFAULT_LAST_UPDATED_ON);
    }

    @Test
    @Transactional
    public void createApplicationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationRepository.findAll().size();

        // Create the Application with an existing ID
        application.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationMockMvc.perform(post("/api/applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(application)))
            .andExpect(status().isBadRequest());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkApplicationNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setApplicationNo(null);

        // Create the Application, which fails.

        restApplicationMockMvc.perform(post("/api/applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(application)))
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplications() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList
        restApplicationMockMvc.perform(get("/api/applications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(application.getId().intValue())))
            .andExpect(jsonPath("$.[*].applicationNo").value(hasItem(DEFAULT_APPLICATION_NO)))
            .andExpect(jsonPath("$.[*].applicationDate").value(hasItem(DEFAULT_APPLICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].applicationReviewStatus").value(hasItem(DEFAULT_APPLICATION_REVIEW_STATUS)))
            .andExpect(jsonPath("$.[*].totalAmountRequested").value(hasItem(DEFAULT_TOTAL_AMOUNT_REQUESTED)))
            .andExpect(jsonPath("$.[*].managerDecision").value(hasItem(DEFAULT_MANAGER_DECISION)))
            .andExpect(jsonPath("$.[*].managerReviewEndDate").value(hasItem(DEFAULT_MANAGER_REVIEW_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].totalReviewTurnTime").value(hasItem(DEFAULT_TOTAL_REVIEW_TURN_TIME)))
            .andExpect(jsonPath("$.[*].notificationSentDate").value(hasItem(DEFAULT_NOTIFICATION_SENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].certificationName").value(hasItem(DEFAULT_CERTIFICATION_NAME)))
            .andExpect(jsonPath("$.[*].certificationTitle").value(hasItem(DEFAULT_CERTIFICATION_TITLE)))
            .andExpect(jsonPath("$.[*].certificationDate").value(hasItem(DEFAULT_CERTIFICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].projectSpecificApplication").value(hasItem(DEFAULT_PROJECT_SPECIFIC_APPLICATION)))
            .andExpect(jsonPath("$.[*].currentReviewStatus").value(hasItem(DEFAULT_CURRENT_REVIEW_STATUS)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].currentCompletePer").value(hasItem(DEFAULT_CURRENT_COMPLETE_PER)))
            .andExpect(jsonPath("$.[*].lastUpdatedOn").value(hasItem(DEFAULT_LAST_UPDATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getApplication() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get the application
        restApplicationMockMvc.perform(get("/api/applications/{id}", application.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(application.getId().intValue()))
            .andExpect(jsonPath("$.applicationNo").value(DEFAULT_APPLICATION_NO))
            .andExpect(jsonPath("$.applicationDate").value(DEFAULT_APPLICATION_DATE.toString()))
            .andExpect(jsonPath("$.applicationReviewStatus").value(DEFAULT_APPLICATION_REVIEW_STATUS))
            .andExpect(jsonPath("$.totalAmountRequested").value(DEFAULT_TOTAL_AMOUNT_REQUESTED))
            .andExpect(jsonPath("$.managerDecision").value(DEFAULT_MANAGER_DECISION))
            .andExpect(jsonPath("$.managerReviewEndDate").value(DEFAULT_MANAGER_REVIEW_END_DATE.toString()))
            .andExpect(jsonPath("$.totalReviewTurnTime").value(DEFAULT_TOTAL_REVIEW_TURN_TIME))
            .andExpect(jsonPath("$.notificationSentDate").value(DEFAULT_NOTIFICATION_SENT_DATE.toString()))
            .andExpect(jsonPath("$.certificationName").value(DEFAULT_CERTIFICATION_NAME))
            .andExpect(jsonPath("$.certificationTitle").value(DEFAULT_CERTIFICATION_TITLE))
            .andExpect(jsonPath("$.certificationDate").value(DEFAULT_CERTIFICATION_DATE.toString()))
            .andExpect(jsonPath("$.projectSpecificApplication").value(DEFAULT_PROJECT_SPECIFIC_APPLICATION))
            .andExpect(jsonPath("$.currentReviewStatus").value(DEFAULT_CURRENT_REVIEW_STATUS))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.currentCompletePer").value(DEFAULT_CURRENT_COMPLETE_PER))
            .andExpect(jsonPath("$.lastUpdatedOn").value(DEFAULT_LAST_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApplication() throws Exception {
        // Get the application
        restApplicationMockMvc.perform(get("/api/applications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplication() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();

        // Update the application
        Application updatedApplication = applicationRepository.findById(application.getId()).get();
        // Disconnect from session so that the updates on updatedApplication are not directly saved in db
        em.detach(updatedApplication);
        updatedApplication
            .applicationNo(UPDATED_APPLICATION_NO)
            .applicationDate(UPDATED_APPLICATION_DATE)
            .applicationReviewStatus(UPDATED_APPLICATION_REVIEW_STATUS)
            .totalAmountRequested(UPDATED_TOTAL_AMOUNT_REQUESTED)
            .managerDecision(UPDATED_MANAGER_DECISION)
            .managerReviewEndDate(UPDATED_MANAGER_REVIEW_END_DATE)
            .totalReviewTurnTime(UPDATED_TOTAL_REVIEW_TURN_TIME)
            .notificationSentDate(UPDATED_NOTIFICATION_SENT_DATE)
            .certificationName(UPDATED_CERTIFICATION_NAME)
            .certificationTitle(UPDATED_CERTIFICATION_TITLE)
            .certificationDate(UPDATED_CERTIFICATION_DATE)
            .projectSpecificApplication(UPDATED_PROJECT_SPECIFIC_APPLICATION)
            .currentReviewStatus(UPDATED_CURRENT_REVIEW_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .currentCompletePer(UPDATED_CURRENT_COMPLETE_PER)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON);

        restApplicationMockMvc.perform(put("/api/applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApplication)))
            .andExpect(status().isOk());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
        Application testApplication = applicationList.get(applicationList.size() - 1);
        assertThat(testApplication.getApplicationNo()).isEqualTo(UPDATED_APPLICATION_NO);
        assertThat(testApplication.getApplicationDate()).isEqualTo(UPDATED_APPLICATION_DATE);
        assertThat(testApplication.getApplicationReviewStatus()).isEqualTo(UPDATED_APPLICATION_REVIEW_STATUS);
        assertThat(testApplication.getTotalAmountRequested()).isEqualTo(UPDATED_TOTAL_AMOUNT_REQUESTED);
        assertThat(testApplication.getManagerDecision()).isEqualTo(UPDATED_MANAGER_DECISION);
        assertThat(testApplication.getManagerReviewEndDate()).isEqualTo(UPDATED_MANAGER_REVIEW_END_DATE);
        assertThat(testApplication.getTotalReviewTurnTime()).isEqualTo(UPDATED_TOTAL_REVIEW_TURN_TIME);
        assertThat(testApplication.getNotificationSentDate()).isEqualTo(UPDATED_NOTIFICATION_SENT_DATE);
        assertThat(testApplication.getCertificationName()).isEqualTo(UPDATED_CERTIFICATION_NAME);
        assertThat(testApplication.getCertificationTitle()).isEqualTo(UPDATED_CERTIFICATION_TITLE);
        assertThat(testApplication.getCertificationDate()).isEqualTo(UPDATED_CERTIFICATION_DATE);
        assertThat(testApplication.getProjectSpecificApplication()).isEqualTo(UPDATED_PROJECT_SPECIFIC_APPLICATION);
        assertThat(testApplication.getCurrentReviewStatus()).isEqualTo(UPDATED_CURRENT_REVIEW_STATUS);
        assertThat(testApplication.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testApplication.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApplication.getCurrentCompletePer()).isEqualTo(UPDATED_CURRENT_COMPLETE_PER);
        assertThat(testApplication.getLastUpdatedOn()).isEqualTo(UPDATED_LAST_UPDATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingApplication() throws Exception {
        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();

        // Create the Application

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationMockMvc.perform(put("/api/applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(application)))
            .andExpect(status().isBadRequest());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplication() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        int databaseSizeBeforeDelete = applicationRepository.findAll().size();

        // Delete the application
        restApplicationMockMvc.perform(delete("/api/applications/{id}", application.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
