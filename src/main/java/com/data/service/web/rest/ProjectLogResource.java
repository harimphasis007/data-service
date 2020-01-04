package com.data.service.web.rest;

import com.data.service.domain.Loan;
import com.data.service.domain.ProjectLog;
import com.data.service.repository.ProjectLogRepository;
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
 * REST controller for managing {@link com.data.service.domain.ProjectLog}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProjectLogResource {

    private final Logger log = LoggerFactory.getLogger(ProjectLogResource.class);

    private static final String ENTITY_NAME = "dataserviceProjectLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectLogRepository projectLogRepository;

    public ProjectLogResource(ProjectLogRepository projectLogRepository) {
        this.projectLogRepository = projectLogRepository;
    }

    /**
     * {@code POST  /project-logs} : Create a new projectLog.
     *
     * @param projectLog the projectLog to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectLog, or with status {@code 400 (Bad Request)} if the projectLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-logs")
    public ResponseEntity<ProjectLog> createProjectLog(@Valid @RequestBody ProjectLog projectLog) throws URISyntaxException {
        log.debug("REST request to save ProjectLog : {}", projectLog);
        if (projectLog.getId() != null) {
            throw new BadRequestAlertException("A new projectLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectLog result = projectLogRepository.save(projectLog);
        return ResponseEntity.created(new URI("/api/project-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-logs} : Updates an existing projectLog.
     *
     * @param projectLog the projectLog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectLog,
     * or with status {@code 400 (Bad Request)} if the projectLog is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectLog couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-logs")
    public ResponseEntity<ProjectLog> updateProjectLog(@Valid @RequestBody ProjectLog projectLog) throws URISyntaxException {
        log.debug("REST request to update ProjectLog : {}", projectLog);
        if (projectLog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectLog result = projectLogRepository.save(projectLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projectLog.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-logs} : get all the projectLogs.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectLogs in body.
     */
    @GetMapping("/project-logs")
    public List<ProjectLog> getAllProjectLogs() {
        log.debug("REST request to get all ProjectLogs");
        return projectLogRepository.findAll();
    }

    /**
     * {@code GET  /project-logs/:id} : get the "id" projectLog.
     *
     * @param id the id of the projectLog to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectLog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-logs/{id}")
    public ResponseEntity<ProjectLog> getProjectLog(@PathVariable Long id) {
        log.debug("REST request to get ProjectLog : {}", id);
        Optional<ProjectLog> projectLog = projectLogRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(projectLog);
    }

    /**
     * {@code DELETE  /project-logs/:id} : delete the "id" projectLog.
     *
     * @param id the id of the projectLog to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-logs/{id}")
    public ResponseEntity<Void> deleteProjectLog(@PathVariable Long id) {
        log.debug("REST request to delete ProjectLog : {}", id);
        projectLogRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/projectlogsbyproject/{id}")
    public List<ProjectLog> getProjectLogByProject(@PathVariable Long id) {
        log.debug("REST request to get ProjectLog : {}", id);
        List<ProjectLog> projectLog = projectLogRepository.findByProject_id(id);
        return projectLog;
    }
}
