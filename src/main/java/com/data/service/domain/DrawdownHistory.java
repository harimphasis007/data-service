package com.data.service.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DrawdownHistory.
 */
@Entity
@Table(name = "drawdown_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DrawdownHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "note")
    private String note;

    @Column(name = "status")
    private String status;

    @Column(name = "amount")
    private String amount;

    @Column(name = "term")
    private String term;

    @Column(name = "rate")
    private String rate;

    @Column(name = "maturity_date")
    private LocalDate maturityDate;

    @Column(name = "int_subsidy_al")
    private String intSubsidyAl;

    @Column(name = "int_subsidy_cr")
    private String intSubsidyCr;

    @ManyToOne
    @JsonIgnoreProperties("drawdownHistories")
    private Project project;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public DrawdownHistory date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public DrawdownHistory note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public DrawdownHistory status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public DrawdownHistory amount(String amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTerm() {
        return term;
    }

    public DrawdownHistory term(String term) {
        this.term = term;
        return this;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getRate() {
        return rate;
    }

    public DrawdownHistory rate(String rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public DrawdownHistory maturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
        return this;
    }

    public void setMaturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getIntSubsidyAl() {
        return intSubsidyAl;
    }

    public DrawdownHistory intSubsidyAl(String intSubsidyAl) {
        this.intSubsidyAl = intSubsidyAl;
        return this;
    }

    public void setIntSubsidyAl(String intSubsidyAl) {
        this.intSubsidyAl = intSubsidyAl;
    }

    public String getIntSubsidyCr() {
        return intSubsidyCr;
    }

    public DrawdownHistory intSubsidyCr(String intSubsidyCr) {
        this.intSubsidyCr = intSubsidyCr;
        return this;
    }

    public void setIntSubsidyCr(String intSubsidyCr) {
        this.intSubsidyCr = intSubsidyCr;
    }

    public Project getProject() {
        return project;
    }

    public DrawdownHistory project(Project project) {
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
        if (!(o instanceof DrawdownHistory)) {
            return false;
        }
        return id != null && id.equals(((DrawdownHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DrawdownHistory{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", note='" + getNote() + "'" +
            ", status='" + getStatus() + "'" +
            ", amount='" + getAmount() + "'" +
            ", term='" + getTerm() + "'" +
            ", rate='" + getRate() + "'" +
            ", maturityDate='" + getMaturityDate() + "'" +
            ", intSubsidyAl='" + getIntSubsidyAl() + "'" +
            ", intSubsidyCr='" + getIntSubsidyCr() + "'" +
            "}";
    }
}
