package com.data.service.web.rest;

import com.data.service.DataserviceApp;
import com.data.service.domain.WorkerHistory;
import com.data.service.repository.WorkerHistoryRepository;
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
import java.util.List;

import static com.data.service.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link WorkerHistoryResource} REST controller.
 */
@SpringBootTest(classes = DataserviceApp.class)
public class WorkerHistoryResourceIT {

    private static final String DEFAULT_WORKER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_WORKER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_WORKER_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_WORKER_ROLE = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NO = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private WorkerHistoryRepository workerHistoryRepository;

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

    private MockMvc restWorkerHistoryMockMvc;

    private WorkerHistory workerHistory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WorkerHistoryResource workerHistoryResource = new WorkerHistoryResource(workerHistoryRepository);
        this.restWorkerHistoryMockMvc = MockMvcBuilders.standaloneSetup(workerHistoryResource)
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
    public static WorkerHistory createEntity(EntityManager em) {
        WorkerHistory workerHistory = new WorkerHistory()
            .workerName(DEFAULT_WORKER_NAME)
            .workerRole(DEFAULT_WORKER_ROLE)
            .source(DEFAULT_SOURCE)
            .phoneNo(DEFAULT_PHONE_NO)
            .email(DEFAULT_EMAIL);
        return workerHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkerHistory createUpdatedEntity(EntityManager em) {
        WorkerHistory workerHistory = new WorkerHistory()
            .workerName(UPDATED_WORKER_NAME)
            .workerRole(UPDATED_WORKER_ROLE)
            .source(UPDATED_SOURCE)
            .phoneNo(UPDATED_PHONE_NO)
            .email(UPDATED_EMAIL);
        return workerHistory;
    }

    @BeforeEach
    public void initTest() {
        workerHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkerHistory() throws Exception {
        int databaseSizeBeforeCreate = workerHistoryRepository.findAll().size();

        // Create the WorkerHistory
        restWorkerHistoryMockMvc.perform(post("/api/worker-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workerHistory)))
            .andExpect(status().isCreated());

        // Validate the WorkerHistory in the database
        List<WorkerHistory> workerHistoryList = workerHistoryRepository.findAll();
        assertThat(workerHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        WorkerHistory testWorkerHistory = workerHistoryList.get(workerHistoryList.size() - 1);
        assertThat(testWorkerHistory.getWorkerName()).isEqualTo(DEFAULT_WORKER_NAME);
        assertThat(testWorkerHistory.getWorkerRole()).isEqualTo(DEFAULT_WORKER_ROLE);
        assertThat(testWorkerHistory.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testWorkerHistory.getPhoneNo()).isEqualTo(DEFAULT_PHONE_NO);
        assertThat(testWorkerHistory.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createWorkerHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workerHistoryRepository.findAll().size();

        // Create the WorkerHistory with an existing ID
        workerHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkerHistoryMockMvc.perform(post("/api/worker-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workerHistory)))
            .andExpect(status().isBadRequest());

        // Validate the WorkerHistory in the database
        List<WorkerHistory> workerHistoryList = workerHistoryRepository.findAll();
        assertThat(workerHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkWorkerNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = workerHistoryRepository.findAll().size();
        // set the field null
        workerHistory.setWorkerName(null);

        // Create the WorkerHistory, which fails.

        restWorkerHistoryMockMvc.perform(post("/api/worker-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workerHistory)))
            .andExpect(status().isBadRequest());

        List<WorkerHistory> workerHistoryList = workerHistoryRepository.findAll();
        assertThat(workerHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWorkerHistories() throws Exception {
        // Initialize the database
        workerHistoryRepository.saveAndFlush(workerHistory);

        // Get all the workerHistoryList
        restWorkerHistoryMockMvc.perform(get("/api/worker-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workerHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].workerName").value(hasItem(DEFAULT_WORKER_NAME)))
            .andExpect(jsonPath("$.[*].workerRole").value(hasItem(DEFAULT_WORKER_ROLE)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].phoneNo").value(hasItem(DEFAULT_PHONE_NO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }
    
    @Test
    @Transactional
    public void getWorkerHistory() throws Exception {
        // Initialize the database
        workerHistoryRepository.saveAndFlush(workerHistory);

        // Get the workerHistory
        restWorkerHistoryMockMvc.perform(get("/api/worker-histories/{id}", workerHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(workerHistory.getId().intValue()))
            .andExpect(jsonPath("$.workerName").value(DEFAULT_WORKER_NAME))
            .andExpect(jsonPath("$.workerRole").value(DEFAULT_WORKER_ROLE))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE))
            .andExpect(jsonPath("$.phoneNo").value(DEFAULT_PHONE_NO))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    @Transactional
    public void getNonExistingWorkerHistory() throws Exception {
        // Get the workerHistory
        restWorkerHistoryMockMvc.perform(get("/api/worker-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkerHistory() throws Exception {
        // Initialize the database
        workerHistoryRepository.saveAndFlush(workerHistory);

        int databaseSizeBeforeUpdate = workerHistoryRepository.findAll().size();

        // Update the workerHistory
        WorkerHistory updatedWorkerHistory = workerHistoryRepository.findById(workerHistory.getId()).get();
        // Disconnect from session so that the updates on updatedWorkerHistory are not directly saved in db
        em.detach(updatedWorkerHistory);
        updatedWorkerHistory
            .workerName(UPDATED_WORKER_NAME)
            .workerRole(UPDATED_WORKER_ROLE)
            .source(UPDATED_SOURCE)
            .phoneNo(UPDATED_PHONE_NO)
            .email(UPDATED_EMAIL);

        restWorkerHistoryMockMvc.perform(put("/api/worker-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorkerHistory)))
            .andExpect(status().isOk());

        // Validate the WorkerHistory in the database
        List<WorkerHistory> workerHistoryList = workerHistoryRepository.findAll();
        assertThat(workerHistoryList).hasSize(databaseSizeBeforeUpdate);
        WorkerHistory testWorkerHistory = workerHistoryList.get(workerHistoryList.size() - 1);
        assertThat(testWorkerHistory.getWorkerName()).isEqualTo(UPDATED_WORKER_NAME);
        assertThat(testWorkerHistory.getWorkerRole()).isEqualTo(UPDATED_WORKER_ROLE);
        assertThat(testWorkerHistory.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testWorkerHistory.getPhoneNo()).isEqualTo(UPDATED_PHONE_NO);
        assertThat(testWorkerHistory.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkerHistory() throws Exception {
        int databaseSizeBeforeUpdate = workerHistoryRepository.findAll().size();

        // Create the WorkerHistory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkerHistoryMockMvc.perform(put("/api/worker-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workerHistory)))
            .andExpect(status().isBadRequest());

        // Validate the WorkerHistory in the database
        List<WorkerHistory> workerHistoryList = workerHistoryRepository.findAll();
        assertThat(workerHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorkerHistory() throws Exception {
        // Initialize the database
        workerHistoryRepository.saveAndFlush(workerHistory);

        int databaseSizeBeforeDelete = workerHistoryRepository.findAll().size();

        // Delete the workerHistory
        restWorkerHistoryMockMvc.perform(delete("/api/worker-histories/{id}", workerHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkerHistory> workerHistoryList = workerHistoryRepository.findAll();
        assertThat(workerHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
