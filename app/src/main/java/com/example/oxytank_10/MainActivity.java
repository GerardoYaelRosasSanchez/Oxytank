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

    // Guardar el numero de usuarios que contiene la tabla usuarios.
    int numUsuarios;

    //Guardar los id y los nombres de los usuarios de la tabla usuarios.
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

    //Iniciar sesión.
    public void iniciarSesion(View view){

        int cont = 0; //Se utiliza para recorrer los arreglos y para realizar los ciclos en las funciones.

        //Se guarda la información ingresada por el usuario.
        String nombreUsuario_ingresado = edt_nombreUsuario.getText().toString();
        String contrasenia_ingresado = edt_contrasenia.getText().toString();

        //Se utilizan para guardar la contraseña y el tipo de cuenta del usuario de la base de datos.
        String contrasenia;
        String tipo;

        //Objeto: Administrar la base de datos.
        DBHelper admin = new DBHelper(this, "Oxytank_db", null, 1);

        /// Abrir la base de datos en modo lectura y escritura.
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        //Conseguir una lista de los nombres y de los id de la tabla usuarios.
        String datosUsuarios_Consulta = "SELECT idUsuario, nombreUsuario FROM usuarios"; // Guardar el texto que corresponde a la consulta.
        Cursor cursor = BaseDatos.rawQuery(datosUsuarios_Consulta, null); //Realizar la consulta en la base de datos.

        numUsuarios = cursor.getCount(); // Guardar cuantos usuarios hay en la tabla.

        //Guardar los resultados de la consulta en sus respectivos arreglos.
        while (cursor.moveToNext()){
            usuarios_id[cont] = cursor.getInt(0);
            usuarios_nombre[cont] = cursor.getString(1);
            cont += 1;
        }

        cont = 0; //Reiniciar el contador.

        cursor.close(); //Cerrar el cursor.

        // Recorrer el arreglo con los nombres de los usuarios para buscar si existe el usuario ingresado por el cliente.
        while (cont < numUsuarios){
            //Caso: Si existe el nombre de usuario ingresado por el cliente.
            if (usuarios_nombre[cont].equals(nombreUsuario_ingresado)){
                existe = true; //Comprobar existencia.
                posicionUsuario = cont; // Guardar su posicion en el arreglo.
                cont = numUsuarios; // Terminar el ciclo.
            }
            cont += 1; //Pasar al siguiente ciclo.
        }

        //Comprobar si existe el usuario.
        if (existe){
            //Conseguir la contraseña y el tipo de cuenta de la BD correspondiente al usuario.
            Cursor fila_usuario = BaseDatos.rawQuery
                    ("select contrasenia, tipo from usuarios " +
                            "where idUsuario =" + usuarios_id[posicionUsuario], null);

            //Si se encontro la información en la base de datos.
            if(fila_usuario.moveToFirst()){

                //Guardar la información.
                contrasenia = fila_usuario.getString(0);
                tipo = fila_usuario.getString(1);

                //Caso: La contraseña ingresada por el usuario coincide con la contraseña en la BD.
                if (contrasenia.equals(contrasenia_ingresado)){

                    //Si es una cuenta de tipo cliente, se pasa a la pantalla principal de cliente.
                    if(tipo.equals("Cliente")){
                        //Pasar a la actividad "PantallaPrincipal cuenta cliente".
                        Intent Act_PantallaPrincipal_cliente = new Intent(this, cuentaTipoCliente_PantallaPrincipal.class);
                        //Enviar el id del usuario a la pantalla siguiente.
                        String IdUsuario_String = Integer.toString(usuarios_id[posicionUsuario]);
                        Act_PantallaPrincipal_cliente.putExtra("usuario_id", IdUsuario_String);
                        startActivity(Act_PantallaPrincipal_cliente);
                    }
                    //Si es una cuenta de tipo comercio
                    else if (tipo.equals("Comercio")){
                        //Pasar a la actividad "PantallaPrincipal de la cuenta comercio".
                        Intent Act_PantallaPrincipal_cliente = new Intent(this, cuentaTipoCliente_PantallaPrincipal.class);
                        startActivity(Act_PantallaPrincipal_cliente);
                    }

                }
                //Caso: Si la contraseña ingresada por el usuario es incorrecta.
                else {
                    Toast.makeText(this, "Contraseña incorrecta.", Toast.LENGTH_LONG).show();
                }
            }
            
        }
        //Caso: Si el nombre de usuario no existe.
        else{
            Toast.makeText(this, "No existe el usuario.", Toast.LENGTH_LONG).show();
        }

        //Cerrar la Base de datos.
        BaseDatos.close();

        //Reiniciar el estado en el caso de que el usuario ingrese mal los datos.
        existe = false;


    }

    //Ir a la pantalla Seleccionar Tipo de Registro.
    public void seleccionarRegistro(View view){
        //Pasar a la actividad "seleccionarRegistro".
        Intent Act_seleccionarRegistro = new Intent(this, seleccionarRegistro.class);
        startActivity(Act_seleccionarRegistro);
    }

}