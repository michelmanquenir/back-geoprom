package com.geoprom.cl.api.backend.controller;

import com.geoprom.cl.api.backend.models.DetalleVentas;
import com.geoprom.cl.api.backend.services.SalesDetail.SalesDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SalesDetailController {
    private final Logger logger = LoggerFactory.getLogger(SalesDetailController.class.getSimpleName());

    private final SalesDetailService salesDetailService;

    @Autowired
    public SalesDetailController(SalesDetailService salesDetailService) {
        this.salesDetailService = salesDetailService;
    }


    @GetMapping("/detalle-venta")
    public ResponseEntity<?> getAllSalesDetails() {
        try {
            Map<String, Object> response = new HashMap<>();
            List<DetalleVentas> salesDetails = salesDetailService.getAllSalesDetails();
            response.put("data", salesDetails);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener los detalles de la venta.");
        }
    }

}
