package com.example.oxytank_10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class registrarCliente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_cliente);
    }

    //Métodos.

    //Ir a la pantalla Registrar Cliente.
    public void registrarCliente_comprobarDatos(View view){
        //Pasar a la actividad "registrarCliente_comprobarDatos".
        Intent Act_registrarCliente_comprobarDatos = new Intent(this, registrarCliente_comprobarDatos.class);
        startActivity(Act_registrarCliente_comprobarDatos);
    }

}