package com.geoprom.cl.api.backend.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "users")
@Where(clause = "status = 1")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 45, nullable = false)
    private String name;

    @Column(name = "last_name", length = 45, nullable = false)
    private String last_name;

    @Column(name = "rut", length = 10, nullable = false, unique = true)
    private String rut;

    @Column(name = "email", length = 65, nullable = false, unique = true)
    private String email;

    @Column(name = "address", length = 255, nullable = false)
    private String address;

    @Column(name = "fecha_nac", nullable = false)
    private Date fecha_nac;

    @Column(name = "status", nullable = false)
    @ColumnDefault("1")
    private int status;

    public void softDelete() {
        this.status = 0;
    }

    @Column(name = "telefono", length = 20, nullable = false)
    private String telefono;

    @Column(name = "phone", length = 64, nullable = false)
    private String phone;

    @Column(name = "password", length = 64)
    private String password;

    @Column(name = "perfil")
    private Integer perfil;
}
