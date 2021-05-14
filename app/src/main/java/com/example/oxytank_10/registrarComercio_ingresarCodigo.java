package com.example.oxytank_10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class registrarComercio_ingresarCodigo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_comercio_ingresar_codigo);
    }

    //Métodos.

    //Ir a la pantalla Registrar Comercio: Usuario.
    public void registrarComercio_usuario(View view){
        //Pasar a la actividad "registrarComercio_usuario".
        Intent Act_registrarComercio_usuario = new Intent(this, registrarComercio_usuario.class);
        startActivity(Act_registrarComercio_usuario);
    }

}