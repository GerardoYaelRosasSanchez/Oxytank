package com.example.oxytank_10;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class cuentaTipoCliente_PantallaPrincipal extends AppCompatActivity {

    //Relación de las variables con el entorno gráfico.
    ListView lv_listaComercios;

    //Arreglos que se utilizaran en el caso de que aún no exista ningun comercio registrado.
        //Comercios.
    private String sinComercios[] = {"Sin comercios", "Sin comercios", "Sin comercios",
            "Sin comercios", "Sin comercios", "Sin comercios", "Sin comercios", "Sin comercios",
            "Sin comercios", "Sin comercios", "Sin comercios", "Sin comercios", "Sin comercios"};
        //Servicios.
    private String sinServicio[] = {"Sin servicio", "Sin servicio", "Sin servicio",
            "Sin servicio", "Sin servicio", "Sin servicio", "Sin servicio", "Sin servicio",
            "Sin servicio", "Sin servicio", "Sin servicio", "Sin servicio", "Sin servicio"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_tipo_cliente_pantalla_principal);

        //Relación de las varaibles con el entorno gráfico.
        lv_listaComercios = findViewById(R.id.lv_cuentaTipoCliente_PantallaPrincipal_mostrarComercios);

        //Se utiliza un adapter para mostrar la información por defaul en el listView.
        MyAdapter adapter = new MyAdapter(this, sinComercios, sinServicio);
        lv_listaComercios.setAdapter(adapter);

    }

    //Clase utilizada para la personalización y funcionamiento del adapter.
    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        //Variables para guardar los datos que se utilizaran.
        String l_sinComercios[];
        String l_sinServicios[];

        //Constructor.
        MyAdapter (Context context, String sinComercio[], String sinServicio[]){
            super(context, R.layout.list_item_listacomercios, R.id.tv_listItems_listaComercios_nombre, sinComercio);
            this.context = context;
            this.l_sinComercios = sinComercios;
            this.l_sinServicios = sinServicio;

        }

        //Seleccionar los datos que se mostraran en el listView.
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            //Relación del documento "list_item_listacomercios".
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View fila = layoutInflater.inflate(R.layout.list_item_listacomercios,parent, false);

            //Relación de las variables con el entorno gráfico.
            TextView comercios = fila.findViewById(R.id.tv_listItems_listaComercios_nombre);
            TextView servicio = fila.findViewById(R.id.tv_listItems_listaComercios_servicioVenta);

            //Mostrar los datos utilizando el formato personalizado.
            comercios.setText(l_sinComercios[position]);
            servicio.setText(l_sinServicios[position]);

            return fila;

        }
    }
}