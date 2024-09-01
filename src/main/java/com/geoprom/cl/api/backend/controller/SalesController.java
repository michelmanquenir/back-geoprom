package com.geoprom.cl.api.backend.controller;

import com.geoprom.cl.api.backend.models.Ventas;
import com.geoprom.cl.api.backend.services.Sales.SalesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sales")
public class SalesController {
    private final Logger logger = LoggerFactory.getLogger(SalesController.class.getSimpleName());

    private final SalesService salesService;

    public SalesController(SalesService salesService){
        this.salesService = salesService;
    }

    @GetMapping
    public ResponseEntity<?> getSales(
            @RequestParam(required = false) Long sale_id) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Ventas> sales = salesService.getSales(sale_id);
            if (sales == null){
                logger.info("Sales does not exist in the database");
                response.put("message", "Error: could not edit the sale with Id: "
                        .concat(sale_id.toString().concat(" does not exist in database")));
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.put("sales", sales);
            response.put("error", 0);
            response.put("message", "Successfully achieved sales");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            response.put("message", "error when searching sales");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/sell")
    public ResponseEntity<Ventas> createSale(@RequestBody Ventas sale) {
        Ventas newSale = salesService.createSale(sale);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSale);
    }
}
