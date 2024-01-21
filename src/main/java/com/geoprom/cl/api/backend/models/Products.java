package com.geoprom.cl.api.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "products")
@Where(clause = "status = 1")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "rut_company")
    private String rutCompany;

    private String sku;

    @Column(name = "purchase_price")
    private Integer purchasePrice;

    @Column(name = "purchase_sell")
    private Integer purchaseSell;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "url_img")
    private String urlImg;

    @Column(name = "status")
    private Integer status;
}
