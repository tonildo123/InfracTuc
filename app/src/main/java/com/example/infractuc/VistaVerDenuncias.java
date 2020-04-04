package com.example.infractuc;


import android.app.ProgressDialog;
import android.content.Context;

import android.os.Bundle;
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

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


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

    ArrayList<ModeloDenuncia> lista_de_denuncias = new ArrayList();


    AdaptadorDenuncias clase_adapatador_denuncias;
    Context context;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_vista_ver_denuncias, container, false);
        campoConsulta =  vista.findViewById(R.id.et_consulta_patente);
        consulta =  vista.findViewById(R.id.boton_consulta_filtro);
        list_denunciass = vista.findViewById(R.id.listaVistaFiltro);
        progressDialog = new ProgressDialog(getContext());
        inicializarFirebase();
        solicitarDatosFirebase();


        consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SolcitarDatosFirebase();// para los datos comunes
            }
        });

        return vista;
    }

    private void SolcitarDatosFirebase() {// solo para mostrar datos en lista NO imagenes
        databaseReference.child(Base_de_Datos).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                lista_de_denuncias.clear();   // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                    ModeloDenuncia p = objSnaptshot.getValue(ModeloDenuncia.class);
                    lista_de_denuncias.add(p);
                    arrayAdapterDenuncia = new ArrayAdapter<ModeloDenuncia>(getActivity(),
                            android.R.layout.simple_list_item_1, lista_de_denuncias);
                    list_denunciass.setAdapter(arrayAdapterDenuncia);

                }
                progressDialog.dismiss();
                Toast.makeText(getActivity(),
                        "Listado Actualizado databaseREALTIME!", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(null, "Failed to read value.", error.toException());
                progressDialog.dismiss();
                Toast.makeText(getContext(),"ERROR", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void solicitarDatosFirebase(){
        progressDialog.setMessage("Cargando contenido...");
        progressDialog.show();

        databaseReference.child(Base_de_Datos).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                lista_de_denuncias.clear();   // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                    ModeloDenuncia p = new ModeloDenuncia();


                    p.setInfraccion(objSnaptshot.getValue(ModeloDenuncia.class).getInfraccion());
                    p.setDescripcion(objSnaptshot.getValue(ModeloDenuncia.class).getDescripcion());
                    p.setPatente(objSnaptshot.getValue(ModeloDenuncia.class).getPatente());
                    p.setVehiculo(objSnaptshot.getValue(ModeloDenuncia.class).getVehiculo());
                    p.setFecha(objSnaptshot.getValue(ModeloDenuncia.class).getFecha());
                    p.setUbicacion(objSnaptshot.getValue(ModeloDenuncia.class).getUbicacion());
                    p.setUrl_imagen(objSnaptshot.getValue(ModeloDenuncia.class).getUrl_imagen());

                    lista_de_denuncias.add(p);
                    clase_adapatador_denuncias = new AdaptadorDenuncias(getContext(), lista_de_denuncias);
                    list_denunciass.setAdapter(clase_adapatador_denuncias);

                }
                progressDialog.dismiss();
                Toast.makeText(getActivity(),
                        "Listado Actualizado", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(null, "Failed to read value.", error.toException());
                progressDialog.dismiss();
                Toast.makeText(getContext(),"ERROR", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void inicializarFirebase() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }


}

