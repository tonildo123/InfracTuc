package com.example.infractuc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;

public class VistaLogin extends Fragment {
    private Button b_facebook;
    private Button b_google;
    private Button b_login;
    private Button b_registrarme;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_vista_login, container, false);
        this.b_facebook = (Button) vista.findViewById(R.id.boton_facebook);
        this.b_google = (Button) vista.findViewById(R.id.boton_google);
        this.b_login = (Button) vista.findViewById(R.id.boton_login);
        this.b_registrarme = (Button) vista.findViewById(R.id.boton_registrarme);
        this.b_facebook.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                VistaLogin.this.LlamarAOpciopnes();
            }
        });
        this.b_google.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                VistaLogin.this.LlamarAOpciopnes();
            }
        });
        this.b_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                VistaLogin.this.LlamarAOpciopnes();
            }
        });
        this.b_registrarme.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                VistaLogin.this.LlamarARegistrarme();
            }
        });
        return vista;
    }

    public void LlamarAOpciopnes() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ic_contenedor, new VistaOpciones()).addToBackStack((String) null).commit();
    }

    public void LlamarARegistrarme() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ic_contenedor, new VistaRegistro()).addToBackStack((String) null).commit();
    }
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       