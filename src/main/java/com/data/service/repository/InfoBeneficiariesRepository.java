package com.data.service.repository;

import com.data.service.domain.InfoBeneficiaries;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InfoBeneficiaries entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InfoBeneficiariesRepository extends JpaRepository<InfoBeneficiaries, Long> {

}
