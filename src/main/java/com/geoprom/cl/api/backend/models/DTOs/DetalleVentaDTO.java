package com.geoprom.cl.api.backend.models.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class DetalleVentaDTO {

    private Long id_producto;
    private Integer cantidad;
    private BigDecimal precio_unitario;
    private BigDecimal subtotal;
}
