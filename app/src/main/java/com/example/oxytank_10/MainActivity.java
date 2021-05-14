package com.example.oxytank_10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Métodos.

    //Ir a la pantalla Seleccionar Tipo de Registro.
    public void seleccionarRegistro(View view){
        //Pasar a la actividad "seleccionarRegistro".
        Intent Act_seleccionarRegistro = new Intent(this, seleccionarRegistro.class);
        startActivity(Act_seleccionarRegistro);
    }
}