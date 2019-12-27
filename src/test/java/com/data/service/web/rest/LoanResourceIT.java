package com.data.service.web.rest;

import com.data.service.DataserviceApp;
import com.data.service.domain.Loan;
import com.data.service.repository.LoanRepository;
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
 * Integration tests for the {@link LoanResource} REST controller.
 */
@SpringBootTest(classes = DataserviceApp.class)
public class LoanResourceIT {

    private static final String DEFAULT_LOANID = "AAAAAAAAAA";
    private static final String UPDATED_LOANID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SETTELMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SETTELMENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_TERM = "AAAAAAAAAA";
    private static final String UPDATED_TERM = "BBBBBBBBBB";

    private static final String DEFAULT_PROPERTY_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_PROPERTY_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_MBS_POOL_ID = "AAAAAAAAAA";
    private static final String UPDATED_MBS_POOL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_MBS_QUALIFIES = "AAAAAAAAAA";
    private static final String UPDATED_MBS_QUALIFIES = "BBBBBBBBBB";

    private static final String DEFAULT_QUALIFICATION_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_QUALIFICATION_METHOD = "BBBBBBBBBB";

    private static final String DEFAULT_DECISION = "AAAAAAAAAA";
    private static final String UPDATED_DECISION = "BBBBBBBBBB";

    private static final String DEFAULT_REJECTION_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REJECTION_REASON = "BBBBBBBBBB";

    @Autowired
    private LoanRepository loanRepository;

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

    private MockMvc restLoanMockMvc;

    private Loan loan;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LoanResource loanResource = new LoanResource(loanRepository);
        this.restLoanMockMvc = MockMvcBuilders.standaloneSetup(loanResource)
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
    public static Loan createEntity(EntityManager em) {
        Loan loan = new Loan()
            .loanid(DEFAULT_LOANID)
            .settelmentDate(DEFAULT_SETTELMENT_DATE)
            .amount(DEFAULT_AMOUNT)
            .term(DEFAULT_TERM)
            .propertyAddress(DEFAULT_PROPERTY_ADDRESS)
            .mbsPoolId(DEFAULT_MBS_POOL_ID)
            .mbsQualifies(DEFAULT_MBS_QUALIFIES)
            .qualificationMethod(DEFAULT_QUALIFICATION_METHOD)
            .decision(DEFAULT_DECISION)
            .rejectionReason(DEFAULT_REJECTION_REASON);
        return loan;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Loan createUpdatedEntity(EntityManager em) {
        Loan loan = new Loan()
            .loanid(UPDATED_LOANID)
            .settelmentDate(UPDATED_SETTELMENT_DATE)
            .amount(UPDATED_AMOUNT)
            .term(UPDATED_TERM)
            .propertyAddress(UPDATED_PROPERTY_ADDRESS)
            .mbsPoolId(UPDATED_MBS_POOL_ID)
            .mbsQualifies(UPDATED_MBS_QUALIFIES)
            .qualificationMethod(UPDATED_QUALIFICATION_METHOD)
            .decision(UPDATED_DECISION)
            .rejectionReason(UPDATED_REJECTION_REASON);
        return loan;
    }

    @BeforeEach
    public void initTest() {
        loan = createEntity(em);
    }

    @Test
    @Transactional
    public void createLoan() throws Exception {
        int databaseSizeBeforeCreate = loanRepository.findAll().size();

        // Create the Loan
        restLoanMockMvc.perform(post("/api/loans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loan)))
            .andExpect(status().isCreated());

        // Validate the Loan in the database
        List<Loan> loanList = loanRepository.findAll();
        assertThat(loanList).hasSize(databaseSizeBeforeCreate + 1);
        Loan testLoan = loanList.get(loanList.size() - 1);
        assertThat(testLoan.getLoanid()).isEqualTo(DEFAULT_LOANID);
        assertThat(testLoan.getSettelmentDate()).isEqualTo(DEFAULT_SETTELMENT_DATE);
        assertThat(testLoan.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testLoan.getTerm()).isEqualTo(DEFAULT_TERM);
        assertThat(testLoan.getPropertyAddress()).isEqualTo(DEFAULT_PROPERTY_ADDRESS);
        assertThat(testLoan.getMbsPoolId()).isEqualTo(DEFAULT_MBS_POOL_ID);
        assertThat(testLoan.getMbsQualifies()).isEqualTo(DEFAULT_MBS_QUALIFIES);
        assertThat(testLoan.getQualificationMethod()).isEqualTo(DEFAULT_QUALIFICATION_METHOD);
        assertThat(testLoan.getDecision()).isEqualTo(DEFAULT_DECISION);
        assertThat(testLoan.getRejectionReason()).isEqualTo(DEFAULT_REJECTION_REASON);
    }

    @Test
    @Transactional
    public void createLoanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = loanRepository.findAll().size();

        // Create the Loan with an existing ID
        loan.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoanMockMvc.perform(post("/api/loans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loan)))
            .andExpect(status().isBadRequest());

        // Validate the Loan in the database
        List<Loan> loanList = loanRepository.findAll();
        assertThat(loanList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLoanidIsRequired() throws Exception {
        int databaseSizeBeforeTest = loanRepository.findAll().size();
        // set the field null
        loan.setLoanid(null);

        // Create the Loan, which fails.

        restLoanMockMvc.perform(post("/api/loans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loan)))
            .andExpect(status().isBadRequest());

        List<Loan> loanList = loanRepository.findAll();
        assertThat(loanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLoans() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get all the loanList
        restLoanMockMvc.perform(get("/api/loans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loan.getId().intValue())))
            .andExpect(jsonPath("$.[*].loanid").value(hasItem(DEFAULT_LOANID)))
            .andExpect(jsonPath("$.[*].settelmentDate").value(hasItem(DEFAULT_SETTELMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.[*].term").value(hasItem(DEFAULT_TERM)))
            .andExpect(jsonPath("$.[*].propertyAddress").value(hasItem(DEFAULT_PROPERTY_ADDRESS)))
            .andExpect(jsonPath("$.[*].mbsPoolId").value(hasItem(DEFAULT_MBS_POOL_ID)))
            .andExpect(jsonPath("$.[*].mbsQualifies").value(hasItem(DEFAULT_MBS_QUALIFIES)))
            .andExpect(jsonPath("$.[*].qualificationMethod").value(hasItem(DEFAULT_QUALIFICATION_METHOD)))
            .andExpect(jsonPath("$.[*].decision").value(hasItem(DEFAULT_DECISION)))
            .andExpect(jsonPath("$.[*].rejectionReason").value(hasItem(DEFAULT_REJECTION_REASON)));
    }
    
    @Test
    @Transactional
    public void getLoan() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get the loan
        restLoanMockMvc.perform(get("/api/loans/{id}", loan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(loan.getId().intValue()))
            .andExpect(jsonPath("$.loanid").value(DEFAULT_LOANID))
            .andExpect(jsonPath("$.settelmentDate").value(DEFAULT_SETTELMENT_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT))
            .andExpect(jsonPath("$.term").value(DEFAULT_TERM))
            .andExpect(jsonPath("$.propertyAddress").value(DEFAULT_PROPERTY_ADDRESS))
            .andExpect(jsonPath("$.mbsPoolId").value(DEFAULT_MBS_POOL_ID))
            .andExpect(jsonPath("$.mbsQualifies").value(DEFAULT_MBS_QUALIFIES))
            .andExpect(jsonPath("$.qualificationMethod").value(DEFAULT_QUALIFICATION_METHOD))
            .andExpect(jsonPath("$.decision").value(DEFAULT_DECISION))
            .andExpect(jsonPath("$.rejectionReason").value(DEFAULT_REJECTION_REASON));
    }

    @Test
    @Transactional
    public void getNonExistingLoan() throws Exception {
        // Get the loan
        restLoanMockMvc.perform(get("/api/loans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLoan() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        int databaseSizeBeforeUpdate = loanRepository.findAll().size();

        // Update the loan
        Loan updatedLoan = loanRepository.findById(loan.getId()).get();
        // Disconnect from session so that the updates on updatedLoan are not directly saved in db
        em.detach(updatedLoan);
        updatedLoan
            .loanid(UPDATED_LOANID)
            .settelmentDate(UPDATED_SETTELMENT_DATE)
            .amount(UPDATED_AMOUNT)
            .term(UPDATED_TERM)
            .propertyAddress(UPDATED_PROPERTY_ADDRESS)
            .mbsPoolId(UPDATED_MBS_POOL_ID)
            .mbsQualifies(UPDATED_MBS_QUALIFIES)
            .qualificationMethod(UPDATED_QUALIFICATION_METHOD)
            .decision(UPDATED_DECISION)
            .rejectionReason(UPDATED_REJECTION_REASON);

        restLoanMockMvc.perform(put("/api/loans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLoan)))
            .andExpect(status().isOk());

        // Validate the Loan in the database
        List<Loan> loanList = loanRepository.findAll();
        assertThat(loanList).hasSize(databaseSizeBeforeUpdate);
        Loan testLoan = loanList.get(loanList.size() - 1);
        assertThat(testLoan.getLoanid()).isEqualTo(UPDATED_LOANID);
        assertThat(testLoan.getSettelmentDate()).isEqualTo(UPDATED_SETTELMENT_DATE);
        assertThat(testLoan.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testLoan.getTerm()).isEqualTo(UPDATED_TERM);
        assertThat(testLoan.getPropertyAddress()).isEqualTo(UPDATED_PROPERTY_ADDRESS);
        assertThat(testLoan.getMbsPoolId()).isEqualTo(UPDATED_MBS_POOL_ID);
        assertThat(testLoan.getMbsQualifies()).isEqualTo(UPDATED_MBS_QUALIFIES);
        assertThat(testLoan.getQualificationMethod()).isEqualTo(UPDATED_QUALIFICATION_METHOD);
        assertThat(testLoan.getDecision()).isEqualTo(UPDATED_DECISION);
        assertThat(testLoan.getRejectionReason()).isEqualTo(UPDATED_REJECTION_REASON);
    }

    @Test
    @Transactional
    public void updateNonExistingLoan() throws Exception {
        int databaseSizeBeforeUpdate = loanRepository.findAll().size();

        // Create the Loan

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanMockMvc.perform(put("/api/loans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loan)))
            .andExpect(status().isBadRequest());

        // Validate the Loan in the database
        List<Loan> loanList = loanRepository.findAll();
        assertThat(loanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLoan() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        int databaseSizeBeforeDelete = loanRepository.findAll().size();

        // Delete the loan
        restLoanMockMvc.perform(delete("/api/loans/{id}", loan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Loan> loanList = loanRepository.findAll();
        assertThat(loanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
