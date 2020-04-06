package com.example.infractuc.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.infractuc.R;

public class MiHolder {

    TextView aqui_infraccion, aqui_vehiculo, aqui_patente, aqui_fecha, aqui_descripcion, aqui_lugar;
    ImageView aqui_mi_imagen;
    public  MiHolder(View itemView)
    {
        aqui_infraccion  = itemView.findViewById(R.id.aqui_infraccion);
        aqui_vehiculo    = itemView.findViewById(R.id.aqui_vehiculo);
        aqui_patente     = itemView.findViewById(R.id.aqui_patente);
        aqui_fecha       = itemView.findViewById(R.id.aqui_fecha);
        aqui_descripcion = itemView.findViewById(R.id.aqui_descripcion);
        aqui_lugar       = itemView.findViewById(R.id.aqui_lugar);
        aqui_mi_imagen   = itemView.findViewById(R.id.aqui_imagen);

    }




}
