package com.geoprom.cl.api.backend.services.Products;

import com.geoprom.cl.api.backend.models.Products;

import java.util.List;

public interface ProductsService {

    List<Products> getProducts(Long product_id);

    Products findById(Long product_id);

    Products createProduct(Products product_id);

    void softDeleteProduct(Long product_id);

    void activateProduct(Long product_id);

    Products save(Products products);

}
