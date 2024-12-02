package com.geoprom.cl.api.backend.models.Request.Clientes;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class UpdateClienteRequest {

    private String nombre;
    private String apellido;
    private String rut;
    private String email;
    private String direccion;
    private Timestamp fecha_nac;
    private Short estado;
    private String telefono;
    private Integer perfil;
    private String contrasena;
    private String urlImg;
}