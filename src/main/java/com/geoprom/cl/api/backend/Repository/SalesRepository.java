package com.geoprom.cl.api.backend.Repository;

import com.geoprom.cl.api.backend.models.Ventas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<Ventas, Long> {
}
