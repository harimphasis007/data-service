package com.data.service.web.rest;

import com.data.service.DataserviceApp;
import com.data.service.domain.Loanpool;
import com.data.service.repository.LoanpoolRepository;
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
 * Integration tests for the {@link LoanpoolResource} REST controller.
 */
@SpringBootTest(classes = DataserviceApp.class)
public class LoanpoolResourceIT {

    private static final String DEFAULT_TOTAL = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_QUALIFIED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_QUALIFIED = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REJECTED = "AAAAAAAAAA";
    private static final String UPDATED_REJECTED = "BBBBBBBBBB";

    private static final String DEFAULT_UNREVIEWED = "AAAAAAAAAA";
    private static final String UPDATED_UNREVIEWED = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAL_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_QUALIFIED_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_QUALIFIED_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_REJECTED_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_REJECTED_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_UNREVIEWED_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_UNREVIEWED_AMOUNT = "BBBBBBBBBB";

    @Autowired
    private LoanpoolRepository loanpoolRepository;

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

    private MockMvc restLoanpoolMockMvc;

    private Loanpool loanpool;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LoanpoolResource loanpoolResource = new LoanpoolResource(loanpoolRepository);
        this.restLoanpoolMockMvc = MockMvcBuilders.standaloneSetup(loanpoolResource)
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
    public static Loanpool createEntity(EntityManager em) {
        Loanpool loanpool = new Loanpool()
            .total(DEFAULT_TOTAL)
            .qualified(DEFAULT_QUALIFIED)
            .rejected(DEFAULT_REJECTED)
            .unreviewed(DEFAULT_UNREVIEWED)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .qualifiedAmount(DEFAULT_QUALIFIED_AMOUNT)
            .rejectedAmount(DEFAULT_REJECTED_AMOUNT)
            .unreviewedAmount(DEFAULT_UNREVIEWED_AMOUNT);
        return loanpool;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Loanpool createUpdatedEntity(EntityManager em) {
        Loanpool loanpool = new Loanpool()
            .total(UPDATED_TOTAL)
            .qualified(UPDATED_QUALIFIED)
            .rejected(UPDATED_REJECTED)
            .unreviewed(UPDATED_UNREVIEWED)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .qualifiedAmount(UPDATED_QUALIFIED_AMOUNT)
            .rejectedAmount(UPDATED_REJECTED_AMOUNT)
            .unreviewedAmount(UPDATED_UNREVIEWED_AMOUNT);
        return loanpool;
    }

    @BeforeEach
    public void initTest() {
        loanpool = createEntity(em);
    }

    @Test
    @Transactional
    public void createLoanpool() throws Exception {
        int databaseSizeBeforeCreate = loanpoolRepository.findAll().size();

        // Create the Loanpool
        restLoanpoolMockMvc.perform(post("/api/loanpools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanpool)))
            .andExpect(status().isCreated());

        // Validate the Loanpool in the database
        List<Loanpool> loanpoolList = loanpoolRepository.findAll();
        assertThat(loanpoolList).hasSize(databaseSizeBeforeCreate + 1);
        Loanpool testLoanpool = loanpoolList.get(loanpoolList.size() - 1);
        assertThat(testLoanpool.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testLoanpool.getQualified()).isEqualTo(DEFAULT_QUALIFIED);
        assertThat(testLoanpool.getRejected()).isEqualTo(DEFAULT_REJECTED);
        assertThat(testLoanpool.getUnreviewed()).isEqualTo(DEFAULT_UNREVIEWED);
        assertThat(testLoanpool.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testLoanpool.getQualifiedAmount()).isEqualTo(DEFAULT_QUALIFIED_AMOUNT);
        assertThat(testLoanpool.getRejectedAmount()).isEqualTo(DEFAULT_REJECTED_AMOUNT);
        assertThat(testLoanpool.getUnreviewedAmount()).isEqualTo(DEFAULT_UNREVIEWED_AMOUNT);
    }

    @Test
    @Transactional
    public void createLoanpoolWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = loanpoolRepository.findAll().size();

        // Create the Loanpool with an existing ID
        loanpool.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoanpoolMockMvc.perform(post("/api/loanpools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanpool)))
            .andExpect(status().isBadRequest());

        // Validate the Loanpool in the database
        List<Loanpool> loanpoolList = loanpoolRepository.findAll();
        assertThat(loanpoolList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = loanpoolRepository.findAll().size();
        // set the field null
        loanpool.setTotal(null);

        // Create the Loanpool, which fails.

        restLoanpoolMockMvc.perform(post("/api/loanpools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanpool)))
            .andExpect(status().isBadRequest());

        List<Loanpool> loanpoolList = loanpoolRepository.findAll();
        assertThat(loanpoolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLoanpools() throws Exception {
        // Initialize the database
        loanpoolRepository.saveAndFlush(loanpool);

        // Get all the loanpoolList
        restLoanpoolMockMvc.perform(get("/api/loanpools?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loanpool.getId().intValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL)))
            .andExpect(jsonPath("$.[*].qualified").value(hasItem(DEFAULT_QUALIFIED.toString())))
            .andExpect(jsonPath("$.[*].rejected").value(hasItem(DEFAULT_REJECTED)))
            .andExpect(jsonPath("$.[*].unreviewed").value(hasItem(DEFAULT_UNREVIEWED)))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT)))
            .andExpect(jsonPath("$.[*].qualifiedAmount").value(hasItem(DEFAULT_QUALIFIED_AMOUNT)))
            .andExpect(jsonPath("$.[*].rejectedAmount").value(hasItem(DEFAULT_REJECTED_AMOUNT)))
            .andExpect(jsonPath("$.[*].unreviewedAmount").value(hasItem(DEFAULT_UNREVIEWED_AMOUNT)));
    }
    
    @Test
    @Transactional
    public void getLoanpool() throws Exception {
        // Initialize the database
        loanpoolRepository.saveAndFlush(loanpool);

        // Get the loanpool
        restLoanpoolMockMvc.perform(get("/api/loanpools/{id}", loanpool.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(loanpool.getId().intValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL))
            .andExpect(jsonPath("$.qualified").value(DEFAULT_QUALIFIED.toString()))
            .andExpect(jsonPath("$.rejected").value(DEFAULT_REJECTED))
            .andExpect(jsonPath("$.unreviewed").value(DEFAULT_UNREVIEWED))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT))
            .andExpect(jsonPath("$.qualifiedAmount").value(DEFAULT_QUALIFIED_AMOUNT))
            .andExpect(jsonPath("$.rejectedAmount").value(DEFAULT_REJECTED_AMOUNT))
            .andExpect(jsonPath("$.unreviewedAmount").value(DEFAULT_UNREVIEWED_AMOUNT));
    }

    @Test
    @Transactional
    public void getNonExistingLoanpool() throws Exception {
        // Get the loanpool
        restLoanpoolMockMvc.perform(get("/api/loanpools/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLoanpool() throws Exception {
        // Initialize the database
        loanpoolRepository.saveAndFlush(loanpool);

        int databaseSizeBeforeUpdate = loanpoolRepository.findAll().size();

        // Update the loanpool
        Loanpool updatedLoanpool = loanpoolRepository.findById(loanpool.getId()).get();
        // Disconnect from session so that the updates on updatedLoanpool are not directly saved in db
        em.detach(updatedLoanpool);
        updatedLoanpool
            .total(UPDATED_TOTAL)
            .qualified(UPDATED_QUALIFIED)
            .rejected(UPDATED_REJECTED)
            .unreviewed(UPDATED_UNREVIEWED)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .qualifiedAmount(UPDATED_QUALIFIED_AMOUNT)
            .rejectedAmount(UPDATED_REJECTED_AMOUNT)
            .unreviewedAmount(UPDATED_UNREVIEWED_AMOUNT);

        restLoanpoolMockMvc.perform(put("/api/loanpools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLoanpool)))
            .andExpect(status().isOk());

        // Validate the Loanpool in the database
        List<Loanpool> loanpoolList = loanpoolRepository.findAll();
        assertThat(loanpoolList).hasSize(databaseSizeBeforeUpdate);
        Loanpool testLoanpool = loanpoolList.get(loanpoolList.size() - 1);
        assertThat(testLoanpool.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testLoanpool.getQualified()).isEqualTo(UPDATED_QUALIFIED);
        assertThat(testLoanpool.getRejected()).isEqualTo(UPDATED_REJECTED);
        assertThat(testLoanpool.getUnreviewed()).isEqualTo(UPDATED_UNREVIEWED);
        assertThat(testLoanpool.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testLoanpool.getQualifiedAmount()).isEqualTo(UPDATED_QUALIFIED_AMOUNT);
        assertThat(testLoanpool.getRejectedAmount()).isEqualTo(UPDATED_REJECTED_AMOUNT);
        assertThat(testLoanpool.getUnreviewedAmount()).isEqualTo(UPDATED_UNREVIEWED_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingLoanpool() throws Exception {
        int databaseSizeBeforeUpdate = loanpoolRepository.findAll().size();

        // Create the Loanpool

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanpoolMockMvc.perform(put("/api/loanpools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanpool)))
            .andExpect(status().isBadRequest());

        // Validate the Loanpool in the database
        List<Loanpool> loanpoolList = loanpoolRepository.findAll();
        assertThat(loanpoolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLoanpool() throws Exception {
        // Initialize the database
        loanpoolRepository.saveAndFlush(loanpool);

        int databaseSizeBeforeDelete = loanpoolRepository.findAll().size();

        // Delete the loanpool
        restLoanpoolMockMvc.perform(delete("/api/loanpools/{id}", loanpool.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Loanpool> loanpoolList = loanpoolRepository.findAll();
        assertThat(loanpoolList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
