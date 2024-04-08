package com.geoprom.cl.api.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "sales_detail")
public class sales_detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_sale")
    private Long id_sale;

    @Column(name = "id_product")
    private Long id_product;

    @Column(name = "id_user")
    private Long id_user;

    @Column(name = "updated_at")
    private Timestamp updated_at;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp created_at;

}
