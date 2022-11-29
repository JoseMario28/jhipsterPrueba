package com.mycompany.myapp.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Vehiculo.
 */
@Entity
@Table(name = "vehiculo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "vehiculo")
public class Vehiculo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "marca")
    private String marca;

    @Column(name = "km")
    private Float km;

    @Column(name = "anno")
    private LocalDate anno;

    @Column(name = "precio")
    private Float precio;

    @Column(name = "patente")
    private String patente;


    //---------------------------------------------------------------------------------------------
    @Column(name="usado")
    private Boolean usado;




    public Boolean getUsado() {
        return usado;
    }

    public void setUsado(Boolean usado) {
        this.usado = usado;
    }

    //---------------------------------------------------------------------------------------------

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public Vehiculo modelo(String modelo) {
        this.modelo = modelo;
        return this;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public Vehiculo marca(String marca) {
        this.marca = marca;
        return this;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Float getKm() {
        return km;
    }

    public Vehiculo km(Float km) {
        this.km = km;
        return this;
    }

    public void setKm(Float km) {
        this.km = km;
    }

    public LocalDate getAnno() {
        return anno;
    }

    public Vehiculo anno(LocalDate anno) {
        this.anno = anno;
        return this;
    }

    public void setAnno(LocalDate anno) {
        this.anno = anno;
    }

    public Float getPrecio() {
        return precio;
    }

    public Vehiculo precio(Float precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public String getPatente() {
        return patente;
    }

    public Vehiculo patente(String patente) {
        this.patente = patente;
        return this;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vehiculo)) {
            return false;
        }
        return id != null && id.equals(((Vehiculo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
            "id=" + getId() +
            ", modelo='" + getModelo() + "'" +
            ", marca='" + getMarca() + "'" +
            ", km=" + getKm() +
            ", anno='" + getAnno() + "'" +
            ", precio=" + getPrecio() +
            ", patente='" + getPatente() + "'" +
            "}";
    }
}
