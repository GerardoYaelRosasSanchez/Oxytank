package com.example.oxytank_10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class cuentaTipoCliente_PantallaPrincipal extends AppCompatActivity {

    //Relación de las variables con el entorno gráfico.
    ListView lv_listaComercios;

    //Arreglo "Vacio"
    private String sinComercios[] = {"Sin comercios", "Sin comercios", "Sin comercios",
            "Sin comercios", "Sin comercios", "Sin comercios", "Sin comercios", "Sin comercios",
            "Sin comercios", "Sin comercios", "Sin comercios", "Sin comercios", "Sin comercios"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_tipo_cliente_pantalla_principal);

        //Relación de las varaibles con el entorno gráfico.
        lv_listaComercios = findViewById(R.id.lv_cuentaTipoCliente_PantallaPrincipal_mostrarComercios);


        //Mostrar al usuario que aún no se ha registrado ningun comercio.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_listacomercios, sinComercios);
        lv_listaComercios.setAdapter(adapter);

        /*
        //Caso: Si aun no se ha registrado ningun comercio.
        if(comercios.size() == 0){
            //Mostrar al usuario que aún no se ha registrado ningun comercio.
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_listacomercios, sinComercios);
            lv_listaComercios.setAdapter(adapter);
        }
        //De lo contrario: Mostrar negocios en "ListView".
        else{

            //Mostrar los negocios en la pantalla principal mediante ListView "lv_pantallaPrincipal_listaNegocios".
            //ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_listanegocios, negocios);
            //lv_listaNegocios.setAdapter(adapter);
        }

         */

    }
}