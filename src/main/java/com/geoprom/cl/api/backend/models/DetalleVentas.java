package com.geoprom.cl.api.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "detalle_ventas")
public class DetalleVentas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_venta", nullable = false)
    @JsonBackReference
    private Ventas sale;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Productos product;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false)
    private BigDecimal precio_unitario;

    @Column(name = "subtotal", nullable = false)
    private BigDecimal subtotal;

    @Column(name = "updated_at")
    private Timestamp updated_at;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp created_at;

}
