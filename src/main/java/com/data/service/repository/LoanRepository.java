package com.data.service.repository;

import com.data.service.domain.Loan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Loan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

}
