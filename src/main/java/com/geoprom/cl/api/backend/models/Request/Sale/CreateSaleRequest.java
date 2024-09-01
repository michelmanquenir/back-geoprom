package com.geoprom.cl.api.backend.models.Request.Sale;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

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

    @Column(name = "estado")
    private Long estado;

    @Column(name = "updated_at")
    private Timestamp updated_at;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp created_at;

    public BigDecimal getTotal_profit() {
        return total_profit;
    }

    public void setTotal_profit(BigDecimal total_profit) {
        this.total_profit = total_profit;
    }

    public BigDecimal getTotal_sale() {
        return total_sale;
    }

    public void setTotal_sale(BigDecimal total_sale) {
        this.total_sale = total_sale;
    }

    public Short getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(Short payment_method) {
        this.payment_method = payment_method;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
