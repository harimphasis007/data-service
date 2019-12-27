package com.data.service.web.rest;

import com.data.service.DataserviceApp;
import com.data.service.domain.InfoBeneficiaries;
import com.data.service.repository.InfoBeneficiariesRepository;
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
 * Integration tests for the {@link InfoBeneficiariesResource} REST controller.
 */
@SpringBootTest(classes = DataserviceApp.class)
public class InfoBeneficiariesResourceIT {

    private static final String DEFAULT_AREA = "AAAAAAAAAA";
    private static final String UPDATED_AREA = "BBBBBBBBBB";

    private static final String DEFAULT_DEVELOPMENT_IND = "AAAAAAAAAA";
    private static final String UPDATED_DEVELOPMENT_IND = "BBBBBBBBBB";

    private static final String DEFAULT_RENTAL_UNITS = "AAAAAAAAAA";
    private static final String UPDATED_RENTAL_UNITS = "BBBBBBBBBB";

    private static final String DEFAULT_OWNER_OCC_UNITS = "AAAAAAAAAA";
    private static final String UPDATED_OWNER_OCC_UNITS = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_CREATED = "AAAAAAAAAA";
    private static final String UPDATED_JOB_CREATED = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_RETAINED = "AAAAAAAAAA";
    private static final String UPDATED_JOB_RETAINED = "BBBBBBBBBB";

    private static final String DEFAULT_GEO_DEFINED_BENEFICIARIES = "AAAAAAAAAA";
    private static final String UPDATED_GEO_DEFINED_BENEFICIARIES = "BBBBBBBBBB";

    private static final String DEFAULT_INDIVIDUAL_BENEFICIARIES = "AAAAAAAAAA";
    private static final String UPDATED_INDIVIDUAL_BENEFICIARIES = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVITY_BENEFICIARIES = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY_BENEFICIARIES = "BBBBBBBBBB";

    private static final String DEFAULT_OTHER_BENEFICIARIES = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_BENEFICIARIES = "BBBBBBBBBB";

    @Autowired
    private InfoBeneficiariesRepository infoBeneficiariesRepository;

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

    private MockMvc restInfoBeneficiariesMockMvc;

    private InfoBeneficiaries infoBeneficiaries;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InfoBeneficiariesResource infoBeneficiariesResource = new InfoBeneficiariesResource(infoBeneficiariesRepository);
        this.restInfoBeneficiariesMockMvc = MockMvcBuilders.standaloneSetup(infoBeneficiariesResource)
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
    public static InfoBeneficiaries createEntity(EntityManager em) {
        InfoBeneficiaries infoBeneficiaries = new InfoBeneficiaries()
            .area(DEFAULT_AREA)
            .developmentInd(DEFAULT_DEVELOPMENT_IND)
            .rentalUnits(DEFAULT_RENTAL_UNITS)
            .ownerOccUnits(DEFAULT_OWNER_OCC_UNITS)
            .jobCreated(DEFAULT_JOB_CREATED)
            .jobRetained(DEFAULT_JOB_RETAINED)
            .geoDefinedBeneficiaries(DEFAULT_GEO_DEFINED_BENEFICIARIES)
            .individualBeneficiaries(DEFAULT_INDIVIDUAL_BENEFICIARIES)
            .activityBeneficiaries(DEFAULT_ACTIVITY_BENEFICIARIES)
            .otherBeneficiaries(DEFAULT_OTHER_BENEFICIARIES);
        return infoBeneficiaries;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InfoBeneficiaries createUpdatedEntity(EntityManager em) {
        InfoBeneficiaries infoBeneficiaries = new InfoBeneficiaries()
            .area(UPDATED_AREA)
            .developmentInd(UPDATED_DEVELOPMENT_IND)
            .rentalUnits(UPDATED_RENTAL_UNITS)
            .ownerOccUnits(UPDATED_OWNER_OCC_UNITS)
            .jobCreated(UPDATED_JOB_CREATED)
            .jobRetained(UPDATED_JOB_RETAINED)
            .geoDefinedBeneficiaries(UPDATED_GEO_DEFINED_BENEFICIARIES)
            .individualBeneficiaries(UPDATED_INDIVIDUAL_BENEFICIARIES)
            .activityBeneficiaries(UPDATED_ACTIVITY_BENEFICIARIES)
            .otherBeneficiaries(UPDATED_OTHER_BENEFICIARIES);
        return infoBeneficiaries;
    }

    @BeforeEach
    public void initTest() {
        infoBeneficiaries = createEntity(em);
    }

    @Test
    @Transactional
    public void createInfoBeneficiaries() throws Exception {
        int databaseSizeBeforeCreate = infoBeneficiariesRepository.findAll().size();

        // Create the InfoBeneficiaries
        restInfoBeneficiariesMockMvc.perform(post("/api/info-beneficiaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infoBeneficiaries)))
            .andExpect(status().isCreated());

        // Validate the InfoBeneficiaries in the database
        List<InfoBeneficiaries> infoBeneficiariesList = infoBeneficiariesRepository.findAll();
        assertThat(infoBeneficiariesList).hasSize(databaseSizeBeforeCreate + 1);
        InfoBeneficiaries testInfoBeneficiaries = infoBeneficiariesList.get(infoBeneficiariesList.size() - 1);
        assertThat(testInfoBeneficiaries.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testInfoBeneficiaries.getDevelopmentInd()).isEqualTo(DEFAULT_DEVELOPMENT_IND);
        assertThat(testInfoBeneficiaries.getRentalUnits()).isEqualTo(DEFAULT_RENTAL_UNITS);
        assertThat(testInfoBeneficiaries.getOwnerOccUnits()).isEqualTo(DEFAULT_OWNER_OCC_UNITS);
        assertThat(testInfoBeneficiaries.getJobCreated()).isEqualTo(DEFAULT_JOB_CREATED);
        assertThat(testInfoBeneficiaries.getJobRetained()).isEqualTo(DEFAULT_JOB_RETAINED);
        assertThat(testInfoBeneficiaries.getGeoDefinedBeneficiaries()).isEqualTo(DEFAULT_GEO_DEFINED_BENEFICIARIES);
        assertThat(testInfoBeneficiaries.getIndividualBeneficiaries()).isEqualTo(DEFAULT_INDIVIDUAL_BENEFICIARIES);
        assertThat(testInfoBeneficiaries.getActivityBeneficiaries()).isEqualTo(DEFAULT_ACTIVITY_BENEFICIARIES);
        assertThat(testInfoBeneficiaries.getOtherBeneficiaries()).isEqualTo(DEFAULT_OTHER_BENEFICIARIES);
    }

    @Test
    @Transactional
    public void createInfoBeneficiariesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = infoBeneficiariesRepository.findAll().size();

        // Create the InfoBeneficiaries with an existing ID
        infoBeneficiaries.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInfoBeneficiariesMockMvc.perform(post("/api/info-beneficiaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infoBeneficiaries)))
            .andExpect(status().isBadRequest());

        // Validate the InfoBeneficiaries in the database
        List<InfoBeneficiaries> infoBeneficiariesList = infoBeneficiariesRepository.findAll();
        assertThat(infoBeneficiariesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAreaIsRequired() throws Exception {
        int databaseSizeBeforeTest = infoBeneficiariesRepository.findAll().size();
        // set the field null
        infoBeneficiaries.setArea(null);

        // Create the InfoBeneficiaries, which fails.

        restInfoBeneficiariesMockMvc.perform(post("/api/info-beneficiaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infoBeneficiaries)))
            .andExpect(status().isBadRequest());

        List<InfoBeneficiaries> infoBeneficiariesList = infoBeneficiariesRepository.findAll();
        assertThat(infoBeneficiariesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInfoBeneficiaries() throws Exception {
        // Initialize the database
        infoBeneficiariesRepository.saveAndFlush(infoBeneficiaries);

        // Get all the infoBeneficiariesList
        restInfoBeneficiariesMockMvc.perform(get("/api/info-beneficiaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(infoBeneficiaries.getId().intValue())))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].developmentInd").value(hasItem(DEFAULT_DEVELOPMENT_IND)))
            .andExpect(jsonPath("$.[*].rentalUnits").value(hasItem(DEFAULT_RENTAL_UNITS)))
            .andExpect(jsonPath("$.[*].ownerOccUnits").value(hasItem(DEFAULT_OWNER_OCC_UNITS)))
            .andExpect(jsonPath("$.[*].jobCreated").value(hasItem(DEFAULT_JOB_CREATED)))
            .andExpect(jsonPath("$.[*].jobRetained").value(hasItem(DEFAULT_JOB_RETAINED)))
            .andExpect(jsonPath("$.[*].geoDefinedBeneficiaries").value(hasItem(DEFAULT_GEO_DEFINED_BENEFICIARIES)))
            .andExpect(jsonPath("$.[*].individualBeneficiaries").value(hasItem(DEFAULT_INDIVIDUAL_BENEFICIARIES)))
            .andExpect(jsonPath("$.[*].activityBeneficiaries").value(hasItem(DEFAULT_ACTIVITY_BENEFICIARIES)))
            .andExpect(jsonPath("$.[*].otherBeneficiaries").value(hasItem(DEFAULT_OTHER_BENEFICIARIES)));
    }
    
    @Test
    @Transactional
    public void getInfoBeneficiaries() throws Exception {
        // Initialize the database
        infoBeneficiariesRepository.saveAndFlush(infoBeneficiaries);

        // Get the infoBeneficiaries
        restInfoBeneficiariesMockMvc.perform(get("/api/info-beneficiaries/{id}", infoBeneficiaries.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(infoBeneficiaries.getId().intValue()))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA))
            .andExpect(jsonPath("$.developmentInd").value(DEFAULT_DEVELOPMENT_IND))
            .andExpect(jsonPath("$.rentalUnits").value(DEFAULT_RENTAL_UNITS))
            .andExpect(jsonPath("$.ownerOccUnits").value(DEFAULT_OWNER_OCC_UNITS))
            .andExpect(jsonPath("$.jobCreated").value(DEFAULT_JOB_CREATED))
            .andExpect(jsonPath("$.jobRetained").value(DEFAULT_JOB_RETAINED))
            .andExpect(jsonPath("$.geoDefinedBeneficiaries").value(DEFAULT_GEO_DEFINED_BENEFICIARIES))
            .andExpect(jsonPath("$.individualBeneficiaries").value(DEFAULT_INDIVIDUAL_BENEFICIARIES))
            .andExpect(jsonPath("$.activityBeneficiaries").value(DEFAULT_ACTIVITY_BENEFICIARIES))
            .andExpect(jsonPath("$.otherBeneficiaries").value(DEFAULT_OTHER_BENEFICIARIES));
    }

    @Test
    @Transactional
    public void getNonExistingInfoBeneficiaries() throws Exception {
        // Get the infoBeneficiaries
        restInfoBeneficiariesMockMvc.perform(get("/api/info-beneficiaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInfoBeneficiaries() throws Exception {
        // Initialize the database
        infoBeneficiariesRepository.saveAndFlush(infoBeneficiaries);

        int databaseSizeBeforeUpdate = infoBeneficiariesRepository.findAll().size();

        // Update the infoBeneficiaries
        InfoBeneficiaries updatedInfoBeneficiaries = infoBeneficiariesRepository.findById(infoBeneficiaries.getId()).get();
        // Disconnect from session so that the updates on updatedInfoBeneficiaries are not directly saved in db
        em.detach(updatedInfoBeneficiaries);
        updatedInfoBeneficiaries
            .area(UPDATED_AREA)
            .developmentInd(UPDATED_DEVELOPMENT_IND)
            .rentalUnits(UPDATED_RENTAL_UNITS)
            .ownerOccUnits(UPDATED_OWNER_OCC_UNITS)
            .jobCreated(UPDATED_JOB_CREATED)
            .jobRetained(UPDATED_JOB_RETAINED)
            .geoDefinedBeneficiaries(UPDATED_GEO_DEFINED_BENEFICIARIES)
            .individualBeneficiaries(UPDATED_INDIVIDUAL_BENEFICIARIES)
            .activityBeneficiaries(UPDATED_ACTIVITY_BENEFICIARIES)
            .otherBeneficiaries(UPDATED_OTHER_BENEFICIARIES);

        restInfoBeneficiariesMockMvc.perform(put("/api/info-beneficiaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInfoBeneficiaries)))
            .andExpect(status().isOk());

        // Validate the InfoBeneficiaries in the database
        List<InfoBeneficiaries> infoBeneficiariesList = infoBeneficiariesRepository.findAll();
        assertThat(infoBeneficiariesList).hasSize(databaseSizeBeforeUpdate);
        InfoBeneficiaries testInfoBeneficiaries = infoBeneficiariesList.get(infoBeneficiariesList.size() - 1);
        assertThat(testInfoBeneficiaries.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testInfoBeneficiaries.getDevelopmentInd()).isEqualTo(UPDATED_DEVELOPMENT_IND);
        assertThat(testInfoBeneficiaries.getRentalUnits()).isEqualTo(UPDATED_RENTAL_UNITS);
        assertThat(testInfoBeneficiaries.getOwnerOccUnits()).isEqualTo(UPDATED_OWNER_OCC_UNITS);
        assertThat(testInfoBeneficiaries.getJobCreated()).isEqualTo(UPDATED_JOB_CREATED);
        assertThat(testInfoBeneficiaries.getJobRetained()).isEqualTo(UPDATED_JOB_RETAINED);
        assertThat(testInfoBeneficiaries.getGeoDefinedBeneficiaries()).isEqualTo(UPDATED_GEO_DEFINED_BENEFICIARIES);
        assertThat(testInfoBeneficiaries.getIndividualBeneficiaries()).isEqualTo(UPDATED_INDIVIDUAL_BENEFICIARIES);
        assertThat(testInfoBeneficiaries.getActivityBeneficiaries()).isEqualTo(UPDATED_ACTIVITY_BENEFICIARIES);
        assertThat(testInfoBeneficiaries.getOtherBeneficiaries()).isEqualTo(UPDATED_OTHER_BENEFICIARIES);
    }

    @Test
    @Transactional
    public void updateNonExistingInfoBeneficiaries() throws Exception {
        int databaseSizeBeforeUpdate = infoBeneficiariesRepository.findAll().size();

        // Create the InfoBeneficiaries

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInfoBeneficiariesMockMvc.perform(put("/api/info-beneficiaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infoBeneficiaries)))
            .andExpect(status().isBadRequest());

        // Validate the InfoBeneficiaries in the database
        List<InfoBeneficiaries> infoBeneficiariesList = infoBeneficiariesRepository.findAll();
        assertThat(infoBeneficiariesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInfoBeneficiaries() throws Exception {
        // Initialize the database
        infoBeneficiariesRepository.saveAndFlush(infoBeneficiaries);

        int databaseSizeBeforeDelete = infoBeneficiariesRepository.findAll().size();

        // Delete the infoBeneficiaries
        restInfoBeneficiariesMockMvc.perform(delete("/api/info-beneficiaries/{id}", infoBeneficiaries.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InfoBeneficiaries> infoBeneficiariesList = infoBeneficiariesRepository.findAll();
        assertThat(infoBeneficiariesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
