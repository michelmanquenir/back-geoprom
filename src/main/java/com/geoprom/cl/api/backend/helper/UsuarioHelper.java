package com.geoprom.cl.api.backend.helper;

import com.geoprom.cl.api.backend.models.Request.Usuarios.UpdateUsuarioRequest;
import com.geoprom.cl.api.backend.models.Usuarios;
import com.geoprom.cl.api.backend.services.Users.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UsuarioHelper {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioHelper.class.getSimpleName());

    private final UserService userService;

    public UsuarioHelper(UserService userService) {
        this.userService = userService;
    }


    public void updateUsuario(UpdateUsuarioRequest updateUsuarioRequest, Usuarios currentUser) {
        logger.info("Start updateProduct");

        // Actualizar solo los campos no nulos de product en currentProduct
        if (updateUsuarioRequest.getNombre() != null) {
            currentUser.setNombre(updateUsuarioRequest.getNombre());
        }
        if (updateUsuarioRequest.getApellido() != null) {
            currentUser.setApellido(updateUsuarioRequest.getApellido());
        }
        if (updateUsuarioRequest.getRut() != null) {
            currentUser.setRut(updateUsuarioRequest.getRut());
        }
        if (updateUsuarioRequest.getEmail() != null) {
            currentUser.setEmail(updateUsuarioRequest.getEmail());
        }
        if (updateUsuarioRequest.getDireccion() != null) {
            currentUser.setDireccion(updateUsuarioRequest.getDireccion());
        }
        if (updateUsuarioRequest.getFecha_nac() != null) {
            currentUser.setFecha_nac(updateUsuarioRequest.getFecha_nac());
        }
        if (updateUsuarioRequest.getEstado() != null) {
            currentUser.setEstado(updateUsuarioRequest.getEstado());
        }
        if (updateUsuarioRequest.getTelefono() != null) {
            currentUser.setTelefono(updateUsuarioRequest.getTelefono());
        }
        if (updateUsuarioRequest.getPerfil() != null) {
            currentUser.setPerfil(updateUsuarioRequest.getPerfil());
        }
        if (updateUsuarioRequest.getContrasena() != null) {
            currentUser.setContrasena(updateUsuarioRequest.getContrasena());
        }
        if (updateUsuarioRequest.getUrlImg() != null) {
            currentUser.setUrlImg(updateUsuarioRequest.getUrlImg());
        }
        // Pasar el producto actualizado al servicio
        userService.save(currentUser);
    }

}



