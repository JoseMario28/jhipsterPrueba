package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Comision;
import com.mycompany.myapp.repository.ComisionRepository;
import com.mycompany.myapp.repository.search.ComisionSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Comision}.
 */
@Service
@Transactional
public class ComisionService {

    private final Logger log = LoggerFactory.getLogger(ComisionService.class);

    private final ComisionRepository comisionRepository;

    private final ComisionSearchRepository comisionSearchRepository;

    public ComisionService(ComisionRepository comisionRepository, ComisionSearchRepository comisionSearchRepository) {
        this.comisionRepository = comisionRepository;
        this.comisionSearchRepository = comisionSearchRepository;
    }

    /**
     * Save a comision.
     *
     * @param comision the entity to save.
     * @return the persisted entity.
     */
    public Comision save(Comision comision) {
        log.debug("Request to save Comision : {}", comision);
        Comision result = comisionRepository.save(comision);
        comisionSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the comisions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Comision> findAll() {
        log.debug("Request to get all Comisions");
        return comisionRepository.findAll();
    }


    /**
     * Get one comision by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Comision> findOne(Long id) {
        log.debug("Request to get Comision : {}", id);
        return comisionRepository.findById(id);
    }

    /**
     * Delete the comision by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Comision : {}", id);
        comisionRepository.deleteById(id);
        comisionSearchRepository.deleteById(id);
    }

    /**
     * Search for the comision corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Comision> search(String query) {
        log.debug("Request to search Comisions for query {}", query);
        return StreamSupport
            .stream(comisionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
