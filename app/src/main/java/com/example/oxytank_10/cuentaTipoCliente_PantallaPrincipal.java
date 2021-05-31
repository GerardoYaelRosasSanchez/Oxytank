package com.example.oxytank_10;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class cuentaTipoCliente_PantallaPrincipal extends AppCompatActivity {

    //Relación de las variables con el entorno gráfico.
    ListView lv_listaComercios;
    EditText edt_nombreComercio_ingresado;

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

    //Verificar si existe el comercio.
    Boolean existe = false;

    //Guardar la posicion del id correspondiente al comercio.
    int posicionComercio;

    //Guardar dar el nombre del comercio en una lista.
    String lista_comercios_nombre[] = new String[100];
    String lista_comercios_venta[] = new String[100];
    String lista_comercios_refil[] = new String[100];
    String lista_comercios_renta[] = new String[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_tipo_cliente_pantalla_principal);

        //Relación de las varaibles con el entorno gráfico.
        lv_listaComercios = findViewById(R.id.lv_cuentaTipoCliente_PantallaPrincipal_mostrarComercios);
        edt_nombreComercio_ingresado = findViewById(R.id.edt_cuentaTipoCliente_PantallaPrincipal_solicitarNombre);

        //Llenar el array "lista_comercios_nombre" con la información de comercio.
        consultarComercio();

        //Crear el arreglo con el que se mostraran los comercios del tamaño de comercios que existen.
        String lv_listaComercios_nombre[] = new String[numComercios];
        String lv_listaComercios_venta[] = new String[numComercios];
        String lv_listaComercios_renta[] = new String[numComercios];
        String lv_listaComercios_refil[] = new String[numComercios];

        //Guardar los datos de los comercios en el array lv para mostrarlos en el listView.
        for (int i = 0; i < numComercios; i++){
            lv_listaComercios_nombre[i] = lista_comercios_nombre[i]; //Guardar el nombre del comercio.
            lv_listaComercios_venta[i] = lista_comercios_venta[i]; //Guardar el estatus del servicio de venta.
            lv_listaComercios_renta[i] = lista_comercios_renta[i]; //Guardar el estatus del servicio de renta.
            lv_listaComercios_refil[i] = lista_comercios_refil[i]; //Guardar el estatus del servicio de refil.
        }

        //Caso: Si no existen comercios.
        if(numComercios == 0){
            //Se utiliza un adapter para mostrar la información por defaul en el listView.
            MyAdapter adapter = new MyAdapter(this, sinComercios, sinServicio, sinServicio, sinServicio);
            lv_listaComercios.setAdapter(adapter); //Mostrar los datos en el listView.
        }
        //De lo contrario: Mostrar comercios en "ListView".
        else{
            //Mostrar los comercios en la pantalla principal mediante ListView "lv_cuentaTipoCliente_PantallaPrincipal_mostrarComercios".
            MyAdapter adapter = new MyAdapter(this, lv_listaComercios_nombre, lv_listaComercios_venta, lv_listaComercios_renta, lv_listaComercios_refil);
            lv_listaComercios.setAdapter(adapter); //Mostrar los datos en listView.

            //Llevar al usuario a la pantalla correspondiente al comercio que selecciono.
            lv_listaComercios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    posicionComercio = position + 1; //Guardar la posición del comercio como variable global.

                    irMostrarDatos();//
                }
            });
        }
    }

    //Ir a la pantalla mostrar comercio en funcion a la seleccion del cliente.
    public void irMostrarDatos(){
        Intent Act_cuentaTipoCliente_PantallaPrincipal = new Intent(this, cuentaTipoCliente_mostrarComercio.class);
        String IdComercio_String = Integer.toString(posicionComercio);
        Act_cuentaTipoCliente_PantallaPrincipal.putExtra("comercio_id", IdComercio_String);
        startActivity(Act_cuentaTipoCliente_PantallaPrincipal);
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
                lista_comercios_renta[cont] = cursor.getString(6); //Guardar el estatus del servicio de renta.
                lista_comercios_venta[cont] = cursor.getString(7); //Guardar el estatus del servicio de venta.
                lista_comercios_refil[cont] = cursor.getString(8); //Guardar el estatus del servicio de refil.
                cont += 1; //Pasar a la siguiente posición del arreglo.

            }while (cursor.moveToNext()); //Pasar al siguiente elemento del cursos.
        }

        //Cerrar el cursor.
        cursor.close();

        //Cerrar la base de datos.
        BaseDatos.close();

    }

    //Buscar comercio.
    public void BuscarPorNombre(View view){

        int cont = 0; //Se utiliza para recorrer los arreglos y para realizar los ciclos en las funciones.

        //Objeto: Administrar la base de datos.
        DBHelper administrador = new DBHelper(this, "Oxytank_db", null, 1);

        //Abrir Base de Datos
        SQLiteDatabase BaseDatos = administrador.getWritableDatabase();

        //Guardar datos ingresados por el usuario.
        String nombreComercio_ingresado = edt_nombreComercio_ingresado.getText().toString();

        if(edt_nombreComercio_ingresado.getText().toString().isEmpty()){
            edt_nombreComercio_ingresado.setError("Ingresar Datos");

            //Mensaje de registro fallido.
            Toast.makeText(this, "Se debe de ingresar el nombre del comercio.", Toast.LENGTH_LONG).show();
        }
        else{ ;

            // Recorrer el arreglo con los nombres de los comercios para buscar si existe el comercio ingresado por el cliente.
            while (cont < numComercios){
                //Caso: Si existe el nombre de comercio ingresado por el cliente.
                if (lista_comercios_nombre[cont].equals(nombreComercio_ingresado)){
                    existe = true; //Comprobar existencia.
                    posicionComercio = cont + 1; // Guardar su posicion en el arreglo.
                    cont = numComercios; // Terminar el ciclo.
                }
                cont += 1; //Pasar al siguiente ciclo.
            }

            if (existe){
                //El comercio existe.
                Intent Act_cuentaTipoCliente_PantallaPrincipal = new Intent(this, cuentaTipoCliente_mostrarComercio.class);
                String IdComercio_String = Integer.toString(posicionComercio);
                Act_cuentaTipoCliente_PantallaPrincipal.putExtra("comercio_id", IdComercio_String);
                startActivity(Act_cuentaTipoCliente_PantallaPrincipal);
            }
            else{
                //El comercio no existe.
                Toast.makeText(this, "Comercio no existe", Toast.LENGTH_LONG).show();
            }
            //Cerrar la Base de datos.
            BaseDatos.close();

            //Reiniciar existencia
            existe = false;
        }

    }

    //Clase utilizada para la personalización y funcionamiento del adapter.
    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        //Variables para guardar los datos que se utilizaran.
        String l_Comercios[];
        String l_servicios_venta[];
        String l_servicios_refil[];
        String l_servicios_renta[];

        //Constructor.
        MyAdapter (Context context, String comercios[], String venta[], String refil[], String renta[]){
            super(context, R.layout.list_item_listacomercios, R.id.tv_listItems_listaComercios_nombre, comercios);
            this.context = context;
            this.l_Comercios = comercios;
            this.l_servicios_venta = venta;
            this.l_servicios_refil = refil;
            this.l_servicios_renta = renta;

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
            TextView venta_tv = fila.findViewById(R.id.tv_listItems_listaComercios_servicioVenta);
            TextView refil_tv = fila.findViewById(R.id.tv_listItems_listaComercios_servicioRefil);
            TextView renta_tv = fila.findViewById(R.id.tv_listItems_listaComercios_servicioRenta);

            //Mostrar la información.
            comercios_tv.setText(l_Comercios[position]);
            venta_tv.setText("Venta:\n" + l_servicios_venta[position]);
            refil_tv.setText("Refil:\n" + l_servicios_refil[position]);
            renta_tv.setText("Renta:\n" + l_servicios_renta[position]);


            return fila;

        }
    }

    //Ocultar y mostrar el menu overflow
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.overflow_cuentacliente, menu);
        return true;
    }

    //Opciones del menu overflow.
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.cuentaTipoCliente_overflow_item_modificarDatos){
            Intent Act_cuentaTipoCliente_modificarinformacion = new Intent(this, cuentaTipoCliente_modificarInformacion.class);
            //Recibir el ID ingresado por el usuario de "MainActivity"
            String usuario_id = getIntent().getStringExtra("usuario_id");
            Act_cuentaTipoCliente_modificarinformacion.putExtra("usuario_id", usuario_id);
            startActivity(Act_cuentaTipoCliente_modificarinformacion);
        }
        else if (id == R.id.cuentaTipoCliente_overflow_item_cerrarSesion){

            Intent Act_MainActivity = new Intent(this, MainActivity.class);
            startActivity(Act_MainActivity);
        }
        return super.onOptionsItemSelected(item);
    }

}