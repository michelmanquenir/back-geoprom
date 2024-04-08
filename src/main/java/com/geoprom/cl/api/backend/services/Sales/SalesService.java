package com.geoprom.cl.api.backend.services.Sales;

import com.geoprom.cl.api.backend.models.Request.Sale.CreateSaleRequest;
import com.geoprom.cl.api.backend.models.Sales;

import java.util.List;

public interface SalesService {

    List<Sales> getSales(Long sale_id);

    Sales createSale(Sales sales);
}
