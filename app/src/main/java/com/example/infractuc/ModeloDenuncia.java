package com.example.infractuc;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class ModeloDenuncia {

    private String infraccion, descripcion, patente, contexto, vehiculo, fecha, ubicacion, id_infraccion;


    public ModeloDenuncia() {

    }

    public ModeloDenuncia(String infraccion, String descripcion,
                          String patente, String contexto, String vehiculo, String fecha,
                          String ubicacion,String id_infraccion) {

        this.infraccion = infraccion;
        this.descripcion = descripcion;
        this.patente = patente;
        this.contexto = contexto;
        this.vehiculo = vehiculo;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.id_infraccion = id_infraccion;

    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }



    public String getInfraccion() {
        return infraccion;
    }

    public void setInfraccion(String infraccion) {
        this.infraccion = infraccion;
    }


    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getContexto() {
        return contexto;
    }

    public void setContexto(String contexto) {
        this.contexto = contexto;
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








}



