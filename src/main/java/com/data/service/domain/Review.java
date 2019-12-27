package com.data.service.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Review.
 */
@Entity
@Table(name = "review")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "assigned_worker")
    private String assignedWorker;

    @Column(name = "review_start_date")
    private LocalDate reviewStartDate;

    @Column(name = "review_end_date")
    private LocalDate reviewEndDate;

    @Column(name = "turn_time")
    private String turnTime;

    @Column(name = "comments")
    private String comments;

    @Column(name = "recommendation")
    private String recommendation;

    @Column(name = "decision")
    private String decision;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssignedWorker() {
        return assignedWorker;
    }

    public Review assignedWorker(String assignedWorker) {
        this.assignedWorker = assignedWorker;
        return this;
    }

    public void setAssignedWorker(String assignedWorker) {
        this.assignedWorker = assignedWorker;
    }

    public LocalDate getReviewStartDate() {
        return reviewStartDate;
    }

    public Review reviewStartDate(LocalDate reviewStartDate) {
        this.reviewStartDate = reviewStartDate;
        return this;
    }

    public void setReviewStartDate(LocalDate reviewStartDate) {
        this.reviewStartDate = reviewStartDate;
    }

    public LocalDate getReviewEndDate() {
        return reviewEndDate;
    }

    public Review reviewEndDate(LocalDate reviewEndDate) {
        this.reviewEndDate = reviewEndDate;
        return this;
    }

    public void setReviewEndDate(LocalDate reviewEndDate) {
        this.reviewEndDate = reviewEndDate;
    }

    public String getTurnTime() {
        return turnTime;
    }

    public Review turnTime(String turnTime) {
        this.turnTime = turnTime;
        return this;
    }

    public void setTurnTime(String turnTime) {
        this.turnTime = turnTime;
    }

    public String getComments() {
        return comments;
    }

    public Review comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public Review recommendation(String recommendation) {
        this.recommendation = recommendation;
        return this;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getDecision() {
        return decision;
    }

    public Review decision(String decision) {
        this.decision = decision;
        return this;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Review)) {
            return false;
        }
        return id != null && id.equals(((Review) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Review{" +
            "id=" + getId() +
            ", assignedWorker='" + getAssignedWorker() + "'" +
            ", reviewStartDate='" + getReviewStartDate() + "'" +
            ", reviewEndDate='" + getReviewEndDate() + "'" +
            ", turnTime='" + getTurnTime() + "'" +
            ", comments='" + getComments() + "'" +
            ", recommendation='" + getRecommendation() + "'" +
            ", decision='" + getDecision() + "'" +
            "}";
    }
}
