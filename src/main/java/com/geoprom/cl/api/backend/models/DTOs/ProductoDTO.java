package com.geoprom.cl.api.backend.models.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoDTO {

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
