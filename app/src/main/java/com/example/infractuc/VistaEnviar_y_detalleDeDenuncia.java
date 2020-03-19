package com.example.infractuc;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class VistaEnviar_y_detalleDeDenuncia extends Fragment {

    private TextView confirmo_petente, confirmo_ubicacion, confirmo_infraccion, confirmo_fecha, confirmo_vehiculo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =inflater.inflate(R.layout.fragment_vista_enviar_y_detalle_de_denuncia, container, false);

        confirmo_ubicacion = vista.findViewById(R.id.txt_confirmo_ubicacion);
        confirmo_petente = vista.findViewById(R.id.txt_confirmo_patente);
        confirmo_infraccion = vista.findViewById(R.id.txt_confirmo_infraccion);
        confirmo_vehiculo = vista.findViewById(R.id.txt_confirmo_vehiculo);
        confirmo_fecha = vista.findViewById(R.id.txt_fecha_y_hora);

        Bundle data = this.getArguments();
        if(data != null){
            String ubicacion = data.getString("Lugar");
            String vehiculo = data.getString("Vehiculo");
            String hora = data.getString("Hora");
            String fecha = data.getString("Fecha");
            String infraccion = data.getString("Infraccion");
            String patente = data.getString("Patente");
            confirmo_petente.setText(patente);
            confirmo_infraccion.setText(infraccion);
            confirmo_vehiculo.setText(vehiculo);
            confirmo_fecha.setText(fecha + " |  | " +hora);
            confirmo_ubicacion.setText(ubicacion);

        }



        return vista;
    }


}
