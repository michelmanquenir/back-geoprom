package com.geoprom.cl.api.backend.controller;

import com.geoprom.cl.api.backend.helper.ProductHelper;
import com.geoprom.cl.api.backend.models.Products;
import com.geoprom.cl.api.backend.services.Products.IProductsServiceImpl;
import com.geoprom.cl.api.backend.services.Products.ProductsService;
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
public class ProductsController {
    private final Logger logger = LoggerFactory.getLogger(ProductsController.class.getSimpleName());

    private final ProductsService productsService;

    public ProductsController(ProductsService productService) {
        this.productsService = productService;
    }

    @GetMapping
    public ResponseEntity<?> getProducts(@RequestParam(required = false) Long product_id) {
        logger.info("consultando productos: " + product_id);
        Map<String, Object> response = new HashMap<>();

        try {
            List<Products> products = productsService.getProducts(product_id);
            logger.info("products" + products.size());
            response.put("products", products);
            response.put("error", 0);
            response.put("code", HttpStatus.OK.value());
            response.put("message", "Productos obtenidos con exito");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            response.put("message", "error when searching users");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Products> createProduct(@RequestBody Products product) {
        Products newProduct = productsService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @DeleteMapping("/{productId}/soft-delete")
    public ResponseEntity<?> softDeleteUser(@PathVariable Long productId) {
        Map<String, Object> response = new HashMap<>();

        productsService.softDeleteProduct(productId);
        response.put("message", "Cpf has been successfully eliminated");
        response.put("code", HttpStatus.OK.value());
        response.put("error", 0);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/activate/{productId}")
    public ResponseEntity<Void> activateProduct(@PathVariable Long productId) {
        productsService.activateProduct(productId);
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
            Products currentProduct = productsService.findById(product_id);

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
            response.put("error", 0);
            response.put("message", "Product successfully updated");

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e){
            response.put("message", "Error to update product");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
