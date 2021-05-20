package com.example.oxytank_10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class registrarComercio_comercio extends AppCompatActivity {

    //Relación con el entorno gráfico
    EditText edt_nombreComercio, edt_telefono;
    CheckBox ckbx_renta, ckbx_venta, ckbx_refil;

    //Cambio Actividad
    private Boolean cumpleRequisitos = false;

    //Guardar el ID correspondiente al comercio
    int numComercios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_comercio_comercio);

        // Relación de las variables con la parte gráfica.
        edt_nombreComercio = findViewById(R.id.edt_registrarComercio_comercio_nombreComercio);
        edt_telefono = findViewById(R.id.edt_registrarComercio_comercio_telefono);
        ckbx_renta = findViewById(R.id.ckbx_registrarComercio_comercio_renta);
        ckbx_venta = findViewById(R.id.ckbx_registrarComercio_comercio_venta);
        ckbx_refil = findViewById(R.id.ckbx_registrarComercio_comercio_refil);

    }

    //Métodos.

    //Ir a la pantalla Registrar Comercio: Ubicacion.
    public void agregarDatosComercio(View view){

        //Agregar los datos del comercio en la base de datos.
        agregarDatosComercioBD();

        //Pasar a la actividad "registrarComercio_comercio".
        if(cumpleRequisitos){
            Intent Act_registrarComercio_ubicacion = new Intent(this, registrarComercio_ubicacion.class);
            String IdComercio_String = Integer.toString(numComercios);
            Act_registrarComercio_ubicacion.putExtra("comercio_id", IdComercio_String);
            startActivity(Act_registrarComercio_ubicacion);
        }
    }

    // Agrega los datos del comercio en la base de datos.
    public void agregarDatosComercioBD(){

        //Objeto: Administrar la base de datos.
        DBHelper admin = new DBHelper(this, "Oxytank_db", null, 1);

        // Abrir la base de datos en modo lectura y escritura.
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        // Guardar la información ingresada por el usuario.
        String nombreComercio = edt_nombreComercio.getText().toString();
        String telefono = edt_telefono.getText().toString();
        //Recibir el ID ingresado por el usuario de "registrarComercio_usuario"
        String usuario_id = getIntent().getStringExtra("usuario_id");
        int id_Usuario = Integer.parseInt(usuario_id);

        //Contar cuantas filas tiene la tabla para crear el id.
        String contarComercios_Consulta = "SELECT nombreComercio FROM comercios"; // Guardar el texto que corresponde a la consulta.
        Cursor cursor = BaseDatos.rawQuery(contarComercios_Consulta, null); //Realizar la consulta en la base de datos.
        numComercios = cursor.getCount(); // Guardar el resultado de la consulta en una varianle-
        cursor.close();

        // En base al numero de comercios, crear el ID de comercio.
        numComercios += 1;

        //Verificar que el usuario haya ingresado datos en los campos.
        if(!nombreComercio.isEmpty() && !telefono.isEmpty()){

            // Comprobar que alguna de las checkbox sea seleccionada.
            if (ckbx_refil.isChecked() || ckbx_venta.isChecked() || ckbx_renta.isChecked()){

                //Objeto: Guardar las opciones ingresadas por el usuario.
                ContentValues registro = new ContentValues();

                //Guardar los datos en el objeto "registro".
                registro.put("idComercio", numComercios);
                registro.put("nombreComercio", nombreComercio);
                registro.put("telefono", telefono);
                registro.put("idUsuario", id_Usuario);


                //Revisar que cuales cajas selecciono el usuario.
                //Revisar si el usuario renta tanques de oxigeno.
                if (ckbx_renta.isChecked()){
                    String renta = "Disponible";
                    registro.put("renta", renta);
                }
                //Si el usuario no renta tanques de oxigeno.
                else{
                    String renta = "Sin servicio";
                    registro.put("renta", renta);
                }
                //Revisar si el usuario vende tanques de oxigeno.
                if (ckbx_venta.isChecked()){
                    String venta = "Disponible";
                    registro.put("venta", venta);
                }
                //Si el usuario no vende tanques de oxigeno.
                else{
                    String venta = "Sin servicio";
                    registro.put("venta", venta);
                }
                //Revisar si el usuario tiene refil de tanques de oxigeno.
                if (ckbx_refil.isChecked()){
                    String refil = "Disponible";
                    registro.put("refil", refil);
                }
                //Si el usuario no tiene el servicio de refil de tanques de oxigeno.
                else{
                    String refil = "Sin servicio";
                    registro.put("refil", refil);
                }

                //Insertar los valores dentro de la tabla "comercios".
                BaseDatos.insert("comercios", null, registro);

                //Cerrar la Base de datos.
                BaseDatos.close();

                /*
                edt_nombreUsuario.setText("");
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
                Toast.makeText(this, "Debes llenar seleccionar al menos un servicio.", Toast.LENGTH_LONG).show();
            }
        }
        //Solicitar al usuario que llene todos los campos.
        else{
            Toast.makeText(this, "Debes de llenar todos los campos", Toast.LENGTH_LONG).show();
        }

    }
}