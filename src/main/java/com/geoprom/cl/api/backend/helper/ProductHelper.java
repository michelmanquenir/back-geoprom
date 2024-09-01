package com.geoprom.cl.api.backend.helper;

import com.geoprom.cl.api.backend.models.Productos;
import com.geoprom.cl.api.backend.services.Products.ProductsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProductHelper {
    private static final Logger logger = LoggerFactory.getLogger(ProductHelper.class.getSimpleName());

    private static ProductsService productsService;

    public ProductHelper(ProductsService productsService) {
        ProductHelper.productsService = productsService;
    }
    public static Productos updateProduct(Productos product, Productos currentProduct) {
        logger.info("Start updateProduct");

        Productos newProducts = new Productos();
        BeanUtils.copyProperties(currentProduct, newProducts);

        // Actualizar solo los campos no nulos de product
        if (product.getId() != null) {
            newProducts.setId(product.getId());
        }
        if (product.getNombre() != null) {
            newProducts.setNombre(product.getNombre());
        }
        if (product.getRut_empresa() != null) {
            newProducts.setRut_empresa(product.getRut_empresa());
        }
        if (product.getSku() != null) {
            newProducts.setSku(product.getSku());
        }
        if (product.getPrecio_compra() != null) {
            newProducts.setPrecio_compra(product.getPrecio_compra());
        }
        if (product.getPrecio_venta() != null) {
            newProducts.setPrecio_venta(product.getPrecio_venta());
        }
        if (product.getStock() != null) {
            newProducts.setStock(product.getStock());
        }
        if (product.getUrlImg() != null) {
            newProducts.setUrlImg(product.getUrlImg());
        }
        if (product.getEstado() != null) {
            newProducts.setEstado(product.getEstado());
        }
        return productsService.save(newProducts);
    }
}
