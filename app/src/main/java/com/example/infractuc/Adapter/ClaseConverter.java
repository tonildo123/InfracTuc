package com.example.infractuc.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


public class ClaseConverter {


    //@SuppressLint("ResourceType")
    public static void StringTOBitmap(Context c, String ur_imagen, ImageView img) {

        if(ur_imagen !=null){
                     Glide.with(c)
                    .load(ur_imagen)
                    .into(img);

        } else {
            Toast.makeText(c,"ERROR", Toast.LENGTH_LONG).show();
        }


    }
}
