package com.data.service.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Assignment.
 */
@Entity
@Table(name = "assignment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Assignment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "current_assign", nullable = false)
    private String currentAssign;

    @Column(name = "current_ass_st_date")
    private LocalDate currentAssStDate;

    @Column(name = "analyst_assign")
    private String analystAssign;

    @Column(name = "manager_assign")
    private String managerAssign;

    @OneToOne
    @JoinColumn(unique = true)
    private Worker worker;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrentAssign() {
        return currentAssign;
    }

    public Assignment currentAssign(String currentAssign) {
        this.currentAssign = currentAssign;
        return this;
    }

    public void setCurrentAssign(String currentAssign) {
        this.currentAssign = currentAssign;
    }

    public LocalDate getCurrentAssStDate() {
        return currentAssStDate;
    }

    public Assignment currentAssStDate(LocalDate currentAssStDate) {
        this.currentAssStDate = currentAssStDate;
        return this;
    }

    public void setCurrentAssStDate(LocalDate currentAssStDate) {
        this.currentAssStDate = currentAssStDate;
    }

    public String getAnalystAssign() {
        return analystAssign;
    }

    public Assignment analystAssign(String analystAssign) {
        this.analystAssign = analystAssign;
        return this;
    }

    public void setAnalystAssign(String analystAssign) {
        this.analystAssign = analystAssign;
    }

    public String getManagerAssign() {
        return managerAssign;
    }

    public Assignment managerAssign(String managerAssign) {
        this.managerAssign = managerAssign;
        return this;
    }

    public void setManagerAssign(String managerAssign) {
        this.managerAssign = managerAssign;
    }

    public Worker getWorker() {
        return worker;
    }

    public Assignment worker(Worker worker) {
        this.worker = worker;
        return this;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Assignment)) {
            return false;
        }
        return id != null && id.equals(((Assignment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Assignment{" +
            "id=" + getId() +
            ", currentAssign='" + getCurrentAssign() + "'" +
            ", currentAssStDate='" + getCurrentAssStDate() + "'" +
            ", analystAssign='" + getAnalystAssign() + "'" +
            ", managerAssign='" + getManagerAssign() + "'" +
            "}";
    }
}
