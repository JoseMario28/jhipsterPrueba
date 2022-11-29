package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.TotalVentas;
import com.mycompany.myapp.domain.Trabajador;
import com.mycompany.myapp.service.TrabajadorService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Trabajador}.
 */
@RestController
@RequestMapping("/api")
public class TrabajadorResource {

    private final Logger log = LoggerFactory.getLogger(TrabajadorResource.class);

    private static final String ENTITY_NAME = "trabajador";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrabajadorService trabajadorService;

    public TrabajadorResource(TrabajadorService trabajadorService) {
        this.trabajadorService = trabajadorService;
    }

    /**
     * {@code POST  /trabajadors} : Create a new trabajador.
     *
     * @param trabajador the trabajador to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trabajador, or with status {@code 400 (Bad Request)} if the trabajador has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trabajadors")
    public ResponseEntity<Trabajador> createTrabajador(@RequestBody Trabajador trabajador) throws URISyntaxException {
        log.debug("REST request to save Trabajador : {}", trabajador);
        if (trabajador.getId() != null) {
            throw new BadRequestAlertException("A new trabajador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Trabajador result = trabajadorService.save(trabajador);
        return ResponseEntity.created(new URI("/api/trabajadors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trabajadors} : Updates an existing trabajador.
     *
     * @param trabajador the trabajador to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trabajador,
     * or with status {@code 400 (Bad Request)} if the trabajador is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trabajador couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trabajadors")
    public ResponseEntity<Trabajador> updateTrabajador(@RequestBody Trabajador trabajador) throws URISyntaxException {
        log.debug("REST request to update Trabajador : {}", trabajador);
        if (trabajador.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Trabajador result = trabajadorService.save(trabajador);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trabajador.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /trabajadors} : get all the trabajadors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trabajadors in body.
     */
    @GetMapping("/trabajadors")
    public ResponseEntity<List<Trabajador>> getAllTrabajadors(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Trabajadors");
        Page<Trabajador> page = trabajadorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /trabajadors/:id} : get the "id" trabajador.
     *
     * @param id the id of the trabajador to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trabajador, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trabajadors/{id}")
    public ResponseEntity<Trabajador> getTrabajador(@PathVariable Long id) {
        log.debug("REST request to get Trabajador : {}", id);
        Optional<Trabajador> trabajador = trabajadorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trabajador);
    }

    /**
     * {@code DELETE  /trabajadors/:id} : delete the "id" trabajador.
     *
     * @param id the id of the trabajador to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trabajadors/{id}")
    public ResponseEntity<Void> deleteTrabajador(@PathVariable Long id) {
        log.debug("REST request to delete Trabajador : {}", id);
        trabajadorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/trabajadors?query=:query} : search for the trabajador corresponding
     * to the query.
     *
     * @param query the query of the trabajador search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/trabajadors")
    public ResponseEntity<List<Trabajador>> searchTrabajadors(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of Trabajadors for query {}", query);
        Page<Trabajador> page = trabajadorService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }





}
