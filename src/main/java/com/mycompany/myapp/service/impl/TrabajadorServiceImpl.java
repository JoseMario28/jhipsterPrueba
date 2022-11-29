package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.TrabajadorService;
import com.mycompany.myapp.domain.Trabajador;
import com.mycompany.myapp.domain.TotalVentas;
import com.mycompany.myapp.repository.TrabajadorRepository;
import com.mycompany.myapp.repository.search.TrabajadorSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Trabajador}.
 */
@Service
@Transactional
public class TrabajadorServiceImpl implements TrabajadorService {

    private final Logger log = LoggerFactory.getLogger(TrabajadorServiceImpl.class);

    private final TrabajadorRepository trabajadorRepository;

    private final TrabajadorSearchRepository trabajadorSearchRepository;

    public TrabajadorServiceImpl(TrabajadorRepository trabajadorRepository, TrabajadorSearchRepository trabajadorSearchRepository) {
        this.trabajadorRepository = trabajadorRepository;
        this.trabajadorSearchRepository = trabajadorSearchRepository;
    }

    /**
     * Save a trabajador.
     *
     * @param trabajador the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Trabajador save(Trabajador trabajador) {
        log.debug("Request to save Trabajador : {}", trabajador);
        Trabajador result = trabajadorRepository.save(trabajador);
        trabajadorSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the trabajadors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Trabajador> findAll(Pageable pageable) {
        log.debug("Request to get all Trabajadors");
        return trabajadorRepository.findAll(pageable);
    }


    /**
     * Get one trabajador by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Trabajador> findOne(Long id) {
        log.debug("Request to get Trabajador : {}", id);
        return trabajadorRepository.findById(id);
    }

    /**
     * Delete the trabajador by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Trabajador : {}", id);
        trabajadorRepository.deleteById(id);
        trabajadorSearchRepository.deleteById(id);
    }

    /**
     * Search for the trabajador corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Trabajador> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Trabajadors for query {}", query);
        return trabajadorSearchRepository.search(queryStringQuery(query), pageable);    }


}
