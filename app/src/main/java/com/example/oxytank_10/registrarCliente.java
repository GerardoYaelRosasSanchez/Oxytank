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

public class registrarCliente extends AppCompatActivity {

    //Relación con el entorno gráfico
    EditText edt_nombreUsuario, edt_correo, edt_contrasenia, edt_verificarContrasenia;

    //Cambio Actividad
    private Boolean cumpleRequisitos = false;

    // Guardar el ID correspondiente al usuario.
    int numUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_cliente);

        // Relación de las variables con la parte gráfica.
        edt_nombreUsuario = findViewById(R.id.edt_registrarCliente_nombreUsuario);
        edt_correo = findViewById(R.id.edt_registrarCliente_correo);
        edt_contrasenia = findViewById(R.id.edt_registrarCliente_contrasenia);
        edt_verificarContrasenia = findViewById(R.id.edt_registrarCliente_verificarContrasenia);

    }

    //Métodos.

    //Ir a la pantalla Registrar Cliente.
    public void registrarCliente_comprobarDatos(View view){

        //Agregar del usuario en la base de datos.
        agregarDatosUsuarioBD();

        //Pasar a la actividad "registrarCliente_comprobarDatos".
        if(cumpleRequisitos){
            //Pasar a la actividad "registrarCliente_comprobarDatos".
            Intent Act_registrarCliente_comprobarDatos = new Intent(this, registrarCliente_comprobarDatos.class);
            String IdUsuario_String = Integer.toString(numUsuarios);
            Act_registrarCliente_comprobarDatos.putExtra("usuario_id", IdUsuario_String);
            startActivity(Act_registrarCliente_comprobarDatos);
        }

    }

    // Agrega los datos del usuario en la base de datos.
    public void agregarDatosUsuarioBD(){

        //Objeto: Administrar la base de datos.
        DBHelper admin = new DBHelper(this, "Oxytank_db", null, 1);

        // Abrir la base de datos en modo lectura y escritura.
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        // Guardar la información ingresada por el usuario.
        String nombreUsuario = edt_nombreUsuario.getText().toString();
        String correo = edt_correo.getText().toString();
        String contrasenia = edt_contrasenia.getText().toString();
        String verificarContrasenia = edt_verificarContrasenia.getText().toString();
        String tipoUsuario = "Cliente";

        //Contar cuantas filas tiene la tabla para crear el id.
        String contarUsuarios_Consulta = "SELECT nombreUsuario FROM usuarios"; // Guardar el texto que corresponde a la consulta.
        Cursor cursor = BaseDatos.rawQuery(contarUsuarios_Consulta, null); //Realizar la consulta en la base de datos.
        numUsuarios = cursor.getCount(); // Guardar el resultado de la consulta en una variable.
        cursor.close();

        // En base al numero de usuarios, crear el ID de usuario.
        numUsuarios += 1;

        //Verificar que el usuario haya ingresado datos en los campos.
        if(!nombreUsuario.isEmpty() && !correo.isEmpty() && !contrasenia.isEmpty() && !verificarContrasenia.isEmpty()){

            // Comprobar que la contrasenia sea la misma.
            if (contrasenia.equals(verificarContrasenia)){

                //Objeto: Guardar las opciones ingresadas por el usuario.
                ContentValues registro = new ContentValues();

                //Guardar los datos en el objeto "registro".
                registro.put("idUsuario", numUsuarios);
                registro.put("nombreUsuario", nombreUsuario);
                registro.put("correo", correo);
                registro.put("contrasenia", contrasenia);
                registro.put("tipo", tipoUsuario);

                //Insertar los valores dentro de la tabla "usuarios".
                BaseDatos.insert("usuarios", null, registro);

                //Cerrar la Base de datos.
                BaseDatos.close();

                /*
                String numU_S = Integer.toString(numUsuarios);

                edt_nombreUsuario.setText(numU_S);
                edt_correo.setText("");
                edt_contrasenia.setText("");
                edt_verificarContrasenia.setText("");
                */

                Toast.makeText(this, "Registro exitoso.", Toast.LENGTH_LONG).show();

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