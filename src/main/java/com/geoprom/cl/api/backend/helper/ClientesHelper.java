package com.geoprom.cl.api.backend.helper;

import com.geoprom.cl.api.backend.models.Clientes;
import com.geoprom.cl.api.backend.models.Request.Clientes.UpdateClienteRequest;
import com.geoprom.cl.api.backend.services.Clientes.ClientesService;
import com.geoprom.cl.api.backend.services.Users.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ClientesHelper {
    private static final Logger logger = LoggerFactory.getLogger(ClientesHelper.class.getSimpleName());

    private final ClientesService clientesService;

    public ClientesHelper(ClientesService clientesService) {
        this.clientesService = clientesService;
    }


    public void updateCliente(UpdateClienteRequest updateClienteRequest, Clientes currentCliente) {
        logger.info("Start updateProduct");

        // Actualizar solo los campos no nulos de product en currentProduct
        if (updateClienteRequest.getNombre() != null) {
            currentCliente.setNombre(updateClienteRequest.getNombre());
        }
        if (updateClienteRequest.getApellido() != null) {
            currentCliente.setApellido(updateClienteRequest.getApellido());
        }
        if (updateClienteRequest.getRut() != null) {
            currentCliente.setRut(updateClienteRequest.getRut());
        }
        if (updateClienteRequest.getEmail() != null) {
            currentCliente.setEmail(updateClienteRequest.getEmail());
        }
        if (updateClienteRequest.getDireccion() != null) {
            currentCliente.setDireccion(updateClienteRequest.getDireccion());
        }
        if (updateClienteRequest.getFecha_nac() != null) {
            currentCliente.setFecha_nac(updateClienteRequest.getFecha_nac());
        }
        if (updateClienteRequest.getEstado() != null) {
            currentCliente.setEstado(updateClienteRequest.getEstado());
        }
        if (updateClienteRequest.getTelefono() != null) {
            currentCliente.setTelefono(updateClienteRequest.getTelefono());
        }
        if (updateClienteRequest.getPerfil() != null) {
            currentCliente.setPerfil(updateClienteRequest.getPerfil());
        }
        if (updateClienteRequest.getContrasena() != null) {
            currentCliente.setContrasena(updateClienteRequest.getContrasena());
        }
        if (updateClienteRequest.getUrlImg() != null) {
            currentCliente.setUrlImg(updateClienteRequest.getUrlImg());
        }
        clientesService.save(currentCliente);
    }
}
