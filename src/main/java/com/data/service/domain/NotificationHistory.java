package com.data.service.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A NotificationHistory.
 */
@Entity
@Table(name = "notification_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NotificationHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "from_email", nullable = false)
    private String fromEmail;

    @Column(name = "sent_date")
    private LocalDate sentDate;

    @Column(name = "to_address")
    private String toAddress;

    @Column(name = "cc_address")
    private String ccAddress;

    @Column(name = "notification_type")
    private String notificationType;

    @Column(name = "subject_line")
    private String subjectLine;

    @ManyToOne
    @JsonIgnoreProperties("notificationHistories")
    private Project project;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public NotificationHistory fromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
        return this;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public LocalDate getSentDate() {
        return sentDate;
    }

    public NotificationHistory sentDate(LocalDate sentDate) {
        this.sentDate = sentDate;
        return this;
    }

    public void setSentDate(LocalDate sentDate) {
        this.sentDate = sentDate;
    }

    public String getToAddress() {
        return toAddress;
    }

    public NotificationHistory toAddress(String toAddress) {
        this.toAddress = toAddress;
        return this;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getCcAddress() {
        return ccAddress;
    }

    public NotificationHistory ccAddress(String ccAddress) {
        this.ccAddress = ccAddress;
        return this;
    }

    public void setCcAddress(String ccAddress) {
        this.ccAddress = ccAddress;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public NotificationHistory notificationType(String notificationType) {
        this.notificationType = notificationType;
        return this;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getSubjectLine() {
        return subjectLine;
    }

    public NotificationHistory subjectLine(String subjectLine) {
        this.subjectLine = subjectLine;
        return this;
    }

    public void setSubjectLine(String subjectLine) {
        this.subjectLine = subjectLine;
    }

    public Project getProject() {
        return project;
    }

    public NotificationHistory project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotificationHistory)) {
            return false;
        }
        return id != null && id.equals(((NotificationHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "NotificationHistory{" +
            "id=" + getId() +
            ", fromEmail='" + getFromEmail() + "'" +
            ", sentDate='" + getSentDate() + "'" +
            ", toAddress='" + getToAddress() + "'" +
            ", ccAddress='" + getCcAddress() + "'" +
            ", notificationType='" + getNotificationType() + "'" +
            ", subjectLine='" + getSubjectLine() + "'" +
            "}";
    }
}
