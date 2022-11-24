package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Trabajador;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Trabajador}.
 */
public interface TrabajadorService {

    /**
     * Save a trabajador.
     *
     * @param trabajador the entity to save.
     * @return the persisted entity.
     */
    Trabajador save(Trabajador trabajador);

    /**
     * Get all the trabajadors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Trabajador> findAll(Pageable pageable);


    /**
     * Get the "id" trabajador.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Trabajador> findOne(Long id);

    /**
     * Delete the "id" trabajador.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the trabajador corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Trabajador> search(String query, Pageable pageable);
}
