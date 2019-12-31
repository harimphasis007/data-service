package com.data.service.web.rest;

import com.data.service.domain.NotificationHistory;
import com.data.service.repository.NotificationHistoryRepository;
import com.data.service.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.data.service.domain.NotificationHistory}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NotificationHistoryResource {

    private final Logger log = LoggerFactory.getLogger(NotificationHistoryResource.class);

    private static final String ENTITY_NAME = "dataserviceNotificationHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotificationHistoryRepository notificationHistoryRepository;

    public NotificationHistoryResource(NotificationHistoryRepository notificationHistoryRepository) {
        this.notificationHistoryRepository = notificationHistoryRepository;
    }

    /**
     * {@code POST  /notification-histories} : Create a new notificationHistory.
     *
     * @param notificationHistory the notificationHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notificationHistory, or with status {@code 400 (Bad Request)} if the notificationHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notification-histories")
    public ResponseEntity<NotificationHistory> createNotificationHistory(@Valid @RequestBody NotificationHistory notificationHistory) throws URISyntaxException {
        log.debug("REST request to save NotificationHistory : {}", notificationHistory);
        if (notificationHistory.getId() != null) {
            throw new BadRequestAlertException("A new notificationHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NotificationHistory result = notificationHistoryRepository.save(notificationHistory);
        return ResponseEntity.created(new URI("/api/notification-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /notification-histories} : Updates an existing notificationHistory.
     *
     * @param notificationHistory the notificationHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notificationHistory,
     * or with status {@code 400 (Bad Request)} if the notificationHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notificationHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/notification-histories")
    public ResponseEntity<NotificationHistory> updateNotificationHistory(@Valid @RequestBody NotificationHistory notificationHistory) throws URISyntaxException {
        log.debug("REST request to update NotificationHistory : {}", notificationHistory);
        if (notificationHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NotificationHistory result = notificationHistoryRepository.save(notificationHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, notificationHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /notification-histories} : get all the notificationHistories.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notificationHistories in body.
     */
    @GetMapping("/notification-histories")
    public List<NotificationHistory> getAllNotificationHistories() {
        log.debug("REST request to get all NotificationHistories");
        return notificationHistoryRepository.findAll();
    }

    /**
     * {@code GET  /notification-histories/:id} : get the "id" notificationHistory.
     *
     * @param id the id of the notificationHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notificationHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/notification-histories/{id}")
    public ResponseEntity<NotificationHistory> getNotificationHistory(@PathVariable Long id) {
        log.debug("REST request to get NotificationHistory : {}", id);
        Optional<NotificationHistory> notificationHistory = notificationHistoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(notificationHistory);
    }

    /**
     * {@code DELETE  /notification-histories/:id} : delete the "id" notificationHistory.
     *
     * @param id the id of the notificationHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/notification-histories/{id}")
    public ResponseEntity<Void> deleteNotificationHistory(@PathVariable Long id) {
        log.debug("REST request to delete NotificationHistory : {}", id);
        notificationHistoryRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
