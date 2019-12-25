package com.data.service.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "p_no")
    private String pNo;

    @Column(name = "program_name")
    private String programName;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "application_date")
    private LocalDate applicationDate;

    @Column(name = "p_status")
    private String pStatus;

    @Column(name = "current_assign")
    private String currentAssign;

    @Column(name = "current_ass_date")
    private LocalDate currentAssDate;

    @Column(name = "analyst_assign")
    private String analystAssign;

    @Column(name = "manager_assign")
    private String managerAssign;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getpNo() {
        return pNo;
    }

    public Project pNo(String pNo) {
        this.pNo = pNo;
        return this;
    }

    public void setpNo(String pNo) {
        this.pNo = pNo;
    }

    public String getProgramName() {
        return programName;
    }

    public Project programName(String programName) {
        this.programName = programName;
        return this;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getMemberName() {
        return memberName;
    }

    public Project memberName(String memberName) {
        this.memberName = memberName;
        return this;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public Project applicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
        return this;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getpStatus() {
        return pStatus;
    }

    public Project pStatus(String pStatus) {
        this.pStatus = pStatus;
        return this;
    }

    public void setpStatus(String pStatus) {
        this.pStatus = pStatus;
    }

    public String getCurrentAssign() {
        return currentAssign;
    }

    public Project currentAssign(String currentAssign) {
        this.currentAssign = currentAssign;
        return this;
    }

    public void setCurrentAssign(String currentAssign) {
        this.currentAssign = currentAssign;
    }

    public LocalDate getCurrentAssDate() {
        return currentAssDate;
    }

    public Project currentAssDate(LocalDate currentAssDate) {
        this.currentAssDate = currentAssDate;
        return this;
    }

    public void setCurrentAssDate(LocalDate currentAssDate) {
        this.currentAssDate = currentAssDate;
    }

    public String getAnalystAssign() {
        return analystAssign;
    }

    public Project analystAssign(String analystAssign) {
        this.analystAssign = analystAssign;
        return this;
    }

    public void setAnalystAssign(String analystAssign) {
        this.analystAssign = analystAssign;
    }

    public String getManagerAssign() {
        return managerAssign;
    }

    public Project managerAssign(String managerAssign) {
        this.managerAssign = managerAssign;
        return this;
    }

    public void setManagerAssign(String managerAssign) {
        this.managerAssign = managerAssign;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        return id != null && id.equals(((Project) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", pNo='" + getpNo() + "'" +
            ", programName='" + getProgramName() + "'" +
            ", memberName='" + getMemberName() + "'" +
            ", applicationDate='" + getApplicationDate() + "'" +
            ", pStatus='" + getpStatus() + "'" +
            ", currentAssign='" + getCurrentAssign() + "'" +
            ", currentAssDate='" + getCurrentAssDate() + "'" +
            ", analystAssign='" + getAnalystAssign() + "'" +
            ", managerAssign='" + getManagerAssign() + "'" +
            "}";
    }
}
