package com.geoprom.cl.api.backend.Repository;

import com.geoprom.cl.api.backend.models.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductsRepository extends JpaRepository<Productos, Long> {

    @Modifying
    @Query("UPDATE Productos u SET u.estado = 0 WHERE u.id = :id")
    void softDelete(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Productos u SET u.estado = 1 WHERE u.id = :productId")
    void activateProduct(@Param("productId") Long productId);
}