package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Trabajador.
 */
@Entity
@Table(name = "trabajador")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "trabajador")
public class Trabajador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "dni")
    private Integer dni;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "cargo")
    private String cargo;

    @OneToMany(mappedBy = "trabajador")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CompraVenta> vendedors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDni() {
        return dni;
    }

    public Trabajador dni(Integer dni) {
        this.dni = dni;
        return this;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public Trabajador nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Trabajador apellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCargo() {
        return cargo;
    }

    public Trabajador cargo(String cargo) {
        this.cargo = cargo;
        return this;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Set<CompraVenta> getVendedors() {
        return vendedors;
    }

    public Trabajador vendedors(Set<CompraVenta> compraVentas) {
        this.vendedors = compraVentas;
        return this;
    }

    public Trabajador addVendedor(CompraVenta compraVenta) {
        this.vendedors.add(compraVenta);
        compraVenta.setTrabajador(this);
        return this;
    }

    public Trabajador removeVendedor(CompraVenta compraVenta) {
        this.vendedors.remove(compraVenta);
        compraVenta.setTrabajador(null);
        return this;
    }

    public void setVendedors(Set<CompraVenta> compraVentas) {
        this.vendedors = compraVentas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trabajador)) {
            return false;
        }
        return id != null && id.equals(((Trabajador) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Trabajador{" +
            "id=" + getId() +
            ", dni=" + getDni() +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", cargo='" + getCargo() + "'" +
            "}";
    }
}
