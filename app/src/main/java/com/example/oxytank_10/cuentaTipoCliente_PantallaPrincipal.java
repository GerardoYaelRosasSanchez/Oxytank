package com.example.oxytank_10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class cuentaTipoCliente_PantallaPrincipal extends AppCompatActivity {

    //Relación de las variables con el entorno gráfico.
    ListView lv_listaComercios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_tipo_cliente_pantalla_principal);

        //Relación de las varaibles con el entorno gráfico.
        lv_listaComercios = findViewById(R.id.lv_cuentaTipoCliente_PantallaPrincipal_mostrarComercios); 

    }
}