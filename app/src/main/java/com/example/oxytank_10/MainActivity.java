package com.example.oxytank_10;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Variables para la relación con el entorno gráfico.
    EditText edt_nombreUsuario, edt_contrasenia;

    // Guardar el numero de usuarios que contiene la tabla usuarios.
    int numUsuarios;
    // Guardar el numero de usuarios que contiene la tabla usuarios.
    int numComercios;

    //Guardar los id y los nombres de los usuarios de la tabla usuarios.
    int usuarios_id[] = new int[100];
    String usuarios_nombre[] = new String[100];

    //Guardar el id del comercio y del usuario de la tabla comercios en una lista.
    int comercio_id[] = new int[100];
    int comercio_usuario_id[] = new int[100];

    //Verificar si existe el usuario.
    Boolean existe = false;

    //Guardar la posicion del id correspondiente al usuario.
    int posicionUsuario;
    //Guardar la posicion del id correspondiente al comercio.
    int posicionComercio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Relación de las variables con el entorno gráfico.
        edt_nombreUsuario = findViewById(R.id.edt_activityMain_nombreUsuario);
        edt_contrasenia = findViewById(R.id.edt_activityMain_contrasenia);

    }

    //Métodos.

    //Guardar en una lista los id de comercio y usuario de la tabla comercios.
    public void buscarComercio(){

        int cont = 0; //Se utiliza para recorrer los arreglos y para realizar los ciclos en las funciones.

        //Objeto: Administrar la base de datos.
        DBHelper admin = new DBHelper(this, "Oxytank_db", null, 1);

        /// Abrir la base de datos en modo lectura y escritura.
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        //Conseguir una lista de los nombres y de los id de la tabla usuarios.
        String datosComercio_Consulta = "SELECT idComercio, idUsuario FROM comercios"; // Guardar el texto que corresponde a la consulta.
        Cursor cursor = BaseDatos.rawQuery(datosComercio_Consulta, null); //Realizar la consulta en la base de datos.

        numComercios = cursor.getCount(); // Guardar cuantos usuarios hay en la tabla.

        //Guardar los resultados de la consulta en sus respectivos arreglos.
        while (cursor.moveToNext()){
            comercio_id[cont] = cursor.getInt(0);
            comercio_usuario_id[cont] = cursor.getInt(1);
            cont += 1;
        }

        cont = 0; //Reiniciar el contador.

        cursor.close(); //Cerrar el cursor.

        BaseDatos.close(); //Cerrar la base de datos.

    }

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

        cont = 0; //Reiniciar el contador.

        //Verificar que todos los campos esten llenos.
        if (nombreUsuario_ingresado.isEmpty() || contrasenia_ingresado.isEmpty()){
            if (nombreUsuario_ingresado.isEmpty()){
                edt_nombreUsuario.setError("Faltan Datos.");
            }
            if (contrasenia_ingresado.isEmpty()){
                edt_contrasenia.setError("Faltan Datos.");
            }
        }
        else{
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

                            buscarComercio(); //Guardar los comercios en una lista.

                            // Recorrer el arreglo con los id usuarios para el id del comercio.
                            while (cont < numComercios){
                                //Caso: Si existe el nombre de usuario ingresado por el cliente.
                                if (comercio_usuario_id[cont] == usuarios_id[posicionUsuario]){
                                    posicionComercio = cont; // Guardar su posicion en el arreglo.
                                    cont = numComercios; // Terminar el ciclo.
                                }
                                cont += 1; //Pasar al siguiente ciclo.
                            }

                            //Pasar a la actividad "PantallaPrincipal de la cuenta comercio".
                            Intent Act_PantallaPrincipal_comercio = new Intent(this, cuentaTipoComercio_PantallaPrincipal.class);
                            //Enviar el id del usuario a la pantalla siguiente.
                            String IdComercio_String = Integer.toString(comercio_id[posicionComercio]);
                            Act_PantallaPrincipal_comercio.putExtra("comercio_id", IdComercio_String);
                            //Enviar el id del usuario a la pantalla siguiente.
                            String IdUsuario_String = Integer.toString(comercio_usuario_id[posicionComercio]);
                            Act_PantallaPrincipal_comercio.putExtra("usuario_id", IdUsuario_String);
                            startActivity(Act_PantallaPrincipal_comercio);
                        }

                    }
                    //Caso: Si la contraseña ingresada por el usuario es incorrecta.
                    else {
                        edt_contrasenia.setError("Contraseña incorrecta.");
                    }
                }

            }
            //Caso: Si el nombre de usuario no existe.
            else{
                edt_nombreUsuario.setError("No existe el usuario.");
            }
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