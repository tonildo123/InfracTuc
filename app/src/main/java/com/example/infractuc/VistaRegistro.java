package com.example.infractuc;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class VistaRegistro extends Fragment {

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private Button b_registrarme;
    private EditText txt_mail, txt_password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =  inflater.inflate(R.layout.fragment_vista_registro, container, false);
        //inicializamos el objeto firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        b_registrarme = vista.findViewById(R.id.button_registrarme_registro);
        txt_mail = vista.findViewById(R.id.campo_email_registro);
        txt_password = vista.findViewById(R.id.campo_password_registro);

        progressDialog = new ProgressDialog(getContext());

        b_registrarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistrarmeEnFirebase();
            }
        });

        return vista;
    }

    public void RegistrarmeEnFirebase() {

//Obtenemos el email y la contraseña desde las cajas de texto
        String email = txt_mail.getText().toString();
        String password = txt_password.getText().toString();

        //Verificamos que las cajas de texto no esten vacías
        if (TextUtils.isEmpty(email)) {//(precio.equals(""))
            Toast.makeText(getActivity(), "Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Realizando registro en linea...");
        progressDialog.show();
        //registramos un nuevo usuario
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Se ha registrado el usuario con el email: "
                                    + txt_mail.getText()
                                    + "\n Puede Volver e ingresar con sus datos registrados !"
                                    , Toast.LENGTH_LONG).show();
                            limpiarCampos();
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                                Toast.makeText(getActivity(), "Ese usuario ya existe, REINTENTAR "
                                                            , Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "No se pudo registrar el usuario, INTENTE EN UNOS MINUTOS!"
                                                            , Toast.LENGTH_LONG).show();

                            }
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    private void limpiarCampos() {
        txt_mail.setText("");
        txt_password.setText("");


    }

}
