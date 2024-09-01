package com.geoprom.cl.api.backend.services.Sales;

import com.geoprom.cl.api.backend.models.Ventas;

import java.util.List;

public interface SalesService {

    List<Ventas> getSales(Long sale_id);

    Ventas createSale(Ventas sales);
}
