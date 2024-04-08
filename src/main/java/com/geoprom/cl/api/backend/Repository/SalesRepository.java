package com.geoprom.cl.api.backend.Repository;

import com.geoprom.cl.api.backend.models.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<Sales, Long> {
}
