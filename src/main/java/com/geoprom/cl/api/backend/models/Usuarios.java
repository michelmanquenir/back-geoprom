package com.geoprom.cl.api.backend.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id_usuario;

    @Column(name = "nombre", length = 45, nullable = false)
    private String nombre;

    @Column(name = "apellido", length = 45, nullable = false)
    private String apellido;

    @Column(name = "rut", length = 10, nullable = false, unique = true)
    private String rut;

    @Column(name = "email", length = 65, nullable = false, unique = true)
    private String email;

    @Column(name = "direccion", length = 255, nullable = false)
    private String direccion;

    @Column(name = "fecha_nac", nullable = false)
    private Date fecha_nac;

    @Column(name = "estado", nullable = false)
    @ColumnDefault("1")
    private int estado;

    @Column(name = "telefono", length = 64, nullable = false)
    private String telefono;

    @Column(name = "contrasena", length = 64)
    private String contrasena;

    @Column(name = "perfil")
    private Integer perfil;
}
