package com.data.service.repository;

import com.data.service.domain.WorkerHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the WorkerHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkerHistoryRepository extends JpaRepository<WorkerHistory, Long> {

    List<WorkerHistory> findByAssignment_id(Long id);
}
