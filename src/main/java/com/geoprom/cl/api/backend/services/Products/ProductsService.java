package com.geoprom.cl.api.backend.services.Products;

import com.geoprom.cl.api.backend.models.Productos;

import java.util.List;

public interface ProductsService {

    List<Productos> getProducts(Long product_id);

    Productos findById(Long product_id);

    Productos createProduct(Productos product_id);

    void softDeleteProduct(Long product_id);

    void activateProduct(Long product_id);

    Productos save(Productos products);

}
