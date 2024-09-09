package com.geoprom.cl.api.backend.models.DTOs;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String nombre;
    private String rut_empresa;
    private String sku;
    private Integer precio_compra;
    private Integer precio_venta;
    private Integer stock;
    private String urlImg;
    private Integer estado;
    private Long id_categoria;
}
