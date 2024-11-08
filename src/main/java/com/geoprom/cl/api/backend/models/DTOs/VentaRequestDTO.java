package com.geoprom.cl.api.backend.models.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class VentaRequestDTO {

    private BigDecimal total_ganancia;
    private BigDecimal total_venta;
    private Short metodo_pago;
    private Long id_usuario;  // ID del usuario que realiza la venta
    private Long id_cliente;  // ID del cliente
    private Short estado;
    private List<DetalleVentaDTO> detalles;
}
