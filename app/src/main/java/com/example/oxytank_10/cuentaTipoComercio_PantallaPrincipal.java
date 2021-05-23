package com.example.oxytank_10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Placeholder;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.RadioButton;

public class cuentaTipoComercio_PantallaPrincipal extends AppCompatActivity {

    //Relación con el entorno gráfico.
    RadioButton venta_Disponible, venta_NoDisponible, venta_SinServicio;
    RadioButton renta_Disponible, renta_NoDisponible, renta_SinServicio;
    RadioButton refil_Disponible, refil_NoDisponible, refil_SinServicio;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_tipo_comercio_pantalla_principal);
    }


    //Métodos.

    //Actualizar el estado de los tanques de oxígeno.
    public void actualizarDisponibilidad(){

    }
}