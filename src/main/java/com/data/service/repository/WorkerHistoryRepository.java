package com.data.service.repository;

import com.data.service.domain.WorkerHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the WorkerHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkerHistoryRepository extends JpaRepository<WorkerHistory, Long> {

}
