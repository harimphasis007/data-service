package com.data.service.web.rest;

import com.data.service.domain.DrawdownHistory;
import com.data.service.domain.Loan;
import com.data.service.repository.DrawdownHistoryRepository;
import com.data.service.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.data.service.domain.DrawdownHistory}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DrawdownHistoryResource {

    private final Logger log = LoggerFactory.getLogger(DrawdownHistoryResource.class);

    private static final String ENTITY_NAME = "dataserviceDrawdownHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DrawdownHistoryRepository drawdownHistoryRepository;

    public DrawdownHistoryResource(DrawdownHistoryRepository drawdownHistoryRepository) {
        this.drawdownHistoryRepository = drawdownHistoryRepository;
    }

    /**
     * {@code POST  /drawdown-histories} : Create a new drawdownHistory.
     *
     * @param drawdownHistory the drawdownHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new drawdownHistory, or with status {@code 400 (Bad Request)} if the drawdownHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/drawdown-histories")
    public ResponseEntity<DrawdownHistory> createDrawdownHistory(@RequestBody DrawdownHistory drawdownHistory) throws URISyntaxException {
        log.debug("REST request to save DrawdownHistory : {}", drawdownHistory);
        if (drawdownHistory.getId() != null) {
            throw new BadRequestAlertException("A new drawdownHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DrawdownHistory result = drawdownHistoryRepository.save(drawdownHistory);
        return ResponseEntity.created(new URI("/api/drawdown-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /drawdown-histories} : Updates an existing drawdownHistory.
     *
     * @param drawdownHistory the drawdownHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated drawdownHistory,
     * or with status {@code 400 (Bad Request)} if the drawdownHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the drawdownHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/drawdown-histories")
    public ResponseEntity<DrawdownHistory> updateDrawdownHistory(@RequestBody DrawdownHistory drawdownHistory) throws URISyntaxException {
        log.debug("REST request to update DrawdownHistory : {}", drawdownHistory);
        if (drawdownHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DrawdownHistory result = drawdownHistoryRepository.save(drawdownHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, drawdownHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /drawdown-histories} : get all the drawdownHistories.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of drawdownHistories in body.
     */


    @GetMapping("/drawdownhistoriesbyproject/{id}")
    public List<DrawdownHistory> getAllDrawdownHistoriesByProject(@PathVariable Long id) {
        log.debug("REST request to get all DrawdownHistories");
        List<DrawdownHistory>  drawdownHist = drawdownHistoryRepository.findByProject_id(id);
        return drawdownHist;
    }


    /**
     * {@code GET  /drawdown-histories/:id} : get the "id" drawdownHistory.
     *
     * @param id the id of the drawdownHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the drawdownHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/drawdown-histories/{id}")
    public ResponseEntity<DrawdownHistory> getDrawdownHistory(@PathVariable Long id) {
        log.debug("REST request to get DrawdownHistory : {}", id);
        Optional<DrawdownHistory> drawdownHistory = drawdownHistoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(drawdownHistory);
    }

    /**
     * {@code DELETE  /drawdown-histories/:id} : delete the "id" drawdownHistory.
     *
     * @param id the id of the drawdownHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/drawdown-histories/{id}")
    public ResponseEntity<Void> deleteDrawdownHistory(@PathVariable Long id) {
        log.debug("REST request to delete DrawdownHistory : {}", id);
        drawdownHistoryRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
