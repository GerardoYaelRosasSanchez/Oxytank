package com.example.oxytank_10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class registrarComercio_usuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_comercio_usuario);
    }

    //Métodos.

    //Ir a la pantalla Registrar Comercio: Datos Comercio.
    public void registrarComercio_comercio(View view){
        //Pasar a la actividad "registrarComercio_comercio".
        Intent Act_registrarComercio_comercio = new Intent(this, registrarComercio_comercio.class);
        startActivity(Act_registrarComercio_comercio);
    }

}