package com.example.infractuc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import java.io.File;

public class VistaCapturaFotografia extends Fragment {
    private String APP_DIRECTORY = "myPictureApp/";
    private final int CODE_PHOTO = 100;
    private String MEDIA_DIRECTORY = (this.APP_DIRECTORY + "media");
    private final int SELECT_PICTURE = 200;
    private String TEMPORAL_PICTURE_NAME = "temporal.png";
    private Button b_siguinte_patente;
    private Button b_tomar_foto;
    private ImageView imagen_picture;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_vista_captura_fotografia, container, false);
        this.imagen_picture = (ImageView) vista.findViewById(R.id.set_image_capture);
        this.b_tomar_foto = (Button) vista.findViewById(R.id.button_capture_picture);
        this.b_siguinte_patente = (Button) vista.findViewById(R.id.button_reconocer_patente);
        this.b_tomar_foto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final CharSequence[] options = {"TOMAR FOTO", "ELEGIR DE GALERIA", "SALIR"};
                AlertDialog.Builder builder = new AlertDialog.Builder(VistaCapturaFotografia.this.getContext());
                builder.setTitle("Elige una opcion");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CharSequence[] charSequenceArr = options;
                        if (charSequenceArr[i] == "TOMAR FOTO") {
                            VistaCapturaFotografia.this.openCamera();
                        } else if (charSequenceArr[i] == "ELEGIR DE GALERIA") {
                            Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("Image/*");
                            VistaCapturaFotografia.this.startActivityForResult(Intent.createChooser(intent, "SELECCIONA UNA IMAGEN"), 200);
                        } else if (charSequenceArr[i] == "SALIR") {
                            dialogInterface.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });
        this.b_siguinte_patente.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                VistaCapturaFotografia.this.LlamarAPRedenuncia();
            }
        });
        return vista;
    }

    public void LlamarAPRedenuncia() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ic_contenedor, new VistaPreDenuncia()).commit();
    }

    public void openCamera() {
        new File(Environment.getExternalStorageDirectory(), this.MEDIA_DIRECTORY).mkdirs();
        File new_file = new File(Environment.getExternalStorageDirectory() + File.separator + this.MEDIA_DIRECTORY + File.separator + this.TEMPORAL_PICTURE_NAME);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output", Uri.fromFile(new_file));
        startActivityForResult(intent, 100);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 100) {
            if (requestCode == 200 && resultCode == -1) {
                this.imagen_picture.setImageURI(data.getData());
            }
        } else if (resultCode == -1) {
            decodeBitmaps(Environment.getExternalStorageDirectory() + File.separator + this.MEDIA_DIRECTORY + File.separator + this.TEMPORAL_PICTURE_NAME);
        }
    }

    private void decodeBitmaps(String dir) {
        this.imagen_picture.setImageBitmap(BitmapFactory.decodeFile(dir));
    }
}