package com.example.infractuc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdaptadorDenuncias extends BaseAdapter {


    Context context;
    ArrayList<ModeloDenuncia> infraccioones;
    LayoutInflater inflater;

    public AdaptadorDenuncias (Context context, ArrayList<ModeloDenuncia> infraccioones) {
        this.context = context;
        this.infraccioones = infraccioones;


    }
    @Override
    public int getCount(){
    return infraccioones.size();

    }
    @Override
    public  Object getItem(int posicion) {
        return infraccioones.get(posicion);
    }
    @Override
    public  long  getItemId(int posicion) {
        return posicion;
    }
    public class Holder
    {
        TextView aqui_infraccion, aqui_vehiculo, aqui_patente, aqui_fecha, aqui_descripcion, aqui_lugar;
        ImageView img;
    }
    @Override
    public View getView(final int posicion, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        Holder holder=new Holder();

        View fila;

        fila = inflater.inflate(R.layout.modelo_denuncia, parent, false);

        holder.aqui_infraccion  = fila.findViewById(R.id.aqui_infraccion);
        holder.aqui_vehiculo    = fila.findViewById(R.id.aqui_vehiculo);
        holder.aqui_patente     = fila.findViewById(R.id.aqui_patente);
        holder.aqui_fecha       = fila.findViewById(R.id.aqui_fecha);
        holder.aqui_descripcion = fila.findViewById(R.id.aqui_descripcion);
        holder.aqui_lugar       = fila.findViewById(R.id.aqui_lugar);
        holder.img              = fila.findViewById(R.id.aqui_imagen);

        holder.img             .setImageBitmap(infraccioones.get(posicion).getImagen_del_contexto());
        holder.aqui_infraccion .setText       (infraccioones.get(posicion).getInfraccion());
        holder.aqui_vehiculo   .setText       (infraccioones.get(posicion).getVehiculo());
        holder.aqui_patente    .setText       (infraccioones.get(posicion).getPatente());
        holder.aqui_fecha      .setText       (infraccioones.get(posicion).getFecha());
        holder.aqui_descripcion.setText       (infraccioones.get(posicion).getDescripcion());
        holder.aqui_lugar      .setText       (infraccioones.get(posicion).getUbicacion());
       // ConvertFormat.StringTOBitmap(infraccioones.get(posicion).getContexto(),holder.img);

        return fila;
    }
}
