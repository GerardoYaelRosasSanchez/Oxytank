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
        db.execSQL("create table usuarios(idUsuario primary key, nombreUsuario text,  correo text, contrasenia text, tipo text)");

        //Crear la tabla comercios.
        db.execSQL("create table comercios(idComercio primary key, nombreComercio text, " +
                "telefono int, longitud real, latitud real,direccion text,  " +
                "renta text, venta text, refil text," +
                "nombreUsuario text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
