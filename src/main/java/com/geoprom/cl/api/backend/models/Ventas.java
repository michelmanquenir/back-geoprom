package com.geoprom.cl.api.backend.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "ventas")
public class Ventas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "total_ganancia")
    private BigDecimal total_ganancia;

    @Column(name = "total_venta")
    private BigDecimal total_venta;

    @Column(name = "metodo_pago")
    private Short metodo_pago;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuarios user;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Clientes cliente;

    @Column(name = "estado", nullable = false)
    private Short estado;

    @Column(name = "updated_at")
    private Timestamp updated_at;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp created_at;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<DetalleVentas> detalleVentas;
}