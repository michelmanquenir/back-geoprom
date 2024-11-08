package com.geoprom.cl.api.backend.models.Response.SalesDetail;

import com.geoprom.cl.api.backend.models.Productos;
import com.geoprom.cl.api.backend.models.Ventas;
import com.geoprom.cl.api.backend.models.Usuarios;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class SalesDetailDTO {

    private Long id;
    private LocalDate fecha;
    private BigDecimal total;
    // ... otros campos de la venta
    private Usuarios usuario;
    private List<Productos> productos;
    private List<Ventas> ventas;
}
