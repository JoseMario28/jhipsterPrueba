package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CompraVenta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.mycompany.myapp.domain.TotalVentas;
import java.util.List;

/**
 * Spring Data  repository for the CompraVenta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompraVentaRepository extends JpaRepository<CompraVenta, Long> {

    @Query("SELECT new com.mycompany.myapp.domain.TotalVentas(c.trabajador.id, COUNT(c)) FROM CompraVenta c WHERE c.trabajador is not null group by c.trabajador")
    List<TotalVentas> searchVehiculosFiltrados();


}
