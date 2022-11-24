package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.CompraVenta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link CompraVenta}.
 */
public interface CompraVentaService {

    /**
     * Save a compraVenta.
     *
     * @param compraVenta the entity to save.
     * @return the persisted entity.
     */
    CompraVenta save(CompraVenta compraVenta);

    /**
     * Get all the compraVentas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompraVenta> findAll(Pageable pageable);


    /**
     * Get the "id" compraVenta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompraVenta> findOne(Long id);

    /**
     * Delete the "id" compraVenta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the compraVenta corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompraVenta> search(String query, Pageable pageable);
}
