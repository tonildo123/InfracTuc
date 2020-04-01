package com.example.infractuc;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.UUID;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class VistaEnviar_y_detalleDeDenuncia extends Fragment {

    private TextView confirmo_petente, confirmo_ubicacion, confirmo_infraccion, confirmo_fecha, confirmo_vehiculo, confirmo_descripcion;
    private ImageView confirmo_contexto;
    private Button confirmo_enviar_a_firebase;
    private String id_infraccion=null;

    private DatabaseReference databaseReference;

    private String Base_de_Datos = "InfracTuc";
    String foto_de_el_contexto = null;


    // para subr imagen a firebase
    private StorageReference storageReference;
    private Uri imguri;
    public static final String FB_Storage_Path = "image/";
    public static final String FB_Database_Path = "image";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =inflater.inflate(R.layout.fragment_vista_enviar_y_detalle_de_denuncia, container, false);
         inicializarFirebase();
        inicializarFirebaseStorage();



        confirmo_ubicacion = vista.findViewById(R.id.txt_confirmo_ubicacion);
        confirmo_petente = vista.findViewById(R.id.txt_confirmo_patente);
        confirmo_descripcion = vista.findViewById(R.id.txt_confirmo_descripcion);
        confirmo_infraccion = vista.findViewById(R.id.txt_confirmo_infraccion);
        confirmo_vehiculo = vista.findViewById(R.id.txt_confirmo_vehiculo);
        confirmo_fecha = vista.findViewById(R.id.txt_fecha_y_hora);
        confirmo_contexto = vista.findViewById(R.id.imagen_confirmo_denuncia);
        confirmo_enviar_a_firebase = vista.findViewById(R.id.button_confirmar_denuncia);

        Bundle data = this.getArguments();
        if(data != null){
            String id_infraccion_string = data.getString("ID_INFRACCION");
            String ubicacion = data.getString("Lugar");
            String vehiculo = data.getString("Vehiculo");
            String hora = data.getString("Hora");
            String fecha = data.getString("Fecha");
            String infraccion = data.getString("Infraccion");
            String matricula = data.getString("Patente");
            String descripcion = data.getString("Descripcion");
            foto_de_el_contexto = data.getString("FotoContexto");

            id_infraccion = id_infraccion_string;
            confirmo_descripcion.setText(descripcion);
            confirmo_infraccion.setText(infraccion);
            confirmo_vehiculo.setText(vehiculo);
            confirmo_fecha.setText(fecha + "   |  " +hora);
            confirmo_ubicacion.setText(ubicacion);
            confirmo_petente.setText(matricula);

            StringToBitMap(foto_de_el_contexto, confirmo_contexto);

        }


        confirmo_enviar_a_firebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EnviemosTodoAFirebase(id_infraccion,confirmo_petente, confirmo_ubicacion,
                        confirmo_infraccion, confirmo_fecha, confirmo_vehiculo, confirmo_descripcion,foto_de_el_contexto);
            }
        });
        return vista;
    }

    public void inicializarFirebaseStorage() {
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(FB_Database_Path);
    }

    private void EnviemosTodoAFirebase(String confirmo_id_infraccion, TextView confirmo_petente, TextView confirmo_ubicacion,
                                       TextView confirmo_infraccion, TextView confirmo_fecha,
                                       TextView confirmo_vehiculo, TextView confirmo_descripcion, String contexto
                                       ) {
        String id_infraccion = confirmo_id_infraccion;
        String patente = confirmo_petente.getText().toString();
        String ubicacion = confirmo_ubicacion.getText().toString();
        String infraccion = confirmo_infraccion.getText().toString();
        String fecha= confirmo_fecha.getText().toString();
        String vehiculo = confirmo_vehiculo.getText().toString();
        String descripcion = confirmo_descripcion.getText().toString();


        ModeloDenuncia infra = new ModeloDenuncia();
        infra.setId_infraccion(id_infraccion);
        infra.setPatente(patente);
        infra.setUbicacion(ubicacion);
        infra.setInfraccion(infraccion);
        infra.setFecha(fecha);
        infra.setVehiculo(vehiculo);
        infra.setDescripcion(descripcion);
        infra.setContexto(contexto);

        GuradarFotoEnFirebase(id_infraccion);
        databaseReference.child(Base_de_Datos).child(id_infraccion).setValue(infra);
        Toast.makeText(getApplicationContext(), "Se agrego infraccion con la patente " + patente +
                " a nuestra base de datos", Toast.LENGTH_LONG).show();


        }

    public  void GuradarFotoEnFirebase(final String dealId) {
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setTitle("Cargando imagen!");
        dialog.show();
        StorageReference ref = storageReference.child(FB_Storage_Path + dealId );
        confirmo_contexto.setDrawingCacheEnabled(true);
        confirmo_contexto.buildDrawingCache();
        Bitmap bitmap = confirmo_contexto.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = ref.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                dialog.dismiss();
                Toast.makeText(getContext(), "Imagen cargada en firebase ", Toast.LENGTH_SHORT).show();

            }
        });


    }



    public void StringToBitMap(String encodedString, ImageView confirmo_contexto) {
        // Incase you're storing into aws or other places where we have extension stored in the starting.
        String imageDataBytes = encodedString.substring(encodedString.indexOf(",")+1);

        InputStream stream = new ByteArrayInputStream(Base64.decode(imageDataBytes.getBytes(), Base64.DEFAULT));

        Bitmap bitmap = BitmapFactory.decodeStream(stream);
        confirmo_contexto.setImageBitmap(bitmap);
//        GuradarFotoEnFirebase(bitmap, null);

        //uploadFile(bitmap, null);
    }

    public void inicializarFirebase() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }



}
