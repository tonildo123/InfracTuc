package com.example.infractuc;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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


        ArrayAdapter<CharSequence> adapter_vehiculo = ArrayAdapter.createFromResource(getActivity(),
                R.array.tipodevehiculo, android.R.layout.simple_spinner_item);
        adapter_vehiculo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner_vehiculo.setAdapter(adapter_vehiculo);

        ArrayAdapter<CharSequence> adapter_infraccion = ArrayAdapter.createFromResource(getActivity(),
                R.array.tipodeifraccion, android.R.layout.simple_spinner_item);
        adapter_infraccion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnr_infraccion.setAdapter(adapter_infraccion);


        return vista;
    }
}