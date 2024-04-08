package com.geoprom.cl.api.backend.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "sales")
@Where(clause = "status = 1")
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_profit")
    private BigDecimal total_profit;

    @Column(name = "total_sale")
    private BigDecimal total_sale;

    @Column(name = "payment_method")
    private Short payment_method;

    // Relación Many-to-One con la tabla User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    // Relación Many-to-One con la tabla Product
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Products product;

    @Column(name = "status")
    private Long status;

    @Column(name = "updated_at")
    private Timestamp updated_at;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp created_at;

}