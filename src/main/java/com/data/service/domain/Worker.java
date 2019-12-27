package com.data.service.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Worker.
 */
@Entity
@Table(name = "worker")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Worker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "worker_name", nullable = false)
    private String workerName;

    @Column(name = "worker_role")
    private String workerRole;

    @OneToOne
    @JoinColumn(unique = true)
    private Review review;

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

    public Worker workerName(String workerName) {
        this.workerName = workerName;
        return this;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getWorkerRole() {
        return workerRole;
    }

    public Worker workerRole(String workerRole) {
        this.workerRole = workerRole;
        return this;
    }

    public void setWorkerRole(String workerRole) {
        this.workerRole = workerRole;
    }

    public Review getReview() {
        return review;
    }

    public Worker review(Review review) {
        this.review = review;
        return this;
    }

    public void setReview(Review review) {
        this.review = review;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Worker)) {
            return false;
        }
        return id != null && id.equals(((Worker) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Worker{" +
            "id=" + getId() +
            ", workerName='" + getWorkerName() + "'" +
            ", workerRole='" + getWorkerRole() + "'" +
            "}";
    }
}
