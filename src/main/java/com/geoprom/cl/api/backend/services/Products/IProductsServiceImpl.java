package com.geoprom.cl.api.backend.services.Products;
import com.geoprom.cl.api.backend.Repository.ProductosRepository;
import com.geoprom.cl.api.backend.models.Productos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class IProductsServiceImpl implements ProductsService {
    private final Logger logger = LoggerFactory.getLogger(IProductsServiceImpl.class.getSimpleName());

    private final ProductosRepository productosRepository;

    public IProductsServiceImpl(ProductosRepository productRepository) {
        this.productosRepository = productRepository;
    }

    public List<Productos> getProducts(Long product_id) {
        logger.info("product_id:" + product_id);
        if (product_id != null) {
            Productos products = productosRepository.findById(product_id).orElse(null);
            if (products != null) {
                return Collections.singletonList(products);
            } else {
                return Collections.emptyList();
            }
        } else {
            return productosRepository.findAll();
        }
    }

    @Transactional
    public Productos createProduct(Productos product) {
        return productosRepository.save(product);
    }

    @Transactional
    public void softDeleteProduct(Long product_id) {
        productosRepository.softDelete(product_id);
    }

    @Transactional
    public void activateProduct(Long product_id) {
        productosRepository.activateProduct(product_id);
    }

    @Override
    public Productos save(Productos product) {
        return productosRepository.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Productos findById(Long product_id) {
        return productosRepository.findById(product_id).orElse(null);
    }
}
