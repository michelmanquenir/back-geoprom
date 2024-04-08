package com.geoprom.cl.api.backend.models.Request.Sale;


import com.geoprom.cl.api.backend.models.Products;
import com.geoprom.cl.api.backend.models.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@Table(name = "sales")
public class CreateSaleRequest {

    @Column(name = "total_profit")
    private BigDecimal total_profit;

    @Column(name = "total_sale")
    private BigDecimal total_sale;

    @Column(name = "payment_method")
    private Short payment_method;

    @Column(name = "user_id", nullable = false)
    private Long user_id;

    @Column(name = "product_id", nullable = false)
    private Long product_id;

    @Column(name = "status")
    private Long status;

    @Column(name = "updated_at")
    private Timestamp updated_at;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp created_at;
}
