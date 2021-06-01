package com.example.oxytank_10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class cuentaTipoComercio_modificarInformacion extends AppCompatActivity {

    //Relación con el entorno gráfico
    EditText edt_nombreUsuario, edt_correo, edt_contrasenia, edt_verificarContrasenia;
    EditText edt_nombreComercio, edt_telefono;

    //Cambio Actividad
    private Boolean cumpleRequisitos = false;

    //Guardar el id del usuario como string.
    String id_Usuario;
    int int_idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_tipo_comercio_modificar_informacion);

        // Relación de las variables con la parte gráfica.
        edt_nombreUsuario = findViewById(R.id.edt_cuentaTipoComercio_modificarInformacion_nombre);
        edt_correo = findViewById(R.id.edt_cuentaTipoComercio_modificarInformacion_correo);
        edt_contrasenia = findViewById(R.id.edt_cuentaTipoComercio_modificarInformacion_contrasenia);
        edt_verificarContrasenia = findViewById(R.id.edt_cuentaTipoComercio_modificarInformacion_verificarContrasenia);
        edt_nombreComercio = findViewById(R.id.edt_cuentaTipoComercio_modificarInformacion_nombreComercio);
        edt_telefono = findViewById(R.id.edt_cuentaTipoComercio_modificarInformacion_telefono);

        //Mostrar los datos actuales del comercio.
        mostrarDatos();

    }

    //Mostrar los datos actuales del comercio.
    public void mostrarDatos(){

        //Recibir el ID correspondiente al comercio.
        String comercio_id = getIntent().getStringExtra("comercio_id");
        int id_comercio = Integer.parseInt(comercio_id);

        //Objeto: Administrar la base de datos.
        DBHelper admin = new DBHelper(this, "Oxytank_db", null, 1);

        // Abrir la base de datos en modo lectura y escritura.
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        //Caso: Validar que los campos se encuentren llenos.
        //Objeto: Seleccionar el comercio que corresponde a la cuenta actual.
        Cursor fila_comercio = BaseDatos.rawQuery
                ("select nombreComercio, telefono, idUsuario from comercios " +
                        "where idComercio =" + id_comercio, null);

        //Caso: Se encontro el comercio.
        if(fila_comercio.moveToFirst()){

            //Mostrar datos del comercio en la pantalla.
            edt_nombreComercio.setText(fila_comercio.getString(0));
            edt_telefono.setText(fila_comercio.getString(1));
            id_Usuario = fila_comercio.getString(2);
            int_idUsuario = Integer.parseInt(id_Usuario);

        }


        //Cerrar el cursor.
        fila_comercio.close();

        //Caso: Validar que los campos se encuentren llenos.
        //Objeto: Seleccionar un el usuario correspondiente al comercio.
        Cursor fila_usuario = BaseDatos.rawQuery
                ("select nombreUsuario, correo, contrasenia from usuarios " +
                        "where idUsuario =" + int_idUsuario, null);


        //Caso: Se encontro el usuario.
        if(fila_usuario.moveToFirst()){

            //Mostrar datos del usuario en la pantalla.
            edt_nombreUsuario.setText(fila_usuario.getString(0));
            edt_correo.setText(fila_usuario.getString(1));
            edt_contrasenia.setText(fila_usuario.getString(2));
            edt_verificarContrasenia.setText(fila_usuario.getString(2));

        }

        //Cerrar la base de datos.
        BaseDatos.close();

    }

}