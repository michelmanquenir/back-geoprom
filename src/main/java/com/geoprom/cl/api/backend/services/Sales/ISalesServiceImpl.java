package com.geoprom.cl.api.backend.services.Sales;

import com.geoprom.cl.api.backend.Repository.SalesDetailRepository;
import com.geoprom.cl.api.backend.Repository.SalesRepository;
import com.geoprom.cl.api.backend.models.Ventas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ISalesServiceImpl implements SalesService {
    private final Logger logger = LoggerFactory.getLogger(ISalesServiceImpl.class.getSimpleName());

    private final SalesRepository salesRepository;

   private final SalesDetailRepository salesDetailRepository;

    public ISalesServiceImpl(SalesRepository salesRepository,
                             SalesDetailRepository salesDetailRepository) {
        this.salesRepository = salesRepository;
        this.salesDetailRepository = salesDetailRepository;
    }

    @Override
    public List<Ventas> getSales(Long sale_id) {
        logger.info("sale_id:" + sale_id);
        if (sale_id != null) {
            Ventas sales = salesRepository.findById(sale_id).orElse(null);
            if (sales != null) {
                return Collections.singletonList(sales);
            } else {
                return Collections.emptyList();
            }
        } else {
            return salesRepository.findAll();
        }
    }

    @Override
    public Ventas createSale(Ventas sale) {

        return null;
    }
}
