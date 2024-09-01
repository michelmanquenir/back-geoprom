package com.geoprom.cl.api.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Entity
@Table(name = "productos")
public class Productos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "rut_empresa")
    private String rut_empresa;

    @Column(name = "sku")
    private String sku;

    @Column(name = "precio_compra")
    private Integer precio_compra;

    @Column(name = "precio_venta")
    private Integer precio_venta;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "url_img")
    private String urlImg;

    @Column(name = "estado")
    private Integer estado;
}
