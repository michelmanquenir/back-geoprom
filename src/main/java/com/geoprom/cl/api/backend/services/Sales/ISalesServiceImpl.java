package com.geoprom.cl.api.backend.services.Sales;

import com.geoprom.cl.api.backend.Repository.SalesRepository;
import com.geoprom.cl.api.backend.models.Request.Sale.CreateSaleRequest;
import com.geoprom.cl.api.backend.models.Sales;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ISalesServiceImpl implements SalesService {
    private final Logger logger = LoggerFactory.getLogger(ISalesServiceImpl.class.getSimpleName());

    private final SalesRepository salesRepository;


    public ISalesServiceImpl(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    @Override
    public List<Sales> getSales(Long sale_id) {
        logger.info("sale_id:" + sale_id);
        if (sale_id != null) {
            Sales sales = salesRepository.findById(sale_id).orElse(null);
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
    public Sales createSale(Sales sales) {

        logger.info(sales.toString());




       // return salesRepository.save(sales);
        return null;
    }
}
