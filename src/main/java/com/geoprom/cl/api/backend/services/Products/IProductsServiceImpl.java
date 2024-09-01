package com.geoprom.cl.api.backend.services.Products;
import com.geoprom.cl.api.backend.Repository.ProductsRepository;
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

    private final ProductsRepository productsRepository;

    public IProductsServiceImpl(ProductsRepository productRepository) {
        this.productsRepository = productRepository;
    }

    public List<Productos> getProducts(Long product_id) {
        logger.info("product_id:" + product_id);
        if (product_id != null) {
            Productos products = productsRepository.findById(product_id).orElse(null);
            if (products != null) {
                return Collections.singletonList(products);
            } else {
                return Collections.emptyList();
            }
        } else {
            return productsRepository.findAll();
        }
    }

    @Transactional
    public Productos createProduct(Productos product) {
        return productsRepository.save(product);
    }

    @Transactional
    public void softDeleteProduct(Long product_id) {
        productsRepository.softDelete(product_id);
    }

    @Transactional
    public void activateProduct(Long product_id) {
        productsRepository.activateProduct(product_id);
    }

    @Override
    public Productos save(Productos product) {
        return productsRepository.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Productos findById(Long product_id) {
        return productsRepository.findById(product_id).orElse(null);
    }
}