package com.example.infractuc;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import java.io.File;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;

import static android.Manifest.permission.CAMERA;

import android.content.pm.PackageManager;

import com.google.android.material.snackbar.Snackbar;


public class VistaCapturaFotografia extends Fragment {
    private static String APP_DIRECTORY = "myInfraTucApp/";
    private static String MEDIA_DIRECTORY = (APP_DIRECTORY + "denuncias");

    private final int PERMISSIONS = 100;
    private final int CODE_PHOTO = 200;
    private final int SELECT_PICTURE = 300;

    private Button b_siguinte_patente;
    private Button b_tomar_foto;
    private ImageView imagen_picture;
    private LinearLayout linearView;

    private String mPhat;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_vista_captura_fotografia, container, false);
        imagen_picture =  vista.findViewById(R.id.set_image_capture);
        b_tomar_foto =  vista.findViewById(R.id.button_capture_picture);
        b_siguinte_patente = vista.findViewById(R.id.button_reconocer_patente);
        linearView = vista.findViewById(R.id.linear_dise);

        if(mayRequestStoragePermission())
            b_tomar_foto.setEnabled(true);
            else
                b_tomar_foto.setEnabled(false);


        b_tomar_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpcionesDelBoton();
            }
        });



        b_siguinte_patente.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LlamarAPRedenuncia();
            }
        });
        return vista;
    }

    private boolean mayRequestStoragePermission() {
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PERMISSIONS){
            if(grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getContext(), "Permisos aceptados", Toast.LENGTH_SHORT).show();
                b_tomar_foto.setEnabled(true);
            }
        }else{
            showExplanation();
        }



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


    public void OpcionesDelBoton() {


        final CharSequence[] options = {"TOMAR FOTO", "ELEGIR DE GALERIA", "SALIR"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Elige una opcion");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                CharSequence[] charSequenceArr = options;
                if (charSequenceArr[i] == "TOMAR FOTO") {
                    openCamera();
                } else if (charSequenceArr[i] == "ELEGIR DE GALERIA") {
                    Intent intentAbrirGaleria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    //intent.setType("Image/*");
                    startActivityForResult(Intent.createChooser(intentAbrirGaleria, "SELECCIONA UNA IMAGEN"), SELECT_PICTURE);
                } else if (charSequenceArr[i] == "SALIR") {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();


    }

    public void openCamera() {

        /*File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if(isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();
        if(isDirectoryCreated){
            Long timestamp = System.currentTimeMillis()/1000;
            String image_name = timestamp.toString() + ".jpg";

            mPhat  = Environment.getExternalStorageState() + File.separator + MEDIA_DIRECTORY +
                    File.separator + image_name;

            File new_file= new File(mPhat);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new_file));
            startActivityForResult(intent, CODE_PHOTO);
        }
*/

        Intent intentTomarFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentTomarFoto, CODE_PHOTO);

    }

/*    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file_path", mPhat);
    }

*/


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*if (resultCode == RESULT_OK) {
            switch(requestCode){
                case CODE_PHOTO:
                    MediaScannerConnection.scanFile(getContext(), new String[]{mPhat},
                            null, new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String s, Uri uri) {
                            Log.i("ExternalStorage", "Scanned" + s + ":");
                            Log.i("ExternalStorage", "-> URI" + uri + ":");
                        }
                    });
                    Bitmap bitmap =  BitmapFactory.decodeFile(mPhat);
                    imagen_picture.setImageBitmap(bitmap);
                    break;

                case SELECT_PICTURE:
                    Uri path = data.getData();
                    imagen_picture.setImageURI(path);
                    break;
            }

            }*/


        if(requestCode == CODE_PHOTO && resultCode == RESULT_OK){
            imagen_picture.setImageURI(data.getData());
        }
        if(requestCode == SELECT_PICTURE && resultCode == RESULT_OK){
            imagen_picture.setImageBitmap((Bitmap)data.getExtras().get("data"));
        }


    }





    public void LlamarAPRedenuncia() {
        getActivity().getSupportFragmentManager().beginTransaction().replace
                (R.id.ic_contenedor, new VistaPreDenuncia()).addToBackStack(null).commit();
    }

}