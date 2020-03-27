package com.example.infractuc;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VistaOpciones extends Fragment {


    private Button b_denuncia, b_salir,b_ver_denuncias;
    private TextView texto_de_bienvenida;
    private ImageView imagen_de_facebook;
    private String estado = null;
    private GoogleSignInClient mGoogleSignInClient;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_vista_opciones, container, false);
        b_denuncia = (Button) vista.findViewById(R.id.button_realizar_denuncia);
        b_ver_denuncias = (Button) vista.findViewById(R.id.button_ver_denuncias);
        b_salir = (Button) vista.findViewById(R.id.button_salir);
        texto_de_bienvenida = vista.findViewById(R.id.txt_nombre_de_usuario);
        imagen_de_facebook = vista.findViewById(R.id.imageView2);

        GoogleSignInAccount cuenta = GoogleSignIn.getLastSignedInAccount(getContext()); // Si entra por google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso); // para salir de google

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // por facebook

        if (cuenta!= null && user == null){
            String nombre = cuenta.getDisplayName();
            Uri url_imagen = cuenta.getPhotoUrl();

            texto_de_bienvenida.setText("HOLA " + nombre + " Bienvenido a InfracTuc");
            Glide.with(getContext())
                    .load(String.valueOf(url_imagen))
                    .into(imagen_de_facebook);
             estado ="Con Google";

        } else if (cuenta!= null && user == null){
            String usuario = user.getDisplayName();
            Uri imagen_desde_facebook = user.getPhotoUrl();

            texto_de_bienvenida.setText(usuario);
            Glide.with(getContext())
                    .load(String.valueOf(imagen_desde_facebook))
                    .into(imagen_de_facebook);
             estado ="Con Facebook";
        } else {
            String usuario = "Bienvenido a infracTuc, ya puede realizar denuncias viales!";
            texto_de_bienvenida.setText(usuario);
            estado ="Con Email";

        }


        b_denuncia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { MenuDEBotones(b_denuncia.getId()); } });
        b_ver_denuncias.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { MenuDEBotones(b_ver_denuncias.getId());}});
        b_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { MenuDEBotones(b_salir.getId());
            }
        });
        return vista;
    }

    public void MenuDEBotones(int botones) {

        switch (botones) {
            case R.id.button_realizar_denuncia:
                LlamarAPredenuncia();
                break;
            case R.id.button_ver_denuncias:
                LlamarAListaDeDenuncias();
                break;
            case R.id.button_salir:
                LlamaraSalir();
                break;
        }
    }
    public void LlamarAPredenuncia() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ic_contenedor,
                new VistaPreDenuncia()).addToBackStack(null).commit();
    }
    public void LlamarAListaDeDenuncias() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ic_contenedor, new VistaVerDenuncias())
                .addToBackStack(null).commit();
    }
    public void LlamaraSalir() {

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ic_contenedor, new VistaLogin())
                .addToBackStack(null).commit();

        // para salir por facebook
        // FirebaseAuth.getInstance().signOut();

        // para salir por google
        /*mGoogleSignInClient.signOut().addOnCanceledListener(getActivity(), new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Toast.makeText(getContext(), "Hasta Pronto", Toast.LENGTH_SHORT).show();

            }
        });*/

    }


}