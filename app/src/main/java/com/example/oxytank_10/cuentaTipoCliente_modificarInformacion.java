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

public class cuentaTipoCliente_modificarInformacion extends AppCompatActivity {

    //Relación con el entorno gráfico
    EditText edt_nombreUsuario, edt_correo, edt_contrasenia, edt_verificarContrasenia;

    //Cambio Actividad
    private Boolean cumpleRequisitos = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_tipo_cliente_modificar_informacion);

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

        //Recibir el ID correspondiente al cliente.
        String usuario_id = getIntent().getStringExtra("usuario_id");
        int id_usuario = Integer.parseInt(usuario_id);

        //Objeto: Administrar la base de datos.
        DBHelper admin = new DBHelper(this, "Oxytank_db", null, 1);

        // Abrir la base de datos en modo lectura y escritura.
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        //Contar consultar los datos en la BD.
        Cursor fila_usuario = BaseDatos.rawQuery
                ("select nombreUsuario, correo, contrasenia from usuarios " +
                        "where idUsuario =" + id_usuario, null); //Realizar la consulta en la base de datos.

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

    //Ir a la pantalla principal de la cuenta de tipo cliente.
    public void cuentaTipoCliente_modificarInformacion_modificarDatos(View view){

        //Agregar del usuario en la base de datos.
        modificarDatosUsuarioBD();

        //Pasar a la actividad "cuentaTipoCliente_PantallaPrincipal".
        if(cumpleRequisitos){
            //Pasar a la actividad "cuentaTipoCliente_PantallaPrincipal".
            Intent Act_cuentaTipoCliente_PantallaPrincipal = new Intent(this, cuentaTipoCliente_PantallaPrincipal.class);
            String usuario_id = getIntent().getStringExtra("usuario_id");
            Act_cuentaTipoCliente_PantallaPrincipal.putExtra("usuario_id", usuario_id);
            startActivity(Act_cuentaTipoCliente_PantallaPrincipal);
        }

    }

    // Modificar los datos del usuario en la base de datos.
    public void modificarDatosUsuarioBD(){

        //Recibir el ID correspondiente al usuario.
        String usuario_id = getIntent().getStringExtra("usuario_id");
        int id_usuario = Integer.parseInt(usuario_id);

        //Objeto: Administrar la base de datos.
        DBHelper admin = new DBHelper(this, "Oxytank_db", null, 1);

        // Abrir la base de datos en modo lectura y escritura.
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        // Guardar la información ingresada por el usuario.
        String nombreUsuario = edt_nombreUsuario.getText().toString();
        String correo = edt_correo.getText().toString();
        String contrasenia = edt_contrasenia.getText().toString();
        String verificarContrasenia = edt_verificarContrasenia.getText().toString();

        //Verificar que el usuario haya ingresado datos en los campos.
        if(!nombreUsuario.isEmpty() && !correo.isEmpty() && !contrasenia.isEmpty() && !verificarContrasenia.isEmpty()){

            // Comprobar que la contrasenia sea la misma.
            if (contrasenia.equals(verificarContrasenia)){

                //Objeto: Guardar las opciones ingresadas por el usuario.
                ContentValues registro = new ContentValues();

                //Guardar los datos en el objeto "registro".
                registro.put("nombreUsuario", nombreUsuario);
                registro.put("correo", correo);
                registro.put("contrasenia", contrasenia);

                //Modificar los valores dentro de la tabla "usuarios".
                BaseDatos.update("usuarios", registro, "idUsuario=" + id_usuario, null);

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