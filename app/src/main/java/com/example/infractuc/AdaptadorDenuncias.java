package com.example.infractuc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


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

    @Override
    public View getView(final int posicion, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        //if(){}

        if (inflater== null)
        {
            inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } if(convertView==null)
        {
            convertView = inflater.inflate(R.layout.modelo_denuncia, parent, false);

        }


        MiHolder holder=new MiHolder(convertView);


        holder.aqui_infraccion .setText(infraccioones.get(posicion).getInfraccion());
        holder.aqui_vehiculo   .setText(infraccioones.get(posicion).getVehiculo());
        holder.aqui_patente    .setText(infraccioones.get(posicion).getPatente());
        holder.aqui_fecha      .setText(infraccioones.get(posicion).getFecha());
        holder.aqui_descripcion.setText(infraccioones.get(posicion).getDescripcion());
        holder.aqui_lugar      .setText(infraccioones.get(posicion).getUbicacion());

        ClaseConverter.StringTOBitmap(context, infraccioones.get(posicion).getUrl_imagen(),holder.aqui_mi_imagen);

        return convertView;
    }




}
