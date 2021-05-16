package com.example.oxytank_10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class registrarComercio_usuario extends AppCompatActivity {

    EditText edt_nombreUsuario, edt_correo, edt_contrasenia, edt_verificarContrasenia;


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
    public void registrarComercio_comercio(View view){
        //Pasar a la actividad "registrarComercio_comercio".
        Intent Act_registrarComercio_comercio = new Intent(this, registrarComercio_comercio.class);
        startActivity(Act_registrarComercio_comercio);
    }

    // Agrega los datos del usuario en la base de datos.
    public void agregarDatosUsuario(View view){
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
                ContentValues registro = new ContentValues();
                registro.put("nombreUsuario", nombreUsuario);
                registro.put("correo", correo);
                registro.put("contrasenia", contrasenia);

                BaseDatos.insert("usuarios", null, registro);

                BaseDatos.close();

                edt_nombreUsuario.setText("");
                edt_correo.setText("");
                edt_contrasenia.setText("");
                edt_verificarContrasenia.setText("");

                Toast.makeText(this, "Registro exitoso.", Toast.LENGTH_LONG).show();

            }
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