package com.data.service.web.rest;

import com.data.service.domain.Loanpool;
import com.data.service.repository.LoanpoolRepository;
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
 * REST controller for managing {@link com.data.service.domain.Loanpool}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LoanpoolResource {

    private final Logger log = LoggerFactory.getLogger(LoanpoolResource.class);

    private static final String ENTITY_NAME = "dataserviceLoanpool";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LoanpoolRepository loanpoolRepository;

    public LoanpoolResource(LoanpoolRepository loanpoolRepository) {
        this.loanpoolRepository = loanpoolRepository;
    }

    /**
     * {@code POST  /loanpools} : Create a new loanpool.
     *
     * @param loanpool the loanpool to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loanpool, or with status {@code 400 (Bad Request)} if the loanpool has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/loanpools")
    public ResponseEntity<Loanpool> createLoanpool(@Valid @RequestBody Loanpool loanpool) throws URISyntaxException {
        log.debug("REST request to save Loanpool : {}", loanpool);
        if (loanpool.getId() != null) {
            throw new BadRequestAlertException("A new loanpool cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Loanpool result = loanpoolRepository.save(loanpool);
        return ResponseEntity.created(new URI("/api/loanpools/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /loanpools} : Updates an existing loanpool.
     *
     * @param loanpool the loanpool to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loanpool,
     * or with status {@code 400 (Bad Request)} if the loanpool is not valid,
     * or with status {@code 500 (Internal Server Error)} if the loanpool couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/loanpools")
    public ResponseEntity<Loanpool> updateLoanpool(@Valid @RequestBody Loanpool loanpool) throws URISyntaxException {
        log.debug("REST request to update Loanpool : {}", loanpool);
        if (loanpool.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Loanpool result = loanpoolRepository.save(loanpool);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, loanpool.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /loanpools} : get all the loanpools.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of loanpools in body.
     */
    @GetMapping("/loanpools")
    public List<Loanpool> getAllLoanpools() {
        log.debug("REST request to get all Loanpools");
        return loanpoolRepository.findAll();
    }

    /**
     * {@code GET  /loanpools/:id} : get the "id" loanpool.
     *
     * @param id the id of the loanpool to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loanpool, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/loanpools/{id}")
    public ResponseEntity<Loanpool> getLoanpool(@PathVariable Long id) {
        log.debug("REST request to get Loanpool : {}", id);
        Optional<Loanpool> loanpool = loanpoolRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(loanpool);
    }

    /**
     * {@code DELETE  /loanpools/:id} : delete the "id" loanpool.
     *
     * @param id the id of the loanpool to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/loanpools/{id}")
    public ResponseEntity<Void> deleteLoanpool(@PathVariable Long id) {
        log.debug("REST request to delete Loanpool : {}", id);
        loanpoolRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
