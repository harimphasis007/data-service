package com.data.service.web.rest;

import com.data.service.DataserviceApp;
import com.data.service.domain.Commitment;
import com.data.service.repository.CommitmentRepository;
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
 * Integration tests for the {@link CommitmentResource} REST controller.
 */
@SpringBootTest(classes = DataserviceApp.class)
public class CommitmentResourceIT {

    private static final String DEFAULT_COMMITMENT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_COMMITMENT_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_COMMITMENT_BAL = "AAAAAAAAAA";
    private static final String UPDATED_COMMITMENT_BAL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_COMMITMENT_EXPIRATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COMMITMENT_EXPIRATION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_COMMENCE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COMMENCE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_AVAIABLE_BAL = "AAAAAAAAAA";
    private static final String UPDATED_AVAIABLE_BAL = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAL_DRAWDOWNS = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_DRAWDOWNS = "BBBBBBBBBB";

    private static final String DEFAULT_RECENT_DRAWDOWN = "AAAAAAAAAA";
    private static final String UPDATED_RECENT_DRAWDOWN = "BBBBBBBBBB";

    private static final String DEFAULT_AMOUNT_EXPIRING = "AAAAAAAAAA";
    private static final String UPDATED_AMOUNT_EXPIRING = "BBBBBBBBBB";

    @Autowired
    private CommitmentRepository commitmentRepository;

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

    private MockMvc restCommitmentMockMvc;

    private Commitment commitment;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommitmentResource commitmentResource = new CommitmentResource(commitmentRepository);
        this.restCommitmentMockMvc = MockMvcBuilders.standaloneSetup(commitmentResource)
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
    public static Commitment createEntity(EntityManager em) {
        Commitment commitment = new Commitment()
            .commitmentStatus(DEFAULT_COMMITMENT_STATUS)
            .commitmentBal(DEFAULT_COMMITMENT_BAL)
            .commitmentExpiration(DEFAULT_COMMITMENT_EXPIRATION)
            .commenceDate(DEFAULT_COMMENCE_DATE)
            .avaiableBal(DEFAULT_AVAIABLE_BAL)
            .totalDrawdowns(DEFAULT_TOTAL_DRAWDOWNS)
            .recentDrawdown(DEFAULT_RECENT_DRAWDOWN)
            .amountExpiring(DEFAULT_AMOUNT_EXPIRING);
        return commitment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commitment createUpdatedEntity(EntityManager em) {
        Commitment commitment = new Commitment()
            .commitmentStatus(UPDATED_COMMITMENT_STATUS)
            .commitmentBal(UPDATED_COMMITMENT_BAL)
            .commitmentExpiration(UPDATED_COMMITMENT_EXPIRATION)
            .commenceDate(UPDATED_COMMENCE_DATE)
            .avaiableBal(UPDATED_AVAIABLE_BAL)
            .totalDrawdowns(UPDATED_TOTAL_DRAWDOWNS)
            .recentDrawdown(UPDATED_RECENT_DRAWDOWN)
            .amountExpiring(UPDATED_AMOUNT_EXPIRING);
        return commitment;
    }

    @BeforeEach
    public void initTest() {
        commitment = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommitment() throws Exception {
        int databaseSizeBeforeCreate = commitmentRepository.findAll().size();

        // Create the Commitment
        restCommitmentMockMvc.perform(post("/api/commitments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commitment)))
            .andExpect(status().isCreated());

        // Validate the Commitment in the database
        List<Commitment> commitmentList = commitmentRepository.findAll();
        assertThat(commitmentList).hasSize(databaseSizeBeforeCreate + 1);
        Commitment testCommitment = commitmentList.get(commitmentList.size() - 1);
        assertThat(testCommitment.getCommitmentStatus()).isEqualTo(DEFAULT_COMMITMENT_STATUS);
        assertThat(testCommitment.getCommitmentBal()).isEqualTo(DEFAULT_COMMITMENT_BAL);
        assertThat(testCommitment.getCommitmentExpiration()).isEqualTo(DEFAULT_COMMITMENT_EXPIRATION);
        assertThat(testCommitment.getCommenceDate()).isEqualTo(DEFAULT_COMMENCE_DATE);
        assertThat(testCommitment.getAvaiableBal()).isEqualTo(DEFAULT_AVAIABLE_BAL);
        assertThat(testCommitment.getTotalDrawdowns()).isEqualTo(DEFAULT_TOTAL_DRAWDOWNS);
        assertThat(testCommitment.getRecentDrawdown()).isEqualTo(DEFAULT_RECENT_DRAWDOWN);
        assertThat(testCommitment.getAmountExpiring()).isEqualTo(DEFAULT_AMOUNT_EXPIRING);
    }

    @Test
    @Transactional
    public void createCommitmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commitmentRepository.findAll().size();

        // Create the Commitment with an existing ID
        commitment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommitmentMockMvc.perform(post("/api/commitments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commitment)))
            .andExpect(status().isBadRequest());

        // Validate the Commitment in the database
        List<Commitment> commitmentList = commitmentRepository.findAll();
        assertThat(commitmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCommitmentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = commitmentRepository.findAll().size();
        // set the field null
        commitment.setCommitmentStatus(null);

        // Create the Commitment, which fails.

        restCommitmentMockMvc.perform(post("/api/commitments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commitment)))
            .andExpect(status().isBadRequest());

        List<Commitment> commitmentList = commitmentRepository.findAll();
        assertThat(commitmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommitments() throws Exception {
        // Initialize the database
        commitmentRepository.saveAndFlush(commitment);

        // Get all the commitmentList
        restCommitmentMockMvc.perform(get("/api/commitments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commitment.getId().intValue())))
            .andExpect(jsonPath("$.[*].commitmentStatus").value(hasItem(DEFAULT_COMMITMENT_STATUS)))
            .andExpect(jsonPath("$.[*].commitmentBal").value(hasItem(DEFAULT_COMMITMENT_BAL)))
            .andExpect(jsonPath("$.[*].commitmentExpiration").value(hasItem(DEFAULT_COMMITMENT_EXPIRATION.toString())))
            .andExpect(jsonPath("$.[*].commenceDate").value(hasItem(DEFAULT_COMMENCE_DATE.toString())))
            .andExpect(jsonPath("$.[*].avaiableBal").value(hasItem(DEFAULT_AVAIABLE_BAL)))
            .andExpect(jsonPath("$.[*].totalDrawdowns").value(hasItem(DEFAULT_TOTAL_DRAWDOWNS)))
            .andExpect(jsonPath("$.[*].recentDrawdown").value(hasItem(DEFAULT_RECENT_DRAWDOWN)))
            .andExpect(jsonPath("$.[*].amountExpiring").value(hasItem(DEFAULT_AMOUNT_EXPIRING)));
    }
    
    @Test
    @Transactional
    public void getCommitment() throws Exception {
        // Initialize the database
        commitmentRepository.saveAndFlush(commitment);

        // Get the commitment
        restCommitmentMockMvc.perform(get("/api/commitments/{id}", commitment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commitment.getId().intValue()))
            .andExpect(jsonPath("$.commitmentStatus").value(DEFAULT_COMMITMENT_STATUS))
            .andExpect(jsonPath("$.commitmentBal").value(DEFAULT_COMMITMENT_BAL))
            .andExpect(jsonPath("$.commitmentExpiration").value(DEFAULT_COMMITMENT_EXPIRATION.toString()))
            .andExpect(jsonPath("$.commenceDate").value(DEFAULT_COMMENCE_DATE.toString()))
            .andExpect(jsonPath("$.avaiableBal").value(DEFAULT_AVAIABLE_BAL))
            .andExpect(jsonPath("$.totalDrawdowns").value(DEFAULT_TOTAL_DRAWDOWNS))
            .andExpect(jsonPath("$.recentDrawdown").value(DEFAULT_RECENT_DRAWDOWN))
            .andExpect(jsonPath("$.amountExpiring").value(DEFAULT_AMOUNT_EXPIRING));
    }

    @Test
    @Transactional
    public void getNonExistingCommitment() throws Exception {
        // Get the commitment
        restCommitmentMockMvc.perform(get("/api/commitments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommitment() throws Exception {
        // Initialize the database
        commitmentRepository.saveAndFlush(commitment);

        int databaseSizeBeforeUpdate = commitmentRepository.findAll().size();

        // Update the commitment
        Commitment updatedCommitment = commitmentRepository.findById(commitment.getId()).get();
        // Disconnect from session so that the updates on updatedCommitment are not directly saved in db
        em.detach(updatedCommitment);
        updatedCommitment
            .commitmentStatus(UPDATED_COMMITMENT_STATUS)
            .commitmentBal(UPDATED_COMMITMENT_BAL)
            .commitmentExpiration(UPDATED_COMMITMENT_EXPIRATION)
            .commenceDate(UPDATED_COMMENCE_DATE)
            .avaiableBal(UPDATED_AVAIABLE_BAL)
            .totalDrawdowns(UPDATED_TOTAL_DRAWDOWNS)
            .recentDrawdown(UPDATED_RECENT_DRAWDOWN)
            .amountExpiring(UPDATED_AMOUNT_EXPIRING);

        restCommitmentMockMvc.perform(put("/api/commitments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommitment)))
            .andExpect(status().isOk());

        // Validate the Commitment in the database
        List<Commitment> commitmentList = commitmentRepository.findAll();
        assertThat(commitmentList).hasSize(databaseSizeBeforeUpdate);
        Commitment testCommitment = commitmentList.get(commitmentList.size() - 1);
        assertThat(testCommitment.getCommitmentStatus()).isEqualTo(UPDATED_COMMITMENT_STATUS);
        assertThat(testCommitment.getCommitmentBal()).isEqualTo(UPDATED_COMMITMENT_BAL);
        assertThat(testCommitment.getCommitmentExpiration()).isEqualTo(UPDATED_COMMITMENT_EXPIRATION);
        assertThat(testCommitment.getCommenceDate()).isEqualTo(UPDATED_COMMENCE_DATE);
        assertThat(testCommitment.getAvaiableBal()).isEqualTo(UPDATED_AVAIABLE_BAL);
        assertThat(testCommitment.getTotalDrawdowns()).isEqualTo(UPDATED_TOTAL_DRAWDOWNS);
        assertThat(testCommitment.getRecentDrawdown()).isEqualTo(UPDATED_RECENT_DRAWDOWN);
        assertThat(testCommitment.getAmountExpiring()).isEqualTo(UPDATED_AMOUNT_EXPIRING);
    }

    @Test
    @Transactional
    public void updateNonExistingCommitment() throws Exception {
        int databaseSizeBeforeUpdate = commitmentRepository.findAll().size();

        // Create the Commitment

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommitmentMockMvc.perform(put("/api/commitments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commitment)))
            .andExpect(status().isBadRequest());

        // Validate the Commitment in the database
        List<Commitment> commitmentList = commitmentRepository.findAll();
        assertThat(commitmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommitment() throws Exception {
        // Initialize the database
        commitmentRepository.saveAndFlush(commitment);

        int databaseSizeBeforeDelete = commitmentRepository.findAll().size();

        // Delete the commitment
        restCommitmentMockMvc.perform(delete("/api/commitments/{id}", commitment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Commitment> commitmentList = commitmentRepository.findAll();
        assertThat(commitmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
