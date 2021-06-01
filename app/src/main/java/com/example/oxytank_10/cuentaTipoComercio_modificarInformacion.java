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

        //Cerrar el cursor.
        fila_usuario.close();

        //Cerrar la base de datos.
        BaseDatos.close();

    }

    //Ir a la pantalla principal de la cuenta de tipo comercio.
    public void cuentaTipoComercio_modificarInformacion_modificarDatos(View view){

        //Agregar del usuario en la base de datos.
        modificarDatosComercioBD();

        //Pasar a la actividad "cuentaTipoComercio_PantallaPrincipal".
        if(cumpleRequisitos){
            //Pasar a la actividad "cuentaTipoComercio_PantallaPrincipal".
            Intent Act_cuentaTipoComercio_PantallaPrincipal = new Intent(this, cuentaTipoComercio_PantallaPrincipal.class);
            String IdComercio_String = getIntent().getStringExtra("comercio_id");
            Act_cuentaTipoComercio_PantallaPrincipal.putExtra("comercio_id", IdComercio_String);
            startActivity(Act_cuentaTipoComercio_PantallaPrincipal);
        }

    }

    // Modificar los datos del comercio en la base de datos.
    public void modificarDatosComercioBD(){

        //Objeto: Administrar la base de datos.
        DBHelper admin = new DBHelper(this, "Oxytank_db", null, 1);

        // Abrir la base de datos en modo lectura y escritura.
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        // Guardar la información ingresada por el usuario.
        String nombreComercio = edt_nombreComercio.getText().toString();
        String telefono = edt_telefono.getText().toString();
        String nombreUsuario = edt_nombreUsuario.getText().toString();
        String correo = edt_correo.getText().toString();
        String contrasenia = edt_contrasenia.getText().toString();
        String verificarContrasenia = edt_verificarContrasenia.getText().toString();

        //Verificar que el usuario haya ingresado datos en los campos.
        if(!nombreUsuario.isEmpty() && !correo.isEmpty() && !contrasenia.isEmpty() && !verificarContrasenia.isEmpty() &&
                !nombreComercio.isEmpty() && !telefono.isEmpty()){

            // Comprobar que la contrasenia sea la misma.
            if (contrasenia.equals(verificarContrasenia)){

                //Objeto: Guardar las opciones ingresadas por el usuario.
                ContentValues registro_comercio = new ContentValues();
                ContentValues registro_usuario = new ContentValues();

                //Guardar los datos en el objeto "registro_usuario".
                registro_usuario.put("nombreUsuario", nombreUsuario);
                registro_usuario.put("correo", correo);
                registro_usuario.put("contrasenia", contrasenia);
                //Guardar los datos en el objeto "registro_comercio".
                registro_comercio.put("nombreComercio", nombreComercio);
                registro_comercio.put("telefono", telefono);

                //Modificar los valores dentro de la tabla "usuarios".
                BaseDatos.update("usuarios", registro_usuario, "idUsuario=" + int_idUsuario, null);

                //Recibir el ID correspondiente al comercio.
                String comercio_id = getIntent().getStringExtra("comercio_id");
                int id_comercio = Integer.parseInt(comercio_id);

                //Modificar los valores dentro de la tabla "comercios".
                BaseDatos.update("comercios", registro_comercio, "idComercio=" + id_comercio, null);

                //Cerrar la Base de datos.
                BaseDatos.close();

                Toast.makeText(this, "Modificacion exitosa.", Toast.LENGTH_LONG).show();

                //Permitir pasar a la siguiente actividad.
                cumpleRequisitos = true;

            }
            //Solicitar que el usuario revise las contraseñas.
            else{
                Toast.makeText(this, "Las contraseñas tiene que ser iguales.", Toast.LENGTH_LONG).show();
            }
        }
        //Solicitar al usuario que llene todos los campos.
        else{
            Toast.makeText(this, "Debes de llenar todos los campos", Toast.LENGTH_LONG).show();
        }

    }

}