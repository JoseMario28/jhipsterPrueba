package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Trabajador;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Trabajador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrabajadorRepository extends JpaRepository<Trabajador, Long> {

}
