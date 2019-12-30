package com.data.service.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A WorkerHistory.
 */
@Entity
@Table(name = "worker_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WorkerHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "worker_name", nullable = false)
    private String workerName;

    @Column(name = "worker_role")
    private String workerRole;

    @Column(name = "source")
    private String source;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JsonIgnoreProperties("workerHistories")
    private Assignment assignment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkerName() {
        return workerName;
    }

    public WorkerHistory workerName(String workerName) {
        this.workerName = workerName;
        return this;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getWorkerRole() {
        return workerRole;
    }

    public WorkerHistory workerRole(String workerRole) {
        this.workerRole = workerRole;
        return this;
    }

    public void setWorkerRole(String workerRole) {
        this.workerRole = workerRole;
    }

    public String getSource() {
        return source;
    }

    public WorkerHistory source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public WorkerHistory phoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
        return this;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public WorkerHistory email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public WorkerHistory assignment(Assignment assignment) {
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
        if (!(o instanceof WorkerHistory)) {
            return false;
        }
        return id != null && id.equals(((WorkerHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "WorkerHistory{" +
            "id=" + getId() +
            ", workerName='" + getWorkerName() + "'" +
            ", workerRole='" + getWorkerRole() + "'" +
            ", source='" + getSource() + "'" +
            ", phoneNo='" + getPhoneNo() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
