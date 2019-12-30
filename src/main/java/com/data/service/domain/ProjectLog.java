package com.data.service.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A ProjectLog.
 */
@Entity
@Table(name = "project_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProjectLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "worker", nullable = false)
    private String worker;

    @Column(name = "entry_details")
    private String entryDetails;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JsonIgnoreProperties("projectLogs")
    private Project project;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorker() {
        return worker;
    }

    public ProjectLog worker(String worker) {
        this.worker = worker;
        return this;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getEntryDetails() {
        return entryDetails;
    }

    public ProjectLog entryDetails(String entryDetails) {
        this.entryDetails = entryDetails;
        return this;
    }

    public void setEntryDetails(String entryDetails) {
        this.entryDetails = entryDetails;
    }

    public LocalDate getDate() {
        return date;
    }

    public ProjectLog date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Project getProject() {
        return project;
    }

    public ProjectLog project(Project project) {
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
        if (!(o instanceof ProjectLog)) {
            return false;
        }
        return id != null && id.equals(((ProjectLog) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProjectLog{" +
            "id=" + getId() +
            ", worker='" + getWorker() + "'" +
            ", entryDetails='" + getEntryDetails() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
