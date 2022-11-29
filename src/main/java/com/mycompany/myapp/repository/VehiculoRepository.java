package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Vehiculo;

import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Vehiculo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    @Query(value = "Select v from Vehiculo v where v.marca = ?1" )
    Page<Vehiculo> searchVehiculosFiltrados(String marca, Pageable pageable);



}
