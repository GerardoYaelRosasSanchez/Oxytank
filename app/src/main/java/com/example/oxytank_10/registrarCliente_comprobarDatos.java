package com.example.oxytank_10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class registrarCliente_comprobarDatos extends AppCompatActivity {

    //Relación con el entorno grafico.
    TextView nombreUsuario, correo, contrasenia, tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_cliente_comprobar_datos);

        // Relación de las variables con la parte gráfica.
        nombreUsuario = findViewById(R.id.tv_registrarCliente_comprobarDatos_usuario);
        correo = findViewById(R.id.tv_registrarCliente_comprobarDatos_correo);
        contrasenia = findViewById(R.id.tv_registrarCliente_comprobarDatos_contrasenia);
        tipo = findViewById(R.id.tv_registrarCliente_comprobarDatos_tipo);

        // Mostrar los datos.
        mostrarDatos();

    }

    //Métodos.

    //Ir a la pantalla principal de la cuenta de tipo cliente.
    public void cuentaTipoCliente_PantallaPrincipal(View view){

        //Recibir el ID ingresado por el usuario de "registrarCliente"
        String usuario_id = getIntent().getStringExtra("usuario_id");
        int id_usuario = Integer.parseInt(usuario_id);

        //Pasar a la actividad "cuentaTipoCliente_PantallaPrincipal".
        Intent Act_cuentaTipoCliente_PantallaPrincipal = new Intent(this, cuentaTipoCliente_PantallaPrincipal.class);
        String IdComercio_String = Integer.toString(id_usuario);
        Act_cuentaTipoCliente_PantallaPrincipal.putExtra("usuario_id", IdComercio_String);
        startActivity(Act_cuentaTipoCliente_PantallaPrincipal);

    }

    //Comprobar si los datos ingresados por el usuario de tipo comercio son correctos.
    public void mostrarDatos(){


        //Objeto: Administrar la base de datos.
        DBHelper administrador = new DBHelper(this, "Oxytank_db", null, 1);

        //Abrir Base de Datos
        SQLiteDatabase BaseDatos = administrador.getWritableDatabase();

        //Recibir el ID ingresado por el usuario de "registrarCliente"
        String usuario_id = getIntent().getStringExtra("usuario_id");
        int id_usuario = Integer.parseInt(usuario_id);

        //Caso: Validar que los campos se encuentren llenos.
        //Objeto: Seleccionar un el usuario correspondiente a los datos ingresados por el usuario en el registro del cliente.
        Cursor fila_usuario = BaseDatos.rawQuery
                ("select nombreUsuario, correo, contrasenia, tipo from usuarios " +
                        "where idUsuario =" + id_usuario, null);


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