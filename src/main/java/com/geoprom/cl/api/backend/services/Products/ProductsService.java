package com.geoprom.cl.api.backend.services.Products;

import com.geoprom.cl.api.backend.models.DTOs.ProductoDTO;
import com.geoprom.cl.api.backend.models.Productos;
import com.geoprom.cl.api.backend.models.Request.Productos.UpdateProductoRequest;

import java.util.List;

public interface ProductsService {

    List<Productos> getProducts(Long product_id);

    Productos findById(Long product_id);

    Productos createProduct(ProductoDTO product);

    void softDeleteProduct(Long product_id);

    void activateProduct(Long product_id);

    void save(Productos product);  // Se cambia a Productos en lugar de ProductoDTO
}
