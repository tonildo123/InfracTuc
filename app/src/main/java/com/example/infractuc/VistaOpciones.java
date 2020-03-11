package com.example.infractuc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;

public class VistaOpciones extends Fragment {
    private Button b_denuncia;
    private Button b_ver_denuncias;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_vista_opciones, container, false);
        this.b_denuncia = (Button) vista.findViewById(R.id.button_realizar_denuncia);
        this.b_ver_denuncias = (Button) vista.findViewById(R.id.button_ver_denuncias);
        this.b_denuncia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                VistaOpciones.this.LlamarAPredenuncia();
            }
        });
        this.b_ver_denuncias.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                VistaOpciones.this.LlamarAListaDeDenuncias();
            }
        });
        return vista;
    }

    public void LlamarAPredenuncia() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ic_contenedor, new VistaCapturaFotografia()).commit();
    }

    public void LlamarAListaDeDenuncias() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ic_contenedor, new VistaVerDenuncias()).commit();
    }
}