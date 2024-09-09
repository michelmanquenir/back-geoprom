package com.geoprom.cl.api.backend.controller;

import com.geoprom.cl.api.backend.Repository.CategoriasRepository;
import com.geoprom.cl.api.backend.models.Categorias;
import com.geoprom.cl.api.backend.services.Categorias.CategoriasService;
import com.geoprom.cl.api.backend.services.Products.ProductsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CategoriasController {
    private final Logger logger = LoggerFactory.getLogger(CategoriasController.class.getSimpleName());

    private final CategoriasService categoriasService;

    public CategoriasController(CategoriasService categoriasService) {
        this.categoriasService = categoriasService;
    }

    @GetMapping("/categorias")
    public ResponseEntity<?> getCategorias(
            @RequestParam(required = false) Long categoria_id
    ) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Categorias> categorias = categoriasService.getCategorias(categoria_id);
            logger.info("categorias" + categorias.size());
            response.put("categorias", categorias);
            response.put("error", 0);
            response.put("code", HttpStatus.OK.value());
            response.put("message", "Categorias obtenidas con exito");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            response.put("message", "Error en la busqueda de categorias");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
