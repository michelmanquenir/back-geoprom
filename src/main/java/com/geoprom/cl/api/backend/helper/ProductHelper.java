package com.geoprom.cl.api.backend.helper;

import com.geoprom.cl.api.backend.Repository.CategoriasRepository;
import com.geoprom.cl.api.backend.models.Categorias;
import com.geoprom.cl.api.backend.models.DTOs.ProductoDTO;
import com.geoprom.cl.api.backend.models.Productos;
import com.geoprom.cl.api.backend.models.Request.Productos.UpdateProductoRequest;
import com.geoprom.cl.api.backend.services.Products.ProductsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductHelper {
    private static final Logger logger = LoggerFactory.getLogger(ProductHelper.class.getSimpleName());

    private final ProductsService productsService;
    private final CategoriasRepository categoriasRepository;

    public ProductHelper(ProductsService productsService,
                         CategoriasRepository categoriasRepository) {
        this.productsService = productsService;
        this.categoriasRepository = categoriasRepository;
    }

    public void updateProduct(UpdateProductoRequest product, Productos currentProduct) {
        logger.info("Start updateProduct");

        // Actualizar solo los campos no nulos de product en currentProduct
        if (product.getNombre() != null) {
            currentProduct.setNombre(product.getNombre());
        }
        if (product.getRut_empresa() != null) {
            currentProduct.setRut_empresa(product.getRut_empresa());
        }
        if (product.getSku() != null) {
            currentProduct.setSku(product.getSku());
        }
        if (product.getPrecio_compra() != null) {
            currentProduct.setPrecio_compra(product.getPrecio_compra());
        }
        if (product.getPrecio_venta() != null) {
            currentProduct.setPrecio_venta(product.getPrecio_venta());
        }
        if (product.getStock() != null) {
            currentProduct.setStock(product.getStock());
        }
        if (product.getUrlImg() != null) {
            currentProduct.setUrlImg(product.getUrlImg());
        }
        if (product.getEstado() != null) {
            currentProduct.setEstado(product.getEstado());
        }
        if (product.getId_categoria() != null) {
            // Buscar la categoría por ID y asignarla al producto
            Optional<Categorias> categoria = categoriasRepository.findById(product.getId_categoria());
            categoria.ifPresent(currentProduct::setCategoria);
        }

        // Pasar el producto actualizado al servicio
        productsService.save(currentProduct);
    }
}
