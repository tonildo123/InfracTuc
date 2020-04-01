package com.example.infractuc;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BbLocalInfractuc extends SQLiteOpenHelper {



    private static final String MI_BASE_DE_DATOS = "infractuc.db";
    private static final int DATABASE_VERSION = 1;

    // Sentencia SQL para la creación de una tabla
    private static final String TABLA_INFRACCINES = "CREATE TABLE infraccion" +
            "(id_infraccion INT PRIMARY KEY, imagen_de_contexto)";

    public BbLocalInfractuc(Context context, String s, Object o, int i) {
        super(context, MI_BASE_DE_DATOS, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // aqui creamos las tablas
        db.execSQL(TABLA_INFRACCINES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS infraccion");
        onCreate(db);

    }

    public void insertar_contactos(String id_infraccion, String imagen_string) {
        ContentValues registro = new ContentValues();
        registro.put("id_infraccion", id_infraccion);
        registro.put("imagen_de_contexto", imagen_string);

        this.getWritableDatabase().insertOrThrow("infraccion", "", registro);

    }






}



