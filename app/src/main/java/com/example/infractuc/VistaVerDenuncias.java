package com.example.infractuc;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class VistaVerDenuncias extends Fragment {

    private String Base_de_Datos = "InfracTuc";
    ArrayAdapter<ModeloDenuncia> arrayAdapterDenuncia;

    private  EditText campoConsulta;
    private Button consulta;
    DatabaseReference databaseReference;

    private ListView list_denunciass;

    private List<ModeloDenuncia> lista_de_denuncias = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_vista_ver_denuncias, container, false);
        campoConsulta =  vista.findViewById(R.id.et_consulta_patente);
        consulta =  vista.findViewById(R.id.boton_consulta_filtro);
        list_denunciass = vista.findViewById(R.id.listaVistaFiltro);
        inicializarFirebase();

        consulta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //buscarMateria(campoConsulta.getText().toString());
                solicitarDatosFirebase();
            }
        });


        return vista;
    }

    public void solicitarDatosFirebase(){
        databaseReference.child(Base_de_Datos).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                lista_de_denuncias.clear();   // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                    ModeloDenuncia p = objSnaptshot.getValue(ModeloDenuncia.class);
                    lista_de_denuncias.add(p);

                    arrayAdapterDenuncia = new ArrayAdapter<ModeloDenuncia>(getContext(),
                            android.R.layout.simple_list_item_1, lista_de_denuncias);
                    list_denunciass.setAdapter(arrayAdapterDenuncia);
                }

                Toast.makeText(getApplicationContext(),
                        "Listado Actualizado", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(null, "Failed to read value.", error.toException());
                Toast.makeText(getApplicationContext(),"ERROR", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void inicializarFirebase() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }


}
