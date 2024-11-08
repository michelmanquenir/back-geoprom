package com.geoprom.cl.api.backend.controller;


import com.geoprom.cl.api.backend.models.Clientes;
import com.geoprom.cl.api.backend.services.Clientes.ClientesService;
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
public class ClientesController {
    private final Logger logger = LoggerFactory.getLogger(ClientesController.class.getSimpleName());

    private final ClientesService clientesService;

    public ClientesController(ClientesService clientesService){
        this.clientesService = clientesService;
    }


    @GetMapping("/clientes")
    public ResponseEntity<?> getUsers(@RequestParam(required = false) Long id_cliente) {
        Map<String, Object> response = new HashMap<>();

        List<Clientes> clientes = clientesService.getClientes(id_cliente);
        logger.info("clientes" + clientes.size());
        response.put("clientes", clientes);
        response.put("error", 0);
        response.put("message", "Clientes obtenidos con exito");
        response.put("code", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
