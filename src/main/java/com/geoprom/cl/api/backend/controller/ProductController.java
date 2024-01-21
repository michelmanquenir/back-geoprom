package com.geoprom.cl.api.backend.controller;

import com.geoprom.cl.api.backend.helper.ProductHelper;
import com.geoprom.cl.api.backend.models.Products;
import com.geoprom.cl.api.backend.services.Products.IProductServiceImpl;
import com.geoprom.cl.api.backend.services.Products.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(ProductController.class.getSimpleName());

    private final ProductService productService;

    public ProductController(IProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<?> getProducts(@RequestParam(required = false) Long product_id) {
        logger.info("consultando productos: " + product_id);
        Map<String, Object> response = new HashMap<>();
        List<Products> products = productService.getProducts(product_id);
        logger.info("products" + products.size());
        response.put("productos", products);
        response.put("error", 0);
        response.put("message", "Productos obtenidos con exito");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Products> createProduct(@RequestBody Products product) {
        Products newProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @DeleteMapping("/{productId}/soft-delete")
    public ResponseEntity<Void> softDeleteUser(@PathVariable Long productId) {
        productService.softDeleteProduct(productId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/activate/{productId}")
    public ResponseEntity<Void> activateProduct(@PathVariable Long productId) {
        productService.activateProduct(productId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-product/{product_id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long product_id,
            @RequestBody Products product) {
        logger.info("update producto" + product);
        logger.info("product id: " + product_id);
        Map<String, Object> response = new HashMap<>();
        try {
            Products products;
            Products currentProduct = productService.findById(product_id);

            if (currentProduct == null) {
                logger.info("Product does not exist in the database");
                response.put("message", "Error: could not edit the product with Id: "
                        .concat(product_id.toString().concat(" does not exist in database")));
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            logger.info("data update process begins");
            products = ProductHelper.updateProduct(product, currentProduct);

            logger.info("successfully updated");
            response.put("data", products);
            response.put("code", HttpStatus.OK.value());
            response.put("message", "Product successfully updated");

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e){
            response.put("message", "Error to update product");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
