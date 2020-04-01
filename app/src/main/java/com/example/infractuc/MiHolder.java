package com.example.infractuc;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MiHolder {

    TextView aqui_infraccion, aqui_vehiculo, aqui_patente, aqui_fecha, aqui_descripcion, aqui_lugar;
    ImageView img;
    public  MiHolder(View itemView)
    {
        aqui_infraccion  = itemView.findViewById(R.id.aqui_infraccion);
        aqui_vehiculo    = itemView.findViewById(R.id.aqui_vehiculo);
        aqui_patente     = itemView.findViewById(R.id.aqui_patente);
        aqui_fecha       = itemView.findViewById(R.id.aqui_fecha);
        aqui_descripcion = itemView.findViewById(R.id.aqui_descripcion);
        aqui_lugar       = itemView.findViewById(R.id.aqui_lugar);
        img              = itemView.findViewById(R.id.aqui_imagen);

    }




}
