package com.data.service.web.rest;

import com.data.service.domain.Commitment;
import com.data.service.repository.CommitmentRepository;
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
 * REST controller for managing {@link com.data.service.domain.Commitment}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CommitmentResource {

    private final Logger log = LoggerFactory.getLogger(CommitmentResource.class);

    private static final String ENTITY_NAME = "dataserviceCommitment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommitmentRepository commitmentRepository;

    public CommitmentResource(CommitmentRepository commitmentRepository) {
        this.commitmentRepository = commitmentRepository;
    }

    /**
     * {@code POST  /commitments} : Create a new commitment.
     *
     * @param commitment the commitment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commitment, or with status {@code 400 (Bad Request)} if the commitment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/commitments")
    public ResponseEntity<Commitment> createCommitment(@Valid @RequestBody Commitment commitment) throws URISyntaxException {
        log.debug("REST request to save Commitment : {}", commitment);
        if (commitment.getId() != null) {
            throw new BadRequestAlertException("A new commitment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Commitment result = commitmentRepository.save(commitment);
        return ResponseEntity.created(new URI("/api/commitments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /commitments} : Updates an existing commitment.
     *
     * @param commitment the commitment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commitment,
     * or with status {@code 400 (Bad Request)} if the commitment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commitment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/commitments")
    public ResponseEntity<Commitment> updateCommitment(@Valid @RequestBody Commitment commitment) throws URISyntaxException {
        log.debug("REST request to update Commitment : {}", commitment);
        if (commitment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Commitment result = commitmentRepository.save(commitment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, commitment.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /commitments} : get all the commitments.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commitments in body.
     */
    @GetMapping("/commitments")
    public List<Commitment> getAllCommitments() {
        log.debug("REST request to get all Commitments");
        return commitmentRepository.findAll();
    }

    /**
     * {@code GET  /commitments/:id} : get the "id" commitment.
     *
     * @param id the id of the commitment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commitment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/commitments/{id}")
    public ResponseEntity<Commitment> getCommitment(@PathVariable Long id) {
        log.debug("REST request to get Commitment : {}", id);
        Optional<Commitment> commitment = commitmentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(commitment);
    }

    /**
     * {@code DELETE  /commitments/:id} : delete the "id" commitment.
     *
     * @param id the id of the commitment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/commitments/{id}")
    public ResponseEntity<Void> deleteCommitment(@PathVariable Long id) {
        log.debug("REST request to delete Commitment : {}", id);
        commitmentRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
