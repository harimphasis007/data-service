package com.data.service.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A InfoBeneficiaries.
 */
@Entity
@Table(name = "info_beneficiaries")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InfoBeneficiaries implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "area", nullable = false)
    private String area;

    @Column(name = "development_ind")
    private String developmentInd;

    @Column(name = "rental_units")
    private String rentalUnits;

    @Column(name = "owner_occ_units")
    private String ownerOccUnits;

    @Column(name = "job_created")
    private String jobCreated;

    @Column(name = "job_retained")
    private String jobRetained;

    @Column(name = "geo_defined_beneficiaries")
    private String geoDefinedBeneficiaries;

    @Column(name = "individual_beneficiaries")
    private String individualBeneficiaries;

    @Column(name = "activity_beneficiaries")
    private String activityBeneficiaries;

    @Column(name = "other_beneficiaries")
    private String otherBeneficiaries;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public InfoBeneficiaries area(String area) {
        this.area = area;
        return this;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDevelopmentInd() {
        return developmentInd;
    }

    public InfoBeneficiaries developmentInd(String developmentInd) {
        this.developmentInd = developmentInd;
        return this;
    }

    public void setDevelopmentInd(String developmentInd) {
        this.developmentInd = developmentInd;
    }

    public String getRentalUnits() {
        return rentalUnits;
    }

    public InfoBeneficiaries rentalUnits(String rentalUnits) {
        this.rentalUnits = rentalUnits;
        return this;
    }

    public void setRentalUnits(String rentalUnits) {
        this.rentalUnits = rentalUnits;
    }

    public String getOwnerOccUnits() {
        return ownerOccUnits;
    }

    public InfoBeneficiaries ownerOccUnits(String ownerOccUnits) {
        this.ownerOccUnits = ownerOccUnits;
        return this;
    }

    public void setOwnerOccUnits(String ownerOccUnits) {
        this.ownerOccUnits = ownerOccUnits;
    }

    public String getJobCreated() {
        return jobCreated;
    }

    public InfoBeneficiaries jobCreated(String jobCreated) {
        this.jobCreated = jobCreated;
        return this;
    }

    public void setJobCreated(String jobCreated) {
        this.jobCreated = jobCreated;
    }

    public String getJobRetained() {
        return jobRetained;
    }

    public InfoBeneficiaries jobRetained(String jobRetained) {
        this.jobRetained = jobRetained;
        return this;
    }

    public void setJobRetained(String jobRetained) {
        this.jobRetained = jobRetained;
    }

    public String getGeoDefinedBeneficiaries() {
        return geoDefinedBeneficiaries;
    }

    public InfoBeneficiaries geoDefinedBeneficiaries(String geoDefinedBeneficiaries) {
        this.geoDefinedBeneficiaries = geoDefinedBeneficiaries;
        return this;
    }

    public void setGeoDefinedBeneficiaries(String geoDefinedBeneficiaries) {
        this.geoDefinedBeneficiaries = geoDefinedBeneficiaries;
    }

    public String getIndividualBeneficiaries() {
        return individualBeneficiaries;
    }

    public InfoBeneficiaries individualBeneficiaries(String individualBeneficiaries) {
        this.individualBeneficiaries = individualBeneficiaries;
        return this;
    }

    public void setIndividualBeneficiaries(String individualBeneficiaries) {
        this.individualBeneficiaries = individualBeneficiaries;
    }

    public String getActivityBeneficiaries() {
        return activityBeneficiaries;
    }

    public InfoBeneficiaries activityBeneficiaries(String activityBeneficiaries) {
        this.activityBeneficiaries = activityBeneficiaries;
        return this;
    }

    public void setActivityBeneficiaries(String activityBeneficiaries) {
        this.activityBeneficiaries = activityBeneficiaries;
    }

    public String getOtherBeneficiaries() {
        return otherBeneficiaries;
    }

    public InfoBeneficiaries otherBeneficiaries(String otherBeneficiaries) {
        this.otherBeneficiaries = otherBeneficiaries;
        return this;
    }

    public void setOtherBeneficiaries(String otherBeneficiaries) {
        this.otherBeneficiaries = otherBeneficiaries;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InfoBeneficiaries)) {
            return false;
        }
        return id != null && id.equals(((InfoBeneficiaries) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "InfoBeneficiaries{" +
            "id=" + getId() +
            ", area='" + getArea() + "'" +
            ", developmentInd='" + getDevelopmentInd() + "'" +
            ", rentalUnits='" + getRentalUnits() + "'" +
            ", ownerOccUnits='" + getOwnerOccUnits() + "'" +
            ", jobCreated='" + getJobCreated() + "'" +
            ", jobRetained='" + getJobRetained() + "'" +
            ", geoDefinedBeneficiaries='" + getGeoDefinedBeneficiaries() + "'" +
            ", individualBeneficiaries='" + getIndividualBeneficiaries() + "'" +
            ", activityBeneficiaries='" + getActivityBeneficiaries() + "'" +
            ", otherBeneficiaries='" + getOtherBeneficiaries() + "'" +
            "}";
    }
}
