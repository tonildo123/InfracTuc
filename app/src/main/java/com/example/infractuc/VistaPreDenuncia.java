package com.example.infractuc;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Environment;
import android.os.FileObserver;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class VistaPreDenuncia extends Fragment {

    private Spinner spiner_vehiculo, spinnr_infraccion;
    private Button b_siguiente;
    private EditText txt_patente, txt_ubicacion;
    private ImageView imagen_cartel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =  inflater.inflate(R.layout.fragment_vista_pre_denuncia, container, false);
        spiner_vehiculo = vista.findViewById(R.id.spinner);
        spinnr_infraccion = vista.findViewById(R.id.spinner2);
        b_siguiente = vista.findViewById(R.id.boton_continuar);
        txt_ubicacion = vista.findViewById(R.id.campo_ubicacion);
        txt_patente = vista.findViewById(R.id.campo_patente);

        ArrayAdapter<CharSequence> adapter_vehiculo = ArrayAdapter.createFromResource(getActivity(),
                R.array.tipodevehiculo, android.R.layout.simple_spinner_item);
        adapter_vehiculo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner_vehiculo.setAdapter(adapter_vehiculo);

        ArrayAdapter<CharSequence> adapter_infraccion = ArrayAdapter.createFromResource(getActivity(),
                R.array.tipodeifraccion, android.R.layout.simple_spinner_item);
        adapter_infraccion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnr_infraccion.setAdapter(adapter_infraccion);


        b_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatosAEnviarADenuncia();

            }
        });

        return vista;
    }

    public void DatosAEnviarADenuncia() {


        VistaEnviar_y_detalleDeDenuncia vistaEnviar_y_detalleDeDenuncia = new VistaEnviar_y_detalleDeDenuncia();

        String patente_a_enviar = txt_patente.getText().toString();
        String ubicacion_a_enviar = txt_ubicacion.getText().toString();
        String infraccion_a_enviar = spinnr_infraccion.getSelectedItem().toString();
        String vehiculo_a_enviar = spiner_vehiculo.getSelectedItem().toString();
        Date date = new Date();
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        String hora = hourFormat.format(date);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = dateFormat.format(date);

        Bundle enviar = new Bundle();

        enviar.putString("Patente",patente_a_enviar);
        enviar.putString("Lugar",ubicacion_a_enviar);
        enviar.putString("Infraccion",infraccion_a_enviar);
        enviar.putString("Vehiculo",vehiculo_a_enviar);
        enviar.putString("Hora",hora);
        enviar.putString("Fecha",fecha);

        vistaEnviar_y_detalleDeDenuncia.setArguments(enviar);
        getActivity().getSupportFragmentManager().beginTransaction().
                replace(R.id.ic_contenedor, vistaEnviar_y_detalleDeDenuncia).
                addToBackStack(null).commit();
    }
}