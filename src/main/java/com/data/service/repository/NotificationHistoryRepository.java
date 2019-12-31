package com.data.service.repository;

import com.data.service.domain.NotificationHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NotificationHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificationHistoryRepository extends JpaRepository<NotificationHistory, Long> {

}
