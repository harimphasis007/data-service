package com.data.service.repository;

import com.data.service.domain.ProjectLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProjectLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectLogRepository extends JpaRepository<ProjectLog, Long> {

}
