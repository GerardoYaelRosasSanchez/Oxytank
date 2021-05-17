package com.example.oxytank_10;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    // Constructor
    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // Métodos
    @Override
    public void onCreate(SQLiteDatabase db) {

        //Crear la tabla usuarios.
        db.execSQL("create table usuarios(nombreUsuario text primary key, correo text, contrasenia text, tipo text)");

        //Crear la tabla comercios.
        db.execSQL("create table comercios(nombreComercio text primary key, " +
                "telefono int, longitud real, latitud real, renta text, venta text, refil text," +
                "FOREIGN KEY (nombreUsuario) REFERENCES usuarios(nombreUsuario))");

        //Crear tabla valoraciones.
        db.execSQL("create table valoraciones(idvaloracion int primary key, valoracion int, fechaValoracion date," +
                "FOREIGN KEY (nombreUsuario) REFERENCES usuarios(nombreUsuario)," +
                "FOREIGN KEY (nombreComercio) REFERENCES comercios(nombreComercio))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
