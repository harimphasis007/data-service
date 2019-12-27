package com.data.service.repository;

import com.data.service.domain.Commitment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Commitment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommitmentRepository extends JpaRepository<Commitment, Long> {

}
