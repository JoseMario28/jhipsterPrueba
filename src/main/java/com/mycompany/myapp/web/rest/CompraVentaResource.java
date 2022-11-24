package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.CompraVenta;
import com.mycompany.myapp.service.CompraVentaService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CompraVenta}.
 */
@RestController
@RequestMapping("/api")
public class CompraVentaResource {

    private final Logger log = LoggerFactory.getLogger(CompraVentaResource.class);

    private static final String ENTITY_NAME = "compraVenta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompraVentaService compraVentaService;

    public CompraVentaResource(CompraVentaService compraVentaService) {
        this.compraVentaService = compraVentaService;
    }

    /**
     * {@code POST  /compra-ventas} : Create a new compraVenta.
     *
     * @param compraVenta the compraVenta to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new compraVenta, or with status {@code 400 (Bad Request)} if the compraVenta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/compra-ventas")
    public ResponseEntity<CompraVenta> createCompraVenta(@RequestBody CompraVenta compraVenta) throws URISyntaxException {
        log.debug("REST request to save CompraVenta : {}", compraVenta);
        if (compraVenta.getId() != null) {
            throw new BadRequestAlertException("A new compraVenta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompraVenta result = compraVentaService.save(compraVenta);
        return ResponseEntity.created(new URI("/api/compra-ventas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /compra-ventas} : Updates an existing compraVenta.
     *
     * @param compraVenta the compraVenta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated compraVenta,
     * or with status {@code 400 (Bad Request)} if the compraVenta is not valid,
     * or with status {@code 500 (Internal Server Error)} if the compraVenta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/compra-ventas")
    public ResponseEntity<CompraVenta> updateCompraVenta(@RequestBody CompraVenta compraVenta) throws URISyntaxException {
        log.debug("REST request to update CompraVenta : {}", compraVenta);
        if (compraVenta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompraVenta result = compraVentaService.save(compraVenta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, compraVenta.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /compra-ventas} : get all the compraVentas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of compraVentas in body.
     */
    @GetMapping("/compra-ventas")
    public ResponseEntity<List<CompraVenta>> getAllCompraVentas(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of CompraVentas");
        Page<CompraVenta> page = compraVentaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /compra-ventas/:id} : get the "id" compraVenta.
     *
     * @param id the id of the compraVenta to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the compraVenta, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/compra-ventas/{id}")
    public ResponseEntity<CompraVenta> getCompraVenta(@PathVariable Long id) {
        log.debug("REST request to get CompraVenta : {}", id);
        Optional<CompraVenta> compraVenta = compraVentaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(compraVenta);
    }

    /**
     * {@code DELETE  /compra-ventas/:id} : delete the "id" compraVenta.
     *
     * @param id the id of the compraVenta to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/compra-ventas/{id}")
    public ResponseEntity<Void> deleteCompraVenta(@PathVariable Long id) {
        log.debug("REST request to delete CompraVenta : {}", id);
        compraVentaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/compra-ventas?query=:query} : search for the compraVenta corresponding
     * to the query.
     *
     * @param query the query of the compraVenta search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/compra-ventas")
    public ResponseEntity<List<CompraVenta>> searchCompraVentas(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of CompraVentas for query {}", query);
        Page<CompraVenta> page = compraVentaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
