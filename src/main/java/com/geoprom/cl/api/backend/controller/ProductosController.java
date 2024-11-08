package com.geoprom.cl.api.backend.controller;

import com.geoprom.cl.api.backend.helper.ProductHelper;
import com.geoprom.cl.api.backend.models.DTOs.ProductoDTO;
import com.geoprom.cl.api.backend.models.Productos;
import com.geoprom.cl.api.backend.models.Request.Productos.UpdateProductoRequest;
import com.geoprom.cl.api.backend.models.Response.Productos.ResponseDTO;
import com.geoprom.cl.api.backend.services.Products.ProductsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProductosController {
    private final Logger logger = LoggerFactory.getLogger(ProductosController.class.getSimpleName());

    private final ProductsService productsService;
    private final ProductHelper productHelper;

    public ProductosController(ProductsService productService,
                               ProductHelper productHelper) {
        this.productsService = productService;
        this.productHelper = productHelper;
    }

    @GetMapping("/productos")
    public ResponseEntity<?> getProducts(@RequestParam(required = false) Long product_id) {
        logger.info("consultando productos: " + product_id);
        Map<String, Object> response = new HashMap<>();

        try {
            List<Productos> productos = productsService.getProducts(product_id);
            logger.info("products" + productos.size());
            response.put("productos", productos);
            response.put("error", 0);
            response.put("code", HttpStatus.OK.value());
            response.put("message", "Productos obtenidos con exito");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            response.put("message", "Error en la busqueda de productos");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/crear-producto")
    public ResponseEntity<ResponseDTO> createProduct(
            @RequestPart("producto") ProductoDTO product,
            @RequestPart("file") MultipartFile file) {

        ResponseDTO response = new ResponseDTO();
        try {
            // Manejo del archivo de imagen
            if (!file.isEmpty()) {
                // Verificar si el directorio de subida existe, si no, crearlo
                String uploadDir = "uploads/images/";
                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) {
                    uploadDirFile.mkdirs();
                }

                // Guardar la imagen en el servidor
                String fileName = file.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + fileName);
                Files.write(filePath, file.getBytes());

                // Establecer la URL de la imagen en el DTO
                product.setUrlImg("/uploads/images/" + fileName);
            }

            // Crear el producto con la imagen asignada
            Productos newProduct = productsService.createProduct(product);

            response.setError(0); // Sin errores
            response.setMessage("Producto creado con éxito");
            response.setProduct(newProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            response.setError(1); // Error genérico
            response.setMessage("Ocurrió un error durante la creación del producto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/productos/{productId}/soft-delete")
    public ResponseEntity<?> softDeleteProduct(@PathVariable Long productId) {
        Map<String, Object> response = new HashMap<>();

        productsService.softDeleteProduct(productId);
        response.put("message", "Producto eliminado con exito");
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
            @RequestPart("producto") UpdateProductoRequest updateProductoRequest,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        logger.info("update producto" + updateProductoRequest);
        logger.info("product id: " + product_id);
        Map<String, Object> response = new HashMap<>();
        try {
            Productos currentProduct = productsService.findById(product_id);

            if (currentProduct == null) {
                logger.info("El producto no existe en la base de datos");
                response.put("message", "Error: could not edit the product with Id: "
                        .concat(product_id.toString().concat(" does not exist in database")));
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            logger.info("data update process begins");

            // Si se recibe un archivo (imagen), procesar la actualización de la imagen
            if (file != null && !file.isEmpty()) {
                // Directorio donde se almacenan las imágenes
                String uploadDir = "uploads/images/";
                String existingImagePath = currentProduct.getUrlImg();

                // Si ya existe una imagen, eliminarla
                if (existingImagePath != null && !existingImagePath.isEmpty()) {
                    Path oldImagePath = Paths.get(uploadDir).resolve(existingImagePath).toAbsolutePath();
                    Files.deleteIfExists(oldImagePath); // Elimina la imagen anterior
                }

                // Guardar la nueva imagen
                String newFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path newImagePath = Paths.get(uploadDir).resolve(newFileName).toAbsolutePath();

                // Crear el directorio si no existe
                Path uploadDirectory = newImagePath.getParent();
                if (!Files.exists(uploadDirectory)) {
                    Files.createDirectories(uploadDirectory);
                    System.out.println("Directorio creado: " + uploadDirectory.toString());
                }

                System.out.println("Intentando copiar el archivo a: " + newImagePath.toString());
                Files.copy(file.getInputStream(), newImagePath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Archivo copiado exitosamente a: " + newImagePath.toString());

                // Actualizar la URL de la imagen en el producto
                updateProductoRequest.setUrlImg("/uploads/images/" + newFileName);
            }

            // Actualizar los demás campos del producto
            productHelper.updateProduct(updateProductoRequest, currentProduct);

            logger.info("successfully updated");
            response.put("code", HttpStatus.OK.value());
            response.put("error", 0);
            response.put("message", "Producto actualizado correctamente");

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("message", "Error to update product");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}