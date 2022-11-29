package com.mycompany.myapp.domain;


public class TotalVentas {

    long idTrabajador;
    long total;

    public long getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(long idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long totalVentas) {
        this.total = totalVentas;
    }

    public TotalVentas(long idTrabajador, long totalVentas) {
        this.idTrabajador = idTrabajador;
        this.total = totalVentas;
    }

    public TotalVentas() {
    }

}
