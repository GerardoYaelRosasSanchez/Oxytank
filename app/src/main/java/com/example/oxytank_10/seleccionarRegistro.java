package com.example.oxytank_10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class seleccionarRegistro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_registro);
    }

    //Métodos.

    //Ir a la pantalla Registrar Comercio: Pedir Codigo.
    public void registrarComercio_ingresarCodigo(View view){
        //Pasar a la actividad "registrarComercio_ingresarCodigo".
        Intent Act_registrarComercio_ingresarCodigo = new Intent(this, registrarComercio_ingresarCodigo.class);
        startActivity(Act_registrarComercio_ingresarCodigo);
    }


}