package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.VehiculoService;
import com.mycompany.myapp.domain.Vehiculo;
import com.mycompany.myapp.repository.VehiculoRepository;
import com.mycompany.myapp.repository.search.VehiculoSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Vehiculo}.
 */
@Service
@Transactional
public class VehiculoServiceImpl implements VehiculoService {

    private final Logger log = LoggerFactory.getLogger(VehiculoServiceImpl.class);

    private final VehiculoRepository vehiculoRepository;

    private final VehiculoSearchRepository vehiculoSearchRepository;

    public VehiculoServiceImpl(VehiculoRepository vehiculoRepository, VehiculoSearchRepository vehiculoSearchRepository) {
        this.vehiculoRepository = vehiculoRepository;
        this.vehiculoSearchRepository = vehiculoSearchRepository;
    }

    /**
     * Save a vehiculo.
     *
     * @param vehiculo the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Vehiculo save(Vehiculo vehiculo) {
        log.debug("Request to save Vehiculo : {}", vehiculo);
        Vehiculo result = vehiculoRepository.save(vehiculo);
        vehiculoSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the vehiculos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Vehiculo> findAll(Pageable pageable) {
        log.debug("Request to get all Vehiculos");
        return vehiculoRepository.findAll(pageable);
    }


    /**
     * Get one vehiculo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Vehiculo> findOne(Long id) {
        log.debug("Request to get Vehiculo : {}", id);
        return vehiculoRepository.findById(id);
    }

    /**
     * Delete the vehiculo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vehiculo : {}", id);
        vehiculoRepository.deleteById(id);
        vehiculoSearchRepository.deleteById(id);
    }

    /**
     * Search for the vehiculo corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Vehiculo> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Vehiculos for query {}", query);
        return vehiculoSearchRepository.search(queryStringQuery(query), pageable);    }

    @Override
    public Page<Vehiculo> findVehiculosFiltrados(String marca, Pageable pageable) {

        return vehiculoRepository.searchVehiculosFiltrados(marca, pageable);
    }
}
