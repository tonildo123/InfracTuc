package com.example.infractuc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.infractuc.Views.VistaLogin;

public class MainActivity extends AppCompatActivity {

    VistaLogin login = new VistaLogin();
    FragmentManager adminstrar_vistas = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        llamarLogin();
    }

    public void llamarLogin() {
        adminstrar_vistas.beginTransaction().replace(R.id.ic_contenedor, login)
                .commit();
    }
}
