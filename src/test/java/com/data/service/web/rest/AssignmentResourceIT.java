package com.data.service.web.rest;

import com.data.service.DataserviceApp;
import com.data.service.domain.Assignment;
import com.data.service.repository.AssignmentRepository;
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
 * Integration tests for the {@link AssignmentResource} REST controller.
 */
@SpringBootTest(classes = DataserviceApp.class)
public class AssignmentResourceIT {

    private static final String DEFAULT_CURRENT_ASSIGN = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_ASSIGN = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CURRENT_ASS_ST_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CURRENT_ASS_ST_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ANALYST_ASSIGN = "AAAAAAAAAA";
    private static final String UPDATED_ANALYST_ASSIGN = "BBBBBBBBBB";

    private static final String DEFAULT_MANAGER_ASSIGN = "AAAAAAAAAA";
    private static final String UPDATED_MANAGER_ASSIGN = "BBBBBBBBBB";

    @Autowired
    private AssignmentRepository assignmentRepository;

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

    private MockMvc restAssignmentMockMvc;

    private Assignment assignment;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AssignmentResource assignmentResource = new AssignmentResource(assignmentRepository);
        this.restAssignmentMockMvc = MockMvcBuilders.standaloneSetup(assignmentResource)
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
    public static Assignment createEntity(EntityManager em) {
        Assignment assignment = new Assignment()
            .currentAssign(DEFAULT_CURRENT_ASSIGN)
            .currentAssStDate(DEFAULT_CURRENT_ASS_ST_DATE)
            .analystAssign(DEFAULT_ANALYST_ASSIGN)
            .managerAssign(DEFAULT_MANAGER_ASSIGN);
        return assignment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Assignment createUpdatedEntity(EntityManager em) {
        Assignment assignment = new Assignment()
            .currentAssign(UPDATED_CURRENT_ASSIGN)
            .currentAssStDate(UPDATED_CURRENT_ASS_ST_DATE)
            .analystAssign(UPDATED_ANALYST_ASSIGN)
            .managerAssign(UPDATED_MANAGER_ASSIGN);
        return assignment;
    }

    @BeforeEach
    public void initTest() {
        assignment = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssignment() throws Exception {
        int databaseSizeBeforeCreate = assignmentRepository.findAll().size();

        // Create the Assignment
        restAssignmentMockMvc.perform(post("/api/assignments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assignment)))
            .andExpect(status().isCreated());

        // Validate the Assignment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeCreate + 1);
        Assignment testAssignment = assignmentList.get(assignmentList.size() - 1);
        assertThat(testAssignment.getCurrentAssign()).isEqualTo(DEFAULT_CURRENT_ASSIGN);
        assertThat(testAssignment.getCurrentAssStDate()).isEqualTo(DEFAULT_CURRENT_ASS_ST_DATE);
        assertThat(testAssignment.getAnalystAssign()).isEqualTo(DEFAULT_ANALYST_ASSIGN);
        assertThat(testAssignment.getManagerAssign()).isEqualTo(DEFAULT_MANAGER_ASSIGN);
    }

    @Test
    @Transactional
    public void createAssignmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assignmentRepository.findAll().size();

        // Create the Assignment with an existing ID
        assignment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssignmentMockMvc.perform(post("/api/assignments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assignment)))
            .andExpect(status().isBadRequest());

        // Validate the Assignment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCurrentAssignIsRequired() throws Exception {
        int databaseSizeBeforeTest = assignmentRepository.findAll().size();
        // set the field null
        assignment.setCurrentAssign(null);

        // Create the Assignment, which fails.

        restAssignmentMockMvc.perform(post("/api/assignments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assignment)))
            .andExpect(status().isBadRequest());

        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAssignments() throws Exception {
        // Initialize the database
        assignmentRepository.saveAndFlush(assignment);

        // Get all the assignmentList
        restAssignmentMockMvc.perform(get("/api/assignments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assignment.getId().intValue())))
            .andExpect(jsonPath("$.[*].currentAssign").value(hasItem(DEFAULT_CURRENT_ASSIGN)))
            .andExpect(jsonPath("$.[*].currentAssStDate").value(hasItem(DEFAULT_CURRENT_ASS_ST_DATE.toString())))
            .andExpect(jsonPath("$.[*].analystAssign").value(hasItem(DEFAULT_ANALYST_ASSIGN)))
            .andExpect(jsonPath("$.[*].managerAssign").value(hasItem(DEFAULT_MANAGER_ASSIGN)));
    }
    
    @Test
    @Transactional
    public void getAssignment() throws Exception {
        // Initialize the database
        assignmentRepository.saveAndFlush(assignment);

        // Get the assignment
        restAssignmentMockMvc.perform(get("/api/assignments/{id}", assignment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(assignment.getId().intValue()))
            .andExpect(jsonPath("$.currentAssign").value(DEFAULT_CURRENT_ASSIGN))
            .andExpect(jsonPath("$.currentAssStDate").value(DEFAULT_CURRENT_ASS_ST_DATE.toString()))
            .andExpect(jsonPath("$.analystAssign").value(DEFAULT_ANALYST_ASSIGN))
            .andExpect(jsonPath("$.managerAssign").value(DEFAULT_MANAGER_ASSIGN));
    }

    @Test
    @Transactional
    public void getNonExistingAssignment() throws Exception {
        // Get the assignment
        restAssignmentMockMvc.perform(get("/api/assignments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssignment() throws Exception {
        // Initialize the database
        assignmentRepository.saveAndFlush(assignment);

        int databaseSizeBeforeUpdate = assignmentRepository.findAll().size();

        // Update the assignment
        Assignment updatedAssignment = assignmentRepository.findById(assignment.getId()).get();
        // Disconnect from session so that the updates on updatedAssignment are not directly saved in db
        em.detach(updatedAssignment);
        updatedAssignment
            .currentAssign(UPDATED_CURRENT_ASSIGN)
            .currentAssStDate(UPDATED_CURRENT_ASS_ST_DATE)
            .analystAssign(UPDATED_ANALYST_ASSIGN)
            .managerAssign(UPDATED_MANAGER_ASSIGN);

        restAssignmentMockMvc.perform(put("/api/assignments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAssignment)))
            .andExpect(status().isOk());

        // Validate the Assignment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeUpdate);
        Assignment testAssignment = assignmentList.get(assignmentList.size() - 1);
        assertThat(testAssignment.getCurrentAssign()).isEqualTo(UPDATED_CURRENT_ASSIGN);
        assertThat(testAssignment.getCurrentAssStDate()).isEqualTo(UPDATED_CURRENT_ASS_ST_DATE);
        assertThat(testAssignment.getAnalystAssign()).isEqualTo(UPDATED_ANALYST_ASSIGN);
        assertThat(testAssignment.getManagerAssign()).isEqualTo(UPDATED_MANAGER_ASSIGN);
    }

    @Test
    @Transactional
    public void updateNonExistingAssignment() throws Exception {
        int databaseSizeBeforeUpdate = assignmentRepository.findAll().size();

        // Create the Assignment

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssignmentMockMvc.perform(put("/api/assignments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assignment)))
            .andExpect(status().isBadRequest());

        // Validate the Assignment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAssignment() throws Exception {
        // Initialize the database
        assignmentRepository.saveAndFlush(assignment);

        int databaseSizeBeforeDelete = assignmentRepository.findAll().size();

        // Delete the assignment
        restAssignmentMockMvc.perform(delete("/api/assignments/{id}", assignment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
