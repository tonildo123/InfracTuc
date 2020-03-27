package com.example.infractuc;

import android.widget.ImageView;

public class ModeloDenuncia {

    private String infraccion, descricion, patente, vehiculo, fecha, ubicacion, id_infraccion;


    public ModeloDenuncia() {

    }

    public ModeloDenuncia(String infraccion, String descricion,
                          String patente, String vehiculo, String fecha,
                          String ubicacion,String id_infraccion) {
        this.infraccion = infraccion;
        this.descricion = descricion;
        this.patente = patente;
        this.vehiculo = vehiculo;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.id_infraccion = id_infraccion;

    }

    public String getInfraccion() {
        return infraccion;
    }

    public void setInfraccion(String infraccion) {
        this.infraccion = infraccion;
    }

    public String getDescricion() {
        return descricion;
    }

    public void setDescricion(String descricion) {
        this.descricion = descricion;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }



    public String getId_infraccion() {
        return id_infraccion;
    }

    public void setId_infraccion(String id_infraccion) {
        this.id_infraccion = id_infraccion;
    }

    @Override
    public String toString() {
        return "Detalle de la enuncia vial" +
                "\n infraccion          ='" + infraccion +
                "\n descricion          ='" + descricion +
                "\n patente             ='" + patente +
                "\n vehiculo            ='" + vehiculo +
                "\n fecha               ='" + fecha +
                "\n ubicacion           ='" + ubicacion + '}';
    }
}



