package com.mycompany.myapp.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Comision.
 */
@Entity
@Table(name = "comision")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "comision")
public class Comision implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "rango")
    private Integer rango;

    @Column(name = "porcentaje")
    private Double porcentaje;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Comision nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getRango() {
        return rango;
    }

    public Comision rango(Integer rango) {
        this.rango = rango;
        return this;
    }

    public void setRango(Integer rango) {
        this.rango = rango;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public Comision porcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
        return this;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comision)) {
            return false;
        }
        return id != null && id.equals(((Comision) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Comision{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", rango=" + getRango() +
            ", porcentaje=" + getPorcentaje() +
            "}";
    }
}
