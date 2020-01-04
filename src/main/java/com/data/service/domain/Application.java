package com.data.service.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Application.
 */
@Entity
@Table(name = "application")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "application_no", nullable = false)
    private String applicationNo;

    @Column(name = "application_date")
    private LocalDate applicationDate;

    @Column(name = "application_review_status")
    private String applicationReviewStatus;

    @Column(name = "total_amount_requested")
    private String totalAmountRequested;

    @Column(name = "manager_decision")
    private String managerDecision;

    @Column(name = "manager_review_end_date")
    private LocalDate managerReviewEndDate;

    @Column(name = "total_review_turn_time")
    private String totalReviewTurnTime;

    @Column(name = "notification_sent_date")
    private LocalDate notificationSentDate;

    @Column(name = "certification_name")
    private String certificationName;

    @Column(name = "certification_title")
    private String certificationTitle;

    @Column(name = "certification_date")
    private LocalDate certificationDate;

    @Column(name = "project_specific_application")
    private String projectSpecificApplication;

    @Column(name = "current_review_status")
    private String currentReviewStatus;

    @Column(name = "created_on")
    private LocalDate createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "current_complete_per")
    private String currentCompletePer;

    @Column(name = "last_updated_on")
    private LocalDate lastUpdatedOn;

    @OneToOne
    @JoinColumn(unique = true)
    private Assignment assignment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public Application applicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
        return this;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public Application applicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
        return this;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getApplicationReviewStatus() {
        return applicationReviewStatus;
    }

    public Application applicationReviewStatus(String applicationReviewStatus) {
        this.applicationReviewStatus = applicationReviewStatus;
        return this;
    }

    public void setApplicationReviewStatus(String applicationReviewStatus) {
        this.applicationReviewStatus = applicationReviewStatus;
    }

    public String getTotalAmountRequested() {
        return totalAmountRequested;
    }

    public Application totalAmountRequested(String totalAmountRequested) {
        this.totalAmountRequested = totalAmountRequested;
        return this;
    }

    public void setTotalAmountRequested(String totalAmountRequested) {
        this.totalAmountRequested = totalAmountRequested;
    }

    public String getManagerDecision() {
        return managerDecision;
    }

    public Application managerDecision(String managerDecision) {
        this.managerDecision = managerDecision;
        return this;
    }

    public void setManagerDecision(String managerDecision) {
        this.managerDecision = managerDecision;
    }

    public LocalDate getManagerReviewEndDate() {
        return managerReviewEndDate;
    }

    public Application managerReviewEndDate(LocalDate managerReviewEndDate) {
        this.managerReviewEndDate = managerReviewEndDate;
        return this;
    }

    public void setManagerReviewEndDate(LocalDate managerReviewEndDate) {
        this.managerReviewEndDate = managerReviewEndDate;
    }

    public String getTotalReviewTurnTime() {
        return totalReviewTurnTime;
    }

    public Application totalReviewTurnTime(String totalReviewTurnTime) {
        this.totalReviewTurnTime = totalReviewTurnTime;
        return this;
    }

    public void setTotalReviewTurnTime(String totalReviewTurnTime) {
        this.totalReviewTurnTime = totalReviewTurnTime;
    }

    public LocalDate getNotificationSentDate() {
        return notificationSentDate;
    }

    public Application notificationSentDate(LocalDate notificationSentDate) {
        this.notificationSentDate = notificationSentDate;
        return this;
    }

    public void setNotificationSentDate(LocalDate notificationSentDate) {
        this.notificationSentDate = notificationSentDate;
    }

    public String getCertificationName() {
        return certificationName;
    }

    public Application certificationName(String certificationName) {
        this.certificationName = certificationName;
        return this;
    }

    public void setCertificationName(String certificationName) {
        this.certificationName = certificationName;
    }

    public String getCertificationTitle() {
        return certificationTitle;
    }

    public Application certificationTitle(String certificationTitle) {
        this.certificationTitle = certificationTitle;
        return this;
    }

    public void setCertificationTitle(String certificationTitle) {
        this.certificationTitle = certificationTitle;
    }

    public LocalDate getCertificationDate() {
        return certificationDate;
    }

    public Application certificationDate(LocalDate certificationDate) {
        this.certificationDate = certificationDate;
        return this;
    }

    public void setCertificationDate(LocalDate certificationDate) {
        this.certificationDate = certificationDate;
    }

    public String getProjectSpecificApplication() {
        return projectSpecificApplication;
    }

    public Application projectSpecificApplication(String projectSpecificApplication) {
        this.projectSpecificApplication = projectSpecificApplication;
        return this;
    }

    public void setProjectSpecificApplication(String projectSpecificApplication) {
        this.projectSpecificApplication = projectSpecificApplication;
    }

    public String getCurrentReviewStatus() {
        return currentReviewStatus;
    }

    public Application currentReviewStatus(String currentReviewStatus) {
        this.currentReviewStatus = currentReviewStatus;
        return this;
    }

    public void setCurrentReviewStatus(String currentReviewStatus) {
        this.currentReviewStatus = currentReviewStatus;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public Application createdOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Application createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCurrentCompletePer() {
        return currentCompletePer;
    }

    public Application currentCompletePer(String currentCompletePer) {
        this.currentCompletePer = currentCompletePer;
        return this;
    }

    public void setCurrentCompletePer(String currentCompletePer) {
        this.currentCompletePer = currentCompletePer;
    }

    public LocalDate getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public Application lastUpdatedOn(LocalDate lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
        return this;
    }

    public void setLastUpdatedOn(LocalDate lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public Application assignment(Assignment assignment) {
        this.assignment = assignment;
        return this;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Application)) {
            return false;
        }
        return id != null && id.equals(((Application) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Application{" +
            "id=" + getId() +
            ", applicationNo='" + getApplicationNo() + "'" +
            ", applicationDate='" + getApplicationDate() + "'" +
            ", applicationReviewStatus='" + getApplicationReviewStatus() + "'" +
            ", totalAmountRequested='" + getTotalAmountRequested() + "'" +
            ", managerDecision='" + getManagerDecision() + "'" +
            ", managerReviewEndDate='" + getManagerReviewEndDate() + "'" +
            ", totalReviewTurnTime='" + getTotalReviewTurnTime() + "'" +
            ", notificationSentDate='" + getNotificationSentDate() + "'" +
            ", certificationName='" + getCertificationName() + "'" +
            ", certificationTitle='" + getCertificationTitle() + "'" +
            ", certificationDate='" + getCertificationDate() + "'" +
            ", projectSpecificApplication='" + getProjectSpecificApplication() + "'" +
            ", currentReviewStatus='" + getCurrentReviewStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", currentCompletePer='" + getCurrentCompletePer() + "'" +
            ", lastUpdatedOn='" + getLastUpdatedOn() + "'" +
            "}";
    }
}
