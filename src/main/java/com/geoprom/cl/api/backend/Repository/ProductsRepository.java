package com.geoprom.cl.api.backend.Repository;

import com.geoprom.cl.api.backend.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductsRepository extends JpaRepository<Products, Long> {

    @Modifying
    @Query("UPDATE Products u SET u.status = 0 WHERE u.id = :id")
    void softDelete(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Products u SET u.status = 1 WHERE u.id = :productId")
    void activateProduct(@Param("productId") Long productId);
}