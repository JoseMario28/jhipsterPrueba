package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "compra_venta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "compraventa")
public class CompraVenta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "fecha_venta")
    private LocalDate fechaVenta;

    @Column(name = "garantia")
    private Integer garantia;

    @Column(name = "precio_total")
    private Double precioTotal;

    @ManyToOne
    @JsonIgnoreProperties("compraVentas")
    private Cliente cliente;

    @OneToOne
    @JoinColumn(unique = true)
    private Vehiculo vehiculo;

    @ManyToOne
    @JsonIgnoreProperties("compraVentas")
    private Trabajador trabajador;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public CompraVenta fechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
        return this;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Integer getGarantia() {
        return garantia;
    }

    public CompraVenta garantia(Integer garantia) {
        this.garantia = garantia;
        return this;
    }

    public void setGarantia(Integer garantia) {
        this.garantia = garantia;
    }

    public Double getPrecioTotal() {
        return precioTotal;
    }

    public CompraVenta precioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
        return this;
    }

    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public CompraVenta cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public CompraVenta vehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
        return this;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public CompraVenta trabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
        return this;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompraVenta)) {
            return false;
        }
        return id != null && id.equals(((CompraVenta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CompraVenta{" +
            "id=" + getId() +
            ", fechaVenta='" + getFechaVenta() + "'" +
            ", garantia=" + getGarantia() +
            ", precioTotal=" + getPrecioTotal() +
            "}";
    }
}
