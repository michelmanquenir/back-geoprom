package com.geoprom.cl.api.backend.services.SalesDetail;

import com.geoprom.cl.api.backend.Repository.SalesDetailRepository;
import com.geoprom.cl.api.backend.models.DetalleVentas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ISalesDetailServiceImpl implements SalesDetailService {
    private final Logger logger = LoggerFactory.getLogger(ISalesDetailServiceImpl.class.getSimpleName());

    private final SalesDetailRepository salesDetailRepository;

    public ISalesDetailServiceImpl(SalesDetailRepository salesDetailRepository) {
        this.salesDetailRepository = salesDetailRepository;
    }

    public List<DetalleVentas> getAllSalesDetails() {
        return salesDetailRepository.findAll();
    }
}
