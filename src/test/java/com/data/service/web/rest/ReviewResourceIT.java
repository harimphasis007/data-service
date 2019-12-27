package com.data.service.web.rest;

import com.data.service.DataserviceApp;
import com.data.service.domain.Review;
import com.data.service.repository.ReviewRepository;
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
 * Integration tests for the {@link ReviewResource} REST controller.
 */
@SpringBootTest(classes = DataserviceApp.class)
public class ReviewResourceIT {

    private static final String DEFAULT_ASSIGNED_WORKER = "AAAAAAAAAA";
    private static final String UPDATED_ASSIGNED_WORKER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_REVIEW_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REVIEW_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_REVIEW_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REVIEW_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TURN_TIME = "AAAAAAAAAA";
    private static final String UPDATED_TURN_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_RECOMMENDATION = "AAAAAAAAAA";
    private static final String UPDATED_RECOMMENDATION = "BBBBBBBBBB";

    private static final String DEFAULT_DECISION = "AAAAAAAAAA";
    private static final String UPDATED_DECISION = "BBBBBBBBBB";

    @Autowired
    private ReviewRepository reviewRepository;

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

    private MockMvc restReviewMockMvc;

    private Review review;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReviewResource reviewResource = new ReviewResource(reviewRepository);
        this.restReviewMockMvc = MockMvcBuilders.standaloneSetup(reviewResource)
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
    public static Review createEntity(EntityManager em) {
        Review review = new Review()
            .assignedWorker(DEFAULT_ASSIGNED_WORKER)
            .reviewStartDate(DEFAULT_REVIEW_START_DATE)
            .reviewEndDate(DEFAULT_REVIEW_END_DATE)
            .turnTime(DEFAULT_TURN_TIME)
            .comments(DEFAULT_COMMENTS)
            .recommendation(DEFAULT_RECOMMENDATION)
            .decision(DEFAULT_DECISION);
        return review;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Review createUpdatedEntity(EntityManager em) {
        Review review = new Review()
            .assignedWorker(UPDATED_ASSIGNED_WORKER)
            .reviewStartDate(UPDATED_REVIEW_START_DATE)
            .reviewEndDate(UPDATED_REVIEW_END_DATE)
            .turnTime(UPDATED_TURN_TIME)
            .comments(UPDATED_COMMENTS)
            .recommendation(UPDATED_RECOMMENDATION)
            .decision(UPDATED_DECISION);
        return review;
    }

    @BeforeEach
    public void initTest() {
        review = createEntity(em);
    }

    @Test
    @Transactional
    public void createReview() throws Exception {
        int databaseSizeBeforeCreate = reviewRepository.findAll().size();

        // Create the Review
        restReviewMockMvc.perform(post("/api/reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(review)))
            .andExpect(status().isCreated());

        // Validate the Review in the database
        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList).hasSize(databaseSizeBeforeCreate + 1);
        Review testReview = reviewList.get(reviewList.size() - 1);
        assertThat(testReview.getAssignedWorker()).isEqualTo(DEFAULT_ASSIGNED_WORKER);
        assertThat(testReview.getReviewStartDate()).isEqualTo(DEFAULT_REVIEW_START_DATE);
        assertThat(testReview.getReviewEndDate()).isEqualTo(DEFAULT_REVIEW_END_DATE);
        assertThat(testReview.getTurnTime()).isEqualTo(DEFAULT_TURN_TIME);
        assertThat(testReview.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testReview.getRecommendation()).isEqualTo(DEFAULT_RECOMMENDATION);
        assertThat(testReview.getDecision()).isEqualTo(DEFAULT_DECISION);
    }

    @Test
    @Transactional
    public void createReviewWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reviewRepository.findAll().size();

        // Create the Review with an existing ID
        review.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReviewMockMvc.perform(post("/api/reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(review)))
            .andExpect(status().isBadRequest());

        // Validate the Review in the database
        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllReviews() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList
        restReviewMockMvc.perform(get("/api/reviews?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(review.getId().intValue())))
            .andExpect(jsonPath("$.[*].assignedWorker").value(hasItem(DEFAULT_ASSIGNED_WORKER)))
            .andExpect(jsonPath("$.[*].reviewStartDate").value(hasItem(DEFAULT_REVIEW_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].reviewEndDate").value(hasItem(DEFAULT_REVIEW_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].turnTime").value(hasItem(DEFAULT_TURN_TIME)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].recommendation").value(hasItem(DEFAULT_RECOMMENDATION)))
            .andExpect(jsonPath("$.[*].decision").value(hasItem(DEFAULT_DECISION)));
    }
    
    @Test
    @Transactional
    public void getReview() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get the review
        restReviewMockMvc.perform(get("/api/reviews/{id}", review.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(review.getId().intValue()))
            .andExpect(jsonPath("$.assignedWorker").value(DEFAULT_ASSIGNED_WORKER))
            .andExpect(jsonPath("$.reviewStartDate").value(DEFAULT_REVIEW_START_DATE.toString()))
            .andExpect(jsonPath("$.reviewEndDate").value(DEFAULT_REVIEW_END_DATE.toString()))
            .andExpect(jsonPath("$.turnTime").value(DEFAULT_TURN_TIME))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.recommendation").value(DEFAULT_RECOMMENDATION))
            .andExpect(jsonPath("$.decision").value(DEFAULT_DECISION));
    }

    @Test
    @Transactional
    public void getNonExistingReview() throws Exception {
        // Get the review
        restReviewMockMvc.perform(get("/api/reviews/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReview() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        int databaseSizeBeforeUpdate = reviewRepository.findAll().size();

        // Update the review
        Review updatedReview = reviewRepository.findById(review.getId()).get();
        // Disconnect from session so that the updates on updatedReview are not directly saved in db
        em.detach(updatedReview);
        updatedReview
            .assignedWorker(UPDATED_ASSIGNED_WORKER)
            .reviewStartDate(UPDATED_REVIEW_START_DATE)
            .reviewEndDate(UPDATED_REVIEW_END_DATE)
            .turnTime(UPDATED_TURN_TIME)
            .comments(UPDATED_COMMENTS)
            .recommendation(UPDATED_RECOMMENDATION)
            .decision(UPDATED_DECISION);

        restReviewMockMvc.perform(put("/api/reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReview)))
            .andExpect(status().isOk());

        // Validate the Review in the database
        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList).hasSize(databaseSizeBeforeUpdate);
        Review testReview = reviewList.get(reviewList.size() - 1);
        assertThat(testReview.getAssignedWorker()).isEqualTo(UPDATED_ASSIGNED_WORKER);
        assertThat(testReview.getReviewStartDate()).isEqualTo(UPDATED_REVIEW_START_DATE);
        assertThat(testReview.getReviewEndDate()).isEqualTo(UPDATED_REVIEW_END_DATE);
        assertThat(testReview.getTurnTime()).isEqualTo(UPDATED_TURN_TIME);
        assertThat(testReview.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testReview.getRecommendation()).isEqualTo(UPDATED_RECOMMENDATION);
        assertThat(testReview.getDecision()).isEqualTo(UPDATED_DECISION);
    }

    @Test
    @Transactional
    public void updateNonExistingReview() throws Exception {
        int databaseSizeBeforeUpdate = reviewRepository.findAll().size();

        // Create the Review

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReviewMockMvc.perform(put("/api/reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(review)))
            .andExpect(status().isBadRequest());

        // Validate the Review in the database
        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReview() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        int databaseSizeBeforeDelete = reviewRepository.findAll().size();

        // Delete the review
        restReviewMockMvc.perform(delete("/api/reviews/{id}", review.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
