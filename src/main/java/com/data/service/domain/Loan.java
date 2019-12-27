package com.data.service.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Loan.
 */
@Entity
@Table(name = "loan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Loan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "loanid", nullable = false)
    private String loanid;

    @Column(name = "settelment_date")
    private LocalDate settelmentDate;

    @Column(name = "amount")
    private String amount;

    @Column(name = "term")
    private String term;

    @Column(name = "property_address")
    private String propertyAddress;

    @Column(name = "mbs_pool_id")
    private String mbsPoolId;

    @Column(name = "mbs_qualifies")
    private String mbsQualifies;

    @Column(name = "qualification_method")
    private String qualificationMethod;

    @Column(name = "decision")
    private String decision;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @ManyToOne
    @JsonIgnoreProperties("loans")
    private Application application;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoanid() {
        return loanid;
    }

    public Loan loanid(String loanid) {
        this.loanid = loanid;
        return this;
    }

    public void setLoanid(String loanid) {
        this.loanid = loanid;
    }

    public LocalDate getSettelmentDate() {
        return settelmentDate;
    }

    public Loan settelmentDate(LocalDate settelmentDate) {
        this.settelmentDate = settelmentDate;
        return this;
    }

    public void setSettelmentDate(LocalDate settelmentDate) {
        this.settelmentDate = settelmentDate;
    }

    public String getAmount() {
        return amount;
    }

    public Loan amount(String amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTerm() {
        return term;
    }

    public Loan term(String term) {
        this.term = term;
        return this;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public Loan propertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
        return this;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }

    public String getMbsPoolId() {
        return mbsPoolId;
    }

    public Loan mbsPoolId(String mbsPoolId) {
        this.mbsPoolId = mbsPoolId;
        return this;
    }

    public void setMbsPoolId(String mbsPoolId) {
        this.mbsPoolId = mbsPoolId;
    }

    public String getMbsQualifies() {
        return mbsQualifies;
    }

    public Loan mbsQualifies(String mbsQualifies) {
        this.mbsQualifies = mbsQualifies;
        return this;
    }

    public void setMbsQualifies(String mbsQualifies) {
        this.mbsQualifies = mbsQualifies;
    }

    public String getQualificationMethod() {
        return qualificationMethod;
    }

    public Loan qualificationMethod(String qualificationMethod) {
        this.qualificationMethod = qualificationMethod;
        return this;
    }

    public void setQualificationMethod(String qualificationMethod) {
        this.qualificationMethod = qualificationMethod;
    }

    public String getDecision() {
        return decision;
    }

    public Loan decision(String decision) {
        this.decision = decision;
        return this;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public Loan rejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
        return this;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public Application getApplication() {
        return application;
    }

    public Loan application(Application application) {
        this.application = application;
        return this;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Loan)) {
            return false;
        }
        return id != null && id.equals(((Loan) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Loan{" +
            "id=" + getId() +
            ", loanid='" + getLoanid() + "'" +
            ", settelmentDate='" + getSettelmentDate() + "'" +
            ", amount='" + getAmount() + "'" +
            ", term='" + getTerm() + "'" +
            ", propertyAddress='" + getPropertyAddress() + "'" +
            ", mbsPoolId='" + getMbsPoolId() + "'" +
            ", mbsQualifies='" + getMbsQualifies() + "'" +
            ", qualificationMethod='" + getQualificationMethod() + "'" +
            ", decision='" + getDecision() + "'" +
            ", rejectionReason='" + getRejectionReason() + "'" +
            "}";
    }
}
