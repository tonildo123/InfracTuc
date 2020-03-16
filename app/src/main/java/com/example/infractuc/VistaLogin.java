package com.example.infractuc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

//import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class VistaLogin extends Fragment {


    private EditText txt_email, txt_password;
    private Button b_facebook;

    private Button b_login;
    private Button b_registrarme;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    private GoogleApiClient googleApiClient;
    private SignInButton signInButton;

    public static final int SIGN_IN_CODE_GOOGLE = 777;
    public static final int SIGN_IN_CODE_FACEBOOK = 888;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_vista_login, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        b_facebook =  vista.findViewById(R.id.boton_facebook);

        b_login =  vista.findViewById(R.id.boton_login);
        b_registrarme =  vista.findViewById(R.id.boton_registrarme);
        txt_email =  vista.findViewById(R.id.campo_mail);
        txt_password =  vista.findViewById(R.id.campo_password);

        signInButton = (SignInButton) vista.findViewById(R.id.signInButton);
        progressDialog = new ProgressDialog(getContext());
        //inicializamos el objeto firebaseAuth

        b_facebook.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LlamarAOpciopnes();
            }
        });






        signInButton.setSize(SignInButton.SIZE_WIDE);

        signInButton.setColorScheme(SignInButton.COLOR_DARK);

        /*
         GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(getContext())
                .enableAutoManage(getActivity(), null)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_CODE_GOOGLE);
            }
        });*/
        b_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                OnClickButtonLogin();
            }
        });
        b_registrarme.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LlamarARegistrarme();
            }
        });
        return vista;
    }

    public void LlamarAOpciopnes() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ic_contenedor,
                new VistaOpciones()).addToBackStack(null).commit();
    }

    public void LlamarARegistrarme() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ic_contenedor,
                new VistaRegistro()).addToBackStack(null).commit();
    }


    public void OnClickButtonLogin(){

        //Obtenemos el email y la contraseña desde las cajas de texto
        final String email = txt_email.getText().toString().trim();
        String password = txt_password.getText().toString().trim();

        //Verificamos que las cajas de texto no esten vacías
        if (TextUtils.isEmpty(email)) {//(precio.equals(""))
            Toast.makeText(getActivity(), "Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Realizando consulta en linea...");
        progressDialog.show();

        //loguear usuario
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {

                            LlamarAOpciopnes();

                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                                Toast.makeText(getActivity(), "Ese usuario ya existe ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "No se pudo identificar el usuario ", Toast.LENGTH_LONG).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });



    }


    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_CODE_GOOGLE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    public void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            Toast.makeText(getContext(), "Bienvenido", Toast.LENGTH_SHORT).show();
            LlamarAOpciopnes();
        } else {
            Toast.makeText(getContext(), "Error al Cotinuar con Google", Toast.LENGTH_SHORT).show();
        }




    }*/








}