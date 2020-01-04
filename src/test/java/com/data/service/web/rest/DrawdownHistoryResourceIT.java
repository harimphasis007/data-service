package com.data.service.web.rest;

import com.data.service.DataserviceApp;
import com.data.service.domain.DrawdownHistory;
import com.data.service.repository.DrawdownHistoryRepository;
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
 * Integration tests for the {@link DrawdownHistoryResource} REST controller.
 */
@SpringBootTest(classes = DataserviceApp.class)
public class DrawdownHistoryResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_TERM = "AAAAAAAAAA";
    private static final String UPDATED_TERM = "BBBBBBBBBB";

    private static final String DEFAULT_RATE = "AAAAAAAAAA";
    private static final String UPDATED_RATE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MATURITY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MATURITY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_INT_SUBSIDY_AL = "AAAAAAAAAA";
    private static final String UPDATED_INT_SUBSIDY_AL = "BBBBBBBBBB";

    private static final String DEFAULT_INT_SUBSIDY_CR = "AAAAAAAAAA";
    private static final String UPDATED_INT_SUBSIDY_CR = "BBBBBBBBBB";

    @Autowired
    private DrawdownHistoryRepository drawdownHistoryRepository;

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

    private MockMvc restDrawdownHistoryMockMvc;

    private DrawdownHistory drawdownHistory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DrawdownHistoryResource drawdownHistoryResource = new DrawdownHistoryResource(drawdownHistoryRepository);
        this.restDrawdownHistoryMockMvc = MockMvcBuilders.standaloneSetup(drawdownHistoryResource)
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
    public static DrawdownHistory createEntity(EntityManager em) {
        DrawdownHistory drawdownHistory = new DrawdownHistory()
            .date(DEFAULT_DATE)
            .note(DEFAULT_NOTE)
            .status(DEFAULT_STATUS)
            .amount(DEFAULT_AMOUNT)
            .term(DEFAULT_TERM)
            .rate(DEFAULT_RATE)
            .maturityDate(DEFAULT_MATURITY_DATE)
            .intSubsidyAl(DEFAULT_INT_SUBSIDY_AL)
            .intSubsidyCr(DEFAULT_INT_SUBSIDY_CR);
        return drawdownHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DrawdownHistory createUpdatedEntity(EntityManager em) {
        DrawdownHistory drawdownHistory = new DrawdownHistory()
            .date(UPDATED_DATE)
            .note(UPDATED_NOTE)
            .status(UPDATED_STATUS)
            .amount(UPDATED_AMOUNT)
            .term(UPDATED_TERM)
            .rate(UPDATED_RATE)
            .maturityDate(UPDATED_MATURITY_DATE)
            .intSubsidyAl(UPDATED_INT_SUBSIDY_AL)
            .intSubsidyCr(UPDATED_INT_SUBSIDY_CR);
        return drawdownHistory;
    }

    @BeforeEach
    public void initTest() {
        drawdownHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createDrawdownHistory() throws Exception {
        int databaseSizeBeforeCreate = drawdownHistoryRepository.findAll().size();

        // Create the DrawdownHistory
        restDrawdownHistoryMockMvc.perform(post("/api/drawdown-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drawdownHistory)))
            .andExpect(status().isCreated());

        // Validate the DrawdownHistory in the database
        List<DrawdownHistory> drawdownHistoryList = drawdownHistoryRepository.findAll();
        assertThat(drawdownHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        DrawdownHistory testDrawdownHistory = drawdownHistoryList.get(drawdownHistoryList.size() - 1);
        assertThat(testDrawdownHistory.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDrawdownHistory.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testDrawdownHistory.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDrawdownHistory.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testDrawdownHistory.getTerm()).isEqualTo(DEFAULT_TERM);
        assertThat(testDrawdownHistory.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testDrawdownHistory.getMaturityDate()).isEqualTo(DEFAULT_MATURITY_DATE);
        assertThat(testDrawdownHistory.getIntSubsidyAl()).isEqualTo(DEFAULT_INT_SUBSIDY_AL);
        assertThat(testDrawdownHistory.getIntSubsidyCr()).isEqualTo(DEFAULT_INT_SUBSIDY_CR);
    }

    @Test
    @Transactional
    public void createDrawdownHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = drawdownHistoryRepository.findAll().size();

        // Create the DrawdownHistory with an existing ID
        drawdownHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDrawdownHistoryMockMvc.perform(post("/api/drawdown-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drawdownHistory)))
            .andExpect(status().isBadRequest());

        // Validate the DrawdownHistory in the database
        List<DrawdownHistory> drawdownHistoryList = drawdownHistoryRepository.findAll();
        assertThat(drawdownHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDrawdownHistories() throws Exception {
        // Initialize the database
        drawdownHistoryRepository.saveAndFlush(drawdownHistory);

        // Get all the drawdownHistoryList
        restDrawdownHistoryMockMvc.perform(get("/api/drawdown-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drawdownHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.[*].term").value(hasItem(DEFAULT_TERM)))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE)))
            .andExpect(jsonPath("$.[*].maturityDate").value(hasItem(DEFAULT_MATURITY_DATE.toString())))
            .andExpect(jsonPath("$.[*].intSubsidyAl").value(hasItem(DEFAULT_INT_SUBSIDY_AL)))
            .andExpect(jsonPath("$.[*].intSubsidyCr").value(hasItem(DEFAULT_INT_SUBSIDY_CR)));
    }
    
    @Test
    @Transactional
    public void getDrawdownHistory() throws Exception {
        // Initialize the database
        drawdownHistoryRepository.saveAndFlush(drawdownHistory);

        // Get the drawdownHistory
        restDrawdownHistoryMockMvc.perform(get("/api/drawdown-histories/{id}", drawdownHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(drawdownHistory.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT))
            .andExpect(jsonPath("$.term").value(DEFAULT_TERM))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE))
            .andExpect(jsonPath("$.maturityDate").value(DEFAULT_MATURITY_DATE.toString()))
            .andExpect(jsonPath("$.intSubsidyAl").value(DEFAULT_INT_SUBSIDY_AL))
            .andExpect(jsonPath("$.intSubsidyCr").value(DEFAULT_INT_SUBSIDY_CR));
    }

    @Test
    @Transactional
    public void getNonExistingDrawdownHistory() throws Exception {
        // Get the drawdownHistory
        restDrawdownHistoryMockMvc.perform(get("/api/drawdown-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDrawdownHistory() throws Exception {
        // Initialize the database
        drawdownHistoryRepository.saveAndFlush(drawdownHistory);

        int databaseSizeBeforeUpdate = drawdownHistoryRepository.findAll().size();

        // Update the drawdownHistory
        DrawdownHistory updatedDrawdownHistory = drawdownHistoryRepository.findById(drawdownHistory.getId()).get();
        // Disconnect from session so that the updates on updatedDrawdownHistory are not directly saved in db
        em.detach(updatedDrawdownHistory);
        updatedDrawdownHistory
            .date(UPDATED_DATE)
            .note(UPDATED_NOTE)
            .status(UPDATED_STATUS)
            .amount(UPDATED_AMOUNT)
            .term(UPDATED_TERM)
            .rate(UPDATED_RATE)
            .maturityDate(UPDATED_MATURITY_DATE)
            .intSubsidyAl(UPDATED_INT_SUBSIDY_AL)
            .intSubsidyCr(UPDATED_INT_SUBSIDY_CR);

        restDrawdownHistoryMockMvc.perform(put("/api/drawdown-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDrawdownHistory)))
            .andExpect(status().isOk());

        // Validate the DrawdownHistory in the database
        List<DrawdownHistory> drawdownHistoryList = drawdownHistoryRepository.findAll();
        assertThat(drawdownHistoryList).hasSize(databaseSizeBeforeUpdate);
        DrawdownHistory testDrawdownHistory = drawdownHistoryList.get(drawdownHistoryList.size() - 1);
        assertThat(testDrawdownHistory.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDrawdownHistory.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testDrawdownHistory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDrawdownHistory.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testDrawdownHistory.getTerm()).isEqualTo(UPDATED_TERM);
        assertThat(testDrawdownHistory.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testDrawdownHistory.getMaturityDate()).isEqualTo(UPDATED_MATURITY_DATE);
        assertThat(testDrawdownHistory.getIntSubsidyAl()).isEqualTo(UPDATED_INT_SUBSIDY_AL);
        assertThat(testDrawdownHistory.getIntSubsidyCr()).isEqualTo(UPDATED_INT_SUBSIDY_CR);
    }

    @Test
    @Transactional
    public void updateNonExistingDrawdownHistory() throws Exception {
        int databaseSizeBeforeUpdate = drawdownHistoryRepository.findAll().size();

        // Create the DrawdownHistory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDrawdownHistoryMockMvc.perform(put("/api/drawdown-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drawdownHistory)))
            .andExpect(status().isBadRequest());

        // Validate the DrawdownHistory in the database
        List<DrawdownHistory> drawdownHistoryList = drawdownHistoryRepository.findAll();
        assertThat(drawdownHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDrawdownHistory() throws Exception {
        // Initialize the database
        drawdownHistoryRepository.saveAndFlush(drawdownHistory);

        int databaseSizeBeforeDelete = drawdownHistoryRepository.findAll().size();

        // Delete the drawdownHistory
        restDrawdownHistoryMockMvc.perform(delete("/api/drawdown-histories/{id}", drawdownHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DrawdownHistory> drawdownHistoryList = drawdownHistoryRepository.findAll();
        assertThat(drawdownHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
