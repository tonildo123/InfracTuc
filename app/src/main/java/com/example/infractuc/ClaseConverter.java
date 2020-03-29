package com.example.infractuc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ClaseConverter {


    public static void StringTOBitmap(String contexto, ImageView img) {

        String imageDataBytes = contexto.substring(contexto.indexOf(",")+1);

        InputStream stream = new ByteArrayInputStream(Base64.decode(imageDataBytes.getBytes(), Base64.DEFAULT));

        Bitmap bitmap = BitmapFactory.decodeStream(stream);
        img.setImageBitmap(bitmap);
    }
}
