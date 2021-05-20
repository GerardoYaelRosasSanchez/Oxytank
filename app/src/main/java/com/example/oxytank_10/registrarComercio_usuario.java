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

public class registrarComercio_usuario extends AppCompatActivity {

    //Relación con el entorno gráfico
    EditText edt_nombreUsuario, edt_correo, edt_contrasenia, edt_verificarContrasenia;

    //Cambio Actividad
    private Boolean cumpleRequisitos = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_comercio_usuario);

        // Relación de las variables con la parte gráfica.
        edt_nombreUsuario = findViewById(R.id.edt_registrarComercio_usuario_nombreUsuario);
        edt_correo = findViewById(R.id.edt_registrarComercio_usuario_correo);
        edt_contrasenia = findViewById(R.id.edt_registrarComercio_usuario_contrasenia);
        edt_verificarContrasenia = findViewById(R.id.edt_registrarComercio_usuario_verificarContrasenia);
    }

    //Métodos.

    //Ir a la pantalla Registrar Comercio: Datos Comercio.
    public void agregarDatosUsuario(View view){

        //Agregar del usuario en la base de datos.
        agregarDatosUsuarioBD();

        //Pasar a la actividad "registrarComercio_comercio".
        if(cumpleRequisitos){
            Intent Act_registrarComercio_comercio = new Intent(this, registrarComercio_comercio.class);
            Act_registrarComercio_comercio.putExtra("usuario_id", edt_nombreUsuario.getText().toString());
            startActivity(Act_registrarComercio_comercio);
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

        // Crear el id del usuario.

            //Contar cuantas filas tiene la tabla para crear el id.
        String contarUsuarios_Consulta = "SELECT nombreUsuario FROM usuarios"; // Guardar el texto que corresponde a la consulta.
        Cursor cursor = BaseDatos.rawQuery(contarUsuarios_Consulta, null); //Realizar la consulta en la base de datos.
        int numUsuarios = cursor.getCount(); // Guardar el resultado de la consulta en una varianle-
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