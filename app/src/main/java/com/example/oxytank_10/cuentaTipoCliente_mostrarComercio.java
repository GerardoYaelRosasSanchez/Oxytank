package com.example.oxytank_10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class cuentaTipoCliente_mostrarComercio extends AppCompatActivity {

    //Relación con el entorno grafico.
    TextView nombreComercio, telefono, direccion;
    TextView servicio_venta, servicio_renta, servicio_refil;

    //Guarda el id del comercio de la pantalla anterior.
    String comercio_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_tipo_cliente_mostrar_comercio);

        // Relación de las variables con la parte gráfica.
        nombreComercio = findViewById(R.id.tv_cuentaTipoCliente_mostrarComercio_titulo);
        telefono = findViewById(R.id.tv_cuentaTipoCliente_mostrarComercio_telefono);
        direccion = findViewById(R.id.tv_cuentaTipoCliente_mostrarComercio_direccion);
        servicio_venta = findViewById(R.id.tv_cuentaTipoCliente_mostrarComercio_serviciosVenta);
        servicio_renta = findViewById(R.id.tv_cuentaTipoCliente_mostrarComercio_serviciosRenta);
        servicio_refil = findViewById(R.id.tv_cuentaTipoCliente_mostrarComercio_serviciosRefil);

        // Mostrar los datos.
        mostrarDatos();

    }

    //Métodos.


    //Ir a la pantalla principal de la cuenta de tipo comercio.
    public void irPantallaPrincipal(View view){

        Intent Act_cuentaCliente_pantallaPrincipal = new Intent(this, cuentaTipoCliente_PantallaPrincipal.class);
        startActivity(Act_cuentaCliente_pantallaPrincipal);

    }

    //Comprobar si los datos ingresados por el usuario de tipo comercio son correctos.
    public void mostrarDatos(){


        //Objeto: Administrar la base de datos.
        DBHelper administrador = new DBHelper(this, "Oxytank_db", null, 1);

        //Abrir Base de Datos
        SQLiteDatabase BaseDatos = administrador.getWritableDatabase();

        //Recibir el ID ingresado correspondiente al comercio del usuario en el registro.
        comercio_id = getIntent().getStringExtra("comercio_id");
        int id_comercio = Integer.parseInt(comercio_id); //Convertirlo a entero para usarlo en la base de datos.

        //Caso: Validar que los campos se encuentren llenos.
        //Objeto: Seleccionar un comercio.
        Cursor fila_comercio = BaseDatos.rawQuery
                ("select nombreComercio, telefono, direccion, renta, venta, refil from comercios " +
                        "where idComercio =" + id_comercio, null);

        //Caso: Se encontro el comercio.
        if(fila_comercio.moveToFirst()){

            //Mostrar datos del comercio en la pantalla.
            nombreComercio.setText(fila_comercio.getString(0));
            telefono.setText(fila_comercio.getString(1));
            direccion.setText(fila_comercio.getString(2));
            servicio_renta.setText("Renta:\n" +fila_comercio.getString(3));
            servicio_venta.setText("Venta:\n" +fila_comercio.getString(4));
            servicio_refil.setText("Refil:\n" + fila_comercio.getString(5));

        }

        //Cerrar el cursor.
        fila_comercio.close();

        //Cerrar la Base de datos.
        BaseDatos.close();

    }

    //Ver dirección del comercio
    public void VerDireccion(View view){
        //Objeto: Administrar la base de datos.
        DBHelper administrador = new DBHelper(this, "Oxytank_db", null, 1);

        //Abrir Base de Datos
        SQLiteDatabase BaseDatos = administrador.getWritableDatabase();

        int id_int = Integer.parseInt(comercio_id);

        //Caso: Validar que los campos se encuentren llenos.
        //Objeto: Seleccionar un comercio.
        Cursor fila = BaseDatos.rawQuery
                ("select latitud, longitud from comercios " +
                        "where idComercio =" + id_int, null);

        //Caso: Se encontro el comercio.
        if(fila.moveToFirst()){

            //Conseguir la latitud y longitud
            String latitud = fila.getString(0);
            String longitud = fila.getString(1);

            fila.close(); //Cerrar el cursor.

            //Cerrar la Base de datos.
            BaseDatos.close();

            //Pasar a la actividad "cuentaTipoCliente_verDireccion".
            Intent Act_cuentaTipoCliente_verDireccion = new Intent(this, cuentaTipoCliente_verDireccion.class);
            Act_cuentaTipoCliente_verDireccion.putExtra("comercio_latitud", latitud); //Pasar la latitud del comercio a la siguiente pantalla.
            Act_cuentaTipoCliente_verDireccion.putExtra("comercio_longitud", longitud); //Pasar la longitud del comercio a la siguiente pantalla.
            startActivity(Act_cuentaTipoCliente_verDireccion);

        }




    }

}