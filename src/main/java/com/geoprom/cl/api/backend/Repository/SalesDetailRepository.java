package com.geoprom.cl.api.backend.Repository;

import com.geoprom.cl.api.backend.models.DetalleVentas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesDetailRepository extends JpaRepository<DetalleVentas, Long> {
}
