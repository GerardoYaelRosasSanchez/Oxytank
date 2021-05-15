package com.example.oxytank_10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class registrarComercio_comercio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_comercio_comercio);
    }

    //Métodos.

    //Ir a la pantalla Registrar Comercio: Ubicación.
    public void registrarComercio_ubicacion(View view){
        //Pasar a la actividad "registrarComercio_ubicacion".
        Intent Act_registrarComercio_ubicacion = new Intent(this, registrarComercio_ubicacion.class);
        startActivity(Act_registrarComercio_ubicacion);
    }

}