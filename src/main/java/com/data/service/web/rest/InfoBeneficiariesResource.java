package com.data.service.web.rest;

import com.data.service.domain.InfoBeneficiaries;
import com.data.service.repository.InfoBeneficiariesRepository;
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
 * REST controller for managing {@link com.data.service.domain.InfoBeneficiaries}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class InfoBeneficiariesResource {

    private final Logger log = LoggerFactory.getLogger(InfoBeneficiariesResource.class);

    private static final String ENTITY_NAME = "dataserviceInfoBeneficiaries";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InfoBeneficiariesRepository infoBeneficiariesRepository;

    public InfoBeneficiariesResource(InfoBeneficiariesRepository infoBeneficiariesRepository) {
        this.infoBeneficiariesRepository = infoBeneficiariesRepository;
    }

    /**
     * {@code POST  /info-beneficiaries} : Create a new infoBeneficiaries.
     *
     * @param infoBeneficiaries the infoBeneficiaries to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new infoBeneficiaries, or with status {@code 400 (Bad Request)} if the infoBeneficiaries has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/info-beneficiaries")
    public ResponseEntity<InfoBeneficiaries> createInfoBeneficiaries(@Valid @RequestBody InfoBeneficiaries infoBeneficiaries) throws URISyntaxException {
        log.debug("REST request to save InfoBeneficiaries : {}", infoBeneficiaries);
        if (infoBeneficiaries.getId() != null) {
            throw new BadRequestAlertException("A new infoBeneficiaries cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InfoBeneficiaries result = infoBeneficiariesRepository.save(infoBeneficiaries);
        return ResponseEntity.created(new URI("/api/info-beneficiaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /info-beneficiaries} : Updates an existing infoBeneficiaries.
     *
     * @param infoBeneficiaries the infoBeneficiaries to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated infoBeneficiaries,
     * or with status {@code 400 (Bad Request)} if the infoBeneficiaries is not valid,
     * or with status {@code 500 (Internal Server Error)} if the infoBeneficiaries couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/info-beneficiaries")
    public ResponseEntity<InfoBeneficiaries> updateInfoBeneficiaries(@Valid @RequestBody InfoBeneficiaries infoBeneficiaries) throws URISyntaxException {
        log.debug("REST request to update InfoBeneficiaries : {}", infoBeneficiaries);
        if (infoBeneficiaries.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InfoBeneficiaries result = infoBeneficiariesRepository.save(infoBeneficiaries);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, infoBeneficiaries.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /info-beneficiaries} : get all the infoBeneficiaries.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of infoBeneficiaries in body.
     */
    @GetMapping("/info-beneficiaries")
    public List<InfoBeneficiaries> getAllInfoBeneficiaries() {
        log.debug("REST request to get all InfoBeneficiaries");
        return infoBeneficiariesRepository.findAll();
    }

    /**
     * {@code GET  /info-beneficiaries/:id} : get the "id" infoBeneficiaries.
     *
     * @param id the id of the infoBeneficiaries to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the infoBeneficiaries, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/info-beneficiaries/{id}")
    public ResponseEntity<InfoBeneficiaries> getInfoBeneficiaries(@PathVariable Long id) {
        log.debug("REST request to get InfoBeneficiaries : {}", id);
        Optional<InfoBeneficiaries> infoBeneficiaries = infoBeneficiariesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(infoBeneficiaries);
    }

    /**
     * {@code DELETE  /info-beneficiaries/:id} : delete the "id" infoBeneficiaries.
     *
     * @param id the id of the infoBeneficiaries to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/info-beneficiaries/{id}")
    public ResponseEntity<Void> deleteInfoBeneficiaries(@PathVariable Long id) {
        log.debug("REST request to delete InfoBeneficiaries : {}", id);
        infoBeneficiariesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
