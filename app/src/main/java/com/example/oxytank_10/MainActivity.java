package com.example.oxytank_10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Variables para la relación con el entorno gráfico.
    EditText edt_nombreUsuario, edt_contrasenia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Relación de las variables con el entorno gráfico.
        edt_nombreUsuario = findViewById(R.id.edt_activityMain_nombreUsuario);
        edt_contrasenia = findViewById(R.id.edt_activityMain_contrasenia);

    }

    //Métodos.

    //Ir a la pantalla Seleccionar Tipo de Registro.
    public void seleccionarRegistro(View view){
        //Pasar a la actividad "seleccionarRegistro".
        Intent Act_seleccionarRegistro = new Intent(this, seleccionarRegistro.class);
        startActivity(Act_seleccionarRegistro);
    }

    //Ir a la pantalla principal correspondiente al tipo de cliente que inicio sesión.
    public void pantallaPrincipal(View view){

        iniciarSesion();

        //Pasar a la actividad "PantallaPrincipal".
        //Intent Act_PantallaPrincipal_cliente = new Intent(this, seleccionarRegistro.class);
        //startActivity(Act_PantallaPrincipal_cliente);
    }

    //Realizar la consulta en la base de datos.
    public void iniciarSesion(){

        // Guardar la información ingresada por el usuario.
        String nombreUsuario = edt_nombreUsuario.getText().toString();
        String contrasenia = edt_contrasenia.getText().toString();

        //Verificar que el usuario haya ingresado datos en los campos.
        if(!nombreUsuario.isEmpty() && !contrasenia.isEmpty()){

            //Objeto: Administrar la base de datos.
            DBHelper admin = new DBHelper(this, "Oxytank_db", null, 1);

            // Abrir la base de datos en modo lectura y escritura.
            SQLiteDatabase BaseDatos = admin.getWritableDatabase();

            //Buscar el usuario correspondiente a lo que ingreso el usuario
            Cursor fila_cliente = BaseDatos.rawQuery
                    ("select idUsuario, contrasenia, tipo from usuarios " +
                            "where nombreUsuario =" + nombreUsuario, null);

            //Caso: Se encontro el cliente.
            if(fila_cliente.moveToFirst()){

                //Guardar el nombre de usuario y la contrasenia.
                String idUsuario_db = fila_cliente.getString(0);
                String contrasenia_db = fila_cliente.getString(1);
                String tipo_db = fila_cliente.getString(2);

                /*
                //Verificar que la contraseña ingresada en la base de datos se la misma
                //que la ingresada por el usuario.
                if (contrasenia_db.equals(contrasenia)){

                    Toast.makeText(this, "Contraseña incorrecta.", Toast.LENGTH_LONG).show();

                }
                 */

            }

            //Caso: No se encontro el usuario.
            else {

                Toast.makeText(this, "No existe el usuario.", Toast.LENGTH_LONG).show();

            }

            //Cerrar la Base de datos.
            BaseDatos.close();

        }

        //Solicitar que el usuario llene todos los campos.
        else{
            Toast.makeText(this, "Se deben de llenar ambos campos.", Toast.LENGTH_LONG).show();
        }

    }

}