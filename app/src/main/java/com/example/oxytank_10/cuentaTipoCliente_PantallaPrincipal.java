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

    // Guardar el numero de usuarios que contiene la tabla usuarios.
    int numComercios = 0;

    //Guardar el id del comercio y del usuario de la tabla comercios en una lista.
    String comercio_nombre[] = new String[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_tipo_cliente_pantalla_principal);

        //Relación de las varaibles con el entorno gráfico.
        lv_listaComercios = findViewById(R.id.lv_cuentaTipoCliente_PantallaPrincipal_mostrarComercios);

        consultarComercio();

        String prueba[] = new String[numComercios];

        for (int i = 0; i < numComercios; i++){
            prueba[i] = comercio_nombre[i];
        }

        //Caso: Si no existen comercios.
        if(numComercios == 0){
            //Se utiliza un adapter para mostrar la información por defaul en el listView.
            MyAdapter adapter = new MyAdapter(this, sinComercios);
            lv_listaComercios.setAdapter(adapter);
        }
        //De lo contrario: Mostrar comercios en "ListView".
        else{
            //Mostrar los negocios en la pantalla principal mediante ListView "lv_pantallaPrincipal_listaNegocios".
            MyAdapter adapter = new MyAdapter(this, prueba);
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

    //Mostar los comerxios ingresados por el usuario de tipo comercio en "ListView".
    public void consultarComercio(){

        int cont = 0; //Se utiliza para recorrer los arreglos y para realizar los ciclos en las funciones.

        //Objeto: Administrar la base de datos.
        DBHelper administrador = new DBHelper(this, "Oxytank_db", null, 1);

        //Abrir Base de Datos
        SQLiteDatabase BaseDatos = administrador.getReadableDatabase();

        //Buscar todos los nombres de la base de datos.
        Cursor cursor = BaseDatos.rawQuery("select * from comercios", null);

        numComercios = cursor.getCount();

        //Caso: Se encontraron los nombres.
        if(cursor.moveToFirst()){
            //Llenar el arreglo comercios.
            do{
                comercio_nombre[cont] = cursor.getString(1);

                cont += 1;

            }while (cursor.moveToNext());
        }

        cont = 0;

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

        //Seleccionar los datos que se mostraran en el listView.
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            //Relación del documento "list_item_listacomercios".
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View fila = layoutInflater.inflate(R.layout.list_item_listacomercios,parent, false);

            //Relación de las variables con el entorno gráfico.
            TextView comercios = fila.findViewById(R.id.tv_listItems_listaComercios_nombre);

            comercios.setText(l_Comercios[position]);

            return fila;

        }
    }
}