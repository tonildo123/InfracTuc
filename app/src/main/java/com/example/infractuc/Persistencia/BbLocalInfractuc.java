package com.example.infractuc.Persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BbLocalInfractuc extends SQLiteOpenHelper {



    private static final String MI_BASE_DE_DATOS = "infractuc.db";
    private static final int DATABASE_VERSION = 1;

    // Sentencia SQL para la creación de una tabla
    private static final String TABLA_INFRACCINES = "CREATE TABLE infraccion" +
            "(id_infraccion TEXT PRIMARY KEY, imagen_de_contexto TEXT, patente TEXT," +
            " fecha_y_hora TEXT, ubicacion TEXT, vehiculo TEXT, descripcion TEXT, tipo_de_infraccion TEXT)";

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

    public void InsertarInfraccionModoLocal(String id_infraccion, String imagen_string, String patente,
                                   String fecha_y_hora, String ubicacioon, String descripcion, String vehiculo, String tipo_de_infraccion) {
        ContentValues registro = new ContentValues();
        registro.put("id_infraccion", id_infraccion);
        registro.put("imagen_de_contexto", imagen_string);
        registro.put("patente", patente);
        registro.put("fecha", fecha_y_hora);
        registro.put("ubicacion", ubicacioon);
        registro.put("descripcion", descripcion);
        registro.put("vehiculo", vehiculo);
        registro.put("tipo_de_infraccion", tipo_de_infraccion);


        this.getWritableDatabase().insertOrThrow("infraccion", "", registro);

    }






}



