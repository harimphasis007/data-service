package com.data.service.repository;

import com.data.service.domain.DrawdownHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the DrawdownHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DrawdownHistoryRepository extends JpaRepository<DrawdownHistory, Long> {

    List<DrawdownHistory> findByProject_id(Long id);
}
