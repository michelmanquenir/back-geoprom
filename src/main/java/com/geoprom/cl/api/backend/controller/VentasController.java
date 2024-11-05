package com.geoprom.cl.api.backend.controller;

import com.geoprom.cl.api.backend.models.DTOs.VentaRequestDTO;
import com.geoprom.cl.api.backend.models.Ventas;
import com.geoprom.cl.api.backend.services.Ventas.VentasService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class VentasController {
    private final Logger logger = LoggerFactory.getLogger(VentasController.class.getSimpleName());

    private final VentasService ventaService;

    public VentasController(VentasService ventaService){
        this.ventaService = ventaService;
    }

    @GetMapping("/ventas")
    public ResponseEntity<?> getSales(
            @RequestParam(required = false) Long sale_id) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Ventas> sales = ventaService.getSales(sale_id);
            if (sales == null){
                logger.info("Sales does not exist in the database");
                response.put("message", "Error: could not edit the sale with Id: "
                        .concat(sale_id.toString().concat(" does not exist in database")));
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.put("ventas", sales);
            response.put("error", 0);
            response.put("message", "Ventas encontradas con exito");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            response.put("message", "error when searching sales");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/crear-venta")
    public ResponseEntity<?> crearVenta(@RequestBody VentaRequestDTO ventaRequest) {
        try {
            Ventas nuevaVenta = ventaService.crearVenta(ventaRequest);
            return ResponseEntity.ok(nuevaVenta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }
}
