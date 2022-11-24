package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CompraVenta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CompraVenta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompraVentaRepository extends JpaRepository<CompraVenta, Long> {

}
