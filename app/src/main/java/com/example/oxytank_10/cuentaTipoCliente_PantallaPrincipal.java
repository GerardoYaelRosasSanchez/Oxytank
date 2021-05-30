package com.example.oxytank_10;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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

    // Guardar el numero de comercios que contiene la tabla comercios.
    int numComercios = 0;

    //Guardar dar el nombre del comercio en una lista.
    String lista_comercios_nombre[] = new String[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_tipo_cliente_pantalla_principal);

        //Relación de las varaibles con el entorno gráfico.
        lv_listaComercios = findViewById(R.id.lv_cuentaTipoCliente_PantallaPrincipal_mostrarComercios);

        //Llenar el array "lista_comercios_nombre" con la información de comercio.
        consultarComercio();

        //Crear el arreglo con el que se mostraran los comercios del tamaño de comercios que existen.
        String lv_listaComercios_nombre[] = new String[numComercios];

        //Guardar los comercios en el array lv para mostrarlos en el listView.
        for (int i = 0; i < numComercios; i++){
            lv_listaComercios_nombre[i] = lista_comercios_nombre[i];
        }

        //Caso: Si no existen comercios.
        if(numComercios == 0){
            //Se utiliza un adapter para mostrar la información por defaul en el listView.
            MyAdapter adapter = new MyAdapter(this, sinComercios);
            lv_listaComercios.setAdapter(adapter); //Mostrar los datos en el listView.
        }
        //De lo contrario: Mostrar comercios en "ListView".
        else{
            //Mostrar los comercios en la pantalla principal mediante ListView "lv_cuentaTipoCliente_PantallaPrincipal_mostrarComercios".
            MyAdapter adapter = new MyAdapter(this, lv_listaComercios_nombre);
            lv_listaComercios.setAdapter(adapter); //Mostrar los datos en listView.

            //Llevar al usuario a la pantalla correspondiente al comercio que selecciono.
            lv_listaComercios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //
                }
            });
        }



    }

    //Guardar en una lista los datos del id, nombre y servicios de los comercios.
    public void consultarComercio(){

        int cont = 0; //Se utiliza para recorrer los arreglos y para realizar los ciclos en las funciones.

        //Objeto: Administrar la base de datos.
        DBHelper administrador = new DBHelper(this, "Oxytank_db", null, 1);

        //Abrir Base de Datos
        SQLiteDatabase BaseDatos = administrador.getReadableDatabase();

        //Buscar todos los nombres de la base de datos.
        Cursor cursor = BaseDatos.rawQuery("select * from comercios", null);

        numComercios = cursor.getCount(); //Guardar cuantos comercios estan registrados.

        //Caso: Se encontraron los nombres.
        if(cursor.moveToFirst()){
            //Llenar el arreglo comercios.
            do{
                lista_comercios_nombre[cont] = cursor.getString(1); //Guardar el nombre del comercio.

                cont += 1; //Pasar a la siguiente posición del arreglo.

            }while (cursor.moveToNext()); //Pasar al siguiente elemento del cursos.
        }

        //Cerrar el cursor.
        cursor.close();

        //Cerrar la base de datos.
        BaseDatos.close();

    }

    //Clase utilizada para la personalización y funcionamiento del adapter.
    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        //Variables para guardar los datos que se utilizaran.
        String l_Comercios[];
        //String l_sinServicios[];

        //Constructor.
        MyAdapter (Context context, String comercios[]){
            super(context, R.layout.list_item_listacomercios, R.id.tv_listItems_listaComercios_nombre, comercios);
            this.context = context;
            this.l_Comercios = comercios;

        }

        //Seleccionar los datos que se mostraran en el listView y mostrarlos.
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            //Relación del documento "list_item_listacomercios".
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View fila = layoutInflater.inflate(R.layout.list_item_listacomercios,parent, false);

            //Relación de las variables con el entorno gráfico.
            TextView comercios_tv = fila.findViewById(R.id.tv_listItems_listaComercios_nombre);

            //Mostrar la información.
            comercios_tv.setText(l_Comercios[position]);

            return fila;

        }
    }
}