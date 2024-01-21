package com.geoprom.cl.api.backend.services.Products;
import com.geoprom.cl.api.backend.Repository.ProductRepository;
import com.geoprom.cl.api.backend.models.Products;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class IProductServiceImpl implements ProductService {
    private final Logger logger = LoggerFactory.getLogger(IProductServiceImpl.class.getSimpleName());

    private final ProductRepository productRepository;

    public IProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Products> getProducts(Long product_id) {
        logger.info("product_id:" + product_id);
        if (product_id != null) {
            Products products = productRepository.findById(product_id).orElse(null);
            if (products != null) {
                return Collections.singletonList(products);
            } else {
                return Collections.emptyList();
            }
        } else {
            return productRepository.findAll();
        }
    }

    @Transactional
    public Products createProduct(Products product) {
        return productRepository.save(product);
    }

    @Transactional
    public void softDeleteProduct(Long product_id) {
        productRepository.softDelete(product_id);
    }

    @Transactional
    public void activateProduct(Long product_id) {
        productRepository.activateProduct(product_id);
    }

    @Override
    public Products save(Products product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Products findById(Long product_id) {
        return productRepository.findById(product_id).orElse(null);
    }
}
