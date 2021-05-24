package com.example.oxytank_10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class registrarComercio_comprobarDatos extends AppCompatActivity {

    //Relación con el entorno grafico.
    TextView nombreUsuario, correo, contrasenia, tipo;
    TextView nombreComercio, telefono, direccion;
    TextView servicio_venta, servicio_renta, servicio_refil;

    //Guardar el id del usuario como string.
    String id_Usuario;
    int int_idUsuario;

    //Guarda el id del comercio de la pantalla anterior.
    String comercio_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_comercio_comprobar_datos);

        // Relación de las variables con la parte gráfica.
        nombreUsuario = findViewById(R.id.tv_registrarComercio_comprobarDatos_usuario);
        correo = findViewById(R.id.tv_registrarComercio_comprobarDatos_correo);
        contrasenia = findViewById(R.id.tv_registrarComercio_comprobarDatos_contrasenia);
        tipo = findViewById(R.id.tv_registrarComercio_comprobarDatos_tipo);
        nombreComercio = findViewById(R.id.tv_registrarComercio_comprobarDatos_comercio);
        telefono = findViewById(R.id.tv_registrarComercio_comprobarDatos_telefono);
        direccion = findViewById(R.id.tv_registrarComercio_comprobarDatos_direccion);
        servicio_venta = findViewById(R.id.tv_registrarComercio_comprobarDatos_serviciosVenta);
        servicio_renta = findViewById(R.id.tv_registrarComercio_comprobarDatos_serviciosRenta);
        servicio_refil = findViewById(R.id.tv_registrarComercio_comprobarDatos_serviciosRefil);

        // Mostrar los datos.
        mostrarDatos();

    }

    //Métodos.


    //Ir a la pantalla Registrar Comercio: Ubicacion.
    public void irPantallaPrincipalComercio(View view){

        //Recibir el ID ingresado por el usuario de "registrarComercio_comercio"
        String comercio_id = getIntent().getStringExtra("comercio_id");
        int id_comercio = Integer.parseInt(comercio_id);

        Intent Act_cuentaComercio_pantallaPrincipal = new Intent(this, cuentaTipoComercio_PantallaPrincipal.class);
        String IdComercio_String = Integer.toString(id_comercio);
        Act_cuentaComercio_pantallaPrincipal.putExtra("comercio_id", IdComercio_String);
        startActivity(Act_cuentaComercio_pantallaPrincipal);

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
                ("select nombreComercio, telefono, direccion, renta, venta, refil, idUsuario from comercios " +
                        "where idComercio =" + id_comercio, null);

        //Caso: Se encontro el comercio.
        if(fila_comercio.moveToFirst()){

            //Mostrar datos del comercio en la pantalla.
            nombreComercio.setText(fila_comercio.getString(0));
            telefono.setText(fila_comercio.getString(1));
            direccion.setText(fila_comercio.getString(2));
            servicio_renta.setText("      Ren:    " +fila_comercio.getString(3));
            servicio_venta.setText("      Ven:    " +fila_comercio.getString(4));
            servicio_refil.setText("      Ref:    " + fila_comercio.getString(5));
            //Conseguir el ID del usuario que corresponde al negocio.
            id_Usuario = fila_comercio.getString(6);
            int_idUsuario = Integer.parseInt(id_Usuario);

        }

        //Caso: Validar que los campos se encuentren llenos.
        //Objeto: Seleccionar un el usuario correspondiente al comercio.
        Cursor fila_usuario = BaseDatos.rawQuery
                ("select nombreUsuario, correo, contrasenia, tipo from usuarios " +
                        "where idUsuario =" + int_idUsuario, null);


        //Caso: Se encontro el usuario.
        if(fila_usuario.moveToFirst()){

            //Mostrar datos del usuario en la pantalla.
            nombreUsuario.setText(fila_usuario.getString(0));
            correo.setText(fila_usuario.getString(1));
            contrasenia.setText(fila_usuario.getString(2));
            tipo.setText(fila_usuario.getString(3));

        }

        //Cerrar la Base de datos.
        BaseDatos.close();

    }


}