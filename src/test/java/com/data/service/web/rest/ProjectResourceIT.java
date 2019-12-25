package com.data.service.web.rest;

import com.data.service.DataserviceApp;
import com.data.service.domain.Project;
import com.data.service.repository.ProjectRepository;
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
 * Integration tests for the {@link ProjectResource} REST controller.
 */
@SpringBootTest(classes = DataserviceApp.class)
public class ProjectResourceIT {

    private static final String DEFAULT_P_NO = "AAAAAAAAAA";
    private static final String UPDATED_P_NO = "BBBBBBBBBB";

    private static final String DEFAULT_PROGRAM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROGRAM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MEMBER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MEMBER_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_APPLICATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APPLICATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_P_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_P_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_ASSIGN = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_ASSIGN = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CURRENT_ASS_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CURRENT_ASS_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ANALYST_ASSIGN = "AAAAAAAAAA";
    private static final String UPDATED_ANALYST_ASSIGN = "BBBBBBBBBB";

    private static final String DEFAULT_MANAGER_ASSIGN = "AAAAAAAAAA";
    private static final String UPDATED_MANAGER_ASSIGN = "BBBBBBBBBB";

    @Autowired
    private ProjectRepository projectRepository;

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

    private MockMvc restProjectMockMvc;

    private Project project;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectResource projectResource = new ProjectResource(projectRepository);
        this.restProjectMockMvc = MockMvcBuilders.standaloneSetup(projectResource)
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
    public static Project createEntity(EntityManager em) {
        Project project = new Project()
            .pNo(DEFAULT_P_NO)
            .programName(DEFAULT_PROGRAM_NAME)
            .memberName(DEFAULT_MEMBER_NAME)
            .applicationDate(DEFAULT_APPLICATION_DATE)
            .pStatus(DEFAULT_P_STATUS)
            .currentAssign(DEFAULT_CURRENT_ASSIGN)
            .currentAssDate(DEFAULT_CURRENT_ASS_DATE)
            .analystAssign(DEFAULT_ANALYST_ASSIGN)
            .managerAssign(DEFAULT_MANAGER_ASSIGN);
        return project;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Project createUpdatedEntity(EntityManager em) {
        Project project = new Project()
            .pNo(UPDATED_P_NO)
            .programName(UPDATED_PROGRAM_NAME)
            .memberName(UPDATED_MEMBER_NAME)
            .applicationDate(UPDATED_APPLICATION_DATE)
            .pStatus(UPDATED_P_STATUS)
            .currentAssign(UPDATED_CURRENT_ASSIGN)
            .currentAssDate(UPDATED_CURRENT_ASS_DATE)
            .analystAssign(UPDATED_ANALYST_ASSIGN)
            .managerAssign(UPDATED_MANAGER_ASSIGN);
        return project;
    }

    @BeforeEach
    public void initTest() {
        project = createEntity(em);
    }

    @Test
    @Transactional
    public void createProject() throws Exception {
        int databaseSizeBeforeCreate = projectRepository.findAll().size();

        // Create the Project
        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isCreated());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeCreate + 1);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getpNo()).isEqualTo(DEFAULT_P_NO);
        assertThat(testProject.getProgramName()).isEqualTo(DEFAULT_PROGRAM_NAME);
        assertThat(testProject.getMemberName()).isEqualTo(DEFAULT_MEMBER_NAME);
        assertThat(testProject.getApplicationDate()).isEqualTo(DEFAULT_APPLICATION_DATE);
        assertThat(testProject.getpStatus()).isEqualTo(DEFAULT_P_STATUS);
        assertThat(testProject.getCurrentAssign()).isEqualTo(DEFAULT_CURRENT_ASSIGN);
        assertThat(testProject.getCurrentAssDate()).isEqualTo(DEFAULT_CURRENT_ASS_DATE);
        assertThat(testProject.getAnalystAssign()).isEqualTo(DEFAULT_ANALYST_ASSIGN);
        assertThat(testProject.getManagerAssign()).isEqualTo(DEFAULT_MANAGER_ASSIGN);
    }

    @Test
    @Transactional
    public void createProjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectRepository.findAll().size();

        // Create the Project with an existing ID
        project.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProjects() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList
        restProjectMockMvc.perform(get("/api/projects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(project.getId().intValue())))
            .andExpect(jsonPath("$.[*].pNo").value(hasItem(DEFAULT_P_NO)))
            .andExpect(jsonPath("$.[*].programName").value(hasItem(DEFAULT_PROGRAM_NAME)))
            .andExpect(jsonPath("$.[*].memberName").value(hasItem(DEFAULT_MEMBER_NAME)))
            .andExpect(jsonPath("$.[*].applicationDate").value(hasItem(DEFAULT_APPLICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].pStatus").value(hasItem(DEFAULT_P_STATUS)))
            .andExpect(jsonPath("$.[*].currentAssign").value(hasItem(DEFAULT_CURRENT_ASSIGN)))
            .andExpect(jsonPath("$.[*].currentAssDate").value(hasItem(DEFAULT_CURRENT_ASS_DATE.toString())))
            .andExpect(jsonPath("$.[*].analystAssign").value(hasItem(DEFAULT_ANALYST_ASSIGN)))
            .andExpect(jsonPath("$.[*].managerAssign").value(hasItem(DEFAULT_MANAGER_ASSIGN)));
    }
    
    @Test
    @Transactional
    public void getProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get the project
        restProjectMockMvc.perform(get("/api/projects/{id}", project.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(project.getId().intValue()))
            .andExpect(jsonPath("$.pNo").value(DEFAULT_P_NO))
            .andExpect(jsonPath("$.programName").value(DEFAULT_PROGRAM_NAME))
            .andExpect(jsonPath("$.memberName").value(DEFAULT_MEMBER_NAME))
            .andExpect(jsonPath("$.applicationDate").value(DEFAULT_APPLICATION_DATE.toString()))
            .andExpect(jsonPath("$.pStatus").value(DEFAULT_P_STATUS))
            .andExpect(jsonPath("$.currentAssign").value(DEFAULT_CURRENT_ASSIGN))
            .andExpect(jsonPath("$.currentAssDate").value(DEFAULT_CURRENT_ASS_DATE.toString()))
            .andExpect(jsonPath("$.analystAssign").value(DEFAULT_ANALYST_ASSIGN))
            .andExpect(jsonPath("$.managerAssign").value(DEFAULT_MANAGER_ASSIGN));
    }

    @Test
    @Transactional
    public void getNonExistingProject() throws Exception {
        // Get the project
        restProjectMockMvc.perform(get("/api/projects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Update the project
        Project updatedProject = projectRepository.findById(project.getId()).get();
        // Disconnect from session so that the updates on updatedProject are not directly saved in db
        em.detach(updatedProject);
        updatedProject
            .pNo(UPDATED_P_NO)
            .programName(UPDATED_PROGRAM_NAME)
            .memberName(UPDATED_MEMBER_NAME)
            .applicationDate(UPDATED_APPLICATION_DATE)
            .pStatus(UPDATED_P_STATUS)
            .currentAssign(UPDATED_CURRENT_ASSIGN)
            .currentAssDate(UPDATED_CURRENT_ASS_DATE)
            .analystAssign(UPDATED_ANALYST_ASSIGN)
            .managerAssign(UPDATED_MANAGER_ASSIGN);

        restProjectMockMvc.perform(put("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProject)))
            .andExpect(status().isOk());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getpNo()).isEqualTo(UPDATED_P_NO);
        assertThat(testProject.getProgramName()).isEqualTo(UPDATED_PROGRAM_NAME);
        assertThat(testProject.getMemberName()).isEqualTo(UPDATED_MEMBER_NAME);
        assertThat(testProject.getApplicationDate()).isEqualTo(UPDATED_APPLICATION_DATE);
        assertThat(testProject.getpStatus()).isEqualTo(UPDATED_P_STATUS);
        assertThat(testProject.getCurrentAssign()).isEqualTo(UPDATED_CURRENT_ASSIGN);
        assertThat(testProject.getCurrentAssDate()).isEqualTo(UPDATED_CURRENT_ASS_DATE);
        assertThat(testProject.getAnalystAssign()).isEqualTo(UPDATED_ANALYST_ASSIGN);
        assertThat(testProject.getManagerAssign()).isEqualTo(UPDATED_MANAGER_ASSIGN);
    }

    @Test
    @Transactional
    public void updateNonExistingProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Create the Project

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectMockMvc.perform(put("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        int databaseSizeBeforeDelete = projectRepository.findAll().size();

        // Delete the project
        restProjectMockMvc.perform(delete("/api/projects/{id}", project.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
