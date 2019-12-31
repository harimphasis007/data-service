package com.data.service.web.rest;

import com.data.service.DataserviceApp;
import com.data.service.domain.ProjectLog;
import com.data.service.repository.ProjectLogRepository;
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
 * Integration tests for the {@link ProjectLogResource} REST controller.
 */
@SpringBootTest(classes = DataserviceApp.class)
public class ProjectLogResourceIT {

    private static final String DEFAULT_WORKER = "AAAAAAAAAA";
    private static final String UPDATED_WORKER = "BBBBBBBBBB";

    private static final String DEFAULT_ENTRY_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_ENTRY_DETAILS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ProjectLogRepository projectLogRepository;

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

    private MockMvc restProjectLogMockMvc;

    private ProjectLog projectLog;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectLogResource projectLogResource = new ProjectLogResource(projectLogRepository);
        this.restProjectLogMockMvc = MockMvcBuilders.standaloneSetup(projectLogResource)
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
    public static ProjectLog createEntity(EntityManager em) {
        ProjectLog projectLog = new ProjectLog()
            .worker(DEFAULT_WORKER)
            .entryDetails(DEFAULT_ENTRY_DETAILS)
            .date(DEFAULT_DATE);
        return projectLog;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectLog createUpdatedEntity(EntityManager em) {
        ProjectLog projectLog = new ProjectLog()
            .worker(UPDATED_WORKER)
            .entryDetails(UPDATED_ENTRY_DETAILS)
            .date(UPDATED_DATE);
        return projectLog;
    }

    @BeforeEach
    public void initTest() {
        projectLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectLog() throws Exception {
        int databaseSizeBeforeCreate = projectLogRepository.findAll().size();

        // Create the ProjectLog
        restProjectLogMockMvc.perform(post("/api/project-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectLog)))
            .andExpect(status().isCreated());

        // Validate the ProjectLog in the database
        List<ProjectLog> projectLogList = projectLogRepository.findAll();
        assertThat(projectLogList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectLog testProjectLog = projectLogList.get(projectLogList.size() - 1);
        assertThat(testProjectLog.getWorker()).isEqualTo(DEFAULT_WORKER);
        assertThat(testProjectLog.getEntryDetails()).isEqualTo(DEFAULT_ENTRY_DETAILS);
        assertThat(testProjectLog.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createProjectLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectLogRepository.findAll().size();

        // Create the ProjectLog with an existing ID
        projectLog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectLogMockMvc.perform(post("/api/project-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectLog)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectLog in the database
        List<ProjectLog> projectLogList = projectLogRepository.findAll();
        assertThat(projectLogList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkWorkerIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectLogRepository.findAll().size();
        // set the field null
        projectLog.setWorker(null);

        // Create the ProjectLog, which fails.

        restProjectLogMockMvc.perform(post("/api/project-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectLog)))
            .andExpect(status().isBadRequest());

        List<ProjectLog> projectLogList = projectLogRepository.findAll();
        assertThat(projectLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProjectLogs() throws Exception {
        // Initialize the database
        projectLogRepository.saveAndFlush(projectLog);

        // Get all the projectLogList
        restProjectLogMockMvc.perform(get("/api/project-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].worker").value(hasItem(DEFAULT_WORKER)))
            .andExpect(jsonPath("$.[*].entryDetails").value(hasItem(DEFAULT_ENTRY_DETAILS)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getProjectLog() throws Exception {
        // Initialize the database
        projectLogRepository.saveAndFlush(projectLog);

        // Get the projectLog
        restProjectLogMockMvc.perform(get("/api/project-logs/{id}", projectLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectLog.getId().intValue()))
            .andExpect(jsonPath("$.worker").value(DEFAULT_WORKER))
            .andExpect(jsonPath("$.entryDetails").value(DEFAULT_ENTRY_DETAILS))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProjectLog() throws Exception {
        // Get the projectLog
        restProjectLogMockMvc.perform(get("/api/project-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectLog() throws Exception {
        // Initialize the database
        projectLogRepository.saveAndFlush(projectLog);

        int databaseSizeBeforeUpdate = projectLogRepository.findAll().size();

        // Update the projectLog
        ProjectLog updatedProjectLog = projectLogRepository.findById(projectLog.getId()).get();
        // Disconnect from session so that the updates on updatedProjectLog are not directly saved in db
        em.detach(updatedProjectLog);
        updatedProjectLog
            .worker(UPDATED_WORKER)
            .entryDetails(UPDATED_ENTRY_DETAILS)
            .date(UPDATED_DATE);

        restProjectLogMockMvc.perform(put("/api/project-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectLog)))
            .andExpect(status().isOk());

        // Validate the ProjectLog in the database
        List<ProjectLog> projectLogList = projectLogRepository.findAll();
        assertThat(projectLogList).hasSize(databaseSizeBeforeUpdate);
        ProjectLog testProjectLog = projectLogList.get(projectLogList.size() - 1);
        assertThat(testProjectLog.getWorker()).isEqualTo(UPDATED_WORKER);
        assertThat(testProjectLog.getEntryDetails()).isEqualTo(UPDATED_ENTRY_DETAILS);
        assertThat(testProjectLog.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectLog() throws Exception {
        int databaseSizeBeforeUpdate = projectLogRepository.findAll().size();

        // Create the ProjectLog

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectLogMockMvc.perform(put("/api/project-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectLog)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectLog in the database
        List<ProjectLog> projectLogList = projectLogRepository.findAll();
        assertThat(projectLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjectLog() throws Exception {
        // Initialize the database
        projectLogRepository.saveAndFlush(projectLog);

        int databaseSizeBeforeDelete = projectLogRepository.findAll().size();

        // Delete the projectLog
        restProjectLogMockMvc.perform(delete("/api/project-logs/{id}", projectLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectLog> projectLogList = projectLogRepository.findAll();
        assertThat(projectLogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
