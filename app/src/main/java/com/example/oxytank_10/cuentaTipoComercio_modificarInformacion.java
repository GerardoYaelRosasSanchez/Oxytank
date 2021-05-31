package com.example.oxytank_10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class cuentaTipoComercio_modificarInformacion extends AppCompatActivity {

    //Relación con el entorno gráfico
    EditText edt_nombreUsuario, edt_correo, edt_contrasenia, edt_verificarContrasenia;

    //Cambio Actividad
    private Boolean cumpleRequisitos = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_tipo_comercio_modificar_informacion);

        // Relación de las variables con la parte gráfica.
        edt_nombreUsuario = findViewById(R.id.edt_cuentaTipoCliente_modificarInformacion_nombre);
        edt_correo = findViewById(R.id.edt_cuentaTipoCliente_modificarInformacion_correo);
        edt_contrasenia = findViewById(R.id.edt_cuentaTipoCliente_modificarInformacion_contrasenia);
        edt_verificarContrasenia = findViewById(R.id.edt_cuentaTipoCliente_modificarInformacion_verificarContrasenia);

        //Mostrar los datos actuales del cliente.
        mostrarDatos();

    }

    //Mostrar los datos actuales del cliente.
    public void mostrarDatos(){

        String usuario_id = getIntent().getStringExtra("usuario_id");
        int idUsuario = Integer.parseInt(usuario_id);

        //Objeto: Administrar la base de datos.
        DBHelper admin = new DBHelper(this, "Oxytank_db", null, 1);

        // Abrir la base de datos en modo lectura y escritura.
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        //Contar consultar los datos en la BD.
        String contarUsuario_Consulta = "SELECT nombreUsuario, correo, contrasenia FROM usuarios"; // Guardar el texto que corresponde a la consulta.
        Cursor fila_usuario = BaseDatos.rawQuery
                ("select nombreUsuario, correo, contrasenia from usuarios " +
                        "where idUsuario =" + idUsuario, null); //Realizar la consulta en la base de datos.


        //Caso: Se encontro el usuario.
        if(fila_usuario.moveToFirst()){

            //Mostrar datos del usuario en la pantalla.
            edt_nombreUsuario.setText(fila_usuario.getString(0));
            edt_correo.setText(fila_usuario.getString(1));
            edt_contrasenia.setText(fila_usuario.getString(2));
            edt_verificarContrasenia.setText(fila_usuario.getString(2));

        }

        //Cerrar el cursor.
        fila_usuario.close();

        //Cerrar la base de datos.
        BaseDatos.close();

    }

}