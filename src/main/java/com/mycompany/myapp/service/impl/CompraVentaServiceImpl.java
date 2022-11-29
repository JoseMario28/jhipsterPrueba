package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.CompraVentaService;
import com.mycompany.myapp.domain.CompraVenta;
import com.mycompany.myapp.domain.TotalVentas;
import com.mycompany.myapp.domain.Vehiculo;
import com.mycompany.myapp.repository.CompraVentaRepository;
import com.mycompany.myapp.repository.search.CompraVentaSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link CompraVenta}.
 */
@Service
@Transactional
public class CompraVentaServiceImpl implements CompraVentaService {

    private final Logger log = LoggerFactory.getLogger(CompraVentaServiceImpl.class);

    private final CompraVentaRepository compraVentaRepository;

    private final CompraVentaSearchRepository compraVentaSearchRepository;

    public CompraVentaServiceImpl(CompraVentaRepository compraVentaRepository, CompraVentaSearchRepository compraVentaSearchRepository) {
        this.compraVentaRepository = compraVentaRepository;
        this.compraVentaSearchRepository = compraVentaSearchRepository;
    }



    /**
     * Save a compraVenta.
     *
     * @param compraVenta the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CompraVenta save(CompraVenta compraVenta) {
        log.debug("Request to save CompraVenta : {}", compraVenta);
        Vehiculo vehiculo = compraVenta.getVehiculo();
        compraVenta.setPrecioTotal(Double.parseDouble(""+Math.round(vehiculo.getPrecio() * 1.21*100))/100);
        compraVenta.setFechaVenta(LocalDate.now().plusDays(1));
        return compraVentaRepository.save(compraVenta);
    }

    /**
     * Get all the compraVentas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompraVenta> findAll(Pageable pageable) {
        log.debug("Request to get all CompraVentas");
        return compraVentaRepository.findAll(pageable);
    }


    /**
     * Get one compraVenta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CompraVenta> findOne(Long id) {
        log.debug("Request to get CompraVenta : {}", id);
        return compraVentaRepository.findById(id);
    }

    /**
     * Delete the compraVenta by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompraVenta : {}", id);
        compraVentaRepository.deleteById(id);
        compraVentaSearchRepository.deleteById(id);
    }

    /**
     * Search for the compraVenta corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompraVenta> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CompraVentas for query {}", query);
        return compraVentaSearchRepository.search(queryStringQuery(query), pageable);    }

      /**
     * Get all the trabajadors.
     *
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TotalVentas> filtrarNumeroVentas() {
        log.debug("Request to get all Trabajadors");
        return compraVentaRepository.searchVehiculosFiltrados();
    }
}
