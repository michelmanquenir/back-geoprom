package com.geoprom.cl.api.backend.services.SalesDetail;

import com.geoprom.cl.api.backend.Repository.DetalleVentasRepository;
import com.geoprom.cl.api.backend.models.DetalleVentas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ISalesDetailServiceImpl implements SalesDetailService {
    private final Logger logger = LoggerFactory.getLogger(ISalesDetailServiceImpl.class.getSimpleName());

    private final DetalleVentasRepository detalleVentasRepository;

    public ISalesDetailServiceImpl(DetalleVentasRepository detalleVentasRepository) {
        this.detalleVentasRepository = detalleVentasRepository;
    }

    public List<DetalleVentas> getAllSalesDetails() {
        return detalleVentasRepository.findAll();
    }
}
