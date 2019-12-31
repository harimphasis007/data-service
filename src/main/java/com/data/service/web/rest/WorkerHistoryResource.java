package com.data.service.web.rest;

import com.data.service.domain.WorkerHistory;
import com.data.service.repository.WorkerHistoryRepository;
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
 * REST controller for managing {@link com.data.service.domain.WorkerHistory}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class WorkerHistoryResource {

    private final Logger log = LoggerFactory.getLogger(WorkerHistoryResource.class);

    private static final String ENTITY_NAME = "dataserviceWorkerHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkerHistoryRepository workerHistoryRepository;

    public WorkerHistoryResource(WorkerHistoryRepository workerHistoryRepository) {
        this.workerHistoryRepository = workerHistoryRepository;
    }

    /**
     * {@code POST  /worker-histories} : Create a new workerHistory.
     *
     * @param workerHistory the workerHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workerHistory, or with status {@code 400 (Bad Request)} if the workerHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/worker-histories")
    public ResponseEntity<WorkerHistory> createWorkerHistory(@Valid @RequestBody WorkerHistory workerHistory) throws URISyntaxException {
        log.debug("REST request to save WorkerHistory : {}", workerHistory);
        if (workerHistory.getId() != null) {
            throw new BadRequestAlertException("A new workerHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkerHistory result = workerHistoryRepository.save(workerHistory);
        return ResponseEntity.created(new URI("/api/worker-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /worker-histories} : Updates an existing workerHistory.
     *
     * @param workerHistory the workerHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workerHistory,
     * or with status {@code 400 (Bad Request)} if the workerHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workerHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/worker-histories")
    public ResponseEntity<WorkerHistory> updateWorkerHistory(@Valid @RequestBody WorkerHistory workerHistory) throws URISyntaxException {
        log.debug("REST request to update WorkerHistory : {}", workerHistory);
        if (workerHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkerHistory result = workerHistoryRepository.save(workerHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, workerHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /worker-histories} : get all the workerHistories.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workerHistories in body.
     */
    @GetMapping("/worker-histories")
    public List<WorkerHistory> getAllWorkerHistories() {
        log.debug("REST request to get all WorkerHistories");
        return workerHistoryRepository.findAll();
    }

    /**
     * {@code GET  /worker-histories/:id} : get the "id" workerHistory.
     *
     * @param id the id of the workerHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workerHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/worker-histories/{id}")
    public ResponseEntity<WorkerHistory> getWorkerHistory(@PathVariable Long id) {
        log.debug("REST request to get WorkerHistory : {}", id);
        Optional<WorkerHistory> workerHistory = workerHistoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(workerHistory);
    }

    /**
     * {@code DELETE  /worker-histories/:id} : delete the "id" workerHistory.
     *
     * @param id the id of the workerHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/worker-histories/{id}")
    public ResponseEntity<Void> deleteWorkerHistory(@PathVariable Long id) {
        log.debug("REST request to delete WorkerHistory : {}", id);
        workerHistoryRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
