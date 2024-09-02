package com.geoprom.cl.api.backend.services.Ventas;

import com.geoprom.cl.api.backend.models.DTOs.VentaRequestDTO;
import com.geoprom.cl.api.backend.models.Ventas;

import java.util.List;

public interface VentasService {

    List<Ventas> getSales(Long sale_id);

    Ventas crearVenta(VentaRequestDTO ventaRequest);
}
