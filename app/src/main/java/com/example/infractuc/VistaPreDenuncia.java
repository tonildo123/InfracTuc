package com.example.infractuc;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class VistaPreDenuncia extends Fragment {
    VistaEnviar_y_detalleDeDenuncia vistaEnviar_y_detalleDeDenuncia = new VistaEnviar_y_detalleDeDenuncia();

    private final int PERMISSIONS = 100;
    private LinearLayout linearView;
    private static String CARPETA_RAIZ = "myInfraTucApp";
    private static String CARPETA_de_IMAGENES = "Imagenes";
    private static String RUTA_IMAGEN = CARPETA_RAIZ + CARPETA_de_IMAGENES;
    private String phat;


    private Spinner spiner_vehiculo, spinnr_infraccion;
    private Button b_siguiente, b_capture_context,b_capture_patente;
    private EditText txt_ubicacion,txt_descripcion;
    private ImageView  imagen_contexto, imagen_patente;
    private TextView texto_patente;

    private final int CODE_PHOTO = 200;
    private final int SELECT_PICTURE = 300;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =  inflater.inflate(R.layout.fragment_vista_pre_denuncia, container, false);

        spiner_vehiculo = vista.findViewById(R.id.spinner);
        spinnr_infraccion = vista.findViewById(R.id.spinner2);
        b_siguiente = vista.findViewById(R.id.boton_continuar);
        imagen_patente = vista.findViewById(R.id.imagen_de_patente);
        b_capture_patente = vista.findViewById(R.id.boton_foto_de_patente);
        txt_ubicacion = vista.findViewById(R.id.campo_ubicacion);
        txt_descripcion = vista.findViewById(R.id.campo_descripcion);
        imagen_contexto = vista.findViewById(R.id.imagen_del_contexto);
        texto_patente =  vista.findViewById(R.id.campo_patente_setear);

        b_capture_context = vista.findViewById(R.id.boton_foto_del_contexto);

        if(mayRequestStoragePermission()){
            b_capture_patente.setEnabled(true);
            b_capture_context.setEnabled(true);}
        else{
            b_capture_patente.setEnabled(false);
            b_capture_context.setEnabled(false);}

        ArrayAdapter<CharSequence> adapter_vehiculo = ArrayAdapter.createFromResource(getActivity(),
                R.array.tipodevehiculo, android.R.layout.simple_spinner_item);
        adapter_vehiculo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner_vehiculo.setAdapter(adapter_vehiculo);

        ArrayAdapter<CharSequence> adapter_infraccion = ArrayAdapter.createFromResource(getActivity(),
                R.array.tipodeifraccion, android.R.layout.simple_spinner_item);
        adapter_infraccion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnr_infraccion.setAdapter(adapter_infraccion);

        b_capture_patente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpcionesDelBotonPatente();
            }
        });
        b_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id_infraccion = UUID.randomUUID().toString();
                String descripcion_a_enviar =  txt_descripcion.getText().toString();
                String ubicacion_a_enviar = txt_ubicacion.getText().toString();
                String infraccion_a_enviar = spinnr_infraccion.getSelectedItem().toString();
                String vehiculo_a_enviar = spiner_vehiculo.getSelectedItem().toString();
                String patente_a_enviar =  texto_patente.getText().toString();
                Date date = new Date();
                DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
                String hora = hourFormat.format(date);
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String fecha = dateFormat.format(date);

                String imagen_en_bitmap = BitMapToString();


                DatosAEnviarADenuncia(id_infraccion, descripcion_a_enviar, ubicacion_a_enviar, infraccion_a_enviar,vehiculo_a_enviar, hora, fecha, patente_a_enviar, imagen_en_bitmap );
            }});



        b_capture_context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpcionesDelBotonContexto();
            }
        });

        return vista;
    }

    // Switch para botones
    public void OpcionesDeBotonesEnPreDenuncia(int botones){
        switch (botones){



        }




    }


    public void BotonSiguiente(){

    }




    // para BOTON CONETXTO
    public void OpcionesDelBotonContexto() {


        final CharSequence[] options = {"TOMAR FOTO", "ELEGIR DE GALERIA", "SALIR"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Elige una opcion");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                CharSequence[] charSequenceArr = options;
                if (charSequenceArr[i] == "TOMAR FOTO") {
                    openCamera();
                } else if (charSequenceArr[i] == "ELEGIR DE GALERIA") {
                    TomarFotoDeGaleria();
                } else if (charSequenceArr[i] == "SALIR") {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    public void TomarFotoDeGaleria() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                   android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, SELECT_PICTURE);
    }


    public void openCamera() {

        String nombre_de_imagen="";

        File file_imagen = new File(Environment.getExternalStorageDirectory(), RUTA_IMAGEN);
        boolean isDirectoryCreated = file_imagen.exists();

        if(isDirectoryCreated==false){
            isDirectoryCreated = file_imagen.mkdirs();}
        if(isDirectoryCreated==true) {
            nombre_de_imagen = (System.currentTimeMillis() / 1000) + "jpg";
        }

            phat  = Environment.getExternalStorageDirectory() + File.separator + RUTA_IMAGEN +
                    File.separator + nombre_de_imagen;

            File new_file= new File(phat);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            String autorizado = getActivity().getPackageName() + ".provider";
            //Uri uri_imagen = FileProvider.getUriForFile (getContext(), autorizado, new_file);
            //intent.putExtra(MediaStore.EXTRA_OUTPUT, uri_imagen);
               Uri imguri = FileProvider.getUriForFile (getContext(), autorizado, new_file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imguri);
            }else
            {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new_file));
            }

            startActivityForResult(intent, CODE_PHOTO);



    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            switch(requestCode){
                case CODE_PHOTO:
                    MediaScannerConnection.scanFile(getContext(), new String[]{phat},
                            null, new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String s, Uri uri) {

                        }
                    });
                    Bitmap bitmap =  BitmapFactory.decodeFile(phat);
                    imagen_contexto.setImageBitmap(bitmap);
                    break;

                case SELECT_PICTURE:
                    if (data != null) {
                        //imagen_contexto.setImageBitmap((Bitmap)data.getExtras().get("data"));
                        Uri imguri = data.getData();
                        imagen_contexto.setImageURI(imguri);

                    }
                    break;
            }

            }


    }

    // convertir de bitmap a string
    public String BitMapToString(){

        Bitmap bitmap = BitmapFactory.decodeFile(phat);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // In case you want to compress your image, here it's at 40%
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);



    }



// BOTON SIGUIENTE para enviar denuncia

    public void DatosAEnviarADenuncia(String id_infraccion, String descripcion_a_enviar, String ubicacion_a_enviar, String infraccion_a_enviar, String vehiculo_a_enviar, String hora, String fecha, String patente , String foto_contexto) {




            Bundle enviar = new Bundle();

            enviar.putString("ID_INFRACCION",id_infraccion);
            enviar.putString("Descripcion",descripcion_a_enviar);
            enviar.putString("Lugar",ubicacion_a_enviar);
            enviar.putString("Infraccion",infraccion_a_enviar);
            enviar.putString("Vehiculo",vehiculo_a_enviar);
            enviar.putString("Hora",hora);
            enviar.putString("Fecha",fecha);
            enviar.putString("Patente",patente);
            enviar.putString("FotoContexto",foto_contexto);

            vistaEnviar_y_detalleDeDenuncia.setArguments(enviar);
            getActivity().getSupportFragmentManager().beginTransaction().
                    replace(R.id.ic_contenedor, vistaEnviar_y_detalleDeDenuncia).
                    addToBackStack(null).commit();



    }


// para patente y permisos
// Para BOTON PATENTE
public void OpcionesDelBotonPatente() {

    final CharSequence[] options = {"TOMAR FOTO", "ELEGIR DE GALERIA", "SALIR"};
    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
    builder.setTitle("Elige una opcion");
    builder.setItems(options, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialogInterface, int i) {
            CharSequence[] charSequenceArr = options;
            if (charSequenceArr[i] == "TOMAR FOTO") {
                openCameraPatente();
            } else if (charSequenceArr[i] == "ELEGIR DE GALERIA") {
                TomarFotoDeGaleriaPatente();
            } else if (charSequenceArr[i] == "SALIR") {
                dialogInterface.dismiss();
            }
        }
    });
    builder.show();

}

    public void openCameraPatente() {
    }
    private void TomarFotoDeGaleriaPatente() {
    }



    // Gestion de permisos para ambos botones
    //GESTION DE PERMISOS
    public boolean mayRequestStoragePermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;
        if((ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(getContext(),
                CAMERA)==PackageManager.PERMISSION_GRANTED)){
            return true;
        }
        if((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))){
            Snackbar.make(linearView, "Los permisos son necesarios para poder usar la aplicación",
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, PERMISSIONS);
                }
            }).show();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, PERMISSIONS);
        }
        return false;

    }

    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Permisos denegados");
        builder.setMessage("Para usar las funciones de la app necesitas aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", String.valueOf(getParentFragment()), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PERMISSIONS){
            if(grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getContext(), "Permisos aceptados", Toast.LENGTH_SHORT).show();
                b_capture_patente.setEnabled(true);
                b_capture_context.setEnabled(true);
            }
        }else{
            showExplanation();
        }
    }


}