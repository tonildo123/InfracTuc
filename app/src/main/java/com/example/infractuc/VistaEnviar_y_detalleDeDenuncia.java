package com.example.infractuc;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class VistaEnviar_y_detalleDeDenuncia extends Fragment {

    private TextView confirmo_petente, confirmo_ubicacion, confirmo_infraccion, confirmo_fecha, confirmo_vehiculo, confirmo_descripcion;
    private ImageView confirmo_contexto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =inflater.inflate(R.layout.fragment_vista_enviar_y_detalle_de_denuncia, container, false);

        confirmo_ubicacion = vista.findViewById(R.id.txt_confirmo_ubicacion);
        confirmo_petente = vista.findViewById(R.id.txt_confirmo_patente);
        confirmo_descripcion = vista.findViewById(R.id.txt_confirmo_descripcion);
        confirmo_infraccion = vista.findViewById(R.id.txt_confirmo_infraccion);
        confirmo_vehiculo = vista.findViewById(R.id.txt_confirmo_vehiculo);
        confirmo_fecha = vista.findViewById(R.id.txt_fecha_y_hora);
        confirmo_contexto = vista.findViewById(R.id.imagen_confirmo_denuncia);

        Bundle data = this.getArguments();
        if(data != null){
            String ubicacion = data.getString("Lugar");
            String vehiculo = data.getString("Vehiculo");
            String hora = data.getString("Hora");
            String fecha = data.getString("Fecha");
            String infraccion = data.getString("Infraccion");
            String descripcion = data.getString("Descripcion");
            String foto_de_el_contexto = data.getString("FotoContexto");


            confirmo_descripcion.setText(descripcion);
            confirmo_infraccion.setText(infraccion);
            confirmo_vehiculo.setText(vehiculo);
            confirmo_fecha.setText(fecha + "   |  " +hora);
            confirmo_ubicacion.setText(ubicacion);

            StringToBitMap(foto_de_el_contexto, confirmo_contexto);


        }



        return vista;
    }

    public void StringToBitMap(String encodedString, ImageView confirmo_contexto) {
        // Incase you're storing into aws or other places where we have extension stored in the starting.
        String imageDataBytes = encodedString.substring(encodedString.indexOf(",")+1);

        InputStream stream = new ByteArrayInputStream(Base64.decode(imageDataBytes.getBytes(), Base64.DEFAULT));

        Bitmap bitmap = BitmapFactory.decodeStream(stream);
        confirmo_contexto.setImageBitmap(bitmap);

    }


}
