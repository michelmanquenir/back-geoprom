package com.geoprom.cl.api.backend.helper;

import com.geoprom.cl.api.backend.models.Products;
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
    public static Products updateProduct(Products product, Products currentProduct) {
        logger.info("Start updateProduct");

        Products newProducts = new Products();
        BeanUtils.copyProperties(currentProduct, newProducts);

        // Actualizar solo los campos no nulos de product
        if (product.getId() != null) {
            newProducts.setId(product.getId());
        }
        if (product.getName() != null) {
            newProducts.setName(product.getName());
        }
        if (product.getRutCompany() != null) {
            newProducts.setRutCompany(product.getRutCompany());
        }
        if (product.getSku() != null) {
            newProducts.setSku(product.getSku());
        }
        if (product.getPurchase_price() != null) {
            newProducts.setPurchase_price(product.getPurchase_price());
        }
        if (product.getPurchase_sell() != null) {
            newProducts.setPurchase_sell(product.getPurchase_sell());
        }
        if (product.getStock() != null) {
            newProducts.setStock(product.getStock());
        }
        if (product.getUrlImg() != null) {
            newProducts.setUrlImg(product.getUrlImg());
        }
        if (product.getStatus() != null) {
            newProducts.setStatus(product.getStatus());
        }
        return productsService.save(newProducts);
    }
}
