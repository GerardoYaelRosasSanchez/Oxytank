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

    //Ir a la pantalla Registrar Comercio: Ingresar Datos del usuario.
    public void registrarComercio_usuario(View view){
        //Pasar a la actividad "registrarComercio_usuario".
        Intent Act_registrarComercio_usuario = new Intent(this, registrarComercio_usuario.class);
        startActivity(Act_registrarComercio_usuario);
    }

    //Ir a la pantalla Activity Main: Iniciar Sesión.
    public void activityMain_inicioSesion(View view){
        //Pasar a la actividad "activityMain".
        Intent Act_activityMain = new Intent(this, MainActivity.class);
        startActivity(Act_activityMain);
    }

}