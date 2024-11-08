package com.geoprom.cl.api.backend.models.Request.Usuarios;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class UpdateUsuarioRequest {

    private String nombre;
    private String apellido;
    private String rut;
    private String email;
    private String direccion;
    private Timestamp fecha_nac;
    private Short estado; // 1 para activo, 0 para inactivo
    private String telefono;
    private Integer perfil;
    private String contrasena;
    private String urlImg; // URL de la imagen actual (opcional)

}
