package com.example.infractuc;

import android.widget.ImageView;

public class ModeloDenuncia {

    private String lugar, fecha_y_hora,tipo_de_infraccion, tipo_de_vehiculo, patente;
    private ImageView imagen;

    public ModeloDenuncia() {

    }

    public ModeloDenuncia(String lugar, String fecha_y_hora, String tipo_de_infraccion, String tipo_de_vehiculo, String patente, ImageView imagen) {
        this.lugar = lugar;
        this.fecha_y_hora = fecha_y_hora;
        this.tipo_de_infraccion = tipo_de_infraccion;
        this.tipo_de_vehiculo = tipo_de_vehiculo;
        this.patente = patente;
        this.imagen = imagen;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getFecha_y_hora() {
        return fecha_y_hora;
    }

    public void setFecha_y_hora(String fecha_y_hora) {
        this.fecha_y_hora = fecha_y_hora;
    }

    public String getTipo_de_infraccion() {
        return tipo_de_infraccion;
    }

    public void setTipo_de_infraccion(String tipo_de_infraccion) {
        this.tipo_de_infraccion = tipo_de_infraccion;
    }

    public String getTipo_de_vehiculo() {
        return tipo_de_vehiculo;
    }

    public void setTipo_de_vehiculo(String tipo_de_vehiculo) {
        this.tipo_de_vehiculo = tipo_de_vehiculo;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public ImageView getImagen() {
        return imagen;
    }

    public void setImagen(ImageView imagen) {
        this.imagen = imagen;
    }


    @Override
    public String toString() {
        return "Detalles de la Denuncia Vial/ Infraccion \n" +
                "\n lugar              : " + lugar +
                "\n Fecha y Hora       : " + fecha_y_hora +
                "\n Tipo de Infraccion : " + tipo_de_infraccion +
                "\n Vehiculo Infractor : " + tipo_de_vehiculo +
                "\n Patente            : " + patente +
                "\n Foto de Contexto   : " + imagen;
    }
}



