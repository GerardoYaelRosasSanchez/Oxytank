package com.example.oxytank_10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class registrarCliente_comprobarDatos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_cliente_comprobar_datos);
    }

    //Métodos.

    //Ir a la pantalla principal de la cuenta de tipo cliente.
    public void cuentaTipoCliente_PantallaPrincipal(View view){
        //Pasar a la actividad "cuentaTipoCliente_PantallaPrincipal".
        Intent Act_cuentaTipoCliente_PantallaPrincipal = new Intent(this, cuentaTipoCliente_PantallaPrincipal.class);
        startActivity(Act_cuentaTipoCliente_PantallaPrincipal);
    }

}