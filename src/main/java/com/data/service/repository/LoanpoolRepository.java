package com.data.service.repository;

import com.data.service.domain.Loanpool;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Loanpool entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoanpoolRepository extends JpaRepository<Loanpool, Long> {

}
