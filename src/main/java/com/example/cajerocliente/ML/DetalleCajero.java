package com.example.cajerocliente.ML;

public class DetalleCajero {

    private int idDetalleCajero;
    private Cajero cajero;
    private Denominacion denominacion;
    private int cantidad;

    public int getIdDetalleCajero() {
        return idDetalleCajero;
    }

    public void setIdDetalleCajero(int idDetalleCajero) {
        this.idDetalleCajero = idDetalleCajero;
    }

    public Cajero getCajero() {
        return cajero;
    }

    public void setCajero(Cajero cajero) {
        this.cajero = cajero;
    }

    public Denominacion getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(Denominacion denominacion) {
        this.denominacion = denominacion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
