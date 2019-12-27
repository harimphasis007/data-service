package com.data.service.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Commitment.
 */
@Entity
@Table(name = "commitment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Commitment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "commitment_status", nullable = false)
    private String commitmentStatus;

    @Column(name = "commitment_bal")
    private String commitmentBal;

    @Column(name = "commitment_expiration")
    private LocalDate commitmentExpiration;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommitmentStatus() {
        return commitmentStatus;
    }

    public Commitment commitmentStatus(String commitmentStatus) {
        this.commitmentStatus = commitmentStatus;
        return this;
    }

    public void setCommitmentStatus(String commitmentStatus) {
        this.commitmentStatus = commitmentStatus;
    }

    public String getCommitmentBal() {
        return commitmentBal;
    }

    public Commitment commitmentBal(String commitmentBal) {
        this.commitmentBal = commitmentBal;
        return this;
    }

    public void setCommitmentBal(String commitmentBal) {
        this.commitmentBal = commitmentBal;
    }

    public LocalDate getCommitmentExpiration() {
        return commitmentExpiration;
    }

    public Commitment commitmentExpiration(LocalDate commitmentExpiration) {
        this.commitmentExpiration = commitmentExpiration;
        return this;
    }

    public void setCommitmentExpiration(LocalDate commitmentExpiration) {
        this.commitmentExpiration = commitmentExpiration;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Commitment)) {
            return false;
        }
        return id != null && id.equals(((Commitment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Commitment{" +
            "id=" + getId() +
            ", commitmentStatus='" + getCommitmentStatus() + "'" +
            ", commitmentBal='" + getCommitmentBal() + "'" +
            ", commitmentExpiration='" + getCommitmentExpiration() + "'" +
            "}";
    }
}
