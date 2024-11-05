package com.geoprom.cl.api.backend.models.Request.Productos;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateProductoRequest {

    private Long id;
    private String nombre;
    private String rut_empresa;
    private String sku;
    private BigDecimal precio_compra;
    private BigDecimal precio_venta;
    private Integer stock;
    private String urlImg;
    private Integer estado;
    private Long id_categoria;
}
