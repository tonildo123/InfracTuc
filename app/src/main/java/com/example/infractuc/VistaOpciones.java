package com.example.infractuc;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

import java.net.URI;
import java.net.URL;

public class VistaOpciones extends Fragment {
    private Button b_denuncia, b_salir,b_ver_denuncias;
    private TextView texto_de_bienvenida;
    private ImageView imagen_de_facebook;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_vista_opciones, container, false);
        b_denuncia = (Button) vista.findViewById(R.id.button_realizar_denuncia);
        b_ver_denuncias = (Button) vista.findViewById(R.id.button_ver_denuncias);
        b_salir = (Button) vista.findViewById(R.id.button_salir);
        texto_de_bienvenida = vista.findViewById(R.id.txt_nombre_de_usuario);
        imagen_de_facebook = vista.findViewById(R.id.imageView2);


        // Si entra por google
        GoogleSignInAccount cuenta = GoogleSignIn.getLastSignedInAccount(getContext());
        if(cuenta!= null){
            String nombre = cuenta.getDisplayName();
            Uri url_imagen = cuenta.getPhotoUrl();

            texto_de_bienvenida.setText("HOLA " + nombre + " Bienvenido a InfracTuc");
            Glide.with(getContext())
                    .load(String.valueOf(url_imagen))
                    .into(imagen_de_facebook);
        }


        // si entra por facebook
        Bundle data = this.getArguments();
        if(data != null){
            String usuario = data.getString("Usuario");
            String imagen_desde_facebook = data.getString("Foto");

            texto_de_bienvenida.setText(usuario);
            Glide.with(getContext())
                    .load(imagen_desde_facebook)
                    .into(imagen_de_facebook);
                        }
        
        b_denuncia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LlamarAPredenuncia();
            }
        });
        b_ver_denuncias.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LlamarAListaDeDenuncias();
            }
        });
        b_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LlamaraSalir();
            }
        });
        return vista;
    }

    public void LlamarAPredenuncia() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ic_contenedor,
                new VistaPreDenuncia()).addToBackStack(null).commit();
    }

    public void LlamaraSalir() {
        FirebaseAuth.getInstance().signOut();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ic_contenedor, new VistaLogin())
                .addToBackStack(null).commit();
    }

    public void LlamarAListaDeDenuncias() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ic_contenedor, new VistaVerDenuncias())
                .addToBackStack(null).commit();
    }
}