package com.example.oxytank_10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Variables para la relación con el entorno gráfico.
    EditText edt_nombreUsuario, edt_contrasenia;

    // Guardar el ID correspondiente al usuario.
    int numUsuarios;

    //Arreglo "Usuarios_id"
    int usuarios_id[] = new int[100];
    String usuarios_nombre[] = new String[100];

    //Verificar si existe el usuario.
    Boolean existe = false;

    //Guardar la posicion del id correspondiente al usuario.
    int posicionUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Relación de las variables con el entorno gráfico.
        edt_nombreUsuario = findViewById(R.id.edt_activityMain_nombreUsuario);
        edt_contrasenia = findViewById(R.id.edt_activityMain_contrasenia);

    }

    //Métodos.

    //Consultar todos los usuarios de la base de datos y guardarlos en el ArrayAdapter.
    public void consultarUsuarios(View view){

        int cont = 0;

        String nombreUsuario_ingresado = edt_nombreUsuario.getText().toString();
        String contrasenia_ingresado = edt_contrasenia.getText().toString();
        
        String contrasenia;
        String tipo;

        //Objeto: Administrar la base de datos.
        DBHelper admin = new DBHelper(this, "Oxytank_db", null, 1);

        /// Abrir la base de datos en modo lectura y escritura.
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        //Conseguir una lista de los datos de la tabla usuarios.
        String datosUsuarios_Consulta = "SELECT idUsuario, nombreUsuario FROM usuarios"; // Guardar el texto que corresponde a la consulta.
        Cursor cursor = BaseDatos.rawQuery(datosUsuarios_Consulta, null); //Realizar la consulta en la base de datos.
        numUsuarios = cursor.getCount();

        while (cursor.moveToNext()){
            usuarios_id[cont] = cursor.getInt(0);
            usuarios_nombre[cont] = cursor.getString(1);
            cont += 1;
        }

        cont = 0;

        cursor.close();



        while (cont < numUsuarios){
            String usuario = usuarios_nombre[cont];
            if (usuario.equals(nombreUsuario_ingresado)){
                existe = true;
                posicionUsuario = cont;
                cont = numUsuarios;
            }
            cont += 1;
        }

        if (existe){

            Cursor fila_usuario = BaseDatos.rawQuery
                    ("select contrasenia, tipo from usuarios " +
                            "where idUsuario =" + usuarios_id[posicionUsuario], null);

            //Caso: Se encontro el usuario.
            if(fila_usuario.moveToFirst()){
                //Mostrar datos del usuario en la pantalla.
                contrasenia = fila_usuario.getString(0);
                tipo = fila_usuario.getString(1);

                if (contrasenia.equals(contrasenia_ingresado)){

                    if(tipo.equals("Cliente")){
                        //Pasar a la actividad "PantallaPrincipal cuenta cliente".
                        Intent Act_PantallaPrincipal_cliente = new Intent(this, cuentaTipoCliente_PantallaPrincipal.class);
                        String IdUsuario_String = Integer.toString(usuarios_id[posicionUsuario]);
                        Act_PantallaPrincipal_cliente.putExtra("usuario_id", IdUsuario_String);
                        startActivity(Act_PantallaPrincipal_cliente);
                    }
                    else if (tipo.equals("Comercio")){
                        //Pasar a la actividad "PantallaPrincipal de la cuenta comercio".
                        Intent Act_PantallaPrincipal_cliente = new Intent(this, cuentaTipoCliente_PantallaPrincipal.class);
                        startActivity(Act_PantallaPrincipal_cliente);
                    }

                }
                else {
                    Toast.makeText(this, "Contraseña incorrecta.", Toast.LENGTH_LONG).show();
                }
            }
            
        }
        else{
            Toast.makeText(this, "No existe el usuario.", Toast.LENGTH_LONG).show();
        }

        //Cerrar la Base de datos.
        BaseDatos.close();
        
        existe = false;


    }

    //Ir a la pantalla Seleccionar Tipo de Registro.
    public void seleccionarRegistro(View view){
        //Pasar a la actividad "seleccionarRegistro".
        Intent Act_seleccionarRegistro = new Intent(this, seleccionarRegistro.class);
        startActivity(Act_seleccionarRegistro);
    }

    //Ir a la pantalla principal correspondiente al tipo de cliente que inicio sesión.
    public void pantallaPrincipal(View view){

        //iniciarSesion();

        //Pasar a la actividad "PantallaPrincipal".
        //Intent Act_PantallaPrincipal_cliente = new Intent(this, seleccionarRegistro.class);
        //startActivity(Act_PantallaPrincipal_cliente);
    }


    /*
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


                //Verificar que la contraseña ingresada en la base de datos se la misma
                //que la ingresada por el usuario.
                if (contrasenia_db.equals(contrasenia)){

                    Toast.makeText(this, "Contraseña incorrecta.", Toast.LENGTH_LONG).show();

                }


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
    */

}